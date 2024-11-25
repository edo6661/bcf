package com.example.slicingbcf.ui.shared

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Preview(showBackground = true)
@Composable
fun PrimaryButton(
  modifier : Modifier = Modifier,
  onClick : () -> Unit = {},
  text : String = "Default",
  style : TextStyle = StyledText.MobileSmallRegular,
  color : Color = ColorPalette.PrimaryColor700,
  textColor : Color = Color.White

) {
  Button(
    modifier = modifier,
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(
      containerColor = color
    )
  ) {
    Text(
      text,
      style = style,
      color = textColor
    )
  }
}