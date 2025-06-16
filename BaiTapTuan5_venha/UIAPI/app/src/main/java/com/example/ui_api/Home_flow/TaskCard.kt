package com.example.ui_api.Home_flow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TaskCard(task: Task, backgroundColor: Color, onDelete: () -> Unit,
             onClick: () -> Unit // Tham số này đã có sẵn và đúng
) {
    var checked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick), // Sử dụng tham số onClick ở đây

        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                Checkbox(checked = checked, onCheckedChange = { checked = it })
                Spacer(Modifier.width(8.dp))
                Text(text = task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Text(text = task.description, fontSize = 14.sp)

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // <-- Thêm dòng này
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Status: ${task.status}", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold )
                Spacer(Modifier.width(8.dp))
                Text(text = "${task.dueDate}", fontSize = 12.sp, color = Color.Black)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

// Preview function
@Preview(showBackground = true, name = "TaskCard Preview")
@Composable
fun TaskCardPreview() {
    // You might want to wrap it in your app's theme for accurate preview
    // YourAppTheme { // Replace YourAppTheme with your actual theme if you have one
    TaskCard(
        task = Task( // Sample task data
            title = "Hoàn thành báo cáo",
            description = "Viết phần kết luận và tóm tắt cho báo cáo dự án.",
            dueDate = "25/12/2023",
            id = 1,
            status = "In Progress",
            priority = "High",
            category = "Work",
            createdAt = "2023-11-20 09:15:30",
            updatedAt = "2023-11-22 14:05:00"
        ),
        backgroundColor = Color.White, // Sample background color
        onDelete = {
            // This lambda does nothing in the preview,
            // but it's required by the TaskCard function.
            println("Delete button clicked in preview")
        },
        onClick = {} // <-- THÊM VÀO ĐỂ PREVIEW HOẠT ĐỘNG

    )
    // }
}

@Preview(showBackground = true, name = "TaskCard Dark Preview")
@Composable
fun TaskCardDarkPreview() {
    // Example with a different background color
    TaskCard(
        task = Task(
            id = 1,
            title = "Hoàn thành báo cáo dự án Alpha",
            description = "Viết phần tổng kết, phân tích số liệu và đề xuất giải pháp cho dự án Alpha. Đảm bảo tất cả các biểu đồ đã được cập nhật.",
            status = "In Progress",
            priority = "High",
            category = "Work",
            dueDate = "2023-12-15 17:00",
            createdAt = "2023-11-20 09:15:30",
            updatedAt = "2023-11-22 14:05:00"
        ),
        backgroundColor = Color(0xFFE0E0E0), // A light gray
        onDelete = {},
        onClick = {} // <-- THÊM VÀO ĐỂ PREVIEW HOẠT ĐỘNG

    )
}