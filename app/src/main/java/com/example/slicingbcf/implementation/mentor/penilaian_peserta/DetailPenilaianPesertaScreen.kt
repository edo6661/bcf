package com.example.slicingbcf.implementation.mentor.penilaian_peserta

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.Penilaian
import com.example.slicingbcf.data.local.headerTable
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.message.SecondaryButton
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator
import com.example.slicingbcf.ui.shared.textfield.TextFieldWithTitle

// TODO: benerin input text field nya (ui nya sesuai in ama figma)
@Composable
fun DetailPenilaianPesertaScreenMentor(
  modifier: Modifier,
  id: String = "1",
  viewModel: DetailPenilaianPesertaScreenMentorViewModel = hiltViewModel()
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
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(40.dp)
  ) {
    when {
      state.error != null -> {
        ErrorWithReload(errorMessage = state.error) {
          viewModel.onEvent(DetailPenilaianEvent.Refresh)
        }
      }

      !state.loadingData -> {
        TopSection(
          isEdit = state.isEdit,
          toggleEdit = {
            viewModel.onEvent(DetailPenilaianEvent.ToggleEdit)
          }
        )
        BottomSection(
          penilaian = state.penilaian,
          isEdit = state.isEdit,
          toggleEdit = {
            viewModel.onEvent(DetailPenilaianEvent.ToggleEdit)
          },
          state = state,
           onEvent = { event ->
             viewModel.onEvent(event)
           }
        )
      }
    }
  }

  if (state.loadingData) {
    LoadingCircularProgressIndicator()
  }
  SubmitLoadingIndicatorDouble(
    isLoading = state.loadingSubmit,
    title = "Memproses Umpan Balik Anda...",
    onAnimationFinished = {
      viewModel.onEvent(DetailPenilaianEvent.ClearState)
    },
    titleBerhasil = "Umpan Balik Anda Berhasil Terkirim!",
  )

}

@Composable
fun BottomSection(
  penilaian: Penilaian,
  isEdit: Boolean,
  toggleEdit: () -> Unit,
  state: DetailPenilaianState,
  onEvent: (DetailPenilaianEvent) -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    InfoSection(
      data = listOf(
        "Nama Lembaga" to penilaian.namaLembaga,
        "Batch" to penilaian.batch.toString(),
        "Total Penilaian" to penilaian.totalPenilaian.toString()
      )
    )
    TableSection(
      penilaianUmums = state.penilaianUmums,
      nilaiCapaianClusters = state.nilaiCapaianClusters
    )
    FormSection(
      isEdit = isEdit,
      toggleEdit = toggleEdit,
      state = state,
      onEvent = onEvent

    )
  }
}


@Composable
fun FormSection(
  isEdit : Boolean,
  toggleEdit : () -> Unit,
  state: DetailPenilaianState,
  onEvent: (DetailPenilaianEvent) -> Unit
) {
  Column {

    Column(
      modifier = Modifier.padding(
        bottom = 40.dp
      ),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      TextFieldWithTitle(
        heading = "Hal-Hal Yang Perlu Ditingkatkan",
        title = "Umpan Balik Mentor Cluster",
        onChange = {
          onEvent(DetailPenilaianEvent.PenilaianMentorClusterChanged(
            penilaianMentorCluster = state.penilaianMentorCluster.copy(
              umpanBalik = it
            )
          ))
        },
        value = state.penilaianMentorCluster.umpanBalik,

        placeholder = "Umpan Balik yang diberikan",
        label = null,
        isEdit = isEdit,
        heightTextField = 124
      )
      TextFieldWithTitle(
        title = "Umpan Balik Mentor Desain Program",
        onChange = {
          onEvent(DetailPenilaianEvent.PenilaianMentorDesainProgramChanged(
            penilaianMentorDesainProgram = state.penilaianMentorDesainProgram.copy(
              umpanBalik = it
            )
          ))
        },
        value = state.penilaianMentorDesainProgram.umpanBalik,

        placeholder = "Umpan Balik yang diberikan",
        label = null,
        isEdit = isEdit,
        heightTextField = 124
      )
      TextFieldWithTitle(
        heading = "Hal-hal Yang Perlu Ditingkatkan",
        title = "Masukan Mentor Cluster",
        onChange = {
          onEvent(DetailPenilaianEvent.PenilaianMentorClusterChanged(
            penilaianMentorCluster = state.penilaianMentorCluster.copy(
              kegiatan = it
            )
          ))
        },
        value = state.penilaianMentorCluster.kegiatan,
        placeholder = "",
        label = null,
        isEdit = isEdit,
        heightTextField = 124
      )
      TextFieldWithTitle(
        title = "Masukan Mentor Desain Program",
        onChange = {
          onEvent(DetailPenilaianEvent.PenilaianMentorDesainProgramChanged(
            penilaianMentorDesainProgram = state.penilaianMentorDesainProgram.copy(
              kegiatan = it
            )
          ))
        },
        value = state.penilaianMentorDesainProgram.kegiatan,
        placeholder = "Umpan Balik yang diberikan",
        label = null,
        isEdit = isEdit,
        heightTextField = 124
      )
    }
    AnimatedVisibility(isEdit) {
      HorizontalDivider()
      Row(
        modifier = Modifier
          .padding(
            horizontal = 8.dp,
            vertical = 16.dp
          )
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
          8.dp,
          Alignment.End
        ),
        verticalAlignment = Alignment.CenterVertically
      ) {
        SecondaryButton(
          text = "Batal",
          onClick = {
            toggleEdit()
          }
        )
        PrimaryButton(
          text = "Simpan",
          onClick = {
            onEvent(DetailPenilaianEvent.SubmitForm)
          }
        )
      }
    }
  }
}


@Composable
fun TopSection(
  isEdit : Boolean,
  toggleEdit : () -> Unit
) {
  val icon = if (isEdit) Icons.Outlined.Inbox else Icons.Outlined.Edit
  Column {

    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          bottom = 32.dp
        ),
    ) {
      Text(
        text = "Detail Penilaian Peserta",
        style = StyledText.MobileLargeMedium,
        color = ColorPalette.Black,
      )
      FloatingActionButton(
        onClick = {
          toggleEdit()
        },
        containerColor = ColorPalette.PrimaryColor100,
        contentColor = ColorPalette.PrimaryColor700,
        modifier = Modifier.size(56.dp)
      ) {
        Icon(
          imageVector = icon,
          contentDescription = ""
        )
      }
    }
    HorizontalDivider(
      Modifier
        .padding(0.dp)
        .height(1.dp)
        .background(color = ColorPalette.OutlineVariant)
    )
  }
}


@Composable
fun InfoSection(data : List<Pair<String, String>>) {
  Column(
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    data.forEach { (label, value) ->
      InfoRow(label = label, value = value)
    }
  }
}

@Composable
fun InfoRow(label : String, value : String) {
  Column(
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(
      text = label,
      style = StyledText.MobileBaseSemibold,
      color = ColorPalette.PrimaryColor700,
    )
    Text(
      text = value,
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.Black,
    )
  }
}


@Composable
fun TableSection(
  penilaianUmums : List<com.example.slicingbcf.data.local.PenilaianUmum>,
  nilaiCapaianClusters : List<com.example.slicingbcf.data.local.PenilaianUmum>
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
      text = "Evaluasi Capaian Cluster",

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

