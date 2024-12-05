package com.example.slicingbcf.implementation.mentor.pitchdeck

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
import com.example.slicingbcf.data.local.LembarKerjaPeserta
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator

@Composable
fun MoreDetailPitchdeckScreen(
  viewModel: MoreDetailPitchDeckViewModel = hiltViewModel(),
  modifier: Modifier = Modifier,
  id: String
) {
  val state by viewModel.state.collectAsState()

  val scrollState = rememberScrollState()

  when {
    state.isLoading -> {
      LoadingCircularProgressIndicator()
    }
    state.error != null -> {
      ErrorWithReload(
        modifier = modifier,
        errorMessage = state.error,
        onRetry = {
          viewModel.onEvent(MoreDetailPitchDeckEvent.Reload)
        }
      )
    }
     else -> {
       Column(
         modifier = modifier
           .padding(horizontal = 16.dp)
           .padding(top = 24.dp)
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
         Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
           state.pitchDecks.forEach {
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
             Table(state.lembarKerjas)
           }
         }
       }
     }

    }
  }




@Composable
private fun Table(data: List<LembarKerjaPeserta>) {
  Column(modifier = Modifier.fillMaxWidth()) {
    HeaderTable()
    data.forEachIndexed { index, worksheetPeserta ->
      RowTable(index + 1, worksheetPeserta)
    }
  }
}

@Composable
private fun RowTable(index: Int, worksheetPeserta: LembarKerjaPeserta) {
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
      .padding(16.dp),
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

