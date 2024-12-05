package com.example.slicingbcf.implementation.mentor.umpan_balik

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.Batch
import com.example.slicingbcf.data.local.EvaluasiMentoring
import com.example.slicingbcf.data.local.JadwalMentoring
import com.example.slicingbcf.data.local.Lembaga
import com.example.slicingbcf.implementation.peserta.feedback_peserta.*
import com.example.slicingbcf.ui.shared.dropdown.DropdownText
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator

@Composable
fun DetailUmpanBalikMentorScreen(
  modifier : Modifier = Modifier,
  id : String,
  viewModel : DetailUmpanBalikMentorViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()

  var evaluasiMentoring by remember { mutableStateOf("") }
  when {
    state.isLoading -> {
      LoadingCircularProgressIndicator()
    }

    state.error != null -> {
      ErrorWithReload(
        modifier = modifier,
        errorMessage = state.error,
        onRetry = {
          viewModel.onEvent(DetailUmpanBalikMentorEvent.Reload)
        }
      )
    }

    else -> {
      Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier.padding(16.dp).verticalScroll(rememberScrollState())
      ) {
        Text(
          text = "Detail Umpan Balik Peserta",
          style = StyledText.MobileMediumMedium,
          color = ColorPalette.Black,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )
        PesertaInformation(
          lembaga = state.lembaga,
          batch = state.batch,
        )
        JadwalMentoring(
          jadwalMentorings = state.jadwalMentorings
        )
        PenilaianLembaga(
          batchOnValueChange = {newValue -> evaluasiMentoring = newValue},
        evaluasiMentoringMentors = state.evaluasiMentoringMentor,
          lembaga = state.lembaga
          )
      }

    }

  }

}

@Composable
fun PesertaInformation(
  lembaga : Lembaga,
  batch : Batch
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(4.dp),
  ) {
    Text(
      text = lembaga.namaPeserta ?: "N/A",
      style = StyledText.MobileBaseSemibold,
      color = ColorPalette.PrimaryColor700,
    )
    LabelValueItem(
      label = "Nama Lembaga",
      value = lembaga.namaLembaga
    )
    LabelValueItem(
      label = "Tipe Mentoring",
      value = batch.tipeMentoring ?: "N/A"
    )
    LabelValueItem(
      label = "Batch",
      value = batch.namaBatch
    )
    LabelValueItem(
      label = "Capaian Program",
      value = batch.capaianProgram ?: "N/A"
    )
  }
}

@Composable
fun JadwalMentoring(
  jadwalMentorings : List<JadwalMentoring>,
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(0.dp)
  ) {
    Text(
      text = "Jadwal Mentoring",
      style = StyledText.MobileBaseSemibold,
      color = ColorPalette.PrimaryColor700,
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
    )
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(8.dp))
        .background(Color.White)
        .border(
          width = 1.dp,
          color = ColorPalette.Monochrome300,
          shape = RoundedCornerShape(8.dp)
        )
    ) {
      Column {
        HeaderRow(jadwal_headers)
        jadwalMentorings.forEachIndexed { index, jadwalMentoring ->
          JadwalMentoringRow(jadwalMentoring, index)
        }
      }
    }
  }
}

@Composable
fun PenilaianLembaga(
  batchOnValueChange : (String) -> Unit,
  lembaga : Lembaga,
  evaluasiMentoringMentors : List<EvaluasiMentoring>

) {
  var expandedBatch by remember { mutableStateOf(false) }
  var selectedBatch by remember { mutableStateOf("") }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Top
  ) {
    Column {
      Text(
        text = "Evaluasi Capaian Mentoring:",
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.Monochrome400
      )
      Text(
        text = lembaga.namaLembaga,
        style = StyledText.MobileBaseSemibold,
        color = ColorPalette.PrimaryColor700
      )
    }
    BatchDropdownMenu(
      value = selectedBatch,
      placeholder = "Batch 3",
      onValueChange = { newValue ->
        selectedBatch = newValue
        batchOnValueChange(newValue)
      },
      expanded = expandedBatch,
      onChangeExpanded = { expandedBatch = it },
      dropdownItems = listOf("Batch 1", "Batch 2", "Batch 3", "Batch 4")
    )
  }
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(Color.White)
      .border(
        width = 1.dp,
        color = ColorPalette.Monochrome300,
        shape = RoundedCornerShape(8.dp)
      )
  ) {
    Column {
      HeaderRow(evaluasi_headers)
      evaluasiMentoringMentors.forEachIndexed { index, evaluasiMentoring ->
        EvaluasiCapaianRow(evaluasiMentoring, index)
      }
    }
  }
}

@Composable
fun HeaderRow(headers : List<Header>) {
  Row(
    modifier = Modifier
      .background(
        color = ColorPalette.PrimaryColor100,
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
      )
      .border(
        BorderStroke(1.dp, ColorPalette.Monochrome300),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
      )
  ) {
    headers.forEach { header ->
      TableCell(
        text = header.name,
        isHeader = true,
        weight = header.weight
      )
    }
  }
}


@Composable
fun TableCell(
  text : String,
  isHeader : Boolean = false,
  weight : Float,
  color : Color = ColorPalette.Monochrome900
) {
  Text(
    text = text,
    style = if (isHeader) StyledText.MobileXsBold else StyledText.MobileXsRegular,
    color = color,
    modifier = Modifier
      .width(120.dp * weight)
      .padding(12.dp),
  )
}

@Composable
fun LabelValueItem(
  label : String,
  value : String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Text(
      text = label,
      style = StyledText.MobileSmallRegular,
      modifier = Modifier.weight(1.3f)
    )
    Text(
      text = ":",
      style = StyledText.MobileSmallRegular,
      modifier = Modifier.weight(0.1f)
    )
    Text(
      text = value,
      style = StyledText.MobileSmallRegular,
      modifier = Modifier.weight(2f)
    )
  }
}

@Composable
fun BatchDropdownMenu(
  value : String,
  placeholder : String,
  onValueChange : (String) -> Unit,
  dropdownItems : List<String>,
  expanded : Boolean,
  onChangeExpanded : (Boolean) -> Unit

) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 32.dp)
  ) {
    Box(
      modifier = Modifier.width(150.dp)
    ) {
      OutlinedButton(
        onClick = { onChangeExpanded(! expanded) },
        modifier = Modifier
          .width(150.dp)
          .height(40.dp),
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
        colors = ButtonDefaults.outlinedButtonColors(
          containerColor = Color.Transparent,
          contentColor = ColorPalette.PrimaryColor700
        ),
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = value.ifEmpty { placeholder },
            style = StyledText.MobileSmallRegular,
            color = ColorPalette.PrimaryColor700,
          )
          Icon(
            imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowRight,
            contentDescription = null,
            tint = ColorPalette.PrimaryColor700
          )
        }
      }
    }

    DropdownText(
      expanded = expanded,
      onExpandedChange = {
        onChangeExpanded(it)
      },
      onItemSelected = { item ->
        onValueChange(item)
        onChangeExpanded(false)
      },
      items = dropdownItems,
      currentItem = value
    )
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailUmpanBalikMentorScreen() {
  DetailUmpanBalikMentorScreen(
    id = "1"
  )
}