package com.example.slicingbcf.ui.shared.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.message.SecondaryButton


@Composable
fun OutlineTextFieldComment(
  value : String,
  onValueChange : (String) -> Unit,
  onSubmit : () -> Unit,
  label : String,
  placeholder : String,
  isEnabled : Boolean = false

) {
  var isFocused by remember { mutableStateOf(false) }
  val focusManager = LocalFocusManager.current

  fun clearFocus() {
    isFocused = false
    focusManager.clearFocus()
  }

  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp),
    modifier = Modifier.animateContentSize()
  ) {
    CustomOutlinedTextField(
      label = label,
      value = value,
      onValueChange = onValueChange,
      maxLines = 5,
      placeholder = placeholder,
      isFocused = isFocused,
      onFocusChange = { isFocused = it },
      rounded = 80,
      labelFocusedColor = ColorPalette.PrimaryColor700,
      labelDefaultColor = ColorPalette.Monochrome400,
      modifier = Modifier.fillMaxWidth(),
      borderFocusedColor = ColorPalette.PrimaryColor700,
      isEnabled = isEnabled

    )
    AnimatedVisibility(
      visible = isFocused
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
      ) {
        SecondaryButton(
          text = "Batal",
          onClick = {
            clearFocus()

          }
        )
        Spacer(modifier = Modifier.width(20.dp))
        PrimaryButton(
          text = "Kirim",
          onClick = {
            clearFocus()
            onSubmit()
          }
        )
      }
    }
  }
}
