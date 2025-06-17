package com.example.xinquyen.Model

import kotlinx.serialization.Serializable

@Serializable // Annotation này rất quan trọng để chuyển đổi sang JSON
data class CheckInRecord(
    val locationInfo: String, // Lưu lại thông tin người dùng nhập (URL hoặc tọa độ)
    val timestamp: Long      // Thời gian check-in
)