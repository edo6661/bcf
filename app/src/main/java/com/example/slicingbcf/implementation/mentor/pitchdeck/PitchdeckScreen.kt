package com.example.slicingbcf.implementation.mentor.pitchdeck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.pitchDeck
import com.example.slicingbcf.ui.shared.pitchdeck_worksheet.PitchDeckItem
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator

@Composable
fun PitchdeckScreen(
  modifier : Modifier = Modifier,
  onNavigateDetailPitchdeck : (String) -> Unit,
  viewModel : PitchDeckViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()

  Column(
    verticalArrangement = Arrangement.spacedBy(32.dp),
    modifier = modifier.padding(
      horizontal = 16.dp,
      vertical = 24.dp
    )
  ) {
    when (state) {
      is UiState.Loading -> {
        LoadingCircularProgressIndicator()
      }

      is UiState.Success -> {
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
          items(pitchDeck.size) { index ->
            PitchDeckItem(
              data = pitchDeck[index],
              onClick = { onNavigateDetailPitchdeck(pitchDeck[index].title) },
              bgColor = ColorPalette.OnPrimary,
              id = "1"
            )
          }
        }
      }

      is UiState.Error -> {
        val errorMessage = (state as UiState.Error).message
        ErrorWithReload(
          errorMessage = errorMessage,
          onRetry = {
            viewModel.onEvent(PitchDeckEvent.ReloadData)
          }
        )
      }
    }
  }
}