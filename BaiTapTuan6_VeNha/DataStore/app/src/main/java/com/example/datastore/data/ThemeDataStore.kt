package com.example.datastore.data


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Định nghĩa một enum để quản lý các theme, an toàn hơn dùng chuỗi String
enum class AppTheme {
    LIGHT,
    BLUE,
    MAGENTA,
    DARK
}

class ThemeDataStore(private val context: Context) {
    // Khởi tạo DataStore
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("app_settings")
        private val THEME_KEY = stringPreferencesKey("app_theme")
    }

    // Luồng (Flow) để đọc theme đã lưu. Nếu chưa có, trả về theme LIGHT
    val getTheme: Flow<AppTheme> = context.dataStore.data.map { preferences ->
        val themeName = preferences[THEME_KEY] ?: AppTheme.LIGHT.name
        AppTheme.valueOf(themeName)
    }

    // Hàm để lưu theme mới
    suspend fun saveTheme(theme: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.name
        }
    }
}