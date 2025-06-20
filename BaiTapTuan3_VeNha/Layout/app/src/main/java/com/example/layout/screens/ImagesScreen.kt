package com.example.layout.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em // Import em nếu bạn dùng lineHeight = 1.24.em
import androidx.compose.ui.unit.sp
import com.example.layout.R // Quan trọng: đảm bảo bạn có file R
import com.example.layout.ui.theme.LayoutTheme // Giả sử Theme của bạn là LayoutTheme

// Màu sắc (giữ nguyên hoặc lấy từ theme)
val topBarBlue = Color(0xFF007AFF) // Bạn có thể định nghĩa màu này ở đâu đó chung

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun ImagesScreen(onBackClick: () -> Unit) {
    val linkUrl = "https://giaothongvantaitphcm.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png"
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Images",
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.uth), // THAY THẾ bằng tài nguyên drawable của bạn
                contentDescription = "UTH Building",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- BẮT ĐẦU PHẦN XỬ LÝ LINK ---
            val annotatedStringWithLink = buildAnnotatedString { // Đổi tên biến để rõ ràng
                withLink(
                    link = LinkAnnotation.Url(
                        url = linkUrl,
                        styles = TextLinkStyles(style = SpanStyle(textDecoration = TextDecoration.Underline))
                    )
                ) {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.SansSerif,
                        )
                    ) {
                        append(linkUrl)
                    }
                }
            }

            Text( // Sử dụng Text Composable để hiển thị và xử lý link
                text = annotatedStringWithLink,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        textAlign = TextAlign.Center,
                        lineHeight = 1.24.em, // Bạn có thể dùng lineHeight nếu cần
                    )
                ),
                modifier = Modifier
                    .widthIn(max = 303.dp)
                    .align(Alignment.CenterHorizontally),
                linkInteractionListener = LinkInteractionListener { link ->
                    if (link is LinkAnnotation.Url) {
                        try {
                            uriHandler.openUri(link.url)
                        } catch (e: Exception) {
                            Log.e("ImagesScreen", "Could not open URL: ${link.url}", e)
                        }
                    }
                }
            )
            // --- KẾT THÚC PHẦN XỬ LÝ LINK ---


            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.another_image_placeholder), // THAY THẾ bằng tài nguyên drawable của bạn
                contentDescription = "Cityscape",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "In app",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagesScreenPreview() {
    LayoutTheme { // Đảm bảo LayoutTheme được định nghĩa và import đúng
        ImagesScreen {}
    }
}