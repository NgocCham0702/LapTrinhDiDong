package com.example.thuchanh_1

import android.graphics.Color
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun GmailLoginScreen() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        messageState.value = Message.Success(user?.email ?: "")
                    } else {
                        messageState.value = Message.Error("Firebase login failed.")
                    }
                }
        } catch (e: ApiException) {
            messageState.value = Message.Error("Google Sign-In Failed\n${e.localizedMessage}")
        }
    }

    val messageState = remember { mutableStateOf<Message>(Message.None) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("AIzaSyD6QOYx721b2IWMCfRPx_boJfTQRAnbPf4") // Lấy trong Firebase > Authentication > Sign-in method
                    .requestEmail()
                    .build()
                val client = GoogleSignIn.getClient(context, gso)
                launcher.launch(client.signInIntent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Login by Gmail", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val msg = messageState.value) {
            is Message.Error -> MessageBox(msg.text, Color(0xFFFFCDD2))
            is Message.Success -> MessageBox(
                "Success!\nHi ${msg.email}\nWelcome to UTHSmartTasks",
                Color(0xFFB3E5FC)
            )
            else -> {}
        }
    }
}

@Composable
fun MessageBox(message: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(message, fontWeight = FontWeight.SemiBold)
    }
}

sealed class Message {
    data object None : Message()
    data class Error(val text: String) : Message()
    data class Success(val email: String) : Message()
}
