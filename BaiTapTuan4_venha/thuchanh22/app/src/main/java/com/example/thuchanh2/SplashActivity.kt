package com.example.thuchanh2


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash) // Gọi layout splash.xml

        // Delay 2-3 giây rồi chuyển sang OnboardingActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish() // Kết thúc splash để không quay lại khi nhấn back
        }, 2000) // 2000 = 2 giây
    }
}
