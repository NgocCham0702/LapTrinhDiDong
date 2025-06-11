package com.example.ui_api.data
//
//import com.example.ui_api.Home_flow.ApiResponse
//import com.example.ui_api.Home_flow.Task
//import retrofit2.Response
//import retrofit2.http.DELETE
//import retrofit2.http.GET
//import retrofit2.http.Path
//
//interface ApiService {
//    @GET("tasks")
//    suspend fun getTasks(): ApiResponse
//
//    @GET("task/{id}")
//    suspend fun getTaskById(@Path("id") id: Int): Task
//
//    @DELETE("task/{id}")
//    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
//}

import com.example.ui_api.Home_flow.Task
import retrofit2.http.GET

// data class này phải khớp với cấu trúc JSON từ API
//data class Task(
//    val id: Int,
//    val title: String,
//    val description: String,
//    val dueDate: String
//)

interface ApiService {
    // @GET chỉ định phương thức HTTP GET.
    // "tasks" là phần cuối của URL (https://amock.io/api/researchUTH/tasks)
    @GET("tasks")
    suspend fun getTasks(): ApiResponse<List<Task>> // Dùng suspend vì đây là hoạt động bất đồng bộ
}