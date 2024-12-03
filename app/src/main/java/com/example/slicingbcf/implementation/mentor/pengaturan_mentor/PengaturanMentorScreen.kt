package com.dicoding.lead

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.zIndex // Import zIndex

val poppinsRegular = FontFamily(
    Font(R.font.poppins_regular)
)

@Composable
fun ChangePasswordScreen() {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Navbar()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 90.dp) // Ensure padding to avoid overlap with Navbar
                .verticalScroll(rememberScrollState()), // Added import for scroll state
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Pengaturan",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1F1F1F),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontFamily = poppinsRegular)
            )

            Text(
                text = "Ubah Kata Sandi",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1F1F1F),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = TextStyle(fontFamily = poppinsRegular)
            )

            Text(
                text = "Kata Sandi Saat Ini",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1F1F1F),
                style = TextStyle(fontFamily = poppinsRegular)
            )
            PasswordInputField(
                hint = "Masukkan Kata Sandi saat ini",
                password = currentPassword,
                onPasswordChange = { currentPassword = it },
                passwordVisible = currentPasswordVisible,
                onVisibilityChange = { currentPasswordVisible = !currentPasswordVisible },
                isError = showError
            )

            Text(
                text = "Kata Sandi Baru",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1F1F1F),
                style = TextStyle(fontFamily = poppinsRegular)
            )
            PasswordInputField(
                hint = "Masukkan Kata Sandi Anda",
                password = newPassword,
                onPasswordChange = { newPassword = it },
                passwordVisible = newPasswordVisible,
                onVisibilityChange = { newPasswordVisible = !newPasswordVisible },
                isError = showError
            )

            Text(
                text = "Konfirmasi Kata Sandi Baru",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1F1F1F),
                style = TextStyle(fontFamily = poppinsRegular)
            )
            PasswordInputField(
                hint = "Masukkan Kata Sandi Anda",
                password = confirmPassword,
                onPasswordChange = { confirmPassword = it },
                passwordVisible = confirmPasswordVisible,
                onVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible },
                isError = showError
            )

            if (showError) {
                Text(
                    text = "Anda hanya dapat mengganti password setiap 2 minggu sekali.",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFDC3545),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = TextStyle(fontFamily = poppinsRegular)
                )
            }

            Button(
                onClick = {
                    // Example validation logic
                    showError = true // Show error message
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(1.dp, Color(0xFF0D4690), shape = RoundedCornerShape(8.dp))
                    .background(Color(0xFF0D4690), shape = RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0D4690),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Ubah Password",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        lineHeight = 19.6.sp
                    ),
                    modifier = Modifier.width(110.dp)
                )
            }
        }
    }
}

@Composable
fun Navbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(color = Color.White)
            .shadow(elevation = 2.dp)
            .zIndex(1f), // Added zIndex import
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(width = 80.dp, height = 35.dp),
            contentScale = ContentScale.Fit
        )

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Sidebar Icon",
                modifier = Modifier
                    .size(width = 70.dp, height = 35.dp)
            )
        }
    }
}

@Composable
fun PasswordInputField(
    hint: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibilityChange: () -> Unit,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = {
            Text(
                text = hint,
                color = Color(0xFF858585),
                style = TextStyle(fontSize = 13.sp, fontFamily = poppinsRegular)
            )
        },
        singleLine = true,
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = onVisibilityChange) {
                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(imageVector = icon, contentDescription = null)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .height(47.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isError) Color(0xFFDC3545) else MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = if (isError) Color(0xFFDC3545) else Color.Gray,
            textColor = Color.Black
        )
    )
}

@Preview
@Composable
fun Preview() {
    ChangePasswordScreen()
}
