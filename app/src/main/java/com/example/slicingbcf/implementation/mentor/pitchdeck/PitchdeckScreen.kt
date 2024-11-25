package com.example.slicingbcf.implementation.mentor.pitchdeck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.worksheetsPeserta
import com.example.slicingbcf.ui.shared.pitchdeck_worksheet.WorksheetItem

@Composable
fun PitchdeckScreen(
  modifier : Modifier = Modifier,
  onNavigateDetailPitchdeck : (String) -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(32.dp),
    modifier = modifier.padding(
      horizontal = 16.dp,
      vertical = 24.dp
    )
  ) {
    Text(
      text = "Pitch Deck",
      style = StyledText.MobileMediumMedium,
      color = ColorPalette.Black,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      items(worksheetsPeserta.size) { index ->
        WorksheetItem(
          worksheet = worksheetsPeserta[index],
          onClick = { onNavigateDetailPitchdeck(worksheetsPeserta[index].title) }
        )
      }

    }
  }
}