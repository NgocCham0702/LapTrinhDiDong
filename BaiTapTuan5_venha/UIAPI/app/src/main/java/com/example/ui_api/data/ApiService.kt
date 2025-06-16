package com.example.ui_api.data
import com.example.ui_api.Home_flow.Task
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("tasks")
    suspend fun getTasks(): ApiResponse<List<Task>> // Dùng suspend vì đây là hoạt động bất đồng bộ

    @GET("task/{tasksId}")
    suspend fun getTaskById(@Path("tasksId") id: Int): Task // Or ApiResponse<Task>
    @DELETE("task/{tasksId}")
    suspend fun deleteTask(@Path("tasksId") id: Int): Response<Void> // Chú thích @Path cho tham số taskId
}
