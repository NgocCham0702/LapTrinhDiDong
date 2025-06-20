package com.example.layout

import com.example.layout.ui.theme.LayoutTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.layout.screens.* // Import tất cả các màn hình

object AppScreenNav {
    const val UI_COMPONENTS_LIST = "ui_components_list_screen"
    const val TEXT_DETAIL = "text_detail_screen"
    const val IMAGES = "images_screen"
    const val TEXT_FIELD = "text_field_screen"
    const val ROW_LAYOUT = "row_layout_screen"
    // Thêm route cho "Tự tìm hiểu" nếu nó là một màn hình riêng
    const val SELF_STUDY = "self_study_screen"
    const val COLUMN_LAYOUT_SCREEN = "column_layout_screen" // Or your desired route name
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LayoutTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigator()
                }
            }
        }
    }
}

@Composable
fun AppNavigator() {
    var currentScreen by remember { mutableStateOf(AppScreenNav.UI_COMPONENTS_LIST) }

    val navigateTo: (String) -> Unit = { route ->
        currentScreen = route
    }

    val navigateBack: () -> Unit = {
        currentScreen = AppScreenNav.UI_COMPONENTS_LIST // Luôn quay lại danh sách
    }

    when (currentScreen) {
        AppScreenNav.UI_COMPONENTS_LIST -> UIComponentsListScreen(
            onComponentClick = navigateTo,
            onSelfStudyClick = { navigateTo(AppScreenNav.SELF_STUDY) } // Ví dụ route cho "Tự tìm hiểu"
        )
        AppScreenNav.TEXT_DETAIL -> TextDetailScreen(onBackClick = navigateBack)
        AppScreenNav.IMAGES -> ImagesScreen(onBackClick = navigateBack)
        AppScreenNav.TEXT_FIELD -> TextFieldScreen(onBackClick = navigateBack)
        AppScreenNav.ROW_LAYOUT -> RowLayoutScreen(onBackClick = navigateBack)
        AppScreenNav.SELF_STUDY -> {
            // Tạo một Composable đơn giản cho màn hình "Tự tìm hiểu" hoặc một màn hình phức tạp hơn
            SelfStudyScreenPlaceholder(onBackClick = navigateBack)
        }
        else -> UIComponentsListScreen(onComponentClick = navigateTo, onSelfStudyClick = {}) // Màn hình mặc định
    }
}

@Composable
fun SelfStudyScreenPlaceholder(onBackClick: () -> Unit) {
    // Đây chỉ là placeholder, bạn có thể tạo file riêng cho màn hình này
    // Hoặc hiển thị nội dung tương tự như card "Tự tìm hiểu" lớn hơn
    // Ví dụ sử dụng lại UI từ bài tập trước (TextFieldScreen có TopAppBar)
    TextFieldScreen(onBackClick = onBackClick, title = "Tự Tìm Hiểu")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LayoutTheme {
        AppNavigator()
    }
}