package com.example.slicingbcf.implementation.mentor.kelompok_mentoring_mentor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.KelompokMentoring
import com.example.slicingbcf.data.local.headerKelompokMentorings
import com.example.slicingbcf.implementation.peserta.kelompok_mentoring.KelompokMentoringEvent
import com.example.slicingbcf.implementation.peserta.kelompok_mentoring.KelompokMentoringViewModel
import com.example.slicingbcf.implementation.peserta.kelompok_mentoring.Mentor
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator

@Preview(showSystemUi = true)
@Composable
fun KelompokMentoringMentorScreen(
  modifier: Modifier = Modifier,
  vm: KelompokMentoringViewModel = hiltViewModel()
) {
  val state by vm.state.collectAsState()
  val currentMentor by vm.currentMentor.collectAsState()

  Column(
    modifier = modifier.padding(
      horizontal = 16.dp,
      vertical = 24.dp
    ),
    verticalArrangement = Arrangement.spacedBy(40.dp),
  ) {
    TopSection(
      onTabChanged = {
        vm.onEvent(KelompokMentoringEvent.TabChanged(it))
      },
      mentor = currentMentor
    )

    when (state) {
      is UiState.Loading -> {
        LoadingCircularProgressIndicator()
      }
      is UiState.Success -> {
        val data = (state as UiState.Success<List<KelompokMentoring>>).data
        BottomSection(kelompoksMentoring = data)
      }
      is UiState.Error   -> {
        val errorMessage = (state as UiState.Error).message
        ErrorWithReload(
          errorMessage = errorMessage,
          onRetry = {
            vm.onEvent(KelompokMentoringEvent.ReloadData)
          }
        )
      }

    }
  }
}

@Composable
fun TopSection(
  mentor: Mentor?,
  onTabChanged: (Int) -> Unit,
) {
  val tabTitles = listOf("Cluster", "Desain Program")
  Column(
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(
      text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
          append("Halo")
        }
        append(", Peserta")
      },
      style = StyledText.MobileMediumRegular
    )
    Text(
      text = "Berikut Kelompok Mentoring dan Mentor yang akan menjadi pendampingmu selama perjalanan LEAD Indonesia!",
      style = StyledText.MobileSmallRegular
    )

    mentor?.let { mentor ->
      TabContent(mentor = mentor)
    }
  }
}

@Composable
fun TabContent(mentor: Mentor) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(20.dp),
    modifier = Modifier.padding(
      top = 16.dp
    ),
    verticalAlignment = Alignment.CenterVertically

  ) {
    Image(
      modifier = Modifier.size(100.dp),
      painter = painterResource(id = R.drawable.avatar),
      contentDescription = "Person",
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = mentor.name,
        style = StyledText.MobileMediumMedium
      )
      Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Text(
          text = mentor.role,
          style = StyledText.MobileSmallRegular
        )
        Text(
          text = mentor.expertise,
          style = StyledText.Mobile2xsRegular
        )
      }
    }
  }
}

@Composable
fun BottomSection(kelompoksMentoring: List<KelompokMentoring>) {
  ScrollableTable(kelompoksMentoring = kelompoksMentoring)
}

@Composable
fun ScrollableTable(kelompoksMentoring: List<KelompokMentoring>) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    HeaderTable()

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(scrollState)
    ) {
      Column {
        HeaderRow()
        kelompoksMentoring.forEachIndexed { i, kelompokMentoring ->
          KelompokMentoringRow(
            kelompokMentoring = kelompokMentoring,
            i = i,
            isLastRow = i == kelompoksMentoring.size - 1
          )
        }
      }
    }
  }
}

@Composable
private fun HeaderTable() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(ColorPalette.F5F9FE)
      .border(
        width = 1.dp,
        color = ColorPalette.Monochrome300,
        shape = RoundedCornerShape(
          topStart = 8.dp,
          topEnd = 8.dp
        )
      ),
  ) {
    Text(
      text = "Kelompok Mentoring",
      style = StyledText.MobileXsBold,
      color = ColorPalette.Monochrome900,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    )
  }
}

@Composable
fun HeaderRow() {
  Row(
    modifier = Modifier
      .background(ColorPalette.F5F9FE)
      .border(
        width = 1.dp,
        color = ColorPalette.Monochrome300,
      )
  ) {
    headerKelompokMentorings.forEach { header ->
      TableCell(
        text = header.name,
        isHeader = true,
        weight = header.weight,
        onClickSort = { }
      )
    }
  }
}

@Composable
fun TableCell(
  text: String,
  isHeader: Boolean = false,
  weight: Float,
  color: Color = ColorPalette.Monochrome900,
  onClickSort: () -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .width(120.dp * weight)
      .padding(12.dp),
    horizontalArrangement = Arrangement.spacedBy(6.dp)
  ) {
    Text(
      text = text,
      style = if (isHeader) StyledText.MobileXsBold else StyledText.MobileXsRegular,
      color = color,
    )
    if (isHeader && text != "No") {
      IconButton(
        onClick = onClickSort,
        modifier = Modifier.size(9.dp)
      ) {
        Icon(
          painter = painterResource(id = R.drawable.sort),
          contentDescription = "Sort",
          tint = ColorPalette.Monochrome900,
          modifier = Modifier.fillMaxSize()
        )
      }
    }
  }
}

@Composable
fun KelompokMentoringRow(
  kelompokMentoring: KelompokMentoring,
  i: Int,
  isLastRow: Boolean
) {
  val backgroundColor = if (i % 2 == 0) {
    MaterialTheme.colorScheme.surface
  } else {
    MaterialTheme.colorScheme.surfaceVariant
  }

  Row(
    modifier = Modifier
      .background(backgroundColor)
      .then(
        Modifier.border(
          width = 1.dp,
          color = ColorPalette.Monochrome300,
        )

      )
  ) {
    headerKelompokMentorings.forEach { header ->
      TableCell(
        text = when (header.name) {
          "No" -> (i + 1).toString()
          "Nama Lembaga" -> kelompokMentoring.namaLembaga
          "Fokus Isu" -> kelompokMentoring.fokusIsu
          "Jumlah Peserta" -> kelompokMentoring.jumlahPeserta.toString()
          "Jumlah Mentor" -> kelompokMentoring.jumlahMentor.toString()
          "Jumlah Sesi" -> kelompokMentoring.jumlahSesi.toString()
          else -> ""
        },
        isHeader = false,
        weight = header.weight,
        color = ColorPalette.Monochrome900,
        onClickSort = { }
      )
    }
  }
}



