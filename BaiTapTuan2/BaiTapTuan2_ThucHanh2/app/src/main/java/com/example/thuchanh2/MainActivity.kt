package com.example.thuchanh2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thuchanh2.ui.theme.EmailTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailTheme {
                EmailValidationScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleComposablePreview() {
    EmailTheme {
        EmailValidationScreen()
    }
}

@Composable
fun EmailValidationScreen() {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var message by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White // Nền trắng
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Thực hành 02",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    message = ""
                },
                placeholder = { Text("Email", color = Color.Gray) },
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(15.dp), // Bo tròn như hình
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFBDBDBD),      // Viền xám khi focus
                    unfocusedBorderColor = Color(0xFFBDBDBD),    // Viền xám khi chưa focus
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                singleLine = true
            )



            Spacer(modifier = Modifier.height(8.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.contains("hợp lệ")) Color.Red else Color.Red
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val emailText = email.text.trim()
                    message = when {
                        emailText.isEmpty() -> "Email không hợp lệ"
                        !emailText.contains("@") -> "Email không đúng định dạng"
                        else -> "Bạn đã nhập email hợp lệ"
                    }
                },
                modifier = Modifier.width(320.dp) .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Nút xanh
            ) {
                Text("Kiểm tra", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
