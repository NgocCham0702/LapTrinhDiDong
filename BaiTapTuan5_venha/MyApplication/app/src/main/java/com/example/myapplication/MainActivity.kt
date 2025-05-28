package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class ManHinh(val duongDan: String) {
    object DangNhap : ManHinh("login")
    object HoSo : ManHinh("profile")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = ManHinh.DangNhap.duongDan) {
        composable(ManHinh.DangNhap.duongDan) { LoginScreen(navController) }
        composable(ManHinh.HoSo.duongDan) { ProfileScreen(navController) }
    }
}
