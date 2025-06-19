package com.example.mvvm.ui.view


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.mvvm.data.model.ProjectDetail
import com.example.mvvm.ui.theme.CardGray
import com.example.mvvm.ui.theme.DarkBackground
import com.example.mvvm.ui.theme.LightBlue
import com.example.mvvm.viewmodel.DetailViewModel

import androidx.compose.foundation.rememberScrollState // <-- THÊM IMPORT NÀY
import androidx.compose.foundation.verticalScroll     // <-- THÊM IMPORT NÀY

// (V) - Màn hình chỉ hiển thị dữ liệu từ ViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = viewModel()
) {
    val detail by viewModel.projectDetail.collectAsState()

    Scaffold(
        containerColor = DarkBackground,
        topBar = { CenterAlignedTopAppBar(
            title = { Text("Detail", fontWeight = FontWeight.Bold, color = Color.Blue) },
            navigationIcon = {
                IconButton(
                    onClick = { /* Xử lý nút back nếu cần */
                        navController.popBackStack()},
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(LightBlue, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Blue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            )
        )}
    ) { paddingValues ->
        // Chỉ hiển thị nội dung nếu detail không null
        detail?.let {
            DetailContent(it, paddingValues)
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Hiển thị loading
        }
    }
}


@Composable
fun DetailContent(detail: ProjectDetail, paddingValues: PaddingValues) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // <-- THÊM DÒNG NÀY ĐỂ CHO PHÉP CUỘN
            .padding(paddingValues)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(detail.project.title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(Modifier.height(8.dp))
        Text(detail.project.subtitle, fontSize = 16.sp, color = Color.Black.copy(alpha = 0.7f))
        Spacer(Modifier.height(32.dp))

        Text("Subtasks", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(Modifier.height(16.dp))
        detail.subtasks.forEach { InfoCard(it) }

        Spacer(Modifier.height(32.dp))

        Text("Attachments", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(Modifier.height(16.dp))
        detail.attachments.forEach { InfoCard(it) }
    }
}

// Widget cho card thông tin trong màn hình Detail
@Composable
fun InfoCard(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(CardGray)
            .padding(16.dp)
    ) {
        // SỬA LẠI MÀU CHỮ NÀY ĐỂ RÕ HƠN TRÊN NỀN XÁM
        Text(
            text = text,
            color = Color(0xFF575454), // Chữ trắng sẽ dễ đọc hơn
            fontSize = 16.sp
        )
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFFFFF) // Nền đen để xem cho đúng
@Composable
fun DetailContentPreview() {
    // 1. Tạo dữ liệu giả (mock data) để hiển thị
    val mockProject = com.example.mvvm.data.model.Project(
        id = 1,
        title = "Complete Android Project",
        subtitle = "Finish the UI, integrate API, and write documentation",
        color = Color.Transparent // Màu không quan trọng ở màn hình này
    )
    val mockDetail = ProjectDetail(
        project = mockProject,
        subtasks = listOf(
            "This task is related to Fitness. It needs to be completed",
            "This task is related to Fitness. It needs to be completed",
            "This task is related to Fitness. It needs to be completed"
        ),
        attachments = listOf(
            "document_1_0.pdf",
            "document_1_0.pdf"
        )
    )

    // 2. Gọi trực tiếp Composable chứa UI và truyền dữ liệu giả vào
    MaterialTheme(
        colorScheme = darkColorScheme(background = White)
    ) {

        // Chỉ preview phần nội dung, không cần ViewModel hay NavController
       DetailContent(detail = mockDetail, paddingValues = PaddingValues(0.dp))
    }
}