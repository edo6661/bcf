package com.example.slicingbcf.ui.shared.pusat_informasi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.data.local.DataPusatInformasi


@Composable
fun PusatInformasiItem(
  data : DataPusatInformasi,
  onClick : () -> Unit,
) {
  Column(
    modifier = Modifier
      .clip(
        shape = RoundedCornerShape(32.dp)
      )
      .clickable(onClick = onClick)
      .background(ColorPalette.Monochrome100)
      .padding(
        horizontal = 16.dp,
        vertical = 20.dp
      )
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(32.dp)
  ) {
    PusatInformasiContent(data)
  }
}