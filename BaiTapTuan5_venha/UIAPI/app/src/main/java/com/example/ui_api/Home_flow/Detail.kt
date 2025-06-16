// Thay thế toàn bộ nội dung của Detail.kt
package com.example.ui_api.Home_flow

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui_api.Home_flow.modol.TaskDetailUiState
import com.example.ui_api.Home_flow.modol.TaskDetailViewModel
import com.example.ui_api.R
import androidx.compose.foundation.lazy.items
// Định nghĩa màu sắc
val DetailBlue = Color(0xFF0A84FF)
val DeleteOrange = Color(0xFFFE9500)
val InfoCardPink = Color(0xFFEBCAD1)
val ItemGray = Color(0xFFD1D1D6)
val TextGray = Color(0xFF8E8E93)
val BackgroundColor = Color.White // Hoặc màu nền bạn muốn

@Composable
fun TaskDetailScreen(
    taskId: Int,
    onNavigateBack: () -> Unit,
    viewModel: TaskDetailViewModel = viewModel()
) {
    // Tải dữ liệu khi màn hình được tạo lần đầu
    LaunchedEffect(key1 = taskId) {
        viewModel.loadTaskDetails(taskId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { DetailTopAppBar(onBackClick = onNavigateBack, onDeleteClick = {}) },
        containerColor = BackgroundColor
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = uiState) {
                is TaskDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is TaskDetailUiState.Success -> {
                    TaskDetailContent(task = state.task)
                }
                is TaskDetailUiState.Error -> {
                    Text(text = state.message, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun DetailTopAppBar(onBackClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.background(DetailBlue, shape = RoundedCornerShape(12.dp))
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
        }
        Text("Detail", color = DetailBlue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.background(DeleteOrange, shape = RoundedCornerShape(12.dp))
        ) {
            Icon(Icons.Default.Delete, "Delete Task", tint = Color.White)
        }
    }
}

@Composable
fun TaskDetailContent(task: Task, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(task.title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(Modifier.height(8.dp))
            Text(task.description, fontSize = 16.sp, color = TextGray)
            Spacer(Modifier.height(16.dp))
            InfoCard(task = task) // Truyền task vào
        }

        item {
            Text("Subtasks", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = TextGray)
        }
        items(task.subtasks) { subtask ->
            SubtaskItem(subtask = subtask) // Truyền cả đối tượng subtask
        }

        item {
            Text("Attachments", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = TextGray)
        }
        items(task.attachments) { attachment ->
            AttachmentItem(attachment = attachment)
        }
    }
}

@Composable
fun InfoCard(task: Task) {
    Surface(shape = RoundedCornerShape(20.dp), color = InfoCardPink, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lấy dữ liệu từ task thật
            InfoItem(iconRes = R.drawable.category, label = "Category", value = task.category)
            InfoItem(iconRes = R.drawable.image, label = "Status", value = task.status)
            InfoItem(iconRes = R.drawable.imagep, label = "Priority", value = task.priority)
        }
    }
}

@Composable
fun InfoItem(@DrawableRes iconRes: Int, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Image(painterResource(id = iconRes), label, modifier = Modifier.size(24.dp))
        Text(label, color = Color.Black.copy(alpha = 0.7f), fontSize = 14.sp)
        Text(value, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SubtaskItem(subtask: Subtask) {
    var isChecked by remember { mutableStateOf(subtask.isCompleted) }
    Surface(shape = RoundedCornerShape(16.dp), color = ItemGray, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.Black.copy(alpha = 0.8f),
                    checkedColor = DetailBlue
                )
            )
            Text(subtask.title, color = Color.Black, fontSize = 16.sp, modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
fun AttachmentItem(attachment: Attachment) {
    Surface(shape = RoundedCornerShape(16.dp), color = ItemGray, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painterResource(id = R.drawable.imageghim), "Attachment", modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(12.dp))
            Text(attachment.fileName, color = Color.Black, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailScreenPreview() {
    val fakeTask = Task(
        id = 1,
        title = "Preview: Complete Android Project",
        description = "This is a preview description for the task.",
        status = "In Progress",
        priority = "High",
        category = "Work",
        dueDate = "", createdAt = "", updatedAt = "",
        subtasks = listOf(Subtask(1, "Preview Subtask", false)),
        attachments = listOf(Attachment(1, "preview_document.pdf", ""))
    )
    Scaffold(
        topBar = { DetailTopAppBar({}, {}) },
        containerColor = BackgroundColor
    ) { padding ->
        TaskDetailContent(task = fakeTask, modifier = Modifier.padding(padding))
    }
}