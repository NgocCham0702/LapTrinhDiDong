package com.example.baitap1.screens // Hoặc package của Login_Flow.kt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baitap1.R
import com.example.baitap1.screens.ui.theme.Black
import com.example.baitap1.screens.ui.theme.Blue
import com.example.baitap1.screens.ui.theme.GrayBlue
import com.example.baitap1.ui.theme.BaiTap1Theme
import com.example.baitap1.viewmodel.LoginViewModel // GIẢ SỬ ĐƯỜNG DẪN NÀY ĐÚNG
import com.example.baitap1.viewmodel.SignInResult    // GIẢ SỬ ĐƯỜNG DẪN NÀY ĐÚNG
// import com.example.baitap1.screens.user_profile.UserProfileScreen // BẠN CẦN TẠO FILE NÀY

// --- BẠN CẦN TẠO FILE UserProfileScreen.kt RIÊNG ---
// Ví dụ nội dung UserProfileScreen.kt (đã cung cấp ở các câu trả lời trước)
// package com.example.baitap1.screens.user_profile
//
// import ...
// @Composable
// fun UserProfileScreen(navController: NavController) { ... }
// ----------------------------------------------------

// Định nghĩa các route (có thể đặt trong một file riêng nếu muốn)
object AppDestinations {
    const val LOGIN_ROUTE = "login"
    const val USER_PROFILE_ROUTE = "user_profile"
}

class Login_Flow : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaiTap1Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = AppDestinations.LOGIN_ROUTE
                ) {
                    composable(AppDestinations.LOGIN_ROUTE) {
                        LoginScreen(navController = navController) // Truyền NavController
                    }
                    composable(AppDestinations.USER_PROFILE_ROUTE) {
                        // UserProfileScreen(navController = navController) // BỎ COMMENT KHI BẠN CÓ UserProfileScreen
                        // Thay thế bằng một Composable tạm thời nếu chưa có UserProfileScreen
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("User Profile Screen (Placeholder)")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current // Không dùng trực tiếp trong code này, nhưng có thể cần cho các tác vụ khác
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val signInResult by loginViewModel.signInResult.observeAsState(initial = SignInResult.Idle)

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        if (data != null) {
            loginViewModel.handleGoogleSignInResult(data)
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Google Sign-In cancelled or failed to get data.")
            }
        }
    }

    LaunchedEffect(signInResult) {
        when (val result = signInResult) {
            is SignInResult.Success -> {
                snackbarHostState.showSnackbar("Sign-in successful!")
                // kotlinx.coroutines.delay(1500) // Tùy chọn: đợi một chút
                navController.navigate(AppDestinations.USER_PROFILE_ROUTE) {
                    popUpTo(AppDestinations.LOGIN_ROUTE) { inclusive = true }
                }
                loginViewModel.resetSignInResult()
            }
            is SignInResult.Error -> {
                snackbarHostState.showSnackbar("Sign-in failed: ${result.message}")
                loginViewModel.resetSignInResult()
            }
            is SignInResult.Loading -> {
                // Đang tải, không cần làm gì ở đây nếu đã có CircularProgressIndicator
            }
            is SignInResult.Idle -> {
                // Trạng thái ban đầu
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // QUAN TRỌNG: Áp dụng padding từ Scaffold
                .padding(24.dp), // Padding ban đầu của bạn
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // PHẦN 1: Logo và tên app
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "Background Image", // Sửa contentDescription
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_1),
                        contentDescription = "App Logo", // Sửa contentDescription
                        modifier = Modifier.size(240.dp),
                    )
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "SmartTasks",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Blue // Đảm bảo Blue được định nghĩa trong theme của bạn
                    )
                    Text(
                        text = "A simple and efficient to-do app.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GrayBlue // Đảm bảo GrayBlue được định nghĩa
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            // PHẦN 2: Mô tả và lời chào
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Ready to explore? Log in to get started.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 9.dp),
                    color = Black // Đảm bảo Black được định nghĩa
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // PHẦN 3: Nút đăng nhập Google và Loading Indicator
            if (signInResult is SignInResult.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))
            } else {
                Button(
                    onClick = {
                        loginViewModel.launchGoogleSignIn(googleSignInLauncher)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD5EDFF), // Màu nền của nút
                        contentColor = Color.Black          // Màu cho Text và Icon (nếu là vector đơn sắc)
                    )
                ) {
                    Image( // Nên dùng Icon nếu là vector, Image nếu là PNG/JPG
                        painter = painterResource(id = R.drawable.gg),
                        contentDescription = "Google Sign-In", // Sửa contentDescription
                        modifier = Modifier.size(24.dp)
                        // tint = Color.Unspecified // Nếu R.drawable.gg là ảnh PNG đa màu, không cần tint
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("SIGN IN WITH GOOGLE")
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun LoginScreenPreview() {
    BaiTap1Theme {
        // Trong preview, NavController có thể không hoạt động đầy đủ,
        // nhưng bạn có thể truyền một NavController giả hoặc để trống nếu hàm chấp nhận null.
        // Ở đây, LoginScreen yêu cầu NavController, nên ta cần cung cấp một cái gì đó.
        // Cách đơn giản là tạo một NavController tạm thời cho preview.
        val navController = rememberNavController()
        LoginScreen(navController = navController)
    }
}