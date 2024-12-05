package com.example.slicingbcf.implementation.mentor.worksheet_mentor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.LembarKerjaPeserta
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator

@Composable
fun DetailWorksheetMentorScreen(
  modifier: Modifier = Modifier,
  id: String,
  viewModel: DetailWorksheetMentorViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()
  val scrollState = rememberScrollState()

  when (state) {
    is UiState.Loading -> {
    LoadingCircularProgressIndicator()
    }
    is UiState.Success -> {
      val detail = (state as UiState.Success<WorksheetMentorDetail>).data
      Column(
        modifier = modifier.padding(
          horizontal = 16.dp,
          vertical = 24.dp
        )
          .verticalScroll(scrollState)
        ,
        verticalArrangement = Arrangement.spacedBy(36.dp),
      ) {
        Text(
          text = "Submisi Lembar Kerja",
          style = StyledText.MobileLargeMedium,
          color = ColorPalette.Black,
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Center,
        )
        KeyValueColumn(
          title = "Judul Lembar Kerja",
          description = detail.judulLembarKerja
        )
        KeyValueColumn(
          title = "Deskripsi Lembar Kerja",
          description = detail.deskripsiLembarkKerja
        )
        KeyValueColumn(
          title = "Tautan Lembar Kerja",
          description = detail.tautanLembarKerja
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Text(
            text = "Batas Submisi Lembar Kerja",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700
          )
          Text(
            text = detail.batasSubmisi,
            style = StyledText.MobileBaseRegular,
          )
        }
        Column(
          verticalArrangement = Arrangement.spacedBy(16.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "Submisi Lembar Kerja Peserta",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
          )
          Table(data = detail.submisiPeserta)
        }
      }

      }


    is UiState.Error -> {
      val errorMessage = (state as UiState.Error).message
      ErrorWithReload(
        errorMessage = errorMessage,
        onRetry = {
          viewModel.onEvent(DetailWorksheetMentorEvent.ReloadData)
        }
      )
    }
  }
}


@Composable
private fun Table(
  data : List<LembarKerjaPeserta>
) {
  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    HeaderTable()
    data.forEachIndexed { index, worksheetPeserta ->
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
    modifier = modifier.padding(
      vertical = 8.dp
    )

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

