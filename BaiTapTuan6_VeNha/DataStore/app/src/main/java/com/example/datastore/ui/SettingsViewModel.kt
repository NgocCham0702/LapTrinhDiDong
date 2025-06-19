package com.example.datastore.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastore.data.AppTheme
import com.example.datastore.data.ThemeDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.util.Log // <-- IMPORT LỚP LOG

// Lớp State để chứa trạng thái tạm thời của UI
data class SettingsUiState(
    val selectedTheme: AppTheme = AppTheme.LIGHT
)

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "SettingsViewModel"
    private val dataStore = ThemeDataStore(application)

    // Trạng thái của theme đã được lưu và áp dụng toàn app
    val appliedTheme = dataStore.getTheme

    // Trạng thái của UI trên màn hình Settings (có thể chưa được "Apply")
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Log khi ViewModel được khởi tạo
        Log.d(TAG, "ViewModel được khởi tạo .")

        // Khi ViewModel khởi tạo, đọc theme đã lưu và cập nhật UI state
        viewModelScope.launch {
            appliedTheme.collectLatest { theme ->
                // Log mỗi khi theme đã lưu được đọc từ DataStore
                Log.d(TAG, "đọc theme từ datastore: $theme")

                _uiState.value = SettingsUiState(selectedTheme = theme)
            }
        }
    }

    // Khi người dùng chọn một màu mới
    fun onThemeSelected(theme: AppTheme) {
        // Log khi người dùng chọn một theme mới (chưa apply)
        Log.d(TAG, "Người dung chọn màu : $theme")
        _uiState.value = _uiState.value.copy(selectedTheme = theme)
    }

    // Khi người dùng nhấn nút "Apply"
    fun onApplyClicked() {
        // Log khi người dùng nhấn nút Apply
        val themeToSave = _uiState.value.selectedTheme
        Log.d(TAG, "người dùng nhất nút app lưu màu : $themeToSave")

        viewModelScope.launch {
            dataStore.saveTheme(_uiState.value.selectedTheme)
            // Không cần làm gì thêm, vì `appliedTheme` sẽ tự động cập nhật
            // và MainActivity sẽ render lại với theme mới.
            Log.i(TAG, "chọn $themeToSave lưu  lại .") // Dùng Log.i cho sự kiện quan trọng
        }
    }
}