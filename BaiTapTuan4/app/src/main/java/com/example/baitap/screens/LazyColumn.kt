package com.example.baitap.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed // Để lấy index nếu cần
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight // More specificimport androidx.compose.material3.Icon
import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme // Nếu bạn có theme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitap.ui.theme.BaiTapTuan4Theme // Giả sử Theme của bạn
import java.text.NumberFormat
import java.util.Locale

// Màu sắc từ hình ảnh
val lazyScreenBgColor = Color.Black
val lazyTitleColor = Color(0xFF007AFF) // Xanh dương cho tiêu đề
val lazyItemBackgroundColor = Color(0xFF0095FF) // Xanh dương sáng hơn cho item
val lazyItemTextColor = Color.White
val lazyItemArrowBackgroundColor = Color.Black
val lazyItemArrowIconColor = Color.White

data class ListItemData(val id: Int, val text: String)

@Composable
fun LazyColumnScreen(onItemClick: (ListItemData) -> Unit) {
    // Tạo danh sách dữ liệu mẫu
    // Để có 1.000.000 item, chúng ta sẽ tạo một danh sách lớn.
    // Trong thực tế, dữ liệu này có thể đến từ API hoặc database.
    val largeList = remember {
        (1..1000000).map {
            ListItemData(it, "The only way to do great work is to love what you do.")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lazyScreenBgColor)
            .padding(top = 24.dp, bottom = 16.dp) // Padding cho toàn bộ màn hình
    ) {
        // Tiêu đề "LazyColumn"
        Text(
            text = "LazyColumn",
            color = lazyTitleColor,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        // Danh sách cuộn LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp) // Khoảng cách giữa các item
        ) {
            itemsIndexed(largeList, key = { _, item -> item.id }) { index, itemData ->
                // Định dạng số cho item đầu tiên và item cuối cùng theo yêu cầu
                val displayIndex = when (itemData.id) {
                    1, 2, 3, 4, 5 -> String.format(Locale.ROOT, "%02d", itemData.id)
                    1000000 -> NumberFormat.getNumberInstance(Locale.GERMAN).format(itemData.id)
                    else -> itemData.id.toString()
                }

                // Chỉ hiển thị các item đầu tiên và item cuối cùng như trong hình mẫu,
                // hoặc hiển thị một lượng giới hạn để demo.
                // Nếu muốn hiển thị tất cả, xóa điều kiện if này.
                if (itemData.id <= 5 || itemData.id == 1000000) {
                    MyListItem(
                        indexText = displayIndex,
                        mainText = itemData.text,
                        onClick = { onItemClick(itemData) }
                    )
                }
            }
        }
    }
}

@Composable
fun MyListItem(indexText: String, mainText: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // Đảm bảo Row có chiều cao tối thiểu dựa trên nội dung
            .clip(RoundedCornerShape(12.dp))
            .background(lazyItemBackgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp), // Padding bên trong item
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Phần text (số thứ tự và nội dung)
        Text(
            text = "$indexText | $mainText",
            color = lazyItemTextColor,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f), // Cho phép text chiếm không gian còn lại
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.width(12.dp)) // Khoảng cách giữa text và icon

        // Phần icon mũi tên
        Box(
            modifier = Modifier
                .size(36.dp) // Kích thước của Box chứa icon
                .clip(RoundedCornerShape(8.dp))
                .background(lazyItemArrowBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Go to detail",
                tint = lazyItemArrowIconColor,
                modifier = Modifier.size(28.dp) // Kích thước của icon
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000) // Nền đen cho Preview
@Composable
fun LazyColumnScreenPreview() {
    BaiTapTuan4Theme { // Sử dụng Theme của bạn
        LazyColumnScreen(onItemClick = {})
    }
}