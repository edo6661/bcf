package com.example.slicingbcf.implementation.peserta.pitch_deck

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.pitchDeck
import com.example.slicingbcf.implementation.peserta.worksheet_peserta.WorksheetPesertaEvent
import com.example.slicingbcf.implementation.peserta.worksheet_peserta.WorksheetPesertaViewModel
import com.example.slicingbcf.ui.shared.pitchdeck_worksheet.PitchDeckItem
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator

@Composable
@Preview(showSystemUi = true)
fun PitchDeckPesertaScreen(
    modifier: Modifier = Modifier,
    onNavigatePitchDeckPeserta: (String) -> Unit = {},
    viewModel : WorksheetPesertaViewModel = hiltViewModel()
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
                            onClick = { onNavigatePitchDeckPeserta(pitchDeck[index].title) },
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
                        viewModel.onEvent(WorksheetPesertaEvent.ReloadData)
                    }
                )
            }
        }
    }
}