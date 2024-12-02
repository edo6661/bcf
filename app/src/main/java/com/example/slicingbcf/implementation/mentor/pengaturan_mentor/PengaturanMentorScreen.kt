package com.example.slicingbcf.implementation.mentor.pengaturan_mentor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.implementation.peserta.pengaturan.PengaturanEvent
import com.example.slicingbcf.implementation.peserta.pengaturan.PengaturanState
import com.example.slicingbcf.implementation.peserta.pengaturan.PengaturanViewModel
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField

@Composable
fun PengaturanMentorScreen(
  modifier: Modifier = Modifier,
  viewModel: PengaturanViewModel = hiltViewModel()
) {
  val scrollState = rememberScrollState()
  val uiState by viewModel.uiState.collectAsState()


  Column(
    modifier = modifier
      .statusBarsPadding()
      .padding(horizontal = 16.dp)
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.spacedBy(36.dp)
  ) {
    Text(
      text = "Pengaturan",
      style = StyledText.MobileLargeMedium,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Text(
        text = "Ubah Kata Sandi",
        style = StyledText.MobileMediumSemibold,
        color = ColorPalette.PrimaryColor700
      )
      Forms(
        uiState = uiState,
        onOldPasswordChanged = { viewModel.onEvent(PengaturanEvent.OldPasswordChanged(it)) },
        onNewPasswordChanged = { viewModel.onEvent(PengaturanEvent.NewPasswordChanged(it)) },
        onConfirmPasswordChanged = { viewModel.onEvent(PengaturanEvent.ConfirmPasswordChanged(it)) },
        onSubmit = { viewModel.onEvent(PengaturanEvent.Submit) }
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Forms(
  uiState: PengaturanState,
  onOldPasswordChanged: (String) -> Unit,
  onNewPasswordChanged: (String) -> Unit,
  onConfirmPasswordChanged: (String) -> Unit,
  onSubmit: () -> Unit
) {
  val isPasswordVisible = remember { mutableStateOf(false) }
  val isNewPasswordVisible = remember { mutableStateOf(false) }
  val isConfirmationPasswordVisible = remember { mutableStateOf(false) }
  var showDialog by remember { mutableStateOf(false) }

  TextFieldWithTitle(
    title = "Kata Sandi Lama",
    placeholder = "Masukkan kata sandi lama",
    label = "Kata Sandi Lama",
    isPasswordVisible = isPasswordVisible,
    value = uiState.oldPassword,
    onValueChange = onOldPasswordChanged,
    error = uiState.oldPasswordError,

    )
  TextFieldWithTitle(
    title = "Kata Sandi Baru",
    placeholder = "Masukkan kata sandi baru",
    label = "Kata Sandi Baru",
    isPasswordVisible = isNewPasswordVisible,
    value = uiState.newPassword,
    onValueChange = onNewPasswordChanged,
    error = uiState.newPasswordError
  )
  TextFieldWithTitle(
    title = "Konfirmasi Kata Sandi",
    placeholder = "Masukkan konfirmasi kata sandi",
    label = "Konfirmasi Kata Sandi",
    isPasswordVisible = isConfirmationPasswordVisible,
    value = uiState.confirmPassword,
    onValueChange = onConfirmPasswordChanged,
    error = uiState.confirmPasswordError
  )
  Text(
    text = "*) Anda hanya dapat mengganti kata sandi setiap 2 minggu sekali",
    style = StyledText.MobileSmallRegular,
    color = ColorPalette.Danger500
  )
  Spacer(modifier = Modifier.height(16.dp))
  PrimaryButton(
    text = if (uiState.isLoading) "Mengubah..." else "Ubah Kata Sandi",
    style = StyledText.MobileSmallMedium,
    onClick = onSubmit,
    modifier = Modifier.fillMaxWidth(),
    textColor = ColorPalette.OnPrimary,
    isEnabled = !uiState.isLoading
  )
  SubmitLoadingIndicatorDouble(
    isLoading = uiState.isLoading,
    title = "Mohon Tunggu",
    titleBerhasil = uiState.successMessage ?: "Kata Sandi Berhasil Diubah!",
    description = "Megubah kata sandi anda...",
    descriptionColor = ColorPalette.OnSurface,
    titleColor = ColorPalette.PrimaryColor700,
    onAnimationFinished = {},
  )
  AnimatedVisibility(uiState.errorMessage != null) {
    BasicAlertDialog(onDismissRequest ={
      showDialog = false
    } ) {
      Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
          .clip(RoundedCornerShape(24.dp))
          .background(ColorPalette.OnPrimary)
          .padding(24.dp)
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Text(
            text = "Ubah Kata Sandi Gagal!",
            style = StyledText.MobileMediumSemibold,
            textAlign = TextAlign.Center,
            color = ColorPalette.Error,
            modifier = Modifier.fillMaxWidth()
          )
          Text(
            text = "Anda hanya dapat mengganti kata sandi setiap 2 minggu sekali",
            style = StyledText.MobileXsRegular,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
          )
        }
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxWidth()
        ) {
          Image(
            painter = painterResource(id = com.example.slicingbcf.R.drawable.exclamation),
            contentDescription = "Error",
            modifier = Modifier.size(50.dp)
          )
        }
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxWidth()
        ) {
          FilledTonalButton(
            onClick = {
              showDialog = false
            },
            enabled = true,
            colors = ButtonDefaults.buttonColors(
              containerColor = ColorPalette.PrimaryColor100
            ),

            ) {
            Text(
              text = "Tutup",
              style = StyledText.MobileBaseMedium,
              color = ColorPalette.PrimaryColor700
            )
          }
        }

      }
    }
  }

}

@Composable
private fun TextFieldWithTitle(
  title: String,
  placeholder: String,
  label: String,
  isPasswordVisible: MutableState<Boolean>,
  value: String,
  onValueChange: (String) -> Unit,
  error: String?
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = title,
      style = StyledText.MobileBaseSemibold
    )
    CustomOutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      placeholder = placeholder,
      label = label,
      isPassword = true,
      isPasswordVisible = isPasswordVisible,
      modifier = Modifier.fillMaxWidth(),
      rounded = 40,
      error = error,
      labelFocusedColor = ColorPalette.OnSurfaceVariant,
    )

  }
}
