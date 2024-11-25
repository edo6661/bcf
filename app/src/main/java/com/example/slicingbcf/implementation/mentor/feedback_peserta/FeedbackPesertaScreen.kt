package com.example.slicingbcf.implementation.mentor.feedback_peserta

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.animations.AnimatedContentSlide
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.rating.RatingField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.shared.textfield.TextFieldWithTitle

// TODO: jadiin best practice tentang animasi dan suruh jelasin tentang animasi nya
@Composable
fun FeedbackPesertaScreen(
  modifier : Modifier = Modifier,
) {
  var currentScreen by rememberSaveable { mutableStateOf(0) }
  val onChangeScreen : (Int) -> Unit = { screen ->
    currentScreen = screen
  }
  val scrollState = rememberScrollState()

  var initialState by remember { mutableStateOf(0) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(24.dp),
  ) {
    Title()
    AnimatedContentSlide(
      currentScreen = currentScreen,
      initialState = initialState,
      label = "Feedback Peserta Animation Content ",
    ) { targetScreen ->
      when (targetScreen) {
        0 -> FirstScreen(
          onChangeScreen = onChangeScreen,
          namaLembaga = "",
          namaLembagaOnValueChange = {},
          periodeCapaianMentoring = "",
          periodeCapaianMentoringOnValueChange = {}
        )

        1 -> SecondScreen(
          onBackClick = { onChangeScreen(0) }
        )
      }

      LaunchedEffect(currentScreen) {
        initialState = currentScreen
      }
    }
  }
}


@Composable
fun FirstScreen(
  namaLembaga : String,
  namaLembagaOnValueChange : (String) -> Unit,
  periodeCapaianMentoring : String,
  periodeCapaianMentoringOnValueChange : (String) -> Unit,
  onChangeScreen : (Int) -> Unit,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    FirstScreenTopSection(
      namaLembaga = namaLembaga,
      namaLembagaOnValueChange = namaLembagaOnValueChange,
      periodeCapaianMentoring = periodeCapaianMentoring,
      periodeCapaianMentoringOnValueChange = periodeCapaianMentoringOnValueChange
    )
    FirstBottomSection(
      onChangeScreen = onChangeScreen,

      )
  }

}

@Composable
fun SecondScreen(
  onBackClick : () -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    SecondScreenTopSection()
    SecondBottomSection(
      onBackClick = onBackClick
    )


  }
}


@Composable
fun FirstScreenTopSection(
  namaLembaga : String,
  namaLembagaOnValueChange : (String) -> Unit,
  periodeCapaianMentoring : String,
  periodeCapaianMentoringOnValueChange : (String) -> Unit
) {
  var expandedNamaLembaga by remember { mutableStateOf(false) }
  var expandedPeriodeCapaianMentoring by remember { mutableStateOf(false) }

  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    CustomOutlinedTextFieldDropdown(
      value = namaLembaga,
      onValueChange = namaLembagaOnValueChange,
      expanded = expandedNamaLembaga,
      onChangeExpanded = { expandedNamaLembaga = it },
      label = "Nama Lembaga",
      placeholder = "Pilih Nama Lembaga",
      dropdownItems = listOf("Lembaga 1", "Lembaga 2", "Lembaga 3")
    )
    CustomOutlinedTextFieldDropdown(
      value = periodeCapaianMentoring,
      onValueChange = periodeCapaianMentoringOnValueChange,
      expanded = expandedPeriodeCapaianMentoring,
      onChangeExpanded = { expandedPeriodeCapaianMentoring = it },
      label = "Periode Capaian Mentoring",
      placeholder = "Pilih Periode Capaian Mentoring",
      dropdownItems = listOf("Periode 1", "Periode 2", "Periode 3")
    )
    HorizontalDivider(
      modifier = Modifier.fillMaxWidth()
    )
  }

}

@Composable
private fun Title() {
  Text(
    text = "Umpan Balik Peserta",
    style = StyledText.MobileLargeMedium,
    color = ColorPalette.Black,
    textAlign = TextAlign.Center,
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
fun FirstBottomSection(
  onChangeScreen : (Int) -> Unit,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    RatingField(
      title = "Kualitas Materi",
      description = "Seberapa baik kualitas materi yang diberikan?",
      rating = "1",
      onRatingChange = {}
    )
    RatingField(
      title = "Kualitas Materi",
      description = "Seberapa baik kualitas materi yang diberikan?",
      rating = "1",
      onRatingChange = {}
    )
    Box(
      modifier = Modifier
        .fillMaxWidth(),
      contentAlignment = Alignment.CenterEnd

    ) {
      PrimaryButton(
        text = "Berikutnya",
        onClick = { onChangeScreen(1) },
        style = StyledText.MobileBaseMedium,
        textColor = ColorPalette.Monochrome10,
      )
    }
  }
}


@Composable
fun SecondScreenTopSection() {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    RatingField(
      title = "Evaluasi Lembaga",
      description = "Apakah setiap lembaga on the track untuk mencapai tujuannya)",
      rating = "1",
      onRatingChange = {}
    )
    RatingField(
      title = "Evaluasi Kepuasan",
      description = "Apakah Anda puas dalam sesi mentoring yang telah dilakukan?*",
      rating = "1",
      onRatingChange = {}
    )

  }
}

@Composable
fun SecondBottomSection(
  onBackClick : () -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    FormSection()
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.End
    ) {
      PrimaryButton(
        text = "Kembali",
        onClick = onBackClick,
        style = StyledText.MobileBaseMedium,
        textColor = ColorPalette.PrimaryColor700,
        color = ColorPalette.SurfaceContainerLowest,
      )
      PrimaryButton(
        text = "Kirim",
        onClick = onBackClick,
        style = StyledText.MobileBaseMedium,
        textColor = ColorPalette.Monochrome10,
      )
    }
  }
}


@Composable
fun FormSection(
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    TextFieldWithTitle(
      heading = "Hal-hal yang dibahas selama kegiatan mentoring",
      title = "Hal-hal yang Dibahas Selama Kegiatan Mentoring*",
      onChange = {},
      value = "",
      placeholder = "Dibahas",
      label = "Kegiatan",
      styleTitle = StyledText.MobileSmallMedium
    )
    TextFieldWithTitle(
      title = "Silakan sampaikan tantangan utama yang dihadapi dari setiap lembaga*",
      onChange = {},
      value = "",
      placeholder = "Umpan Balik",
      label = "Umpan",
      styleTitle = StyledText.MobileSmallMedium
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(12.dp),

      ) {
      Text(
        text = "Dokumentasi Sesi Mentoring Cluster*",
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.Black,
      )
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
          .border(
            width = 1.dp,
            color = ColorPalette.Monochrome500,
            shape = RoundedCornerShape(16.dp)
          )
          .padding(
            16.dp
          )
      ) {
        Image(
          painter = painterResource(
            R.drawable.icon_wrapper
          ),
          contentDescription = "",
          modifier = Modifier.size(48.dp)
        )
        Text(
          text = "Klik atau tarik file ke area ini untuk mengunggahnya",

          style = StyledText.MobileXsMedium,
        )
        Text(
          text = "Format file yang dapat diunggah hanya PDF, dengan ukuran maksimal 5 MB",
          style = StyledText.Mobile2xsRegular,
          color = ColorPalette.Monochrome300,
          textAlign = TextAlign.Center
        )

      }
    }

  }

}