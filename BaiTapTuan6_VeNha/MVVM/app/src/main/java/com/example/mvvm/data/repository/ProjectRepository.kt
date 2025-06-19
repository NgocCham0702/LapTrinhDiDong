package com.example.mvvm.data.repository

import com.example.mvvm.data.model.Project
import com.example.mvvm.data.model.ProjectDetail
import com.example.mvvm.ui.theme.DarkBlue
import com.example.mvvm.ui.theme.LightGreen
import com.example.mvvm.ui.theme.LightPink


// (M) - Lớp kho chứa, chịu trách nhiệm cung cấp dữ liệu
class ProjectRepository {

    private val projects = listOf(
        Project(1, "Complete Android Project", "Finish the UI, integrate API, and write documentation", DarkBlue),
        Project(2, "Complete iOS Project", "Finish the UI, integrate API, and write documentation", LightPink),
        Project(3, "Web Backend Task", "Finish the UI, integrate API, and write documentation", LightGreen),
        Project(4, "Design Meeting", "Finish the UI, integrate API, and write documentation", LightPink),
        Project(5, "Final Review", "Finish the UI, integrate API, and write documentation", DarkBlue, isFaded = true)
    )

    fun getProjects(): List<Project> {
        // Trong thực tế, bạn sẽ gọi API hoặc truy vấn DB ở đây
        return projects
    }

    fun getProjectDetailById(id: Int): ProjectDetail? {
        val project = projects.find { it.id == id }
        return project?.let {
            ProjectDetail(
                project = it,
                subtasks = listOf(
                    "This task is related to Fitness. It needs to be completed",
                    "This task is related to Fitness. It needs to be completed",
                    "This task is related to Fitness. It needs to be completed"
                ),
                attachments = listOf(
                    "document_1_0.pdf",
                    "document_1_0.pdf"
                )
            )
        }
    }
}