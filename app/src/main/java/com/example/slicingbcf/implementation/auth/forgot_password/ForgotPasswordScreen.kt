@file:Suppress("t")

package com.example.slicingbcf.implementation.auth.forgot_password

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.implementation.auth.login.ForgotPasswordEvent
import com.example.slicingbcf.implementation.auth.login.ForgotPasswordViewModel
import com.example.slicingbcf.ui.navigation.Screen
import com.example.slicingbcf.ui.shared.CenteredAuthImage
import com.example.slicingbcf.ui.shared.CenteredLogo
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.message.ErrorMessage
import com.example.slicingbcf.ui.shared.message.SuccessMessage
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField

@Composable
fun ForgotPasswordScreen(
  modifier : Modifier = Modifier,
  viewModel : ForgotPasswordViewModel = hiltViewModel(),
  navController : NavHostController
) {
  val state by viewModel.state.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(state) {
    if (state.isSuccess) {
      snackbarHostState.showSnackbar(
        message = "Permintaan berhasil, silakan cek email Anda.",
        actionLabel = "Tutup",
        duration = SnackbarDuration.Long
      )
      viewModel.onEvent(ForgotPasswordEvent.ClearState)
    }

    state.error?.let {
      snackbarHostState.showSnackbar(
        message = it,
        actionLabel = "Tutup",
        duration = SnackbarDuration.Long
      )
      viewModel.onEvent(ForgotPasswordEvent.ClearState)
    }
  }


  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) }
  ) { paddingValues ->
    Column(
      verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
      modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(paddingValues)
    ) {
      TopSection()
      CenteredAuthImage()

      BottomSection(
        email = state.email,
        onChange = { email -> viewModel.onEvent(ForgotPasswordEvent.EmailChanged(email)) },
        emailError = state.emailError,
        onSubmit = { viewModel.onEvent(ForgotPasswordEvent.Submit) },
        navigateToLogin = { navController.navigate(Screen.Auth.Login.route) }
      )

      AnimatedVisibility(state.isSuccess) {
        SuccessMessage(
          message = "Permintaan berhasil, silakan cek email Anda."
        )
      }

      AnimatedVisibility(state.error != null) {
        ErrorMessage(
          message = state.error ?: ""
        )
      }
    }
  }
}


@Composable
fun TopSection() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    CenteredLogo()
    Text(
      text = "Atur Ulang Kata Sandi",
      style = StyledText.MobileLargeSemibold
    )
    Text(
      text = "Masukkan email akun yang sudah didaftarkan",
      style = StyledText.MobileSmallRegular
    )

  }
}

@Composable
fun BottomSection(
  email : String,
  onChange : (String) -> Unit,
  emailError : String?,
  onSubmit : () -> Unit,
  navigateToLogin : () -> Unit = { }
) {

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp),
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),

    ) {

    CustomOutlinedTextField(
      value = email,
      onValueChange = { onChange(it) },
      label = "Email Peserta",
      placeholder = "contoh: @gmail.com",
      keyboardType = KeyboardType.Email,
      error = emailError,
      modifier = Modifier.fillMaxWidth(),
      rounded = 40
    )

  }
  Column(
    modifier = Modifier.padding(
      horizontal = 16.dp
    ),
    verticalArrangement = Arrangement.spacedBy(32.dp),
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth(),
      contentAlignment = Alignment.Center,
    ) {

      PrimaryButton(
        text = "Kirim",
        onClick = onSubmit,
        modifier = Modifier.fillMaxWidth(),
        style = StyledText.MobileSmallMedium,


        )
    }
    GotoLogin(navigateToLogin)
  }

}

@Composable
fun GotoLogin(navigateToLogin : () -> Unit) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
    modifier = Modifier
      .fillMaxWidth()
  ) {
    Text(
      text = "Ingat kata sandi? ",
      style = StyledText.MobileSmallRegular
    )

    Text(
      text = buildAnnotatedString {
        withStyle(
          style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Medium
          )
        ) {
          append("Masuk")
        }
      },
      modifier = Modifier.clickable { navigateToLogin() },
      style = StyledText.MobileSmallRegular,
      color = ColorPalette.Black
    )
  }
}
