package com.example.baitap1.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.baitap1.R

@Composable
fun TextFieldScreen(onBackClick: () -> Unit = {}) {

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .offset(x = 19.dp, y = 47.dp)
                .height(30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onBackClick() }  // Sửa ở đây
            )
            Spacer(modifier = Modifier.width(91.dp))
            Text(
                text = "Text Field",
                color = Color(0xFF2196F3),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(110.dp)
                    .height(30.dp),
                textAlign = TextAlign.Center
            )
        }
            var text by remember { mutableStateOf("")}
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(19.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(299.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = { text =it },
                    placeholder = { Text("Thông tin nhập") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Tự động cập nhật dữ liệu theo textfield",
                    color = Color.Red
                )

            }
        }
    }
@Preview(showBackground = true)
@Composable
fun TextFieldScreenPreview() {
    TextFieldScreen()
}
