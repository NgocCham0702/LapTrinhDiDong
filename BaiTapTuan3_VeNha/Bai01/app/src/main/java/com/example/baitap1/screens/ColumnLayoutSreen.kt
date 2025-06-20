package com.example.baitap1.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
val colTopBarBlueColor = Color(0xFF007AFF) // Màu xanh dương cho TopAppBar
val colScreenBackgroundColor = Color.White
val colItemContainerBackgroundColor = Color(0xFFF0F2F5) // Màu nền xám rất nhạt cho container
val colLightGreenBoxColor = Color(0xFFD8ECD8) // Xanh lá nhạt
val colMediumGreenBoxColor = Color(0xFF8CCB8C) // Xanh lá đậm hơn

class ColumnLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme { // Thay thế bằng Theme của ứng dụng bạn
                ColumnLayoutScreen(onBackClick = {})
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnLayoutScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Column Layout",
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
                            tint = colTopBarBlueColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colScreenBackgroundColor, // Nền  cho TopAppBar
                    titleContentColor = colTopBarBlueColor,
                    navigationIconContentColor = colTopBarBlueColor
                )
            )
        },
        containerColor = colScreenBackgroundColor // Nền trắng cho toàn màn hình dưới TopAppBar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp), // Padding chung cho nội dung
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Container cho các mục, có nền xám nhạt và bo góc lớn
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    // .weight(1f) // Nếu muốn nó chiếm không gian còn lại
                    .background(colItemContainerBackgroundColor, RoundedCornerShape(16.dp))
                    .padding(16.dp) // Padding bên trong container
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp) // Khoảng cách giữa các mục
                ) {
                    ColumnColorBoxItem(color = colLightGreenBoxColor)
                    ColumnColorBoxItem(color = colMediumGreenBoxColor)
                    ColumnColorBoxItem(color = colLightGreenBoxColor)
                }
            }
        }
    }
}

@Composable
fun ColumnColorBoxItem(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp) // Chiều cao cố định cho các box, bạn có thể điều chỉnh
            .clip(RoundedCornerShape(12.dp)) // Bo góc cho từng box
            .background(color)
    )
    // Nội dung bên trong box (nếu có)
}


@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
fun ColumnLayoutScreenPreview() {
    MyApplicationTheme {
        ColumnLayoutScreen(onBackClick = {})
    }
}