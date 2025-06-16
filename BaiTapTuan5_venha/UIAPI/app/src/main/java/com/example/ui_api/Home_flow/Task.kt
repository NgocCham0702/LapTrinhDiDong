package com.example.ui_api.Home_flow

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: List<Task>
)

data class Task(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("priority")
    val priority: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("dueDate")
    val dueDate: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    // --- THÊM CÁC THUỘC TÍNH NÀY ---
    // API có thể trả về hoặc không, nên ta cho giá trị mặc định là list rỗng
    @SerializedName("subtasks")
    val subtasks: List<Subtask> = emptyList(),

    @SerializedName("attachments")
    val attachments: List<Attachment> = emptyList()
)

data class Subtask(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("isCompleted")
    val isCompleted: Boolean
)

data class Attachment(
    @SerializedName("id")
    val id: Int,

    @SerializedName("fileName")
    val fileName: String,

    @SerializedName("fileUrl")
    val fileUrl: String
)

data class Reminder(
    @SerializedName("id")
    val id: Int,

    @SerializedName("time")
    val time: String,

    @SerializedName("type")
    val type: String
)

