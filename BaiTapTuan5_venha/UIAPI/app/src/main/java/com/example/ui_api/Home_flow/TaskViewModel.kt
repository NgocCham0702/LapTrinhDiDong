import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui_api.Home_flow.Task
import com.example.ui_api.data.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
fun deleteTask(taskId: Int) {
    // Logic xóa task vẫn như cũ, nó chỉ thao tác trên danh sách hiện tại
    viewModelScope.launch {
        _tasks.update { currentTasks ->
            currentTasks.filterNot { it.id == taskId }
        }
    }
    // Lưu ý: Việc xóa này chỉ diễn ra ở phía client.
    // Để xóa vĩnh viễn, bạn cần gọi một API DELETE đến server.
}
    // Có thể thêm các hàm khác như addTask, updateTask ở đây
}
