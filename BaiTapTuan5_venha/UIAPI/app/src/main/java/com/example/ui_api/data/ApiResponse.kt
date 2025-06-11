package com.example.ui_api.data

import com.google.gson.annotations.SerializedName


data class ApiResponse<T>(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: T // T là kiểu dữ liệu chung, trong trường hợp này nó sẽ là List<Task>
)
