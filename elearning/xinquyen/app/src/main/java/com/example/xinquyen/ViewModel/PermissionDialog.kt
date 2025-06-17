package com.example.xinquyen.ViewModel

// PermissionDialog.kt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.xinquyen.R

@Composable
fun PermissionDialog(
    icon: Painter, // <-- SỬA LỖI: Thống nhất dùng Painter
    title: String,
    description: String,
    primaryButtonText: String,
    onPrimaryClick: () -> Unit,
    secondaryButtonText: String,
    onSecondaryClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(painter = icon, contentDescription = title) }, // <-- SỬA LỖI: Dùng painter
        title = { Text(text = title) },
        text = { Text(text = description, textAlign = TextAlign.Center) },
        confirmButton = {
            Button(onClick = onPrimaryClick) {
                Text(primaryButtonText)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onSecondaryClick) {
                Text(secondaryButtonText)
            }
        }
    )
}

// Preview giúp bạn xem giao diện mà không cần chạy cả ứng dụng
@Preview(showBackground = true)
@Composable
fun PreviewPermissionDialog() {
    PermissionDialog(
        icon = painterResource(id = R.drawable.outline_add_location_alt_24), // Dùng ảnh drawable để preview
        title = "Location",
        description = "Cấp phép cho ứng dụng mở quuyền truy cập Vị Trí ",
        primaryButtonText = "Đồng ý ",
        onPrimaryClick = {},
        secondaryButtonText = "Bỏ qua",
        onSecondaryClick = {},
        onDismiss = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCameraPermissionDialog() {
    PermissionDialog(
        icon = painterResource(id = R.drawable.baseline_camera_alt_24), // Replace with your camera icon
        title = "Máy ảnh",
        description = "Ứng dụng cần quyền truy cập máy ảnh để bạn có thể chụp ảnh.",
        primaryButtonText = "Đồng ý",
        onPrimaryClick = { /* Handle camera permission grant */ },
        secondaryButtonText = "Từ chối",
        onSecondaryClick = { /* Handle camera permission denial */ },
        onDismiss = { /* Handle dialog dismiss */ }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationPermissionDialog() {
    PermissionDialog(
        icon = painterResource(id = R.drawable.baseline_circle_notifications_24), // Replace with your notification icon
        title = "Thông báo",
        description = "Cho phép ứng dụng gửi thông báo để bạn không bỏ lỡ các cập nhật quan trọng.",
        primaryButtonText = "Cho phép",
        onPrimaryClick = { /* Handle notification permission grant */ },
        secondaryButtonText = "Để sau",
        onSecondaryClick = { /* Handle notification permission denial or deferral */ },
        onDismiss = { /* Handle dialog dismiss */ }
    )
}