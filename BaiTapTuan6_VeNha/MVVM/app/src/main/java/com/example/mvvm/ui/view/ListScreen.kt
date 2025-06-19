package com.example.mvvm.ui.view
import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.mvvm.data.model.Project
import com.example.mvvm.ui.theme.LightBlue
import com.example.mvvm.viewmodel.ListViewModel


// (V) - Màn hình chỉ hiển thị dữ liệu từ ViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = viewModel()
) {
    // Lắng nghe sự thay đổi của state
    val projects by viewModel.projects.collectAsState()
    val activity = LocalActivity.current

    Scaffold(
        topBar = { CenterAlignedTopAppBar(
            title = { Text("List", fontWeight = FontWeight.Bold, color = Color.Blue) },
            navigationIcon = {
                IconButton(
                    onClick = { /* Xử lý nút back nếu cần */
                        activity?.finish()},
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(LightBlue, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            )
        ) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dùng hàm items để vẽ danh sách từ state
            items(projects) { project ->
                ProjectListItem(
                    project = project,
                    onClick = {
                        // Gửi sự kiện: điều hướng đến màn hình detail với ID
                        navController.navigate("detail/${project.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun ProjectListItem(project: Project, onClick: () -> Unit) {
    val contentColor =  Color.Black

    val textAlpha = if (project.isFaded) 0.5f else 1f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(project.color)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = project.title,
                color = Color.Black, // Màu chữ giờ sẽ tự động là đen hoặc trắng
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.subtitle,
                color = Color.Black, // Tương tự cho phụ đề
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(20.dp))
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ListScreenPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme(background = White)
    ) {
        ListScreen(navController = rememberNavController())
    }
}