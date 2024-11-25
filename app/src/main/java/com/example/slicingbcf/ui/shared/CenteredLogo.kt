package com.example.slicingbcf.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R

@Composable
@Preview(showBackground = true)
fun CenteredLogo(
  modifier : Modifier = Modifier.size(
    width = 56.dp,
    height = 35.dp
  )
) {
  Box(
    modifier = Modifier
      .fillMaxWidth(),
    contentAlignment = Alignment.Center,

    ) {
    Image(
      painter = painterResource(id = R.drawable.logo_lead),
      contentDescription = stringResource(id = R.string.logo),
      contentScale = ContentScale.Crop,
      modifier = modifier

    )
  }
}