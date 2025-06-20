package com.example.layout.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.layout.ui.theme.LayoutTheme

val brownColor = Color(0xFFA1887F) // Hoặc 0xFFD2691E

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Text Detail",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center, // Căn giữa tiêu đề
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
                .padding(24.dp)
        ) {
            Text(
                buildAnnotatedString {
                    append("The quick ")
                    withStyle(style = SpanStyle(color = brownColor, fontSize = 38.sp, fontWeight = FontWeight.Bold)) {
                        append("Brown")
                    }
                },
                fontSize = 30.sp, color = Color.Black, lineHeight = 40.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                buildAnnotatedString {
                    append("fox j u m p s ") // Giả lập letter spacing
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("over")
                    }
                },
                fontSize = 30.sp, color = Color.Black, lineHeight = 40.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("the")
                    }
                    append(" ")
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append("lazy")
                    }
                    append(" dog.")
                },
                fontSize = 30.sp, color = Color.Black, fontFamily = FontFamily.Serif, lineHeight = 40.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextDetailScreenPreview() {
    LayoutTheme {
        TextDetailScreen {}
    }
}