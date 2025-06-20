package com.example.baitap1.screens // << HÃY THAY ĐỔI CHO PHÙ HỢP VỚI CẤU TRÚC DỰ ÁN CỦA BẠN

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baitap1.AppDestinations // << KIỂM TRA LẠI ĐƯỜNG DẪN NÀY (nơi bạn định nghĩa AppDestinations)
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class) // Cần thiết cho TopAppBar
@Composable
fun UserProfileScreen(navController: NavController) {
    val currentUser = Firebase.auth.currentUser

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer, // Ví dụ màu nền
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer // Ví dụ màu chữ
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Áp dụng padding từ Scaffold
                .padding(16.dp), // Thêm padding riêng cho nội dung
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentUser != null) {
                Text(
                    text = "Welcome, ${currentUser.displayName ?: "User"}!",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Email: ${currentUser.email ?: "No email available"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                // Bạn có thể hiển thị thêm thông tin người dùng ở đây nếu muốn
                // Ví dụ: Avatar (currentUser.photoUrl)

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {
                    Firebase.auth.signOut()
                    // Điều hướng trở lại màn hình đăng nhập và xóa UserProfileScreen khỏi back stack
                    navController.navigate(AppDestinations.LOGIN_ROUTE) {
                        popUpTo(AppDestinations.USER_PROFILE_ROUTE) {
                            inclusive = true // Xóa cả màn hình USER_PROFILE_ROUTE
                        }
                        launchSingleTop = true // Đảm bảo không tạo nhiều instance của LoginScreen nếu nó đã ở trên cùng
                    }
                }) {
                    Text("Sign Out")
                }
            } else {
                // Trường hợp này không nên xảy ra nếu điều hướng được quản lý đúng
                // vì UserProfileScreen chỉ nên được truy cập khi người dùng đã đăng nhập.
                Text("Not signed in. Please return to login.")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    navController.navigate(AppDestinations.LOGIN_ROUTE) {
                        popUpTo(AppDestinations.USER_PROFILE_ROUTE) { inclusive = true }
                        launchSingleTop = true
                    }
                }) {
                    Text("Go to Login")
                }
            }
        }
    }
}