package com.example.ui_api.Home_flow.modol

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui_api.Home_flow.Task
import com.example.ui_api.data.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    // 1. Tạo một MutableStateFlow private để chỉ có ViewModel mới có thể thay đổi
    //    QUAN TRỌNG: Khởi tạo nó với một danh sách rỗng (emptyList())
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    // 2. Cung cấp một StateFlow public, chỉ đọc (read-only) cho UI
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        // Tải dữ liệu ban đầu hoặc dữ liệu giả để kiểm tra
        //loadInitialTasks()
        fetchTasks()

    }

//    private fun loadInitialTasks() {
//        // Thay thế phần này bằng logic lấy dữ liệu thật của bạn (từ database, API, ...)
//        _tasks.value = listOf(
//            Task(id = 1, title = "Làm bài tập tuần 5", description = "Làm bài tập lập trình di động", dueDate = "2023-11-20", status = "pending"),
//            Task(id = 2, title = "Dọn dẹp phòng", description = "Quét nhà, lau nhà", dueDate = "2023-11-18", status = "completed"),
//            Task(id = 3, title = "Đi siêu thị", description = "Mua rau củ và thịt", dueDate = "2023-11-19", status = "pending")
//        )
//    }

    private fun fetchTasks() {
        viewModelScope.launch {
            try {
                Log.d("API_CALL", "Bắt đầu gọi API mới...")
                val response = RetrofitInstance.api.getTasks()

                // QUAN TRỌNG: Kiểm tra xem API có trả về thành công không
                if (response.isSuccess) {
                    // Nếu thành công, lấy dữ liệu từ response.data
                    _tasks.value = response.data
                    Log.d("API_CALL", "API thành công: ${response.message}. Tải được ${response.data.size} tasks.")
                } else {
                    // Nếu thất bại, log message lỗi từ server
                    Log.e("API_CALL", "API báo lỗi: ${response.message}")
                    _tasks.value = emptyList() // Có thể set list rỗng
                }

            } catch (e: Exception) {
                // Xử lý lỗi mạng hoặc lỗi parse JSON
                Log.e("API_CALL", "Lỗi nghiêm trọng khi gọi API!", e)
                _tasks.value = emptyList()
            }
        }
    }
//    fun deleteTask(taskId: Int) {
//        viewModelScope.launch {
//            _tasks.update { currentTasks ->
//                currentTasks.filterNot { it.id == taskId }
//            }
//        }
//    }
// === THÊM MỚI: StateFlow để giữ một task đang được chọn/xem chi tiết ===
private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask.asStateFlow()

    // === THÊM MỚI: StateFlow để thông báo cho UI biết khi có lỗi xảy ra ===
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getTaskById(taskId: Int) {
        viewModelScope.launch {
            _selectedTask.value = null // Xóa task cũ trước khi lấy task mới
            try {
                Log.d("API_CALL", "Đang lấy task với ID: $taskId")
                // Gọi API để lấy task theo ID
                val task = RetrofitInstance.api.getTaskById(taskId)
                _selectedTask.value = task // Cập nhật StateFlow
                Log.d("API_CALL", "Lấy task ID $taskId thành công: ${task.title}")
            } catch (e: Exception) {
                Log.e("API_CALL", "Lỗi khi lấy task ID $taskId!", e)
                _error.value = "Không tìm thấy công việc này."
            }
        }
    }
    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            try {
                Log.d("API_CALL", "Đang yêu cầu xóa task ID: $taskId")
                // Gọi API DELETE đến server
                val response = RetrofitInstance.api.deleteTask(taskId)

                if (response.isSuccessful) {
                    Log.d("API_CALL", "Xóa task ID $taskId trên server thành công.")
                    // Nếu xóa thành công, cập nhật lại danh sách trên UI bằng cách
                    // loại bỏ task vừa xóa khỏi danh sách hiện tại.
                    val currentTasks = _tasks.value
                    _tasks.value = currentTasks.filterNot { it.id == taskId }
                } else {
                    // Xử lý trường hợp server từ chối xóa (ví dụ: không có quyền, task không tồn tại)
                    Log.e("API_CALL", "Server từ chối xóa task ID $taskId. Code: ${response.code()}")
                    _error.value = "Xóa công việc thất bại."
                }
            } catch (e: Exception) {
                // Xử lý lỗi kết nối mạng
                Log.e("API_CALL", "Lỗi kết nối khi xóa task ID $taskId!", e)
                _error.value = "Không thể kết nối để xóa công việc."
            }
        }
    }
     fun clearError() {
        _error.value = null
    }
}