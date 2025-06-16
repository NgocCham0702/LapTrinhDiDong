//package com.example.ui_api.Home_flow
//
//import android.util.Log
//import com.example.ui_api.data.ApiService
//import com.example.ui_api.Home_flow.Task // <-- Sửa import này
//import com.example.ui_api.data.ApiResponse // Đảm bảo import ApiResponse
//
//class TaskRepository(private val apiService: ApiService) {
//    suspend fun getTasks(): List<Task> {
//        val response: ApiResponse<List<Task>> = apiService.getTasks() // Kiểu của response là ApiResponse<List<Task>>
//
//        // Giả sử ApiResponse có thuộc tính 'isSuccess' (Boolean) để kiểm tra thành công
//        // và thuộc tính 'data' (List<Task>?) để lấy dữ liệu.
//        // Bạn cần điều chỉnh cho phù hợp với cấu trúc thực tế của lớp ApiResponse.
//
//        if (response.isSuccess) {
//            // Nếu thành công, trả về 'data'.
//            // Nếu 'data' có thể null ngay cả khi isSuccess là true,
//            // bạn nên cung cấp giá trị mặc định (ví dụ: danh sách rỗng).
//            return response.data ?: emptyList()
//        } else {
//            // Xử lý trường hợp lỗi, ví dụ:
//            // 1. Ném ra một Exception để ViewModel có thể bắt và xử lý
//            // throw Exception("Lỗi API: ${response.errorMessage}") // Giả sử có errorMessage
//
//            // 2. Hoặc trả về một danh sách rỗng (tùy theo logic ứng dụng của bạn)
//            println("Lỗi khi gọi API: ${response.message}") // In ra lỗi để debug
//            return emptyList()
//        }
//    }
//
//    //lay id=1
//    suspend fun getTaskById(id: Int): Task? {
//        return try {
//            // Gọi hàm trong ApiService
//            apiService.getTaskById(id)
//        } catch (e: Exception) {
//            println("TaskRepository - Error getting task by ID: $id - ${e.message}")
//            null // Trả về null nếu có lỗi
//        }
//    }
//    /**
//     * Xóa một task trên server và trả về true nếu thành công.
//     */
//    suspend fun deleteTask(id: Int): Boolean {
//        return try {
//            // Gọi hàm trong ApiService
//            val response = apiService.deleteTask(id)
//            // Kiểm tra xem request có thành công không (mã HTTP 2xx)
//            response.isSuccessful
//        } catch (e: Exception) {
//            Log.e("TaskRepository", "Error deleting task: $id", e)
//            false // Trả về false nếu có lỗi
//        }
//    }
//}