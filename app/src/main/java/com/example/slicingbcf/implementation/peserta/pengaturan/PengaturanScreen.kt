package com.example.slicingbcf.implementation.peserta.pengaturan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField

@Composable
fun PengaturanScreen(
  modifier : Modifier = Modifier
) {
  val scrollState = rememberScrollState()
  Column(
    modifier = modifier
      .statusBarsPadding()
      .padding(
        horizontal = 16.dp,
      )
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.spacedBy(36.dp)
  ) {
    Text(
      text = "Pengaturan",
      style = StyledText.MobileLargeMedium,
      textAlign = TextAlign.Center
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Text(
        text = "Ubah Kata Sandi",
        style = StyledText.MobileMediumSemibold,
        color = ColorPalette.PrimaryColor700
      )
      Forms()
    }

  }
}

@Composable
private fun Forms() {
  val isPasswordVisible = remember { mutableStateOf(false) }
  val isNewPasswordVisible = remember { mutableStateOf(false) }
  val isConfirmationPasswordVisible = remember { mutableStateOf(false) }

  TextFieldWithTitle(
    title = "Kata Sandi Lama",
    placeholder = "Masukkan kata sandi lama",
    label = "Kata Sandi Lama",
    isPasswordVisible = isPasswordVisible
  )
  TextFieldWithTitle(
    title = "Kata Sandi Baru",
    placeholder = "Masukkan kata sandi baru",
    label = "Kata Sandi Baru",
    isPasswordVisible = isNewPasswordVisible
  )
  TextFieldWithTitle(
    title = "Konfirmasi Kata Sandi",
    placeholder = "Masukkan konfirmasi kata sandi",
    label = "Konfirmasi Kata Sandi",
    isPasswordVisible = isConfirmationPasswordVisible
  )
  Text(
    text = "*) Anda hanya dapat mengganti kata sandi setiap 2 minggu sekali",
    style = StyledText.MobileSmallRegular,
    color = ColorPalette.Danger500
  )
  Spacer(
    modifier = Modifier.height(16.dp)
  )
  PrimaryButton(
    text = "Ubah Kata Sandi",
    style = StyledText.MobileSmallMedium,
    modifier = Modifier
      .fillMaxWidth(),
    textColor = ColorPalette.OnPrimary
  )

}

@Composable
private fun TextFieldWithTitle(
  title : String,
  placeholder : String,
  label : String,
  isPasswordVisible : MutableState<Boolean>
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = title,
      style = StyledText.MobileBaseSemibold
    )
    CustomOutlinedTextField(
      value = "",
      onValueChange = {},
      placeholder = placeholder,
      label = label,
      isPassword = true,
      isPasswordVisible = isPasswordVisible,
      modifier = Modifier.fillMaxWidth(),
      rounded = 40,

      )
  }
}