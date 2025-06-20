package com.example.layout.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.layout.ui.theme.LayoutTheme

val textFieldLabelColor = Color.Gray
val textFieldActiveTextColor = Color(0xFFD32F2F) // Màu đỏ cho text bên dưới

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldScreen(onBackClick: () -> Unit, title: String = "TextField") { // Thêm tham số title
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        title, // Sử dụng title được truyền vào
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = topBarBlue,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = topBarBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                label = { Text("Thông tin nhập", color = textFieldLabelColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = topBarBlue,
                    unfocusedBorderColor = Color.LightGray,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tự động cập nhật dữ liệu theo textfield",
                color = textFieldActiveTextColor,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start) // Căn trái text này
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldScreenPreview() {
    LayoutTheme {
        TextFieldScreen({})
    }
}