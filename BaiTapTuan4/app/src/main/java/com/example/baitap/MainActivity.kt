package com.example.baitap // Package của bạn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme // Import MaterialTheme
import androidx.compose.material3.Surface       // Import Surface
// import androidx.compose.material3.Scaffold // Không cần Scaffold ở đây nữa nếu AppThreeScreenNavigator là gốc
// import androidx.compose.material3.Text    // Không cần Text ở đây nữa
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.baitap.ui.theme.BaiTapTuan4Theme // Đảm bảo Theme được import đúng

// Import các màn hình của bạn từ package 'screens'
// GIẢ SỬ các file màn hình của bạn nằm trong package com.example.baitap.screens
import com.example.baitap.screens.DetailScreen
import com.example.baitap.screens.LazyColumnScreen
import com.example.baitap.screens.PushNotificationScreen
import com.example.baitap.screens.ListItemData // Import ListItemData từ nơi bạn định nghĩa nó

// Định nghĩa các route cho 3 màn hình
object ThreeScreenNav {
    const val PUSH_NOTIFICATION = "push_notification_screen"
    const val LAZY_COLUMN = "lazy_column_screen"
    const val DETAIL = "detail_screen"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Xem xét việc giữ lại hoặc bỏ đi tùy theo nhu cầu của bạn
        setContent {
            BaiTapTuan4Theme {
                // Surface là một container tốt cho toàn bộ ứng dụng
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background // Lấy màu nền từ Theme
                ) {
                    AppThreeScreenNavigator() // Gọi Composable điều hướng chính
                }
            }
        }
    }
}

@Composable
fun AppThreeScreenNavigator() {
    var currentScreen by remember { mutableStateOf(ThreeScreenNav.PUSH_NOTIFICATION) }
    var selectedListItemData by remember { mutableStateOf<ListItemData?>(null) }

    val navigateTo: (String) -> Unit = { route ->
        currentScreen = route
    }

    val navigateToDetail: (ListItemData) -> Unit = { itemData ->
        selectedListItemData = itemData
        currentScreen = ThreeScreenNav.DETAIL
    }

    when (currentScreen) {
        ThreeScreenNav.PUSH_NOTIFICATION -> PushNotificationScreen(
            onPushClick = { navigateTo(ThreeScreenNav.LAZY_COLUMN) }
        )
        ThreeScreenNav.LAZY_COLUMN -> LazyColumnScreen(
            onItemClick = { itemData -> navigateToDetail(itemData) }
            // Nếu bạn muốn LazyColumnScreen có nút back riêng để về PUSH_NOTIFICATION:
            // onBackClick = { navigateTo(ThreeScreenNav.PUSH_NOTIFICATION) }
        )
        ThreeScreenNav.DETAIL -> DetailScreen(
            quote = selectedListItemData?.text ?: "No data available",
            author = "Item ID: ${selectedListItemData?.id ?: "N/A"}",
            sourceUrl = "http://example.com/item/${selectedListItemData?.id ?: "na"}",
            onBackClick = { navigateTo(ThreeScreenNav.LAZY_COLUMN) },
            onBackToRootClick = { navigateTo(ThreeScreenNav.PUSH_NOTIFICATION) }
        )
        else -> PushNotificationScreen(onPushClick = { navigateTo(ThreeScreenNav.LAZY_COLUMN) }) // Màn hình mặc định
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultAppPreview() { // Đổi tên Preview để rõ ràng hơn
    BaiTapTuan4Theme {
        AppThreeScreenNavigator() // Preview Composable điều hướng chính
    }
}