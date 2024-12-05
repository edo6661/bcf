package com.example.slicingbcf.implementation.peserta.penilaian_peserta

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.PenilaianUmum
import com.example.slicingbcf.data.local.headerTable
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator
import com.example.slicingbcf.ui.shared.textfield.TextFieldWithTitle

// TODO: Loading nya blm muncul

@Composable
fun PenilaianPesertaScreen(
  modifier: Modifier,
  viewModel: PenilaianPesertaViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()
  val scroll = rememberScrollState()


    Column(
      modifier = modifier
        .padding(
          horizontal = 16.dp,
          vertical = 24.dp
        )
        .verticalScroll(scroll)
        .fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
      when {
        state.error != null -> {
          ErrorWithReload(errorMessage = state.error) {
            viewModel.onEvent(PenilaianPesertaEvent.Refresh)
          }
        }
        !state.loading -> {
          TopSection()
          BottomSection(state = state)
        }
      }
    }

    if (state.loading) {
      LoadingCircularProgressIndicator()
    }
}

@Composable
fun BottomSection(
  state : PenilaianPesertaState,
) {
  val maxPenilaian = 100
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    Text(
      text = "Total Penilaian: ${state.penilaian.totalPenilaian}/$maxPenilaian",
      style = StyledText.MobileBaseSemibold,
      color = ColorPalette.PrimaryColor700,
    )

    TableSection(
      penilaianUmums = state.penilaianUmums,
      nilaiCapaianClusters = state.nilaiCapaianClusters


    )
    FormSection()
  }
}


@Composable
fun FormSection(
) {
  Column {
    Column(
      modifier = Modifier.padding(
        bottom = 40.dp
      ),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      TextFieldWithTitle(
        heading = "Hal-hal Yang Dibahas Selama Kegiatan Mentoring",
        title = "Umpan Balik Mentor Cluster",
        onChange = {},
        value = "",
        placeholder = "Dibahas",
        label = "Kegiatan",
      )
      TextFieldWithTitle(
        title = "Umpan Balik Mentor Desain Program",
        onChange = {},
        value = "",
        placeholder = "Umpan Balik",
        label = "Umpan",
      )
      TextFieldWithTitle(
        heading = "Hal-Hal Yang Perlu Ditingkatkan",
        title = "Masukan Mentor Cluster",
        onChange = {},
        value = "",
        placeholder = "Dibahas",
        label = "Kegiatan",
      )
      TextFieldWithTitle(
        title = "Umpan Balik Mentor Desain Program",
        onChange = {},
        value = "",
        placeholder = "Umpan Balik",
        label = "Umpan",
      )
    }

  }
}


@Composable
fun TopSection(
) {
  Text(
    text = "Penilaian Peserta",
    style = StyledText.MobileLargeMedium,
    color = ColorPalette.Black,
    textAlign = TextAlign.Center,
    modifier = Modifier.fillMaxWidth()
  )

}


@Composable
fun TableSection(
  penilaianUmums : List<PenilaianUmum>,
  nilaiCapaianClusters : List<PenilaianUmum>
) {
  val columnWeights = listOf(0.5f, 1.5f, 1f)
  val rowsPenilaianUmum = penilaianUmums.mapIndexed { index, penilaianUmum ->
    listOf(
      (index + 1).toString(),
      penilaianUmum.aspekPenilaian,
      penilaianUmum.penilaian.toString()
    )
  }
  val rowsNilaiCapaianClusters = nilaiCapaianClusters.mapIndexed { index, penilaianUmum ->
    listOf(
      (index + 1).toString(),
      penilaianUmum.aspekPenilaian,
      penilaianUmum.penilaian.toString()
    )
  }

  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Text(
      text = "Penilaian Umum Peserta",
      style = StyledText.MobileBaseMedium,
      color = ColorPalette.Monochrome800
    )
    Table(
      headers = headerTable,
      columnWeights = columnWeights,
      rows = rowsPenilaianUmum
    )
  }

  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Text(
      text = "Evaluasi Capaian Desain Program",

      style = StyledText.MobileBaseMedium,
      color = ColorPalette.Monochrome800

    )
    Table(
      headers = headerTable,
      columnWeights = columnWeights,
      rows = rowsNilaiCapaianClusters
    )
  }

  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Text(
      text = "Evaluasi Capaian Cluster",

      style = StyledText.MobileBaseMedium,
      color = ColorPalette.Monochrome800

    )
    Table(
      headers = headerTable,
      columnWeights = columnWeights,
      rows = rowsNilaiCapaianClusters
    )
  }


}

@Composable
fun Table(
  headers : List<String>,
  columnWeights : List<Float>,
  rows : List<List<String>>
) {
  Column(
    modifier = Modifier.background(ColorPalette.Shade1)
  ) {
    TableRow {
      headers.forEachIndexed { index, header ->
        TableCell(
          isHeader = true,
          text = header,
          modifier = Modifier.weight(columnWeights.getOrElse(index) { 1f })
        )
      }
    }

    rows.forEach { row ->
      TableRow {
        row.forEachIndexed { index, cell ->
          TableCell(
            text = cell,
            modifier = Modifier.weight(columnWeights.getOrElse(index) { 1f })
          )
        }
      }
    }
  }
}

@Composable
fun TableRow(
  content : @Composable () -> Unit
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
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
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .fillMaxWidth()
  ) {
    content()
  }
}

@Composable
fun TableCell(
  modifier : Modifier = Modifier,
  isHeader : Boolean = false,
  text : String,
) {
  val fontStyle = if (isHeader) StyledText.MobileSmallSemibold else StyledText.MobileSmallRegular

  Text(
    text = text,
    style = fontStyle,
    color = ColorPalette.Black,
    modifier = modifier
      .padding(vertical = 8.dp)
      .fillMaxWidth(),
    textAlign = TextAlign.Start
  )
}

