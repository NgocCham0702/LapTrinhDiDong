package com.example.xinquyen.ui.Codelogicbandau_khongsudung

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.xinquyen.ViewModel.EventViewModel
import com.example.xinquyen.Model.LocationService
import com.example.xinquyen.View.QRScannerScreen
import com.example.xinquyen.ui.theme.XinquyenTheme
import com.google.accompanist.permissions.*
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XinquyenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CheckInApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckInApp() {
    // 1. Quản lý quyền Vị trí & Thông báo (cho Android 13+)
    val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions = permissionsToRequest)

    // Trạng thái của toàn bộ ứng dụng
    var showScanner by remember { mutableStateOf(false) }
    val eventViewModel: EventViewModel = viewModel()

    when {
        // Màn hình chính khi đã có đủ quyền ban đầu
        multiplePermissionsState.allPermissionsGranted -> {
            if (showScanner) {
                CameraPermissionHandler(
                    onPermissionGranted = {
                        QRScannerScreen(onQrCodeScanned = { qrCodeValue ->
                            eventViewModel.showCheckInSuccess.value = true
                            showScanner = false
                        })
                    },
                    onPermissionDenied = {
                        showScanner = false
                    }
                )
            } else {
                MainScreen(
                    eventViewModel = eventViewModel,
                    onCheckInClicked = { showScanner = true }
                )
            }
        }
        // Màn hình yêu cầu cấp quyền ban đầu
        else -> {
            PermissionRequestScreen(multiplePermissionsState)
        }
    }

    // Dialog thông báo check-in thành công
    if (eventViewModel.showCheckInSuccess.value) {
        AlertDialog(
            onDismissRequest = { eventViewModel.showCheckInSuccess.value = false },
            title = { Text("Thành công!") },
            text = { Text("Bạn đã check-in sự kiện thành công.") },
            confirmButton = {
                Button(onClick = { eventViewModel.showCheckInSuccess.value = false }) {
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

    // SỬA LỖI TẠI ĐÂY: Dùng `status.isGranted` thay vì `hasPermission`
    if (cameraPermissionState.status.isGranted) {
        onPermissionGranted()
    } else {
        // Hiển thị màn hình xin quyền camera khi chưa có quyền
        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }

        // Kiểm tra lại trạng thái sau khi yêu cầu
        if (cameraPermissionState.status.shouldShowRationale) {
            // Hiển thị giải thích tại sao cần quyền camera
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Yêu cầu quyền Camera", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Ứng dụng cần quyền truy cập camera để quét mã QR check-in. Vui lòng cấp quyền để tiếp tục.",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text("Thử lại")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onPermissionDenied) {
                        Text("Hủy bỏ")
                    }
                }
            }
        }
    }
}


@Composable
fun MainScreen(eventViewModel: EventViewModel, onCheckInClicked: () -> Unit) {
    val context = LocalContext.current
    val locationService = remember { LocationService(context) }

    // Lắng nghe cập nhật vị trí
    LaunchedEffect(Unit) {
        locationService.requestLocationUpdates().collect { location ->
            eventViewModel.updateUserLocation(location)
        }
    }

    val isUserInArea by eventViewModel.isUserInEventArea.collectAsState()
    val distance by eventViewModel.distanceToEvent.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("CHECK-IN SỰ KIỆN", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        if (distance != null) {
            val distanceInKm = distance!! / 1000
            val displayText = if (distanceInKm < 1) {
                "${distance!!.roundToInt()} mét"
            } else {
                String.format("%.2f km", distanceInKm)
            }
            Text("Bạn còn cách sự kiện:", fontSize = 18.sp)
            Text(displayText, fontSize = 32.sp, style = MaterialTheme.typography.headlineLarge)
        } else {
            Text("Đang xác định vị trí của bạn...", fontSize = 18.sp)
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (isUserInArea) {
            Text(
                "BẠN ĐÃ ĐẾN NƠI!",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Text("Mời bạn check-in để xác nhận tham dự.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onCheckInClicked,
                modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)
            ) {
                Text("Check-in (Quét mã QR)", fontSize = 18.sp)
            }
        }
    }
}

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
            "Để tính khoảng cách đến sự kiện và gửi thông báo, ứng dụng cần quyền Vị trí và Thông báo. Vui lòng cấp các quyền cần thiết.",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
            Text("Cấp quyền")
        }
    }
}