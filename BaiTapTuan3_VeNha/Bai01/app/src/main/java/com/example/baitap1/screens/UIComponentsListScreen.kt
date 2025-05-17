package com.example.baitap1.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UIComponentsListScreen(onComponentClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "UI Components List",
            color = Color(0xFF2196F3),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Display group
        Text("Display", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        ComponentItem("Text", "Displays text") { onComponentClick("Text") }
        ComponentItem("Image", "Displays an image") { onComponentClick("Image") }
        Spacer(modifier = Modifier.height(16.dp))
        // Input group
        Text("Input", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        ComponentItem("TextField", "Input field for text") { onComponentClick("TextField") }
        ComponentItem("PasswordField", "Input field for passwords") { onComponentClick("PasswordField") }
        Spacer(modifier = Modifier.height(16.dp))
        // Layout group
        Text("Layout", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        ComponentItem("Column", "Arranges elements vertically") { onComponentClick("Column") }
        ComponentItem("Row", "Arranges elements horizontally") { onComponentClick("Row") }
    }
}

@Composable
fun ComponentItem(title: String, description: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(56.dp)
            .clickable { onClick() },
        color = Color(0xFFB3E5FC),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = description,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun UIComponentsListScreenPreview() {
    UIComponentsListScreen(onComponentClick = {})
}