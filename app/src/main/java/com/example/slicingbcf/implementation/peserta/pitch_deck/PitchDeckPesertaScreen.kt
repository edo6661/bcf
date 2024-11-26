package com.example.slicingbcf.implementation.peserta.pitch_deck

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.pitchDeck
import com.example.slicingbcf.data.local.worksheetsPeserta
import com.example.slicingbcf.ui.shared.pitchdeck_worksheet.PitchDeckItem
import com.example.slicingbcf.ui.shared.pitchdeck_worksheet.WorksheetItem
import java.text.SimpleDateFormat
import java.util.*

@Composable
@Preview(showSystemUi = true)
fun PitchDeckPesertaScreen(
    modifier: Modifier = Modifier,
    onNavigatePitchDeckPeserta: (String) -> Unit = {}
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
            items(pitchDeck.size) { index ->
                PitchDeckItem(
                    data = pitchDeck[index],
                    onClick = { onNavigatePitchDeckPeserta(pitchDeck[index].title) },
                    bgColor = ColorPalette.PrimaryColor100
                )
            }
        }
    }
}