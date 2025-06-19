package com.example.datastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.datastore.ui.SettingsScreen
import com.example.datastore.ui.SettingsViewModel
import com.example.datastore.ui.theme.DataStoreTheme

class MainActivity : ComponentActivity() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Lắng nghe theme đã được lưu từ ViewModel
            val appliedTheme by viewModel.appliedTheme.collectAsStateWithLifecycle(initialValue = null)

            // Chỉ render khi theme đã được load
            appliedTheme?.let { theme ->
                DataStoreTheme (dynamicTheme = theme) {
                    SettingsScreen(viewModel = viewModel)
                }
            }
        }
    }
}
