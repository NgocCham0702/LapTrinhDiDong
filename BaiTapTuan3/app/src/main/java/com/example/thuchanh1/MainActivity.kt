package com.example.thuchanh1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thuchanh1.ui.theme.BTTuan3_ThucHanh1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BTTuan3_ThucHanh1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyFirstAppScreen()
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThreePartsLayout()
}
@Composable
fun MyFirstAppScreen() {
    // State để lưu nội dung TextView
    var message by remember { mutableStateOf("Hello") }
    // State để kiểm soát kiểu chữ đậm dòng 2 sau khi bấm nút
    var isHiClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Tiêu đề lớn
        Text(
            text = "My First App",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 40.dp)
        )
        // TextView thay đổi nội dung
        if (!isHiClicked) {
            Text(
                text = message,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 40.dp)
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(
                    text = "I'm",
                    fontSize = 20.sp,
                )
                Text(
                    text = "Phạm Thị Ngọc Châm ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        // Nút màu xanh
        Button(
            onClick = {
                isHiClicked = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Say Hi!",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
@Composable
fun ThreePartsLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp),
        //horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Trái nhỏ (weight = 1)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Trái nhỏ", color = Color.White)
        }

        // Giữa lớn nhất (weight = 2)
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(Color.Green),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Giữa lớn nhất", color = Color.White)
        }

        // Phải lớn (weight = 1)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Blue),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Phải lớn", color = Color.White)
        }
    }
}
