package com.example.slicingbcf.ui.shared.form_monthly_report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.util.isValidTimeInput

@Composable
@Preview(showSystemUi = true)
fun KeyboardTime(
  modifier : Modifier = Modifier,
  isHour: Boolean = false,
  description: String = "Minute",
  value : String = "00",
  onValueChange: (String) -> Unit = {},


) {

    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp),

    ) {
      CenteredCustomOutlinedTextField(
        value = value,
        onValueChange = {newValue ->
          if (isValidTimeInput(newValue, isHour)) {
            onValueChange(newValue)
          }
        },
        modifier = modifier
          .width(96.dp)
          .height(72.dp)
      )
      Text(
        text = if (isHour) "Hour" else "Minute",
        style = StyledText.Mobile2xsRegular,
        color = ColorPalette.PrimaryColor200,
      )

  }

}

@Composable
fun CenteredCustomOutlinedTextField(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  textStyle: TextStyle = Typography().displayMedium.copy(
    textAlign = TextAlign.Center,
    color = ColorPalette.OnSurface
  ),
  bgColor: Color = ColorPalette.PrimaryColor100
) {
  Box(
    modifier = modifier
      .background(bgColor, shape = RoundedCornerShape(12.dp))
      .padding(4.dp),
    contentAlignment = Alignment.Center,

  ) {
    BasicTextField(
      value = value,
      onValueChange = onValueChange,
      singleLine = true,
      textStyle = textStyle,
      modifier = Modifier.fillMaxSize(),
      keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
  }
}

@Composable
fun ClickableTextField(
  value: String,
  onValueChange: (String) -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  label: String? = null,
  placeholder: String? = null
) {
  Box(modifier = modifier.clickable { onClick() }) {
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      label = { if (label != null) Text(label) },
      placeholder = { if (placeholder != null) Text(placeholder) },
      modifier = Modifier.fillMaxWidth(),
      readOnly = true
    )
  }
}
