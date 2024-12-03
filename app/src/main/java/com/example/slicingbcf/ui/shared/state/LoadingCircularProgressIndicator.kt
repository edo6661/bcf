package com.example.slicingbcf.ui.shared.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingCircularProgressIndicator() {
  Box(
    modifier = Modifier

      .fillMaxWidth()
      .fillMaxHeight(),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}
