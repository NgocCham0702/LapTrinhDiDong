package com.example.tuan02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.example.tuan02.ui.theme.TuoiTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TuoiTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFFFFF)) {
                    AgeCheckScreen()
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgeCheckerScreenPreview() {
    TuoiTheme {
        AgeCheckScreen()
    }
}

@Composable
fun AgeCheckScreen() {
    var name by remember { mutableStateOf("") }
    var ageInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally

    ) {
        Text(
            text = "THỰC HÀNH 01",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))
// Khung nền xám chứa 2 trường nhập liệu
        Box(
            modifier = Modifier
                .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(12.dp))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Text(
                        text = "Họ và tên",
                        modifier = Modifier.width(90.dp)
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                            .background(Color.White),

                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tuổi",
                        modifier = Modifier.width(90.dp)
                    )
                    OutlinedTextField(
                        value = ageInput,
                        onValueChange = { ageInput = it.filter { c -> c.isDigit() } },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                            .background(Color.White)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Nút kiểm tra màu xanh, căn giữa
        Button(
            onClick = {
                val age = ageInput.toIntOrNull()
                keyboardController?.hide()  // Ẩn bàn phím khi nhấn nút
                result = if (name.isBlank() || age == null) {
                    "Vui lòng nhập đầy đủ và đúng thông tin."
                } else {
                    "$name là " + when {
                        age > 65 -> "Người già"
                        age in 6..65 -> "Người lớn"
                        age in 2..6 -> "Trẻ em"
                        age < 2 -> "Em bé"
                        else -> "Không xác định"
                    }
                }
            },
            modifier = Modifier
                .width(180.dp)
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)) // Màu xanh đậm

        ) {
            Text("Kiểm tra", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


