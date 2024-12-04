package com.example.slicingbcf.ui.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.slicingbcf.constant.ColorPalette


@Composable
fun TextWithAsterisk(
  label : String,
  asteriskColor : Color = ColorPalette.Danger500
) : AnnotatedString {
  return buildAnnotatedString {
    append(label)
    withStyle(style = SpanStyle(color = asteriskColor)) {
      append("*")
    }
  }
}