package com.example.slicingbcf.implementation.mentor.feedback_peserta

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.animations.AnimatedContentSlide
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.rating.RatingField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.shared.textfield.TextFieldWithTitle
import com.example.slicingbcf.ui.upload.FileUploadSection
import kotlinx.coroutines.launch

@Composable
fun FormFeedbackPesertaMentorScreen(
  modifier: Modifier = Modifier,
  viewModel: FeedbackPesertaViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState()
  val currentScreen by viewModel.currentScreen.collectAsState()
  val scrollState = rememberScrollState()


  var initialState by remember { mutableIntStateOf(0) }

  val filePickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument(),
    onResult = { uri ->
      viewModel.onEvent(FeedbackPesertaEvent.DokumentasiSesiMentoringClusterChanged(uri))
    }
  )
  val deleteFile = {
    viewModel.onEvent(FeedbackPesertaEvent.DokumentasiSesiMentoringClusterChanged(null))
  }
  val coroutineScope = rememberCoroutineScope()
  LaunchedEffect(currentScreen) {
    coroutineScope.launch {
      scrollState.scrollTo(0)
    }
  }


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
      label = "Feedback Peserta Animation Content",
    ) { targetScreen ->
      when (targetScreen) {
        0 -> FirstScreen(
          onChangeScreen = { viewModel.onEvent(FeedbackPesertaEvent.ChangeScreen(1)) },
          state = uiState,
          onEvent = { event ->
            viewModel.onEvent(event)
          }
        )

        1 -> SecondScreen(
          onBackClick = { viewModel.onEvent(FeedbackPesertaEvent.ChangeScreen(0)) },
          state = uiState,
          onEvent = { event ->
            viewModel.onEvent(event)
          },
          filePickerLauncher = filePickerLauncher,
          deleteFile = deleteFile
        )
      }

      LaunchedEffect(currentScreen) {
        initialState = currentScreen
      }
    }
  }
  SubmitLoadingIndicatorDouble(
    isLoading = uiState.isLoading,
    onAnimationFinished = {
      viewModel.onEvent(FeedbackPesertaEvent.ClearState)
    },
    title = "Mengirimkan Feedback",
    titleBerhasil = "Feedback Berhasil Dikirim",
  )
}


@Composable
fun FirstScreen(
  onChangeScreen : (Int) -> Unit,
  state: FeedbackPesertaState,
  onEvent: (FeedbackPesertaEvent) -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    FirstScreenTopSection(
      state = state,
      onEvent = onEvent
    )
    FirstBottomSection(
      onChangeScreen = onChangeScreen,
      state = state,
      onEvent = onEvent

      )
  }

}

@Composable
fun SecondScreen(
  onBackClick : () -> Unit,
  state: FeedbackPesertaState,
  onEvent: (FeedbackPesertaEvent) -> Unit,
  filePickerLauncher: ManagedActivityResultLauncher<Array<String>, Uri?>,
  deleteFile: () -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    SecondScreenTopSection(
      state = state,
      onEvent = onEvent
    )
    SecondBottomSection(
      onBackClick = onBackClick,
      state = state,
      onEvent = onEvent,
      filePickerLauncher = filePickerLauncher,
      deleteFile = deleteFile
    )


  }
}


@Composable
fun FirstScreenTopSection(
  state: FeedbackPesertaState,
  onEvent: (FeedbackPesertaEvent) -> Unit
) {
  var expandedNamaLembaga by remember { mutableStateOf(false) }
  var expandedPeriodeCapaianMentoring by remember { mutableStateOf(false) }

  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    CustomOutlinedTextFieldDropdown(
      value = state.namaLembaga,
      onValueChange = {
        onEvent(FeedbackPesertaEvent.NamaLembagaChanged(it))
      },
      expanded = expandedNamaLembaga,
      onChangeExpanded = { expandedNamaLembaga = it },
      label = "Nama Lembaga",
      placeholder = "Pilih Nama Lembaga",
      dropdownItems = listOf("Lembaga 1", "Lembaga 2", "Lembaga 3")
    )
    CustomOutlinedTextFieldDropdown(
      value = state.periodeCapaianMentoring,
      onValueChange = {
        onEvent(FeedbackPesertaEvent.PeriodeCapaianMentoringChanged(it))
      },
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
  state : FeedbackPesertaState,
  onEvent: (FeedbackPesertaEvent) -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    Text(
      text = "Evaluasi Capaian Mentoring Lembaga",
      style = StyledText.MobileBaseSemibold,
      color = ColorPalette.PrimaryColor700,
    )
    ConstantFeedbackPeserta.evaluasiCapaianMentorings.forEachIndexed{i, evaluasiCapaianMentoring ->
      RatingField(
        description = evaluasiCapaianMentoring,
        rating = state.evaluasiCapaianMentoring[i],
        onRatingChange = {
          onEvent(
            FeedbackPesertaEvent.EvaluasiCapaianMentoringChanged(
              index = i,
              evaluasiKepuasan = it
            )
          )
        }
      )
    }
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
fun SecondScreenTopSection(
  state : FeedbackPesertaState,
  onEvent: (FeedbackPesertaEvent) -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    RatingField(
      title = "Evaluasi Lembaga",
      description = "Apakah setiap lembaga on the track untuk mencapai tujuannya)",
      rating = state.evaluasiLembaga,
      onRatingChange = {
        onEvent(FeedbackPesertaEvent.EvaluasiLembagaChanged(it))
      }
    )
    RatingField(
      title = "Evaluasi Kepuasan",
      description = "Apakah Anda puas dalam sesi mentoring yang telah dilakukan?*",
      rating = state.evaluasiKepuasan,
      onRatingChange = {
        onEvent(FeedbackPesertaEvent.EvaluasiKepuasanChanged(it))
      }
    )

  }
}

@Composable
fun SecondBottomSection(
  onBackClick : () -> Unit,
  state : FeedbackPesertaState,
  onEvent: (FeedbackPesertaEvent) -> Unit,
  filePickerLauncher: ManagedActivityResultLauncher<Array<String>, Uri?>,
  deleteFile: () -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    FormSection(
      state = state,
      onEvent = onEvent,
      filePickerLauncher = filePickerLauncher,
      deleteFile = deleteFile
    )
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
        onClick = { onEvent(FeedbackPesertaEvent.Submit) },
        style = StyledText.MobileBaseMedium,
        textColor = ColorPalette.Monochrome10,
      )
    }
  }
}


@Composable
fun FormSection(
  state : FeedbackPesertaState,
  onEvent: (FeedbackPesertaEvent) -> Unit,
  filePickerLauncher: ManagedActivityResultLauncher<Array<String>, Uri?>,
  deleteFile: () -> Unit
  ) {

  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    TextFieldWithTitle(
      heading = "Hal-hal yang dibahas selama kegiatan mentoring",
      isTitleWithAsterisk = true,
      title = "Hal-hal yang Dibahas Selama Kegiatan Mentoring",
      onChange = {
        onEvent(FeedbackPesertaEvent.HalHalYangDibahasChanged(it))
      },
      value = state.halhalYangDibahas,
      placeholder = "Kegiatan Yang Dibahas",
      label = null,
      styleTitle = StyledText.MobileSmallMedium,
      heightTextField = 124
    )
    TextFieldWithTitle(
      title = "Silakan sampaikan tantangan utama yang dihadapi dari setiap lembaga*",
      isTitleWithAsterisk = true,
      onChange = {
        onEvent(FeedbackPesertaEvent.TantanganUtamaChanged(it))
      },
      value = state.tantanganUtama,
      placeholder = "Tantangan Utama",
      label = null,
      styleTitle = StyledText.MobileSmallMedium,
      heightTextField = 124
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(12.dp),

      ) {

      FileUploadSection(
        title = "Dokumentasi Sesi Mentoring Cluster",
        asteriskAtEnd = true,
        onFileSelect = {
          filePickerLauncher.launch(arrayOf("image/*", "application/pdf"))
        },
        buttonText = "Klik untuk unggah file",
        deleteFile = deleteFile,
        selectedFileUri = state.dokumentasiSesiMentoringCluster,

      )
    }

  }

}