package com.example.myapplication
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape // Để tạo hình tròn cho avatar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip // Để áp dụng CircleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter // Để tải ảnh từ URL (thay thế cho rememberAsyncImagePainter từ thư viện khác nếu bạn đang dùng Coil)
// HOẶC nếu bạn đang dùng thư viện accompanist-coil (cũ hơn):
// import com.google.accompanist.coil.rememberCoilPainter
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    var birthday by remember { mutableStateOf("23/05/1995") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        user?.photoUrl?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(Modifier.height(16.dp))
        Text("Name", style = MaterialTheme.typography.labelMedium)
        OutlinedTextField(
            value = user?.displayName ?: "",
            onValueChange = {},
            enabled = false
        )
        Spacer(Modifier.height(8.dp))
        Text("Email", style = MaterialTheme.typography.labelMedium)
        OutlinedTextField(
            value = user?.email ?: "",
            onValueChange = {},
            enabled = false
        )
        Spacer(Modifier.height(8.dp))
        Text("Date of Birth", style = MaterialTheme.typography.labelMedium)
        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text("Ngày sinh") },
            singleLine = true
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("login") {
                    popUpTo("profile") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}

