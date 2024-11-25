package com.example.slicingbcf.ui.scaffold

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette

@Composable
fun DiscussionScaffold(
  onClick : () -> Unit,

  content : @Composable (PaddingValues) -> Unit,
) {
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          onClick()
        },
        modifier = Modifier
          .size(77.dp),
        containerColor = ColorPalette.SecondaryColor400,
        shape = CircleShape

      ) {
        Image(
          painter = painterResource(R.drawable.icon_headset),
          contentDescription = "Discussion",
          modifier = Modifier
            .size(34.dp)
        )
      }
    }
  ) { innerPadding ->
    content(innerPadding)
  }
}