package com.example.baitap1.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween, // Sửa ở đây
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Text Detail",
            color = Color(0xFF2196F3),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center // Căn giữa tiêu đề
        )
        Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách nhỏ giữa tiêu đề và nội dung

        Spacer(modifier = Modifier.weight(1f)) // Đẩy nội dung xuống giữa màn hình
        val annotatedString = buildAnnotatedString {
            append("The ")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                append("quick ")
            }
            withStyle(style = SpanStyle(color = Color(0xFFB8860B), fontWeight = FontWeight.Bold, fontSize = 35.sp)) {
                append("Brown")
            }
            append("\nfox j u m p s ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("over")
            }
            append("\n")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append("the ")
            }
            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, color = Color.Gray)) {
                append("lazy")
            }
            append(" dog.")
        }
        Text(
            text = annotatedString,
            fontSize = 30.sp,
            textAlign = TextAlign.Center //Căn giữa phần nội dung
        )
        Spacer(modifier = Modifier.weight(1f)) // Đẩy nội dung lên giữa màn hình, cân bằng khoảng cách trên dưới
    }
}

@Preview(showBackground = true)
@Composable
fun TextDecorationPreview() {
    TextDetailScreen()
}
