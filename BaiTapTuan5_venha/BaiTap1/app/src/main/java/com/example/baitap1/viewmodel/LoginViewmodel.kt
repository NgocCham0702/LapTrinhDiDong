package com.example.baitap1.viewmodel // << HÃY THAY ĐỔI CHO PHÙ HỢP VỚI CẤU TRÚC DỰ ÁN CỦA BẠN

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baitap1.R // << KIỂM TRA LẠI ĐƯỜNG DẪN NÀY
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Sealed class cho các trạng thái kết quả đăng nhập
sealed class SignInResult {
    data object Loading : SignInResult() // Trạng thái đang xử lý
    data object Success : SignInResult() // Đăng nhập thành công
    data class Error(val message: String) : SignInResult() // Có lỗi xảy ra
    data object Idle : SignInResult() // Trạng thái ban đầu hoặc sau khi đã xử lý xong
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = Firebase.auth
    val googleSignInClient: GoogleSignInClient

    private val _signInResult = MutableLiveData<SignInResult>(SignInResult.Idle)
    val signInResult: LiveData<SignInResult> = _signInResult

    init {
        // Cấu hình Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // Đảm bảo bạn đã thêm string resource này vào file strings.xml
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(application, gso)
    }

    /**
     * Khởi chạy Intent để người dùng chọn tài khoản Google.
     */
    fun launchGoogleSignIn(launcher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    /**
     * Xử lý kết quả trả về từ Google Sign-In Activity.
     */
    fun handleGoogleSignInResult(data: Intent?) {
        _signInResult.value = SignInResult.Loading
        viewModelScope.launch {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                // Lấy tài khoản Google đã chọn
                val account = task.getResult(ApiException::class.java)!!
                Log.d("LoginViewModel", "Google Sign-In successful. Account ID: ${account.id}")
                // Xác thực với Firebase sử dụng Google ID token
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w("LoginViewModel", "Google sign in failed", e)
                _signInResult.value = SignInResult.Error("Google sign in failed: ${e.message} (Code: ${e.statusCode})")
            } catch (e: Exception) {
                // Các lỗi không mong muốn khác
                Log.e("LoginViewModel", "An unexpected error occurred during Google sign in", e)
                _signInResult.value = SignInResult.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    /**
     * Xác thực với Firebase sử dụng ID token từ tài khoản Google.
     */
    private suspend fun firebaseAuthWithGoogle(idToken: String) {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await() // Đợi kết quả xác thực
            _signInResult.value = SignInResult.Success
            Log.d("LoginViewModel", "Firebase authentication successful")
        } catch (e: Exception) {
            Log.w("LoginViewModel", "Firebase authentication failed", e)
            _signInResult.value = SignInResult.Error("Firebase authentication failed: ${e.message}")
        }
    }

    /**
     * Đặt lại trạng thái đăng nhập về Idle.
     * Nên gọi sau khi đã xử lý xong kết quả (ví dụ, sau khi điều hướng hoặc hiển thị thông báo).
     */
    fun resetSignInResult() {
        _signInResult.value = SignInResult.Idle
    }
}