package com.example.baitap.screens // Hoặc package của bạn (ví dụ: com.example.layout.screens)

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText // Vẫn có thể dùng nếu muốn, nhưng Text với LinkAnnotation tốt hơn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.baitap.ui.theme.BaiTapTuan4Theme // THAY THẾ bằng Theme của bạn

// Màu sắc (bạn có thể định nghĩa chúng ở một nơi chung hoặc trong file Theme/Color.kt)
val detailScreenBackgroundColor = Color.White
val detailTopBarTitleColor = Color(0xFF007AFF)
val detailTopBarIconColor = Color(0xFF007AFF)
val detailQuoteTextColor = Color.DarkGray
val detailCardGradientStart = Color(0xFFACE5EE) // Sky Blue light
val detailCardGradientEnd = Color(0xFF00BFFF)   // Deep Sky Blue
val detailCardTextColor = Color.Black
val detailCardSourceColor = Color(0xFF333333)
val detailButtonBackgroundColor = Color(0xFF007AFF)
val detailButtonTextColor = Color.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class) // Cần cho TopAppBar và LinkAnnotation
@Composable
fun DetailScreen(
    quote: String,
    author: String,
    sourceUrl: String,
    onBackClick: () -> Unit,
    onBackToRootClick: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Detail",
                        color = detailTopBarTitleColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp) // Thêm padding để icon không quá sát mép trái
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(detailTopBarIconColor.copy(alpha = 0.15f))
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = detailTopBarIconColor,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                // modifier = Modifier.padding(horizontal = 16.dp), // Padding này có thể làm icon bị đẩy vào quá
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = detailScreenBackgroundColor
                )
            )
        },
        containerColor = detailScreenBackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "\"$quote\"",
                color = detailQuoteTextColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 250.dp) // Đảm bảo card có chiều cao tối thiểu
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(detailCardGradientStart, detailCardGradientEnd)
                        )
                    )
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "\"$quote\"",
                        color = detailCardTextColor,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp,
                        fontFamily = FontFamily.Serif // Hoặc Monospace
                    )

                    Spacer(modifier = Modifier.heightIn(min = 24.dp)) // Khoảng cách linh hoạt

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = author,
                            color = detailCardSourceColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val annotatedLinkString = buildAnnotatedString {
                            withLink(
                                link = LinkAnnotation.Url(
                                    url = sourceUrl,
                                    styles = TextLinkStyles(style = SpanStyle(textDecoration = TextDecoration.Underline))
                                )
                            ) {
                                withStyle(
                                    style = SpanStyle(
                                        color = detailCardSourceColor.copy(alpha = 0.8f),
                                        fontSize = 13.sp,
                                    )
                                ) {
                                    append(sourceUrl)
                                }
                            }
                        }
                        Text(
                            text = annotatedLinkString,
                            style = LocalTextStyle.current.merge(TextStyle(textAlign = TextAlign.Center))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống dưới

            Button(
                onClick = onBackToRootClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = detailButtonBackgroundColor
                )
            ) {
                Text(
                    text = "BACK TO ROOT",
                    color = detailButtonTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    BaiTapTuan4Theme { // THAY THẾ bằng Theme của bạn
        DetailScreen(
            quote = "The only way to do great work is to love what you do.",
            author = "Steve Jobs",
            sourceUrl = "http://quotes.thisgrandpablogs.com/",
            onBackClick = {},
            onBackToRootClick = {}
        )
    }
}