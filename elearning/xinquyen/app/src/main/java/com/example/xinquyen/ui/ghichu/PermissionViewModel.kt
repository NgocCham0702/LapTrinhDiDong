package com.example.xinquyen.ui.ghichu

import androidx.lifecycle.ViewModel
import com.example.xinquyen.Model.PermissionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PermissionViewModel : ViewModel() {
    // Hàng đợi các quyền cần xin
    private val permissionQueue = mutableListOf(
        PermissionType.LOCATION,
        PermissionType.NOTIFICATION,
        PermissionType.CAMERA
    )

    private val _visiblePermissionDialog =
        MutableStateFlow<PermissionType?>(permissionQueue.firstOrNull())
    val visiblePermissionDialog = _visiblePermissionDialog.asStateFlow()

    // Được gọi khi một quyền đã được xử lý (đồng ý hoặc bỏ qua)
    fun onPermissionResult() {
        permissionQueue.removeFirstOrNull()
        // Cập nhật để hiển thị quyền tiếp theo, hoặc null nếu hết
        _visiblePermissionDialog.value = permissionQueue.firstOrNull()
    }
}