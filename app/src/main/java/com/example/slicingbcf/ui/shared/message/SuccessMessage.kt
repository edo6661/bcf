package com.example.slicingbcf.ui.shared.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Composable
fun SuccessMessage(
  message : String,
  modifier : Modifier = Modifier
) {

  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp)
      .background(ColorPalette.StatusSuccessBg, shape = RoundedCornerShape(14.dp))
      .padding(16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Icon(
      imageVector = Icons.Filled.CheckCircle,
      contentDescription = "Success",
      tint = ColorPalette.SuccessText,
      modifier = Modifier.size(24.dp)
    )
    Text(
      text = message,
      color = ColorPalette.SuccessText,
      style = StyledText.MobileSmallRegular
    )
  }
}
