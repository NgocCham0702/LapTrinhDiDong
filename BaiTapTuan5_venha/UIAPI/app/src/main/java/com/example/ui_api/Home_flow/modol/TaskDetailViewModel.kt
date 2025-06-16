package com.example.ui_api.Home_flow.modol

// Tạo một tệp mới, ví dụ: TaskDetailViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui_api.Home_flow.Task
import com.example.ui_api.Home_flow.Subtask
import com.example.ui_api.Home_flow.Attachment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Trạng thái của UI
sealed class TaskDetailUiState {
    object Loading : TaskDetailUiState()
    data class Success(val task: Task) : TaskDetailUiState()
    data class Error(val message: String) : TaskDetailUiState()
}

class TaskDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<TaskDetailUiState>(TaskDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadTaskDetails(taskId: Int) {
        viewModelScope.launch {
            _uiState.value = TaskDetailUiState.Loading
            try {
                // Trong thực tế, bạn sẽ gọi API ở đây
                // Bây giờ, chúng ta sẽ giả lập dữ liệu trả về
                val task = fetchTaskFromApi(taskId)
                _uiState.value = TaskDetailUiState.Success(task)
            } catch (e: Exception) {
                _uiState.value = TaskDetailUiState.Error("Không thể tải chi tiết công việc.")
            }
        }
    }

    // Hàm giả lập API
    private suspend fun fetchTaskFromApi(taskId: Int): Task {
        kotlinx.coroutines.delay(500) // Giả lập độ trễ mạng
        return Task(
            id = taskId,
            title = "Complete Android Project",
            description = "Finish the UI, integrate API, and write documentation. This is a very detailed description for the task that needs to be done.",
            status = "In Progress",
            priority = "High",
            category = "Work",
            dueDate = "2024-12-31",
            createdAt = "",
            updatedAt = "",
            subtasks = listOf(
                Subtask(1, "Design the UI in Figma", true),
                Subtask(2, "Develop the Composable screens", false),
                Subtask(3, "Integrate the API calls", false)
            ),
            attachments = listOf(
                Attachment(1, "design_mockup_v1.pdf", ""),
                Attachment(2, "api_documentation.docx", "")
            )
        )
    }
}