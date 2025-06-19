package com.example.mvvm.viewmodel

//ViewModel chứa logic nghiệp vụ và trạng thái (state) cho UI.
import androidx.lifecycle.ViewModel
import com.example.mvvm.data.model.Project
import com.example.mvvm.data.repository.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// (VM) - Chứa logic và trạng thái cho ListScreen
class ListViewModel : ViewModel() {
    private val repository = ProjectRepository()

    // StateFlow để giữ danh sách các dự án
    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects = _projects.asStateFlow()

    init {
        loadProjects()
    }

    private fun loadProjects() {
        // Lấy dữ liệu từ repository và cập nhật state
        _projects.value = repository.getProjects()
    }
}
