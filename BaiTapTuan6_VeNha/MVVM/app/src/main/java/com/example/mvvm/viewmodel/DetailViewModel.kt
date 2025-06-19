package com.example.mvvm.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mvvm.data.model.ProjectDetail
import com.example.mvvm.data.repository.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


// (VM) - Chứa logic và trạng thái cho DetailScreen
class DetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val repository = ProjectRepository()

    private val _projectDetail = MutableStateFlow<ProjectDetail?>(null)
    val projectDetail = _projectDetail.asStateFlow()

    // Lấy projectId từ argument của navigation
    private val projectId: Int = checkNotNull(savedStateHandle["projectId"])

    init {
        loadProjectDetail(projectId)
    }

    private fun loadProjectDetail(id: Int) {
        _projectDetail.value = repository.getProjectDetailById(id)
    }
}
