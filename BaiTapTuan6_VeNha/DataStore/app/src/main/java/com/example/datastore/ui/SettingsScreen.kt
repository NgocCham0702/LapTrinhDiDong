package com.example.datastore.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datastore.data.AppTheme
import com.example.datastore.ui.theme.*

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    // Lấy trạng thái UI từ ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Màu nền thay đổi theo theme
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "Setting",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground // Màu chữ thay đổi theo theme
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Choosing the right theme sets the tone and personality of your app",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Hàng chứa các lựa chọn màu
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColorSwatch(
                color = AppBlue,
                isSelected = uiState.selectedTheme == AppTheme.BLUE,
                onClick = { viewModel.onThemeSelected(AppTheme.BLUE) }
            )
            ColorSwatch(
                color = AppMagenta,
                isSelected = uiState.selectedTheme == AppTheme.MAGENTA,
                onClick = { viewModel.onThemeSelected(AppTheme.MAGENTA) }
            )
            ColorSwatch(
                color = AppDarkGray,
                isSelected = uiState.selectedTheme == AppTheme.DARK,
                onClick = { viewModel.onThemeSelected(AppTheme.DARK) }
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        // Nút Apply
        Button(
            onClick = { viewModel.onApplyClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Apply, // Giữ màu nút Apply không đổi
                contentColor = Color.White
            )
        ) {
            Text(text = "Apply", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ColorSwatch(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val border = if (isSelected) BorderStroke(3.dp, Color.Black) else null
    Box(
        modifier = Modifier
            .size(width = 80.dp, height = 50.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .then(if (border != null) Modifier.border(border, RoundedCornerShape(16.dp)) else Modifier)
            .clickable(onClick = onClick)
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    DataStoreTheme {
        SettingsScreen()
    }
}