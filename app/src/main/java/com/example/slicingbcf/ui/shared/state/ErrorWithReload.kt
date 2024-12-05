package com.example.slicingbcf.ui.shared.state

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.PrimaryButton
@Composable
fun ErrorWithReload(
  errorMessage: String?,
  onRetry: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 20.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        text = errorMessage ?: "Unknown Error",
        color = ColorPalette.Error,
        style = StyledText.MobileBaseSemibold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )
      PrimaryButton(
        onClick = onRetry,
        text = "Coba Lagi",
        modifier = Modifier.fillMaxWidth(0.6f)
      )
    }
  }
}
