package com.example.xinquyen.View

// PermissionRequestScreen.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequestScreen(multiplePermissionsState: MultiplePermissionsState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Yêu cầu quyền truy cập", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Để tính khoảng cách đến sự kiện và gửi thông báo, ứng dụng cần quyền Vị trí và Thông báo." +
                    " Vui lòng cấp các quyền cần thiết.",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
            Text("Cấp quyền")
        }
    }
}

// Preview Function rút gọn
@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true, name = "Permission Request Screen Preview")
@Composable
fun PermissionRequestScreenSimplePreview() {
    // Tạo một implementation giả đơn giản nhất cho MultiplePermissionsState
    val dummyPermissionsState = object : MultiplePermissionsState {
        override val allPermissionsGranted: Boolean = false
        override val permissions: List<PermissionState> = emptyList()
        override val revokedPermissions: List<PermissionState> = emptyList()
        override val shouldShowRationale: Boolean = false
        override fun launchMultiplePermissionRequest() {
            // Không làm gì cả trong preview này khi nút được nhấn
            println("Preview: Button clicked, launchMultiplePermissionRequest would be called.")
        }
    }

    MaterialTheme { // Hoặc Theme tùy chỉnh của bạn
        PermissionRequestScreen(multiplePermissionsState = dummyPermissionsState)
    }
}