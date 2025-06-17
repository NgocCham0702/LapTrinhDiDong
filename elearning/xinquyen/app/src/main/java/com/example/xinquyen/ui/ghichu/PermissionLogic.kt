package com.example.xinquyen.ui.ghichu
//PermissionLogic.kt
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import com.example.xinquyen.ViewModel.PermissionDialog
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionLogic(
    permissionState: PermissionState,
    icon: Painter, // <-- SỬA LỖI: Thống nhất dùng Painter
    title: String,
    description: String,
    primaryButtonText: String,
    onResult: () -> Unit
) {
    // Chỉ hiển thị dialog nếu quyền chưa được cấp
    var showDialog by remember { mutableStateOf(!permissionState.status.isGranted) }

    if (showDialog) {
        PermissionDialog(
            icon = icon, // <-- Truyền painter xuống
            title = title,
            description = description,
            primaryButtonText = primaryButtonText,
            onPrimaryClick = {
                permissionState.launchPermissionRequest() // Gửi yêu cầu xin quyền
            },
            secondaryButtonText = "Bỏ qua bây giờ",
            onSecondaryClick = {
                showDialog = false // Tắt dialog khi bấm "Skip"
            },
            onDismiss = {
                showDialog = false // Tắt dialog khi bấm ra ngoài
            }
        )
    }

    // Khi trạng thái dialog hoặc quyền thay đổi
    LaunchedEffect(showDialog, permissionState.status) {
        // Nếu dialog đã tắt, hoặc quyền đã được cấp -> chuyển sang quyền tiếp theo
        if (!showDialog || permissionState.status.isGranted) {
            onResult()
        }
    }
}