package com.example.ui_api.Home_flow

import com.example.ui_api.Home_flow.modol.TaskViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun TaskListScreen(viewModel: TaskViewModel = viewModel(),
    // Giả sử bạn truyền NavController để điều hướng
                   onTaskClick: (Int) -> Unit ) {
    val tasks by viewModel.tasks.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TaskTopBox()
            if (tasks.isEmpty()) {
                // 👉 Nếu không có task thì hiển thị NoTasks
                NoTasks(onBackClick = {
                    // xử lý quay lại nếu cần, hoặc có thể để trống
                })
            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(tasks) { index, task ->
                        TaskCard(
                            task = task,
                            backgroundColor = generateColor(index),
                            onDelete = { viewModel.deleteTask(task.id) },
                            onClick = {
                                // Gọi hàm điều hướng và truyền ID của task
                                onTaskClick(task.id)
                            }
                        )
                    }
                }
            }
            StaticImageFooter()
        }

    }
}

@Composable
fun StaticImageFooter() {
    // Sử dụng Box để xếp chồng các thành phần lên nhau
    Box(
        // Căn chỉnh tất cả các phần tử con vào giữa-dưới của Box
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            // Đặt nền trắng như bạn yêu cầu
            .background(Color.White)
            .padding(bottom = 30.dp)
    ) {
        // 1. Hình ảnh nền (thanh điều hướng) - được vẽ trước
        Image(
            painter = painterResource(id = R.drawable.item), // Tải ảnh từ drawable
            contentDescription = "Navigation items bar",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth // Đảm bảo ảnh trải dài hết chiều rộng
        )

        // 2. Hình ảnh nút cộng - được vẽ sau, nên sẽ nằm trên
        Image(
            painter = painterResource(id = R.drawable.add), // Tải ảnh từ drawable
            contentDescription = "Add button",
            // Nút cộng sẽ tự động được căn giữa-dưới do contentAlignment của Box
            // Thêm một chút padding ở dưới để nâng nó lên nếu cần
            modifier = Modifier
                .padding(bottom = 5.dp)
                .size(100.dp)
        )
    }
}

@Composable
fun NoTasks(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) { // Thêm modifier để có thể tùy chỉnh từ bên ngoài
    Column(
        modifier = modifier.fillMaxSize(), // Sử dụng modifier được truyền vào
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onBackClick() },

            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back), // THAY THẾ ID ẢNH CỦA BẠN
                contentDescription = "quay lai",
                modifier = Modifier.size(50.dp) // Kích thước ví dụ
            )
            Text(
                text = "List",
                fontSize = 20.sp,
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f), // Quan trọng: Text chiếm hết không gian còn lại
                textAlign = androidx.compose.ui.text.style.TextAlign.Center // Căn giữa nội dung Text trong không gian của nó
            )
        }
        // Giả sử R.drawable.no_tasks_image là ảnh bạn muốn hiển thị
        Image(
            painter = painterResource(id = R.drawable.notasks), // THAY THẾ ID ẢNH CỦA BẠN
            contentDescription = "No Tasks Available",
            modifier = Modifier.size(350.dp) // Kích thước ví dụ
        )
        Text("Không có công việc nào", fontSize = 18.sp, color = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))

        StaticImageFooter()
    }

}

// Kết thúc các Composable giả sử
fun generateColor(index: Int): Color {
    val colors = listOf(
        Color(0xFFFFCDD2), Color(0xFFC8E6C9),
        Color(0xFFBBDEFB), Color(0xFFFFF9C4),
        Color(0xFFD1C4E9)
    )
    return colors[index % colors.size]
}

@Preview(showBackground = true)
@Composable
fun FakePreview() {
    val fakeTasks = listOf(
        Task(
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
        Task(
            id = 1,
            title = "Hoàn thành báo cáo dự án Alpha",
            description = "Viết phần tổng kết, phân tích số liệu và đề xuất giải pháp cho dự án Alpha. Đảm bảo tất cả các biểu đồ đã được cập nhật.",
            status = "In Progress",
            priority = "High",
            category = "Work",
            dueDate = "2023-12-15 17:00",
            createdAt = "2023-11-20 09:15:30",
            updatedAt = "2023-11-22 14:05:00"
        )
    )
    Box(modifier = Modifier.fillMaxSize()) {

        Column {
            TaskTopBox()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f), // ✨ Cho LazyColumn chiếm hết không gian còn lại
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 100.dp)

            ) {
                itemsIndexed(fakeTasks) { index, task ->
                    TaskCard(
                        task = task,
                        backgroundColor = generateColor(index),
                        onDelete = {},
                        onClick = {}
                    )
                }
            }

        }
    }

}

// ---- Preview ----

@Preview(showBackground = true, name = "AddItemScreen - Item Selected State")
@Composable
fun AddItemScreenSelectedPreview() {
    MaterialTheme {
        // Để preview trạng thái đã chọn, ta cần mô phỏng việc itemSelected = true
        // Cách đơn giản là tạo một Composable riêng cho trạng thái này hoặc dùng LaunchedEffect
        // ở đây ta sẽ tạo một Composable đơn giản hơn:
        Column(modifier = Modifier.fillMaxSize()) {
            NoTasks(modifier = Modifier.weight(1f))
        }
    }
}
