package com.example.baitap1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.baitap1.screens.UIComponentsListScreen
import com.example.baitap1.ui.theme.Bai01Theme
import com.example.baitap1.screens.WelcomeScreen
import com.example.baitap1.screens.TextDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bai01Theme {
                // Đơn giản dùng biến state để chuyển màn hình
                var screen by remember { mutableStateOf("welcome") }
                when (screen) {
                    "welcome" -> WelcomeScreen(onReadyClick = { screen = "list" })
                    "list" -> UIComponentsListScreen(onComponentClick = { screen = "textDetail" })
                    "textDetail" -> TextDetailScreen()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun WelcomeScreenPreview() {
        Bai01Theme {
            WelcomeScreen(onReadyClick = {})
        }
    }
}
