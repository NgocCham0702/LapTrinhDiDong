// MainScreen.kt
package com.example.xinquyen.View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xinquyen.Model.LocationService
import com.example.xinquyen.ViewModel.EventViewModel
import kotlin.math.roundToInt
/*
@Composable
fun MainScreen(eventViewModel: EventViewModel,
               onCheckInClicked: () -> Unit,
               ) {
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
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text("Check-in (Quét mã QR)", fontSize = 18.sp)
            }
        }
    }
}

 */
@Composable
fun MainScreen(
    eventViewModel: EventViewModel,
    onCheckInClicked: () -> Unit,
    onViewHistoryClicked: () -> Unit // Thêm callback để xem lịch sử
) {
    val isEventSet by eventViewModel.isEventLocationSet

    if (isEventSet) {
        // Giao diện khi đã thiết lập địa điểm -> THEO DÕI KHOẢNG CÁCH
        LocationTrackingScreen(
            eventViewModel = eventViewModel,
            onCheckInClicked = onCheckInClicked,
            onSetNewLocationClicked = { eventViewModel.resetEvent() } // Nút để quay lại
        )
    } else {
        // Giao diện khi chưa thiết lập địa điểm -> NHẬP LIỆU
        LocationInputScreen(
            eventViewModel = eventViewModel,
            onViewHistoryClicked = onViewHistoryClicked
        )
    }
}

@Composable
fun LocationInputScreen(eventViewModel: EventViewModel, onViewHistoryClicked: () -> Unit) {
    val userInput by eventViewModel.userInputText
    val error by eventViewModel.parseError
    val isLoading by eventViewModel.isParsingLocation // <-- Lấy state đang tải

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Thiết lập Địa điểm Check-in", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Nhập link Google Maps hoặc tọa độ (vĩ độ, kinh độ).")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = userInput,
            onValueChange = { eventViewModel.userInputText.value = it },
            label = { Text("Link hoặc Tọa độ") },
            enabled = !isLoading, // Vô hiệu hóa khi đang tải

            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = error != null,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                eventViewModel.processUserInput()
            })
        )
        if (error != null) {
            Text(text = error!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 4.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút bấm sẽ hiển thị vòng xoay khi đang tải
        Button(
            onClick = {
                focusManager.clearFocus()
                eventViewModel.processUserInput()
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            enabled = !isLoading // Vô hiệu hóa nút khi đang tải
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Xác nhận Địa điểm")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onViewHistoryClicked,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Xem Lịch sử Check-in")
        }
    }
}

@Composable
fun LocationTrackingScreen(
    eventViewModel: EventViewModel,
    onCheckInClicked: () -> Unit,
    onSetNewLocationClicked: () -> Unit
) {
    val context = LocalContext.current
    val locationService = remember { LocationService(context) }

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
        verticalArrangement = Arrangement.Center
    ) {
        Text("ĐANG THEO DÕI", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        if (distance != null) {
            val distanceInKm = distance!! / 1000
            val displayText = if (distanceInKm < 1) {
                "${distance!!.roundToInt()} mét"
            } else { String.format("%.2f km", distanceInKm) }
            Text("Bạn còn cách sự kiện:", fontSize = 18.sp)
            Text(displayText, fontSize = 32.sp, style = MaterialTheme.typography.headlineLarge)
        } else {
            Text("Đang xác định vị trí của bạn...", fontSize = 18.sp)
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (isUserInArea) {
            Text("BẠN ĐÃ ĐẾN NƠI!", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleLarge)
            Text("Mời bạn check-in để xác nhận tham dự.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onCheckInClicked, modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)) {
                Text("Check-in (Quét mã QR)", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Đẩy nút này xuống dưới

        OutlinedButton(onClick = onSetNewLocationClicked) {
            Text("Thiết lập Địa điểm mới")
        }
    }
}