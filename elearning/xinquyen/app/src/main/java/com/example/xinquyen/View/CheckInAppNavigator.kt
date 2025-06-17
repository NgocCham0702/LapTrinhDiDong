// CheckInAppNavigator.kt
package com.example.xinquyen.View

import android.Manifest
import android.os.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.xinquyen.ViewModel.EventViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckInAppNavigator() {
    val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.POST_NOTIFICATIONS)
    } else { listOf(Manifest.permission.ACCESS_FINE_LOCATION) }
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions = permissionsToRequest)

    val eventViewModel: EventViewModel = viewModel()
    var showScanner by remember { mutableStateOf(false) }
    var showHistoryScreen by remember { mutableStateOf(false) } // State để điều hướng tới màn hình lịch sử

    when {
        // --- 1. Màn hình xin quyền ban đầu ---
        !multiplePermissionsState.allPermissionsGranted -> {
            PermissionRequestScreen(multiplePermissionsState = multiplePermissionsState)
        }
        // --- 2. Màn hình lịch sử ---
        showHistoryScreen -> {
            val history by eventViewModel.history.collectAsState(initial = emptyList())
            HistoryScreen(
                history = history,
                onBack = { showHistoryScreen = false }
            )
        }
        // --- 3. Màn hình quét QR ---
        showScanner -> {
            CameraPermissionHandler(
                onPermissionGranted = {
                    QRScannerScreen(onQrCodeScanned = {
                        eventViewModel.addCheckInToHistory() // LƯU VÀO LỊCH SỬ
                        eventViewModel.showCheckInSuccess.value = true
                        showScanner = false
                    })
                },
                onPermissionDenied = { showScanner = false }
            )
        }
        // --- 4. Màn hình chính (nhập liệu hoặc theo dõi) ---
        else -> {
            MainScreen(
                eventViewModel = eventViewModel,
                onCheckInClicked = { showScanner = true },
                onViewHistoryClicked = { showHistoryScreen = true } // Callback để mở lịch sử
            )
        }
    }

    // Dialog thông báo thành công
    if (eventViewModel.showCheckInSuccess.value) {
        AlertDialog(
            onDismissRequest = {
                eventViewModel.showCheckInSuccess.value = false
                eventViewModel.resetEvent() // Quay lại màn hình nhập liệu
            },
            title = { Text("Thành công!") },
            text = { Text("Bạn đã check-in sự kiện thành công. Lịch sử đã được lưu.") },
            confirmButton = {
                Button(onClick = {
                    eventViewModel.showCheckInSuccess.value = false
                    eventViewModel.resetEvent() // Quay lại màn hình nhập liệu
                }) {
                    Text("OK")
                }
            }
        )
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionHandler(
    onPermissionGranted: @Composable () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted) {
        onPermissionGranted()
    } else {
        // Tự động yêu cầu quyền khi Composable này được hiển thị
        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}