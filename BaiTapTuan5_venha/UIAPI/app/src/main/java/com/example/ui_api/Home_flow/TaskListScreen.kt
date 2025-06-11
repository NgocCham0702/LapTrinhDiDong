package com.example.ui_api.Home_flow

import TaskViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui_api.R

@Composable
fun TaskTopBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFB2DCEE)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "UTH",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF276C2A)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "University of Transport\nHo Chi Minh City",
                    fontSize = 10.sp,
                    color = Color.Red,
                    lineHeight = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "SmartTasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )
            Text(
                text = "A simple and efficient to-do app",
                fontSize = 10.sp,
                color = Color(0xFF5EA4D3),
            )
        }

        Image(
            painter = painterResource(id = R.drawable.chuong),
            contentDescription = "Notification",
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun TaskListScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TaskTopBox()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(tasks) { index, task ->
                TaskCard(
                    task = task,
                    backgroundColor = generateColor(index),
                    onDelete = { viewModel.deleteTask(task.id) }
                )
            }
        }
    }
}

fun generateColor(index: Int): Color {
    val colors = listOf(
        Color(0xFFFFCDD2), Color(0xFFC8E6C9),
        Color(0xFFBBDEFB), Color(0xFFFFF9C4),
        Color(0xFFD1C4E9)
    )
    return colors[index % colors.size]
}

//@Preview(showBackground = true)
//@Composable
//fun TaskListScreenFakePreview() {
//    val fakeTasks = listOf(
//        Task(id = 1, title = "Task 1", description = "Do homework", dueDate = "2023-11-15", status = "pending"),
//        Task(id = 2, title = "Task 2", description = "Clean room", dueDate = "2023-11-01", status = "completed")
//    )
//
//    Column {
//        TaskTopBox()
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(10.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            itemsIndexed(fakeTasks) { index, task ->
//                TaskCard(
//                    task = task,
//                    backgroundColor = generateColor(index),
//                    onDelete = {}
//                )
//            }
//        }
//    }
//}
