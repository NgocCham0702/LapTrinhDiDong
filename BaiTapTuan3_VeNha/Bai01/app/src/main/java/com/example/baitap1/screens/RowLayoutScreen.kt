package com.example.baitap1.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft // Tự động lật cho RTL
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Màu sắc sử dụng
val topBarBlueColor = Color(0xFF007AFF) // Màu xanh dương cho TopAppBar
val lightBlueBoxColor = Color(0xFFCADDFF) // Xanh dương nhạt cho các ô
val mediumBlueBoxColor = Color(0xFF6EA3FF) // Xanh dương đậm hơn cho ô giữa
val screenBackgroundColor = Color.White
val itemContainerBackgroundColor = Color(0xFFF0F2F5) // Màu nền xám rất nhạt cho container

class RowLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme { // Thay thế bằng Theme của ứng dụng bạn
                RowLayoutScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowLayoutScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar( // CenterAlignedTopAppBar để tiêu đề căn giữa dễ hơn
                title = {
                    Text(
                        text = "Row Layout",
                        color = topBarBlueColor,
                        fontWeight = FontWeight.SemiBold, // Hoặc FontWeight.Bold
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Xử lý quay lại */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = topBarBlueColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = screenBackgroundColor // Nền trắng cho TopAppBar
                )
            )
        },
        containerColor = screenBackgroundColor // Nền trắng cho toàn màn hình
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp), // Padding chung cho nội dung
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Container cho các hàng item, có nền xám nhạt và bo góc
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(itemContainerBackgroundColor, RoundedCornerShape(12.dp))
                    .padding(16.dp) // Padding bên trong container
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp) // Khoảng cách giữa các hàng
                ) {
                    repeat(4) { // Tạo 4 hàng
                        ItemRow()
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween // Phân bố đều không gian
    ) {
        ColorBoxItem(color = lightBlueBoxColor, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(12.dp)) // Khoảng cách giữa các box
        ColorBoxItem(color = mediumBlueBoxColor, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(12.dp)) // Khoảng cách giữa các box
        ColorBoxItem(color = lightBlueBoxColor, modifier = Modifier.weight(1f))
    }
}

@Composable
fun ColorBoxItem(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(55.dp) // Chiều cao cố định cho các box
            .clip(RoundedCornerShape(8.dp)) // Bo góc
            .background(color)
    )
    // Nếu bạn muốn thêm nội dung vào box, có thể đặt ở đây
    // Ví dụ: Text("Item", modifier = Modifier.align(Alignment.Center))
}


@Preview(showBackground = true, widthDp = 375, heightDp = 812) // Kích thước giống iPhone
@Composable
fun RowLayoutScreenPreview() {
    MyApplicationTheme {
        RowLayoutScreen()
    }
}

// Bạn cần định nghĩa Theme cho ứng dụng, ví dụ (nếu chưa có):
@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme( // Sử dụng lightColorScheme cho nền sáng
            primary = topBarBlueColor,
            background = screenBackgroundColor,
            surface = screenBackgroundColor,
            // ... các màu khác nếu cần
        ),
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}