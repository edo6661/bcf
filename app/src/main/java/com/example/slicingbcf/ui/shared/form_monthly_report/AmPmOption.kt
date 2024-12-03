package com.example.slicingbcf.ui.shared.form_monthly_report

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Composable
fun AmPmOption(
  text: String,
  isActive: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  isTopRounded: Boolean = false,
  isBottomRounded: Boolean = false
) {
  val color = animateColorAsState(
    targetValue = if (isActive) ColorPalette.PrimaryColor200 else ColorPalette.PrimaryColor100,
    label = "AmPmOptionColor",

  )
  Text(
    text = text,
    style = StyledText.MobileBaseMedium,
    color = ColorPalette.PrimaryColor700,
    textAlign = TextAlign.Center,
    modifier = modifier
      .background(
        color = color.value,
        shape = RoundedCornerShape(
          topStart = if (isTopRounded) 12.dp else 0.dp,
          topEnd = if (isTopRounded) 12.dp else 0.dp,
          bottomStart = if (isBottomRounded) 12.dp else 0.dp,
          bottomEnd = if (isBottomRounded) 12.dp else 0.dp
        )
      )
      .padding(vertical = 4.dp, horizontal = 8.dp)
      .fillMaxWidth()
      .clickable { onClick() }
  )
}
