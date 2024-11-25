package com.example.slicingbcf.implementation.mentor.pitchdeck

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.slicingbcf.data.local.worksheetsPeserta
import com.example.slicingbcf.ui.shared.message.SecondaryButton
import com.example.slicingbcf.ui.shared.pitchdeck_worksheet.WorksheetItem


@Composable
fun DetailPitchdeckScreen(
  modifier : Modifier = Modifier,
  onNavigateMoreDetailPitchdeck : (String) -> Unit,
  id : String
) {
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
      WorksheetItem(
        worksheet = worksheetsPeserta[0],
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
              id = R.drawable.icon_wrapper
            ),
            modifier = Modifier.size(24.dp),
            contentDescription = "",
          )
          Text(
            text = "bit.ly/pitchdeckcapaiantpt",
            style = StyledText.MobileSmallRegular,
            color = ColorPalette.PrimaryColor400
          )
        }
        Text(
          text = "Buatlah sebuah presentasi secara singkat yang memuat terkait “Program Kolaborasi Peningkatan Capaian Terapi Pencegahan Tuberkulosis (TPT) pada Balita Dan anak usia 5-14 tahun” dengan detail: Profil, Latar Belakang, Logical Framework Analysis, Indikator Program, Anggaran Pendanaan, dan lainnya.",
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
              append("Selasa, 2 April 2024 13.55 WIB")
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
        onClick = { onNavigateMoreDetailPitchdeck(id) },
        style = StyledText.MobileSmallMedium
      )
    }
    WorksheetItem(
      worksheet = worksheetsPeserta[1],
    )

  }
}