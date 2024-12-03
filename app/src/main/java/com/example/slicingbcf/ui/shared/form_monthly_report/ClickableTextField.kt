package com.example.slicingbcf.ui.shared.form_monthly_report

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Composable
fun ClickableTextField(
  modifier: Modifier = Modifier,
  value: String,
  placeholder: String,
  onClick: () -> Unit,
) {
  Box(
    modifier = modifier
      .height(56.dp)
      .fillMaxWidth()
      .clickable { onClick() }
      .border(1.dp, ColorPalette.Monochrome400, RoundedCornerShape(40.dp))
      .padding(horizontal = 16.dp, vertical = 8.dp),
    contentAlignment = Alignment.CenterStart
  ) {
      Text(
        text = value.ifEmpty { placeholder },
        color = Color.Gray,
        style = StyledText.MobileSmallRegular
      )
  }
}
