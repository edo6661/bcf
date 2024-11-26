package com.example.slicingbcf.implementation.peserta.pitch_deck

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.pitchDeck
import com.example.slicingbcf.ui.shared.message.SecondaryButton
import com.example.slicingbcf.ui.shared.pitchdeck_worksheet.PitchDeckItem

@Composable
fun PitchDeckPesertaExpanded(
    modifier : Modifier = Modifier,
    onNavigateDetailPitchdeckPeserta : (String) -> Unit,
    id : String
){
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
            )
            .padding(
                top = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp),

        ) {
        Text(
            text = "Pitch Deck",
            style = StyledText.MobileMediumMedium,
            color = ColorPalette.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ColorPalette.Monochrome200,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            PitchDeckItem(
                data = pitchDeck[0],
                bgColor = ColorPalette.PrimaryColor100
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable {
                    }
                ) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.folder
                        ),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "",
                    )
                    Text(
                        text = pitchDeck[0].link,
                        style = StyledText.MobileSmallRegular,
                        color = ColorPalette.PrimaryColor400
                    )
                }
                Text(
                    text = pitchDeck[0].description,
                    style = StyledText.MobileSmallRegular
                )
                Text(
                    text = buildAnnotatedString {
                        append("Batas Submisi: ")
                        withStyle(
                            style = SpanStyle(
                                color = ColorPalette.SecondaryColor400
                            )
                        ) {
                            append(pitchDeck[0].submissionDeadline)
                        }
                    },
                    style = StyledText.MobileSmallMedium
                )
            }
        }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                SecondaryButton(
                    text = "Lihat Detail",
                    onClick = { onNavigateDetailPitchdeckPeserta(id) },
                    style = StyledText.MobileSmallMedium
                )
            }
        PitchDeckItem(
            data = pitchDeck[1]
        )
    }
}