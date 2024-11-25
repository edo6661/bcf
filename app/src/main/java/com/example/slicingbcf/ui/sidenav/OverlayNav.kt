package com.example.slicingbcf.ui.sidenav

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun OverlayNav(
  isSideNavVisible : Boolean,
  onTap : () -> Unit
) {
  if (isSideNavVisible) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.4f))
        .pointerInput(Unit) {
          detectTapGestures(
            onTap = {
              onTap()
            }
          )

        }
    )
  }


}