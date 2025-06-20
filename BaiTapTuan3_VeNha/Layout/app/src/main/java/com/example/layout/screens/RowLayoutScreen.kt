package com.example.layout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.layout.ui.theme.LayoutTheme

val rowLayoutLightBlue = Color(0xFFCADDFF)
val rowLayoutMediumBlue = Color(0xFF6EA3FF)
val rowLayoutContainerBackground = Color(0xFFF0F2F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowLayoutScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Row Layout",
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(rowLayoutContainerBackground, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    repeat(4) {
                        RowLayoutItem()
                    }
                }
            }
        }
    }
}

@Composable
fun RowLayoutItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp) // Sử dụng spacedBy cho khoảng cách đều
    ) {
        ColorBox(color = rowLayoutLightBlue, modifier = Modifier.weight(1f))
        ColorBox(color = rowLayoutMediumBlue, modifier = Modifier.weight(1f))
        ColorBox(color = rowLayoutLightBlue, modifier = Modifier.weight(1f))
    }
}

@Composable
fun ColorBox(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(55.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
fun RowLayoutScreenPreview() {
    LayoutTheme {
        RowLayoutScreen {}
    }
}