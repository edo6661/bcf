package com.example.slicingbcf.ui.shared.message

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Composable
fun SecondaryButton(
  onClick : () -> Unit,
  text : String,
  color : Color = ColorPalette.PrimaryColor700,
  borderColor : Color = ColorPalette.PrimaryColor700,
  style : TextStyle = StyledText.MobileSmallRegular,
) {
  OutlinedButton(
    onClick = onClick,
    colors = ButtonDefaults.outlinedButtonColors(
      contentColor = color,
    ),
    border = BorderStroke(
      width = 1.dp,
      brush = SolidColor(borderColor)
    ),
  ) {
    Text(
      text = text,
      style = style,
      color = ColorPalette.PrimaryColor700
    )
  }
}