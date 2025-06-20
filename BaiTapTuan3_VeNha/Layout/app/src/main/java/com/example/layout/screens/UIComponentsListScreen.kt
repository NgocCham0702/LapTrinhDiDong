package com.example.layout.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.layout.AppScreenNav // Import routes
import com.example.layout.ui.theme.LayoutTheme


data class ComponentGroup(val name: String, val items: List<ComponentItemData>)
data class ComponentItemData(
    val title: String,
    val description: String,
    val route: String,
    val isSpecial: Boolean = false
)

val topBarBlue = Color(0xFF007AFF)
val listItemBlueBackground = Color(0xFFE0F2FF)
val specialItemRedBackground = Color(0xFFFFD6D6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIComponentsListScreen(
    onComponentClick: (String) -> Unit,
    onSelfStudyClick: () -> Unit
) {
    val componentGroups = listOf(
        ComponentGroup(
            "Display", listOf(
                ComponentItemData("Text", "Displays text", AppScreenNav.TEXT_DETAIL),
                ComponentItemData("Image", "Displays an image", AppScreenNav.IMAGES)
            )
        ),
        ComponentGroup(
            "Input", listOf(
                ComponentItemData("TextField", "Input field for text", AppScreenNav.TEXT_FIELD),
                ComponentItemData("PasswordField", "Input field for passwords", AppScreenNav.TEXT_FIELD) // Tạm dùng TextField route
            )
        ),
        ComponentGroup(
            "Layout", listOf(
                ComponentItemData("Column", "Arranges elements vertically", AppScreenNav.COLUMN_LAYOUT_SCREEN), // Bạn cần tạo file này
                ComponentItemData("Row", "Arranges elements horizontally", AppScreenNav.ROW_LAYOUT)
            )
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "UI Components List",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = topBarBlue,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            componentGroups.forEach { group ->
                item {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }
                items(group.items) { itemData ->
                    ComponentListItem(itemData = itemData, onClick = { onComponentClick(itemData.route) })
                }
            }
            item {
                // "Tự tìm hiểu" item
                ComponentListItem(
                    itemData = ComponentItemData(
                        "Tự tìm hiểu",
                        "Tìm ra tất cả các thành phần UI Cơ bản",
                        AppScreenNav.SELF_STUDY, // Sử dụng route đã định nghĩa
                        isSpecial = true
                    ),
                    onClick = onSelfStudyClick // Sử dụng lambda riêng
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ComponentListItem(itemData: ComponentItemData, onClick: () -> Unit) {
    val backgroundColor = if (itemData.isSpecial) specialItemRedBackground else listItemBlueBackground
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            Text(itemData.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Text(itemData.description, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UIComponentsListScreenPreview() {
    LayoutTheme {
        UIComponentsListScreen(onComponentClick = {}, onSelfStudyClick = {})
    }
}