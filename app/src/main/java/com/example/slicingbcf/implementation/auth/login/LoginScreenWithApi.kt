package com.example.slicingbcf.implementation.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.ui.animations.AnimatedMessage
import com.example.slicingbcf.ui.animations.MessageType
import com.example.slicingbcf.ui.navigation.Screen
import com.example.slicingbcf.ui.navigation.navigateAndClearStack
import com.example.slicingbcf.ui.navigation.navigateSingleTop
import com.example.slicingbcf.ui.shared.CenteredAuthImage
import kotlinx.coroutines.delay


@Composable
fun LoginScreenWithApi(
  modifier : Modifier = Modifier,
  navController : NavHostController,
  viewModel : LoginViewModelWithApi = hiltViewModel()
) {
  val state by viewModel.uiState.collectAsState()

  val isPasswordVisible = remember { mutableStateOf(false) }
  fun onNavigateToForgotPassword() {
    navController.navigateSingleTop(Screen.Auth.ForgotPassword.route)
  }

  LaunchedEffect(state.isSuccess) {
    if (state.isSuccess) {
      delay(1500)
      navController.navigateAndClearStack(Screen.Home.route)
      viewModel.onEvent(LoginEvent.ClearState)
    }
  }

  Box(
    modifier = modifier
      .background(ColorPalette.OnPrimary)
      .statusBarsPadding()
      .padding(horizontal = 16.dp)
  ) {

    Column(
      verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
      modifier = Modifier.fillMaxSize()
    ) {
      TopSection()
      CenteredAuthImage()
      BottomSection(
        onNavigateToForgotPassword = { onNavigateToForgotPassword() },
        email = state.email,
        password = state.password,
        onEmailChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
        onPasswordChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
        onLoginClick = { viewModel.onEvent(LoginEvent.Submit) },
        emailError = state.emailError,
        passwordError = state.passwordError,
        isPasswordVisible = isPasswordVisible,
      )
    }

    AnimatedMessage(
      isVisible = state.isSuccess,
      message = state.message ?: "Login Success.",
      messageType = MessageType.Success,
      modifier = Modifier
        .padding(top = 16.dp)
        .align(Alignment.TopCenter)
    )

    AnimatedMessage(
      isVisible = state.error != null,
      message = state.error ?: "",
      messageType = MessageType.Error,
      modifier = Modifier
        .padding(top = 16.dp)
        .align(Alignment.TopCenter)
    )

  }
}
