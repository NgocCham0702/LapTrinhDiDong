package com.example.xinquyen.Model

import android.location.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class LocationParser {

    // Biểu thức chính quy để tìm tọa độ trong URL đầy đủ
    private val latLonRegex = Regex("""@(-?\d+\.\d+),(-?\d+\.\d+)""")

    /**
     * Phân tích input của người dùng, có khả năng xử lý link rút gọn.
     * Đây là một suspend function vì nó thực hiện tác vụ mạng.
     */
    suspend fun parse(input: String): Location? {
        val trimmedInput = input.trim()

        // 1. Kiểm tra xem có phải là URL không
        if (trimmedInput.startsWith("http://") || trimmedInput.startsWith("https://")) {
            // Theo dõi link để lấy URL đầy đủ
            val fullUrl = resolveRedirects(trimmedInput)
            // Phân tích URL đầy đủ
            return parseUrl(fullUrl)
        } else {
            // 2. Nếu không phải URL, thử phân tích như tọa độ bình thường
            return parseCoordinates(trimmedInput)
        }
    }

    /**
     * Hàm này thực hiện kết nối mạng để lấy URL cuối cùng sau khi chuyển hướng.
     */
    private suspend fun resolveRedirects(urlString: String): String = withContext(Dispatchers.IO) {
        try {
            var currentUrl = urlString
            var connection: HttpURLConnection? = null
            var redirects = 0
            // Giới hạn 5 lần chuyển hướng để tránh lặp vô tận
            while (redirects < 5) {
                val url = URL(currentUrl)
                connection = url.openConnection() as HttpURLConnection
                // Rất quan trọng: Tắt tự động theo dõi chuyển hướng để chúng ta có thể kiểm soát nó
                connection.instanceFollowRedirects = false
                connection.connect()

                val responseCode = connection.responseCode
                // Mã 3xx là mã chuyển hướng (301, 302, 307...)
                if (responseCode in 300..399) {
                    // Lấy URL mới từ header "Location"
                    val newUrl = connection.getHeaderField("Location")
                    if (newUrl != null) {
                        currentUrl = newUrl
                        redirects++
                    } else {
                        // Không có header location, dừng lại
                        break
                    }
                } else {
                    // Không phải chuyển hướng, đây là URL cuối cùng
                    break
                }
            }
            connection?.disconnect()
            return@withContext currentUrl
        } catch (e: Exception) {
            e.printStackTrace()
            // Nếu có lỗi mạng, trả về URL gốc
            return@withContext urlString
        }
    }

    private fun parseUrl(url: String): Location? {
        val matchResult = latLonRegex.find(url)
        if (matchResult != null) {
            val (latStr, lonStr) = matchResult.destructured
            val lat = latStr.toDoubleOrNull()
            val lon = lonStr.toDoubleOrNull()
            if (lat != null && lon != null) {
                return Location("UrlParser").apply {
                    latitude = lat
                    longitude = lon
                }
            }
        }
        return null
    }

    private fun parseCoordinates(coords: String): Location? {
        val parts = coords.split(",").map { it.trim() }
        if (parts.size == 2) {
            val lat = parts[0].toDoubleOrNull()
            val lon = parts[1].toDoubleOrNull()
            if (lat != null && lon != null) {
                return Location("CoordParser").apply {
                    latitude = lat
                    longitude = lon
                }
            }
        }
        return null
    }
}