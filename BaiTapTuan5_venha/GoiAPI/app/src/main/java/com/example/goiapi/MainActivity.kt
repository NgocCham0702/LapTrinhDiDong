package com.example.goiapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.goiapi.ui.theme.GoiAPITheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoiAPITheme {
                ProductDetailScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(viewModel: ProductViewModel = ProductViewModel()) {
    val product = viewModel.product.collectAsState().value

    if (product == null) {.
        CircularProgressIndicator()
        return
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon tròn
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00AEEF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(50.dp))

            // Text căn giữa theo chiều dọc
            Text(
                text = "Product Detail",
                color = Color(0xFF00AEEF),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Product Image
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Title
        Text(
            text = product.name, fontWeight = FontWeight.Bold, fontSize = 20.sp,
            color = Color.Black
            )

        // Price
        Text(text = "Giá: ${product.price}", color = Color.Red, fontSize = 18.sp)


        // Product Description
        Surface(
            color = Color(0xFFF1F1F1),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = product.description
                ,color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyTopAppBarPreview() {
    ProductDetailScreen()
}