// File: app/src/main/java/com/example/xinquyen/Model/HistoryRepository.kt

package com.example.xinquyen.Model // <-- "Địa chỉ" mới của file, đảm bảo đúng

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore // <-- Import cần thiết cho "công thức"
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

// <<< THÊM LẠI DÒNG NÀY - ĐÂY LÀ "CÔNG THỨC" TẠO RA KHO LƯU TRỮ >>>
// Dòng này định nghĩa một thuộc tính mở rộng 'dataStore' cho lớp Context.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "check_in_history")

private const val TAG = "HistoryRepo"

class HistoryRepository(private val context: Context) {

    private val HISTORY_KEY = stringPreferencesKey("history_list")

    // Dùng Serializer tường minh để tránh lỗi
    private val listSerializer = ListSerializer(CheckInRecord.serializer())

    // Lấy danh sách lịch sử dưới dạng một Flow
    val history: Flow<List<CheckInRecord>> = context.dataStore.data // <-- Giờ đây nó sẽ hoạt động
        .map { preferences ->
            val jsonString = preferences[HISTORY_KEY]
            if (jsonString != null && jsonString.isNotEmpty()) {
                try {
                    Json.decodeFromString(listSerializer, jsonString)
                } catch (e: Exception) {
                    Log.e(TAG, "Lỗi giải mã lịch sử: ", e)
                    emptyList()
                }
            } else {
                emptyList()
            }
        }

    // Thêm một bản ghi mới vào lịch sử
    suspend fun addCheckInRecord(record: CheckInRecord) {
        context.dataStore.edit { preferences -> // <-- Giờ đây nó sẽ hoạt động
            val currentJsonString = preferences[HISTORY_KEY]
            val currentList = if (currentJsonString != null && currentJsonString.isNotEmpty()) {
                try {
                    Json.decodeFromString(listSerializer, currentJsonString).toMutableList()
                } catch (e: Exception) {
                    Log.e(TAG, "Lỗi giải mã chuỗi JSON cũ, tạo danh sách mới: ", e)
                    mutableListOf()
                }
            } else {
                mutableListOf()
            }

            currentList.add(0, record)

            val newJsonString = Json.encodeToString(listSerializer, currentList)
            preferences[HISTORY_KEY] = newJsonString
        }
    }
}