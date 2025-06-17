// File: app/src/main/java/com/example/xinquyen/ViewModel/EventViewModel.kt

package com.example.xinquyen.ViewModel

import android.app.Application
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.xinquyen.Model.CheckInRecord
import com.example.xinquyen.Model.HistoryRepository
import com.example.xinquyen.Model.LocationParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// <<< THÊM LẠI HẰNG SỐ BỊ THIẾU >>>
// Khoảng cách cho phép (tính bằng mét)
private const val ALLOWED_DISTANCE_METERS = 30.0

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val locationParser = LocationParser()

    // --- State cho việc nhập liệu và thiết lập sự kiện ---
    val userInputText = mutableStateOf("")
    val isEventLocationSet = mutableStateOf(false)
    val parseError = mutableStateOf<String?>(null)
    val isParsingLocation = mutableStateOf(false)

    // --- State cho việc theo dõi vị trí ---
    private val _eventLocation = MutableStateFlow<Location?>(null)
    private val _userLocation = MutableStateFlow<Location?>(null)
    val userLocation = _userLocation.asStateFlow()
    private val _distanceToEvent = MutableStateFlow<Float?>(null)
    val distanceToEvent = _distanceToEvent.asStateFlow()
    private val _isUserInEventArea = MutableStateFlow(false)
    val isUserInEventArea = _isUserInEventArea.asStateFlow()

    // --- State cho Lịch sử ---
    private val historyRepository = HistoryRepository(application)
    val history = historyRepository.history
    val showCheckInSuccess = mutableStateOf(false)

    // Hàm xử lý input của người dùng
    fun processUserInput() {
        val text = userInputText.value
        if (text.isBlank()) {
            parseError.value = "Vui lòng nhập địa chỉ hoặc tọa độ."
            return
        }

        viewModelScope.launch {
            isParsingLocation.value = true
            parseError.value = null

            val location = locationParser.parse(text)

            if (location != null) {
                _eventLocation.value = location
                isEventLocationSet.value = true
            } else {
                parseError.value = "Định dạng không hợp lệ hoặc không thể phân tích link. Vui lòng thử lại."
            }

            isParsingLocation.value = false
        }
    }

    // <<< SỬA LỖI Ở ĐÂY: Sửa tên tham số từ 'ocation' thành 'location' >>>
    fun updateUserLocation(location: Location) {
        _userLocation.value = location
        checkIfUserIsInArea()
    }

    private fun checkIfUserIsInArea() {
        // Không cần viewModelScope.launch ở đây vì các StateFlow đã xử lý luồng
        val eventLoc = _eventLocation.value
        val userLoc = _userLocation.value

        if (eventLoc != null && userLoc != null) {
            val distance = userLoc.distanceTo(eventLoc)
            _distanceToEvent.value = distance
            _isUserInEventArea.value = distance < ALLOWED_DISTANCE_METERS // <-- Sử dụng hằng số
        } else {
            _distanceToEvent.value = null
            _isUserInEventArea.value = false
        }
    }

    // Thêm bản ghi vào lịch sử sau khi check-in thành công
    fun addCheckInToHistory() {
        viewModelScope.launch {
            val record = CheckInRecord(
                locationInfo = userInputText.value,
                timestamp = System.currentTimeMillis(),
            )
            historyRepository.addCheckInRecord(record)
        }
    }

    // Quay lại màn hình nhập liệu
    fun resetEvent() {
        isEventLocationSet.value = false
        _eventLocation.value = null
        _distanceToEvent.value = null
        _isUserInEventArea.value = false
        userInputText.value = "" // Tùy chọn: Xóa input cũ để người dùng nhập mới
    }
}