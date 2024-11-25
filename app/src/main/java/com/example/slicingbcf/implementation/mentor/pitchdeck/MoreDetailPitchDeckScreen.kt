package com.example.slicingbcf.implementation.mentor.pitchdeck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.WorksheetPeserta


@Composable
fun MoreDetailPitchdeckScreen(
  modifier : Modifier = Modifier,
) {
  val scrollState = rememberScrollState()
  Column(
    modifier = modifier
      .padding(
        horizontal = 16.dp,
      )
      .padding(
        top = 24.dp
      )
      .verticalScroll(scrollState),

    verticalArrangement = Arrangement.spacedBy(36.dp)
  ) {
    Text(
      text = "Submisi Pitch Deck",
      style = StyledText.MobileLargeMedium,
      color = ColorPalette.Black,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
      mockUpWorksheetPeserta.forEach {
        KeyValueColumn(
          title = it.title,
          description = it.description
        )
      }
      Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "Submisi Lembar Kerja Peserta",
          style = StyledText.MobileBaseSemibold,
          color = ColorPalette.PrimaryColor700,
        )
        Table()

      }

    }

  }
}

@Composable
private fun Table() {
  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    HeaderTable()
    mockUpLembarKerjaPeserta.forEachIndexed { index, worksheetPeserta ->
      RowTable(index + 1, worksheetPeserta)
    }
  }
}

@Composable
private fun TableCell(
  modifier : Modifier,
  isHeader : Boolean = false,
  value : String,
  color : Color = ColorPalette.PrimaryBorder,
) {
  val style = if (isHeader) StyledText.MobileSmallSemibold else StyledText.MobileSmallRegular

  Text(
    text = value,
    style = style,
    color = color,
    modifier = modifier

  )
}

@Composable
private fun HeaderTable() {
  Row(
    modifier = Modifier
      .drawBehind {
        drawLine(
          color = ColorPalette.OutlineVariant,
          strokeWidth = 1.dp.toPx(),
          start = Offset(0f, 0f),
          end = Offset(size.width, 0f)
        )
        drawLine(
          color = ColorPalette.OutlineVariant,
          strokeWidth = 1.dp.toPx(),
          start = Offset(0f, size.height),
          end = Offset(size.width, size.height)
        )
      }
      .fillMaxWidth()
      .background(ColorPalette.PrimaryColor100)
      .padding(
        16.dp
      )
  ) {
    headers.forEach {
      TableCell(
        isHeader = true,
        value = it,
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
private fun RowTable(index : Int, worksheetPeserta : LembarKerjaPeserta) {
  Row(
    modifier = Modifier
      .drawBehind {
        drawLine(
          color = ColorPalette.OutlineVariant,
          strokeWidth = 1.dp.toPx(),
          start = Offset(0f, 0f),
          end = Offset(size.width, 0f)
        )
        drawLine(
          color = ColorPalette.OutlineVariant,
          strokeWidth = 1.dp.toPx(),
          start = Offset(0f, size.height),
          end = Offset(size.width, size.height)
        )
      }
      .fillMaxWidth()
      .padding(
        16.dp
      ),
  ) {
    TableCell(value = index.toString(), modifier = Modifier.weight(1f))
    TableCell(value = worksheetPeserta.namaPeserta, modifier = Modifier.weight(1f))
    TableCell(
      value = worksheetPeserta.waktuSubmisi,
      modifier = Modifier.weight(1f),
      color = if (index == 1) ColorPalette.Danger500 else ColorPalette.Success500
    )
  }
}

private val headers = listOf(
  "No",
  "Nama Peserta",
  "Waktu Submisi",
)

data class LembarKerjaPeserta(
  val namaPeserta : String,
  val waktuSubmisi : String,
)

val mockUpLembarKerjaPeserta = listOf(
  LembarKerjaPeserta(
    namaPeserta = "Asep",
    waktuSubmisi = "Senin, 1 April 2024 13.55 WIB",
  ),
  LembarKerjaPeserta(
    namaPeserta = "Budi",
    waktuSubmisi = "Selasa, 2 April 2024 13.55 WIB",
  ),
  LembarKerjaPeserta(
    namaPeserta = "Cecep",
    waktuSubmisi = "Rabu, 3 April 2024 13.55 WIB",
  ),
)

@Composable
private fun KeyValueColumn(
  title : String,
  description : String,
  colorDescription : Color = ColorPalette.Monochrome800
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = title,
      style = StyledText.MobileBaseSemibold,
      color = ColorPalette.PrimaryColor700,
    )
    Text(
      text = description,
      style = StyledText.MobileBaseRegular,
      color = colorDescription
    )
  }

}

private val mockUpWorksheetPeserta = listOf(
  WorksheetPeserta(
    title = "Judul Lembar Kerja",
    description = "Pitch Deck Program Peserta",
  ),
  WorksheetPeserta(
    title = "Batch",
    description = "5",
  ),
  WorksheetPeserta(
    title = "Deskripsi Lembar Kerja",
    description = "Buatlah sebuah presentasi secara singkat yang memuat terkait “Program Kolaborasi Peningkatan Capaian Terapi Pencegahan Tuberkulosis (TPT) pada Balita Dan anak usia 5-14 tahun” dengan detail: Profil, Latar Belakang, Logical Framework Analysis, Indikator Program, Anggaran Pendanaan, dan lainnya.",
  ),
  WorksheetPeserta(
    title = "Tautan Lembar Kerja",
    description = "bit.ly/pitchdeckcapaianTPT",
  ),
  WorksheetPeserta(
    title = "Batas Submisi Lembar Kerja",
    description = "Selasa, 2 April 2024 13.55 WIB",
  ),
)