package com.example.ui_api.ui.theme

// trong file: utils/DateUtils.kt

import java.text.SimpleDateFormat
import java.util.Locale

// HÀM MỚI: Thêm hàm này vào
fun formatApiDateTimeToCustom(dateTimeString: String?): String {
    if (dateTimeString.isNullOrEmpty()) {
        return "N/A"
    }
    return try {
        // Định dạng của chuỗi ngày giờ từ API
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        // Định dạng mong muốn như trong ảnh
        val formatter = SimpleDateFormat("HH:mm yyyy-MM-dd", Locale.getDefault())
        parser.parse(dateTimeString)?.let { formatter.format(it) } ?: "Invalid Date"
    } catch (e: Exception) {
        "Invalid Date"
    }
}