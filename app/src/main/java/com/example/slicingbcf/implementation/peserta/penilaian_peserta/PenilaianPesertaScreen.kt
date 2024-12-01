package com.example.slicingbcf.implementation.peserta.penilaian_peserta

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.message.SecondaryButton
import com.example.slicingbcf.ui.shared.textfield.TextFieldWithTitle


@Composable
fun PenilaianPesertaScreen(
  modifier : Modifier,

  ) {
  val scroll = rememberScrollState()
  Column(
    modifier = modifier
      .statusBarsPadding()
      .padding(
        horizontal = 16.dp,
      )
      .verticalScroll(scroll)
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(40.dp)
  ) {
    TopSection(
    )
    BottomSection(
      penilaian = Penilaian(
        namaLembaga = "Lembaga A",
        batch = 1,
        totalPenilaian = 100
      ),
    )

  }

}

@Composable
fun BottomSection(
  penilaian : Penilaian,
) {
  val maxPenilaian = 100
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    Text(
      text = "Total Penilaian: ${penilaian.totalPenilaian}/$maxPenilaian",
      style = StyledText.MobileBaseSemibold,
      color = ColorPalette.PrimaryColor700,
    )

    TableSection()
    FormSection(
    )

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
        value = "test text asdasd",
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
fun TableSection() {
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


data class Penilaian(
  val namaLembaga : String,
  val batch : Int,
  val totalPenilaian : Int,
)

val headerTable = listOf(
  "No",
  "Aspek Penilaian",
  "Penilaian",
)

data class PenilaianUmum(
  val aspekPenilaian : String,
  val penilaian : Int
)


val penilaianUmums = listOf(
  PenilaianUmum("Kehadiran", 50),
  PenilaianUmum("Keaktifan", 40),
  PenilaianUmum("Kemandirian / Inisiatif", 30),
  PenilaianUmum("Pitching Day", 20),
  PenilaianUmum("Capaian pendanaan yang didapat", 10),
  PenilaianUmum("Kerjasama dengan instansi lain", 5),
  PenilaianUmum("Keaktifan Sosial Media", 5),
  PenilaianUmum("Pengurangan Nilai", 5),
)
val nilaiCapaianClusters = listOf(
  PenilaianUmum("Capaian Pendanaan", 10),
  PenilaianUmum("Kerjasama dengan Instansi Lain", 5),
  PenilaianUmum("Keaktifan Sosial Media", 5),
  PenilaianUmum("Pengurangan Nilai", 5),
)