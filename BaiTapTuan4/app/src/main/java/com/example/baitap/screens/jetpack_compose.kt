package com.example.baitap.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape // Hoặc RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitap.ui.theme.BaiTapTuan4Theme
import com.example.baitap.R // Đảm bảo bạn có R từ package của mình
import androidx.compose.foundation.shape.RoundedCornerShape
// Màu sắc từ hình ảnh
val screenBgColor = Color.White
//val logoPlaceholderColor = Color(0xFF1A237E) // Màu nền tạm cho logo nếu không có ảnh
val textColorOnBlack = Color(0x99FFFFFF) // Màu xám trắng mờ (Alpha 60%)
val buttonPushColor = Color(0xFF007AFF) // Màu xanh dương của nút
val glowColorOuter = Color(0xAA00E5FF) // Màu xanh cyan cho glow ngoài (Alpha ~66%)
val glowColorInner = Color(0xDD40C4FF) // Màu xanh dương nhạt hơn cho glow trong (Alpha ~86%)

@Composable
fun PushNotificationScreen(onPushClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBgColor),
        contentAlignment = Alignment.Center // Căn giữa toàn bộ nội dung
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround // Phân bổ không gian đều
        ) {
            Spacer(modifier = Modifier.weight(0.5f)) // Đẩy logo lên một chút

            // Logo
            Image(
                painter = painterResource(id = R.drawable.jetpack_compose_logo), // THAY THẾ BẰNG LOGO CỦA BẠN
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(180.dp) // Điều chỉnh kích thước logo
                    .clip(RoundedCornerShape(16.dp)), // Bo góc nhẹ cho logo nếu cần
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Text mô tả
            Text(
                text = "is a framework that simplifies the\n" +
                        "implementation of navigation between different\n" +
                        "UI components (activities, fragments, or\n" +
                        "composables) in an app",
                color = textColorOnBlack,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp // Tăng khoảng cách dòng cho dễ đọc
            )

            Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống dưới

            // Nút PUSH với hiệu ứng glow
            Box { // Box để chứa nút và các lớp glow
                // Lớp glow ngoài cùng (lớn nhất, mờ nhất)
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(80.dp) // Cao hơn nút một chút
                        .graphicsLayer { alpha = 0.6f } // Giảm độ mờ nếu dùng blur
                        .shadow(
                            elevation = 32.dp,
                            shape = CircleShape, // Hoặc RoundedCornerShape(40.dp)
                            clip = false, // Để shadow lan ra ngoài
                            ambientColor = glowColorOuter.copy(alpha = 0.3f), // Điều chỉnh alpha
                            spotColor = glowColorOuter
                        )
                        .background(Color.Transparent) // Nền trong suốt cho box glow
                )
                // Lớp glow bên trong (nhỏ hơn, rõ hơn một chút)
                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(70.dp)
                        .graphicsLayer { alpha = 0.8f }
                        .shadow(
                            elevation = 24.dp,
                            shape = CircleShape, // Hoặc RoundedCornerShape(35.dp)
                            clip = false,
                            ambientColor = glowColorInner.copy(alpha = 0.4f),
                            spotColor = glowColorInner
                        )
                        .background(Color.Transparent)
                )

                // Nút PUSH
                Button(
                    onClick = onPushClick,
                    modifier = Modifier
                        .width(250.dp) // Chiều rộng của nút
                        .height(60.dp) // Chiều cao của nút
                        .align(Alignment.Center), // Căn nút vào giữa các lớp glow
                    shape = CircleShape, // Hoặc RoundedCornerShape(30.dp) để có hình viên thuốc
                    colors = ButtonDefaults.buttonColors(containerColor = buttonPushColor)
                ) {
                    Text(
                        text = "PUSH",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.8f)) // Khoảng trống dưới nút
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PushNotificationScreenPreview() {
    BaiTapTuan4Theme { // Sử dụng Theme của bạn
        PushNotificationScreen(onPushClick = {})
    }
}