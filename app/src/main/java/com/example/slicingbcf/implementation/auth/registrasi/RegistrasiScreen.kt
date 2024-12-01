package com.example.slicingbcf.implementation.auth.registrasi

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.dao.model.JangkauanPenerimaManfaat
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.fokusIsus
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.jangkauanPrograms
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.jenisClusterLembagaSosials
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.jenisKelamins
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.kotas
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.lembagaSosials
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.pendidikanTerakhirs
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.pernahs
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.provinsis
import com.example.slicingbcf.implementation.auth.registrasi.ConstantRegistrasi.Companion.wilayahJangkauanPrograms
import com.example.slicingbcf.ui.animations.AnimatedContentSlide
import com.example.slicingbcf.ui.animations.AnimatedMessage
import com.example.slicingbcf.ui.animations.MessageType
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicator
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.TextWithAsterisk
import com.example.slicingbcf.ui.shared.dialog.CustomConfirmationDialog
import com.example.slicingbcf.ui.shared.message.ErrorMessageTextField
import com.example.slicingbcf.ui.shared.message.SecondaryButton
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdownDate
import com.example.slicingbcf.ui.upload.FileUploadSection
import com.example.slicingbcf.util.convertMillisToDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun RegistrasiScreen(
  modifier : Modifier,
  viewModel : RegistrasiViewModel = hiltViewModel(),
  navigateToUmpanBalik : () -> Unit
) {

  val state by viewModel.uiState.collectAsState()

  val verticalScroll = rememberScrollState()

  var currentScreen by rememberSaveable { mutableIntStateOf(0) }
  var indicatorProgress by remember { mutableFloatStateOf(0.2f) }
  var initialState by remember { mutableIntStateOf(0) }
  val changeScreen : (Int) -> Unit = { screen -> currentScreen = screen }


  val resetScreenAndIndicator = {
    currentScreen = 0
    indicatorProgress = 0.2f
  }

  val nextIndicatorProgress = {
    if (indicatorProgress < 1f) {
      indicatorProgress += 0.2f
      changeScreen(currentScreen + 1)
    }
  }

  val prevIndicatorProgress = {
    if (indicatorProgress > 0.2f) {
      indicatorProgress -= 0.2f
      changeScreen(currentScreen - 1)
    }
  }

  val gotoProfilPeserta = {
    changeScreen(2)
    indicatorProgress = 0.6f
  }

  val titleBasedOnScreen = when (currentScreen) {
    0    -> "Profil Lembaga"
    1    -> "Program Lembaga"
    2    -> "Pendaftaran Peserta"
    3    -> "Pertanyaan Umum"
    4    -> "Ringkasan Data Pendaftaran"
    else -> "Wrong Screen"
  }

  val coroutineScope = rememberCoroutineScope()
  LaunchedEffect(currentScreen) {
    coroutineScope.launch {
      verticalScroll.scrollTo(0)
    }
  }

  LaunchedEffect(state.isSuccess) {
    if (state.isSuccess) {
      delay(1500)
      resetScreenAndIndicator()
      viewModel.onEvent(RegisterEvent.ClearState)
      navigateToUmpanBalik()
    }
  }
  LaunchedEffect(state.error != null) {
    if (state.error != null) {
      delay(3000)
      viewModel.onEvent(RegisterEvent.ClearError)
    }
  }



  Box(
    modifier = modifier
      .background(ColorPalette.OnPrimary)
      .statusBarsPadding()
      .padding(horizontal = 16.dp)
  ) {

    Column(
      verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
    ) {

      TopSection(
        indicatorProgress = indicatorProgress,
        titleBasedOnScreen = titleBasedOnScreen
      )
      BottomSection(
        nextIndicatorProgress = nextIndicatorProgress,
        prevIndicatorProgress = prevIndicatorProgress,
        initialState = initialState,
        currentScreen = currentScreen,
        onInitialScreenChange = { initialState = it },
        state = state,
        onEvent = { viewModel.onEvent(it) },
        verticalScroll = verticalScroll,
        gotoProfilPeserta = gotoProfilPeserta
      )
    }
    SubmitLoadingIndicator(
      isLoading = state.isLoading,
      modifier = Modifier.align(Alignment.Center)
    )

    AnimatedMessage(
      isVisible = state.error != null,
      message = state.error ?: "",
      messageType = MessageType.Error,
      modifier = Modifier
        .padding(top = 16.dp)
        .align(Alignment.TopCenter)
    )
  }
}

@Composable
private fun TopSection(
  indicatorProgress : Float,
  titleBasedOnScreen : String
) {
  val animatedProgress by animateFloatAsState(
    targetValue = indicatorProgress,
    animationSpec = tween(durationMillis = 500),
    label = "Animated Progress"
  )

  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Pendaftaran LEAD Indonesia 2023",
      style = StyledText.MobileLargeMedium
    )
    AnimatedContent(
      targetState = titleBasedOnScreen,
      transitionSpec = {
        slideInVertically { it } + fadeIn() togetherWith
          slideOutVertically { - it } + fadeOut()
      }, label = "Title Registrasi Animation Content"
    ) { title ->
      Text(
        text = title,
        style = StyledText.MobileLargeBold
      )
    }
    LinearProgressIndicator(
      progress = { animatedProgress },
      modifier = Modifier.fillMaxWidth(),
      color = ColorPalette.PrimaryColor700,
    )
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSection(
  nextIndicatorProgress : () -> Unit,
  prevIndicatorProgress : () -> Unit,
  initialState : Int,
  onInitialScreenChange : (Int) -> Unit,
  currentScreen : Int,
  state : RegistrasiState,
  onEvent : (RegisterEvent) -> Unit,
  verticalScroll : ScrollState,
  gotoProfilPeserta : () -> Unit
) {

  var expandedProvinsi by remember { mutableStateOf(false) }
  var expandedKota by remember { mutableStateOf(false) }
  var expandedDate by remember { mutableStateOf(false) }
  var expandedLembagaSosial by remember { mutableStateOf(false) }
  var expandedClusterLembagaSosial by remember { mutableStateOf(false) }
  var expandedFokusIsu by remember { mutableStateOf(false) }
  var expandedWilayahJangkauanProgram by remember { mutableStateOf(false) }
  var expandedJangkauanProgram by remember { mutableStateOf(false) }
  var expandedJenisKelamin by remember { mutableStateOf(false) }
  var expandedPendidikan by remember { mutableStateOf(false) }
  val filePickerLauncherDokumentasiSesiMentoringCluster = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument(),
    onResult = { uri ->
      onEvent(
        RegisterEvent.SelectedFileUriDokumentasiSesiMentoringClusterChanged(
          uri
        )
      )
    }
  )
  val deleteFileDokumentasiSesiMentoringCluster = {
    onEvent(RegisterEvent.SelectedFileUriDokumentasiSesiMentoringClusterChanged(null))
  }
  val filePickerLauncherProposalMitra = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument(),
    onResult = { uri -> onEvent(RegisterEvent.SelectedFileUriProposalProgramChanged(uri)) }
  )
  val deleteFileProposalMitra = {
    onEvent(RegisterEvent.SelectedFileUriProposalProgramChanged(null))
  }

  val filePickerLauncherKTP = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument(),
    onResult = { uri ->
      onEvent(RegisterEvent.SelectedFileUriKTPChanged(uri))
    }
  )
  val deleteFileKTP = {
    onEvent(RegisterEvent.SelectedFileUriKTPChanged(null))
  }
  val filePickerLauncherCV = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument(),
    onResult = { uri ->
      onEvent(RegisterEvent.SelectedFileUriCVChanged(uri))
    }
  )
  val deleteFileCV = {
    onEvent(RegisterEvent.SelectedFileUriCVChanged(null))
  }
  val filePickerLauncherLaporanAkhirTahun = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument(),
    onResult = { uri ->
      onEvent(RegisterEvent.SelectedFileUriLaporanAkhirTahunChanged(uri))
    }
  )
  val deleteFileLaporanAkhirTahun = {
    onEvent(RegisterEvent.SelectedFileUriLaporanAkhirTahunChanged(null))
  }


  val datePickerState = rememberDatePickerState()
  LaunchedEffect(datePickerState.selectedDateMillis) {
    datePickerState.selectedDateMillis?.let { millis ->
      val formattedDate = convertMillisToDate(millis)
      if (formattedDate != state.selectedDate) {
        onEvent(RegisterEvent.SelectedDateChanged(formattedDate))
      }
    }
  }


  Box(
    modifier = Modifier
      .fillMaxSize()
      .pointerInput(expandedDate, expandedProvinsi) {
        detectTapGestures {
          expandedDate = false
          expandedProvinsi = false
          expandedKota = false
          expandedLembagaSosial = false
          expandedClusterLembagaSosial = false
          expandedFokusIsu = false
          expandedWilayahJangkauanProgram = false
          expandedJangkauanProgram = false
          expandedJenisKelamin = false
          expandedPendidikan = false


        }
      }

  ) {


    AnimatedContentSlide(
      currentScreen = currentScreen,
      initialState = initialState,
      label = "Registrasi Animation Content",
    ) { targetScreen ->
      Column(
        modifier = Modifier
          .verticalScroll(verticalScroll),
        verticalArrangement = Arrangement.spacedBy(24.dp)
      ) {
        // TODO: FOR DEBUGGING, SOON TO BE REMOVED
//        PrimaryButton(
//          text = "Kirim",
//          onClick = {
//            onEvent(RegisterEvent.Submit)
//          }
//        )
        when (targetScreen) {
          0 -> FirstScreen(
            selectedDate = state.selectedDate,
            datePickerState = datePickerState,
            expandedDate = expandedDate,
            onExpandedDateChange = { expandedDate = it },
            expandedProvinsi = expandedProvinsi,
            onExpandedProvinsiChange = { expandedProvinsi = it },
            expandedKota = expandedKota,
            onExpandedKotaChange = { expandedKota = it },
            expandedLembagaSosial = expandedLembagaSosial,
            onExpandedLembagaSosialChange = { expandedLembagaSosial = it },
            expandedClusterLembagaSosial = expandedClusterLembagaSosial,
            onExpandedClusterLembagaSosialChange = { expandedClusterLembagaSosial = it },
            expandedFokusIsu = expandedFokusIsu,
            onExpandedFokusIsuChange = { expandedFokusIsu = it },
            nextIndicatorProgress = nextIndicatorProgress,
            filePickerLauncher = filePickerLauncherDokumentasiSesiMentoringCluster,
            deleteFile = deleteFileDokumentasiSesiMentoringCluster,
            state = state,
            onEvent = onEvent
          )

          1 -> SecondScreen(
            prevIndicatorProgress = prevIndicatorProgress,
            nextIndicatorProgress = nextIndicatorProgress,
            expandedJangkauanProgram = expandedJangkauanProgram,
            expandedWilayahJangkauanProgram = expandedWilayahJangkauanProgram,
            onExpandedJangkauanProgramChange = {
              expandedJangkauanProgram = it
            },
            onExpandedWilayahJangkauanProgramChange = {
              expandedWilayahJangkauanProgram = it
            },
            filePickerLauncher = filePickerLauncherProposalMitra,
            deleteFile = deleteFileProposalMitra,
            state = state,
            onEvent = onEvent
          )

          2 -> ThirdScreen(
            prevIndicatorProgress = prevIndicatorProgress,
            nextIndicatorProgress = nextIndicatorProgress,
            expandedPendidikan = expandedPendidikan,
            expandedJenisKelamin = expandedJenisKelamin,
            onExpandedPendidikanChange = {
              expandedPendidikan = it
            },
            onExpandedJenisKelaminChange = {
              expandedJenisKelamin = it
            },
            filePickerLauncherKTP = filePickerLauncherKTP,
            selectedFileUriKTP = state.selectedFileUriKTP,
            deleteFileKTP = deleteFileKTP,
            filePickerLauncherCV = filePickerLauncherCV,
            selectedFileUriCV = state.selectedFileUriCV,
            deleteFileCV = deleteFileCV,
            state = state,
            onEvent = onEvent


          )

          3 -> FourthScreen(
            prevIndicatorProgress = prevIndicatorProgress,
            nextIndicatorProgress = nextIndicatorProgress,
            selectedFileUri = state.selectedFileUriLaporanAkhirTahun,
            filePickerLauncher = filePickerLauncherLaporanAkhirTahun,
            deleteFile = deleteFileLaporanAkhirTahun,
            state = state,
            onEvent = onEvent,
            expandedPendidikan = expandedPendidikan,
            onExpandedPendidikanChange = { expandedPendidikan = it }
          )

          4 -> FifthScreen(
            prevIndicatorProgress = prevIndicatorProgress,
            state = state,
            onEvent = onEvent,
            gotoProfilPeserta = gotoProfilPeserta

          )
        }
        LaunchedEffect(currentScreen) { onInitialScreenChange(currentScreen) }
      }
    }
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FirstScreen(
  selectedDate : String,
  datePickerState : DatePickerState,
  expandedDate : Boolean,
  onExpandedDateChange : (Boolean) -> Unit,
  expandedProvinsi : Boolean,
  onExpandedProvinsiChange : (Boolean) -> Unit,
  expandedKota : Boolean,
  onExpandedKotaChange : (Boolean) -> Unit,
  expandedLembagaSosial : Boolean,
  expandedClusterLembagaSosial : Boolean,
  onExpandedClusterLembagaSosialChange : (Boolean) -> Unit,
  onExpandedLembagaSosialChange : (Boolean) -> Unit,
  expandedFokusIsu : Boolean,
  onExpandedFokusIsuChange : (Boolean) -> Unit,
  nextIndicatorProgress : () -> Unit,
  filePickerLauncher : ManagedActivityResultLauncher<Array<String>, Uri?>,
  deleteFile : () -> Unit,
  state : RegistrasiState,
  onEvent : (RegisterEvent) -> Unit,

  ) {


  CustomOutlinedTextField(
    label = "Nama Lembaga",
    value = state.namaLembaga,
    error = state.namaLembagaError,
    onValueChange = {
      onEvent(RegisterEvent.NamaLembagaChanged(it))
    },
    placeholder = "Masukkan nama lembaga",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    labelFocusedColor = ColorPalette.PrimaryColor700,
    borderColor = ColorPalette.Outline,
    rounded = 40,
  )
  CustomOutlinedTextField(
    label = "Email Formal Lembaga",
    value = state.emailLembaga,
    asteriskAtEnd = true,
    onValueChange = {
      onEvent(RegisterEvent.EmailLembagaChanged(it))
    },
    labelDefaultStyle = StyledText.MobileSmallRegular,

    error = state.emailLembagaError,
    placeholder = "contoh@bcf.or.id",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )

  CustomOutlinedTextField(
    label = "Alamat Lengkap Lembaga",
    asteriskAtEnd = true,
    value = state.alamatLembaga,
    onValueChange = {
      onEvent(RegisterEvent.AlamatLembagaChanged(it))
    },
    error = state.alamatLembagaError,
    placeholder = "contoh@bcf.or.id",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40,


    )



  CustomOutlinedTextFieldDropdown(
    label = "Provinsi",
    value = state.provinsi,
    asteriskAtEnd = true,
    onValueChange = {
      onEvent(RegisterEvent.ProvinsiChanged(it))
    },
    placeholder = "Pilih Provinsi",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = provinsis,
    expanded = expandedProvinsi,
    onChangeExpanded = {
      onExpandedProvinsiChange(it)
    },
    error = state.provinsiError
  )

  CustomOutlinedTextFieldDropdown(
    label = "Kota / Kabupaten",
    value = state.kota,
    onValueChange = {
      onEvent(RegisterEvent.KotaChanged(it))
    },
    asteriskAtEnd = true,

    placeholder = "Pilih kota/kabupaten",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = kotas,
    expanded = expandedKota,
    onChangeExpanded = {
      onExpandedKotaChange(it)
    },
    error = state.kotaError
  )
  CustomOutlinedTextFieldDropdownDate(
    label = "Tanggal Berdiri",
    value = selectedDate,
    placeholder = "DD/MM/YYYY",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    datePickerState = datePickerState,
    asteriskAtEnd = true,
    error = state.selectedDateError,
    expanded = expandedDate,
    onChangeExpanded = {
      onExpandedDateChange(it)
    },
  )
  CustomOutlinedTextFieldDropdown(
    label = "Jenis Lembaga Sosial",
    value = state.jenisLembagaSosial,
    asteriskAtEnd = true,
    onValueChange = {
      onEvent(RegisterEvent.JenisLembagaSosialChanged(it))
    },
    placeholder = "Pilih jenis lembaga sosial",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    expanded = expandedLembagaSosial,
    onChangeExpanded = {
      onExpandedLembagaSosialChange(it)
    },
    dropdownItems = lembagaSosials,
    error = state.jenisLembagaSosialError
  )
  CustomOutlinedTextFieldDropdown(
    label = "Jenis Cluster Lembaga Sosial",
    value = state.jenisClusterLembagaSosial,
    onValueChange = {
      onEvent(RegisterEvent.JenisClusterLembagaSosialChanged(it))
    },
    placeholder = "Pilih jenis cluster lembaga sosial",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    expanded = expandedClusterLembagaSosial,
    onChangeExpanded = {
      onExpandedClusterLembagaSosialChange(it)
    },
    asteriskAtEnd = true,
    dropdownItems = jenisClusterLembagaSosials,
    error = state.jenisClusterLembagaSosialError
  )
  CustomOutlinedTextFieldDropdown(
    label = "Fokus Isu",
    value = state.fokusIsu,
    onValueChange = {
      onEvent(RegisterEvent.FokusIsuChanged(it))
    },
    placeholder = "Pilih fokus isu",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = fokusIsus,
    expanded = expandedFokusIsu,
    asteriskAtEnd = true,

    onChangeExpanded = {
      onExpandedFokusIsuChange(it)
    },
    error = state.fokusIsuError
  )
  CustomOutlinedTextField(
    label = "Profil Singkat Lembaga",
    value = state.profilLembaga,
    onValueChange = {
      onEvent(RegisterEvent.ProfilLembagaChanged(it))
    },
    asteriskAtEnd = true,
    error = state.profilLembagaError,
    placeholder = "Berisi uraian penjelasan mengenai kritik dan saran dari peserta untuk agenda sesi mentoring selanjutnya",
    modifier = Modifier
      .fillMaxWidth()
      .height(148.dp),
    multiLine = true,
    maxLines = 5,
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 20,
    borderColor = ColorPalette.Outline,

    )
  CustomOutlinedTextField(
    label = "Apa alasan anda mengikuti LEAD Indonesia 2023?",
    value = state.alasanKeikutsertaan,
    onValueChange = {
      onEvent(RegisterEvent.AlasanKeikutsertaanChanged(it))
    },
    asteriskAtEnd = true,
    error = state.alasanKeikutsertaanError,
    placeholder = "Berisi uraian penjelasan mengenai kritik dan saran dari peserta untuk agenda sesi mentoring selanjutnya",
    modifier = Modifier
      .fillMaxWidth()
      .height(148.dp),
    multiLine = true,
    maxLines = 5,

    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 20,
    borderColor = ColorPalette.Outline,

    )
  FileUploadSection(
    title = "Dokumentasi sesi mentoring cluster",
    asteriskAtEnd = true,

    buttonText = "Klik untuk unggah file",
    onFileSelect = { filePickerLauncher.launch(arrayOf("image/*", "application/pdf")) },
    selectedFileUri = state.selectedFileUriDokumentasiSesiMentoringCluster,
    deleteFile = deleteFile,
    error = state.selectedFileUriDokumentasiSesiMentoringClusterError
  )
  NextPrevButton(
    nextIndicatorProgress = nextIndicatorProgress,
    isPrevExist = false
  )

}

@Composable
private fun SecondScreen(
  state : RegistrasiState,
  onEvent : (RegisterEvent) -> Unit,
  prevIndicatorProgress : () -> Unit,
  nextIndicatorProgress : () -> Unit,
  expandedJangkauanProgram : Boolean,
  onExpandedJangkauanProgramChange : (Boolean) -> Unit,
  expandedWilayahJangkauanProgram : Boolean,
  onExpandedWilayahJangkauanProgramChange : (Boolean) -> Unit,
  filePickerLauncher : ManagedActivityResultLauncher<Array<String>, Uri?>,
  deleteFile : () -> Unit
) {


  Column(
    verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = Modifier.animateContentSize()
  ) {
    CustomOutlinedTextFieldDropdown(
      label = "Jangkauan Program",
      value = state.jangkauanProgram,
      onValueChange = {
        onEvent(RegisterEvent.JangkauanProgramChanged(it))
      },
      placeholder = "Pilih Jangkauan Program",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      dropdownItems = jangkauanPrograms,
      expanded = expandedJangkauanProgram,
      onChangeExpanded = {
        onExpandedJangkauanProgramChange(it)
      },
      asteriskAtEnd = true,
      error = state.jangkauanProgramError
    )
    CustomOutlinedTextFieldDropdown(
      label = "Wilayah Jangkauan Program",
      value = state.wilayahJangkauanProgram,
      onValueChange = {
        onEvent(RegisterEvent.WilayahJangkauanProgramChanged(it))
      },
      placeholder = "Pilih Wilayah Jangkauan Program",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      dropdownItems = wilayahJangkauanPrograms,
      expanded = expandedWilayahJangkauanProgram,
      onChangeExpanded = {
        onExpandedWilayahJangkauanProgramChange(it)
      },
      asteriskAtEnd = true,

      error = state.wilayahJangkauanProgramError
    )

    Column(
      verticalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.animateContentSize()
    ) {
      Text(
        text = TextWithAsterisk(
          label = "Jumlah Angka Penerimaan Manfaat",
        ),
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.PrimaryColor700
      )

      state.jumlahAngkaPenerimaanManfaat.forEachIndexed { index, field ->
        var expandedPilihKota by remember { mutableStateOf(false) }

        CustomRowWithFields(
          dropdownLabel = "Pilih Kota",
          dropdownValue = field.kota,
          onDropdownValueChange = { newValue ->
            onEvent(
              RegisterEvent.UpdateJumlahAngkaPenerimaanManfaat(
                index,
                field.copy(kota = newValue)
              )
            )
          },
          labelFocusedColor = ColorPalette.Monochrome800,
          dropdownPlaceholder = "",
          dropdownItems = kotas,
          expanded = expandedPilihKota,
          onExpandedChange = { expandedPilihKota = it },
          jumlahValue = if (field.jumlah == 0) "" else field.jumlah.toString(),
          onJumlahValueChange = { newValue ->
            onEvent(
              RegisterEvent.UpdateJumlahAngkaPenerimaanManfaat(
                index,
                field.copy(
                  jumlah = newValue.toIntOrNull() ?: 0
                )
              )
            )
          }
        )
      }

      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        SecondaryButton(
          onClick = {
            onEvent(
              RegisterEvent.AddJumlahAngkaPenerimaanManfaat(
                JangkauanPenerimaManfaat(kota = "", jumlah = 0)
              )
            )
          },
          text = "Tambah"
        )
        AnimatedVisibility(
          visible = state.jumlahAngkaPenerimaanManfaat.size > 1,
          enter = fadeIn(),
          exit = fadeOut()
        ) {
          SecondaryButton(
            onClick = {
              onEvent(RegisterEvent.RemoveLastJumlahAngkaPenerimaanManfaat)
            },
            text = "Hapus"
          )
        }
      }
    }

    CustomOutlinedTextField(
      label = "Target Utama Program",
      value = state.targetUtamaProgram,
      onValueChange = {
        onEvent(RegisterEvent.TargetUtamaProgramChanged(it))
      },
      asteriskAtEnd = true,
      error = state.targetUtamaProgramError,
      placeholder = "Ketik target utama program",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      labelFocusedColor = ColorPalette.PrimaryColor700,
      borderColor = ColorPalette.Outline,
      rounded = 40
    )

    FileUploadSection(
      title = "Unggah Proposal Program Dengan Mitra",
      buttonText = "Klik untuk unggah file",
      onFileSelect = { filePickerLauncher.launch(arrayOf("image/*", "application/pdf")) },
      selectedFileUri = state.selectedFileUriProposalProgram,
      deleteFile = deleteFile,
      error = state.selectedFileUriProposalProgramError,
      asteriskAtEnd = true
    )


    NextPrevButton(
      nextIndicatorProgress = nextIndicatorProgress,
      prevIndicatorProgress = prevIndicatorProgress
    )
  }
}

@Composable
private fun ThirdScreen(
  state : RegistrasiState,
  onEvent : (RegisterEvent) -> Unit,
  prevIndicatorProgress : () -> Unit,
  nextIndicatorProgress : () -> Unit,
  expandedPendidikan : Boolean,
  onExpandedPendidikanChange : (Boolean) -> Unit,
  expandedJenisKelamin : Boolean,
  onExpandedJenisKelaminChange : (Boolean) -> Unit,
  filePickerLauncherKTP : ManagedActivityResultLauncher<Array<String>, Uri?>,
  selectedFileUriKTP : Uri?,
  deleteFileKTP : () -> Unit,
  filePickerLauncherCV : ManagedActivityResultLauncher<Array<String>, Uri?>,
  selectedFileUriCV : Uri?,
  deleteFileCV : () -> Unit
) {
  CustomOutlinedTextField(
    label = "Nama Lengkap Peserta",
    value = state.namaLengkapPeserta,
    placeholder = "Masukkan nama lengkap",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    error = state.namaLengkapPesertaError,
    rounded = 40,
    asteriskAtEnd = true,
    labelFocusedColor = ColorPalette.PrimaryColor700,
    borderColor = ColorPalette.Outline,
    onValueChange = {
      onEvent(RegisterEvent.NamaLengkapPesertaChanged(it))
    }
  )
  CustomOutlinedTextField(
    label = "Posisi",
    value = state.posisiPeserta,
    onValueChange = {
      onEvent(RegisterEvent.PosisiPesertaChanged(it))
    },
    asteriskAtEnd = true,
    labelFocusedColor = ColorPalette.PrimaryColor700,
    borderColor = ColorPalette.Outline,
    error = state.posisiPesertaError,
    placeholder = "Masukkan posisi",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )

  CustomOutlinedTextFieldDropdown(
    label = "Pendidikan Terakhir",
    value = state.pendidikanTerakhir,
    onValueChange = {
      onEvent(RegisterEvent.PendidikanTerakhirChanged(it))
    },
    asteriskAtEnd = true,
    labelFocusedColor = ColorPalette.PrimaryColor700,
    placeholder = "Pilih Pendidikan Terakhir",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = pendidikanTerakhirs,
    expanded = expandedPendidikan,
    onChangeExpanded = {
      onExpandedPendidikanChange(it)
    },
    error = state.pendidikanTerakhirError

  )
  CustomOutlinedTextField(
    label = "Jurusan Pendidikan Terakhir",
    value = state.jurusanPendidikanTerakhir,
    onValueChange = {
      onEvent(RegisterEvent.JurusanPendidikanTerakhirChanged(it))
    },
    asteriskAtEnd = true,
    labelFocusedColor = ColorPalette.PrimaryColor700,

    error = state.jurusanPendidikanTerakhirError,
    placeholder = "Ketik jurusan pendidikan terakhir anda",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )

  CustomOutlinedTextFieldDropdown(
    label = "Jenis Kelamin",
    value = state.jenisKelamin,
    onValueChange = {
      onEvent(RegisterEvent.JenisKelaminChanged(it))
    },
    placeholder = "Pilih jenis kelamin anda",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = jenisKelamins,
    expanded = expandedJenisKelamin,
    asteriskAtEnd = true,
    labelFocusedColor = ColorPalette.PrimaryColor700,
    borderColor = ColorPalette.Outline,
    onChangeExpanded = {
      onExpandedJenisKelaminChange(it)
    },
    error = state.jenisKelaminError
  )
  CustomOutlinedTextField(
    label = "Nomor Whatsapp Peserta",
    value = state.nomorWhatsappPeserta,
    onValueChange = {
      onEvent(RegisterEvent.NomorWhatsappPesertaChanged(it))
    },
    error = state.nomorWhatsappPesertaError,
    placeholder = "Contoh: 08980861214",
    modifier = Modifier.fillMaxWidth(),
    asteriskAtEnd = true,
    labelFocusedColor = ColorPalette.PrimaryColor700,
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40,
    borderColor = ColorPalette.Outline,
  )

  CustomOutlinedTextField(
    label = "Email Peserta",
    value = state.emailPeserta,
    onValueChange = {
      onEvent(RegisterEvent.EmailPesertaChanged(it))
    },
    asteriskAtEnd = true,
    labelFocusedColor = ColorPalette.PrimaryColor700,
    borderColor = ColorPalette.Outline,
    error = state.emailPesertaError,
    placeholder = "Contoh: @gmail.com",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )
  FileUploadSection(
    title = "Unggah Kartu Tanda Penduduk (KTP)",
    buttonText = "Klik untuk unggah file",
    onFileSelect = { filePickerLauncherKTP.launch(arrayOf("image/*", "application/pdf")) },
    selectedFileUri = selectedFileUriKTP,
    deleteFile = deleteFileKTP,
    asteriskAtEnd = true,

    error = state.selectedFileUriKTPError
  )
  FileUploadSection(
    title = "Unggah Curriculum Vitae (CV)",
    buttonText = "Klik untuk unggah file",
    onFileSelect = { filePickerLauncherCV.launch(arrayOf("image/*", "application/pdf")) },
    selectedFileUri = selectedFileUriCV,
    deleteFile = deleteFileCV,
    asteriskAtEnd = true,
    error = state.selectedFileUriCVError
  )

  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = TextWithAsterisk("Apakah ada pengurus lain yang akan diikutsertakan sebagai peserta?"),
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.PrimaryColor700
      )
      Text(
        text = "Mohon isi apabila pada kegiatan Mini Training, Initial Mentoring, dan Pendampingan Intensif memerlukan rekan pengganti pada hari tertentu.",
        style = StyledText.MobileXsRegular,
        color = ColorPalette.Monochrome300
      )
    }
    Column(
      verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      RowRadioButton(
        selected = state.adaPengurusLain,
        onClick = {
          onEvent(RegisterEvent.AdaPengurusLainChanged(true))
        },
        text = "Ya, terdapat rekan saya sebagai peserta 2"
      )
      RowRadioButton(
        selected = ! state.adaPengurusLain,
        onClick = {
          onEvent(RegisterEvent.AdaPengurusLainChanged(false))
        },
        text = "Tidak, hanya saya sendiri sebagai penanggung jawab utama"
      )
    }
  }

  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {

    Text(
      text = TextWithAsterisk("Apakah ada pengurus lain yang akan diikutsertakan sebagai peserta?"),
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )

    Column(
      verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      RowCheckboxButton(
        checked = state.miniTrainingChecked,
        onCheckedChange = {
          onEvent(
            RegisterEvent.MiniTrainingCheckedChanged(
              ! state.miniTrainingChecked
            )
          )
        },
        text = "Saya bersedia mengikuti agenda Mini Training LEAD Indonesia yang akan dilakukan secara online selama 5 hari",
        error = state.miniTrainingCheckedError,
      )
      RowCheckboxButton(
        checked = state.initialMentoringChecked,
        onCheckedChange = {
          onEvent(
            RegisterEvent.InitialMentoringCheckedChanged(
              ! state.initialMentoringChecked
            )
          )
        },
        text = "Saya bersedia mengikuti agenda Initial Mentoring LEAD Indonesia yang akan dilakukan secara online selama 5 hari",
        error = state.initialMentoringCheckedError,
      )
      RowCheckboxButton(
        checked = state.pendampinganIntensifChecked,
        onCheckedChange = {
          onEvent(
            RegisterEvent.PendampinganIntensifCheckedChanged(
              ! state.pendampinganIntensifChecked
            )
          )
        },
        text = "Saya bersedia mengikuti agenda Pendampingan Intensif bersama pada Mentor LEAD Indonesia yang akan dilakukan secara online minimal 2-3 kali setiap bulan",
        error = state.pendampinganIntensifCheckedError,
      )
    }

  }
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Text(
      text = TextWithAsterisk("Apakah ada pengurus lain yang akan diikutsertakan sebagai peserta?"),
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
    CustomOutlinedTextField(
      label = "Masukkan alasan anda bersedia mengikuti agenda tersebut",
      value = state.alasanTidakMengikutiAgenda,
      error = state.alasanTidakMengikutiAgendaError,
      onValueChange = {
        onEvent(RegisterEvent.AlasanTidakMengikutiAgendaChanged(it))
      },
      placeholder = "Ketik alasan anda tidak bersedia mengikuti agenda tersebut",
      labelDefaultColor = ColorPalette.Monochrome400,
      modifier = Modifier
        .fillMaxWidth()
        .height(148.dp),
      rounded = 20,
      labelFocusedColor = ColorPalette.Monochrome800,
      maxLines = 5,
      multiLine = true,
    )

  }

  NextPrevButton(
    nextIndicatorProgress = nextIndicatorProgress,
    prevIndicatorProgress = prevIndicatorProgress,
  )

}

@Composable
fun RowRadioButton(
  selected : Boolean,
  onClick : () -> Unit,
  text : String,

  ) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .background(
        if (selected) ColorPalette.PrimaryColor100 else Color.Transparent
      )
      .clickable {
        onClick()
      }
      .border(
        width = 1.dp,
        color = ColorPalette.Monochrome150,
        shape = RoundedCornerShape(8.dp)
      )
      .padding(
        8.dp
      )
  ) {
    RadioButton(
      selected = selected,
      onClick = { },
      colors = RadioButtonDefaults.colors(
        selectedColor = ColorPalette.PrimaryColor700,
        unselectedColor = ColorPalette.Monochrome400
      )
    )
    Text(
      text = text,
      style = StyledText.MobileSmallRegular,
      color = ColorPalette.OnSurface
    )
  }
}

@Composable
fun RowCheckboxButton(
  checked : Boolean,
  onCheckedChange : () -> Unit,
  text : String,
  error : String? = null

) {
  Column {
    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .clickable { onCheckedChange() }

        .padding(
          8.dp
        )
    ) {
      Checkbox(
        checked = checked,
        onCheckedChange = { },
        colors = CheckboxDefaults.colors(
          checkedColor = ColorPalette.PrimaryColor700,
          uncheckedColor = ColorPalette.Monochrome400
        )
      )
      Text(
        text = text,
        style = StyledText.MobileSmallRegular,
        color = ColorPalette.OnSurface
      )
    }
    AnimatedVisibility(visible = error != null) {
      error?.let {
        ErrorMessageTextField(it)
      }
    }
  }

}

@Composable
fun FourthScreen(
  prevIndicatorProgress : () -> Unit,
  nextIndicatorProgress : () -> Unit,
  filePickerLauncher : ManagedActivityResultLauncher<Array<String>, Uri?>,
  selectedFileUri : Uri?,
  deleteFile : () -> Unit,
  state : RegistrasiState,
  onEvent : (RegisterEvent) -> Unit,
  expandedPendidikan : Boolean,
  onExpandedPendidikanChange : (Boolean) -> Unit
) {
  val sumberInformasiOptions = listOf(
    "Instagram @leadindonesia",
    "Facebook @leadindonesia",
    "Youtube @leadindonesia",
    "Website Bakrie Center Foundation (http://bcr.or.id)",
    "Teman atau rekan kerja",
    "Alumni LEAD Indonesia",
    "Yang lain"
  )

  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {

    Text(
      text = TextWithAsterisk("Apakah anda pernah mengikuti pelatihan atau memiliki pengetahuan terkait desain program sebelum mendaftar LEAD Indonesia 2024?"),
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )

    CustomOutlinedTextFieldDropdown(
      label = "Pilih Jawaban",
      value = state.pernahMengikutiPelatihanDesainProgram,
      onValueChange = {
        onEvent(RegisterEvent.PernahMengikutiPelatihanDesainProgramChanged(it))
      },
      placeholder = "Pilih Jawaban",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      dropdownItems = pernahs,
      expanded = expandedPendidikan,
      onChangeExpanded = {
        onExpandedPendidikanChange(it)
      },
      error = state.pernahMengikutiPelatihanDesainProgramError
    )
  }
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Text(
      text = "Darimana anda mengetahui LEAD Indonesia?",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )

    Column {
      sumberInformasiOptions.forEach { sumber ->
        RowCheckboxButton(
          checked = state.sumberInformasiLEAD.contains(sumber),
          onCheckedChange = {
            val isChecked = ! state.sumberInformasiLEAD.contains(sumber)
            onEvent(RegisterEvent.SumberInformasiLEADChanged(sumber, isChecked))
          },
          text = sumber
        )
      }
      AnimatedVisibility(visible = state.errorSumberInformasiLEAD != null) {
        state.errorSumberInformasiLEAD?.let {
          ErrorMessageTextField(it)
        }
      }

    }

  }
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {

    Text(
      text = TextWithAsterisk("Apa yang anda ketahui terkait desain program?"),
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )

    CustomOutlinedTextField(
      label = "Jelaskan pengetahuan mu terkait Desain Program",

      value = state.pengetahuanDesainProgram,
      onValueChange = {
        onEvent(RegisterEvent.PengetahuanDesainProgramChanged(it))
      },
      multiLine = true,
      maxLines = 5,
      error = state.pengetahuanDesainProgramError,
      placeholder = "Ketik pengetahuanmu terkait Desain Program",
      modifier = Modifier
        .fillMaxWidth()
        .height(148.dp),
      rounded = 20,
      borderColor = ColorPalette.Outline,

      )
  }
  ColumnTextField(
    label = "Apakah yang anda ketahui terkait sustainability atau keberlanjutan?",
    value = state.pengetahuanSustainability,
    text = "Jelaskan pengetahuanmu terkait sustainability atau keberlanjutan",
    onValueChange = {

      onEvent(RegisterEvent.PengetahuanSustainabilityChanged(it))
    },
    modifier = Modifier.fillMaxWidth(),
    error = state.pengetahuanSustainabilityError

  )
  ColumnTextField(
    label = "Apakah yang anda ketahui terkait social report atau laporan social?",
    value = state.pengetahuanSocialReport,
    text = "Jelaskan pengetahuanmu terkait social report atau laporan social",
    onValueChange = {
      onEvent(RegisterEvent.PengetahuanSocialReportChanged(it))
    },
    modifier = Modifier
      .fillMaxWidth(),

    error = state.pengetahuanSocialReportError
  )
  FileUploadSection(
    title = "Unggah laporan akhir tahun atau laporan pertanggungjawaban pelaksanaan program instansi",
    buttonText = "Klik untuk unggah file",
    onFileSelect = { filePickerLauncher.launch(arrayOf("image/*", "application/pdf")) },
    selectedFileUri = selectedFileUri,
    deleteFile = deleteFile,
    error = state.selectedFileUriLaporanAkhirTahunError,
    asteriskAtEnd = true
  )
  ColumnTextField(
    label = "Jelaskan ekspetasi anda setelah mengikuti kegiatan LEAD Indonesia 2023?",
    value = state.ekspetasiSetelahLEAD,
    text = "Jelaskan ekspetasi anda setelah mengikuti kegiatan LEAD Indonesia 2023",
    onValueChange = {
      onEvent(RegisterEvent.EkspetasiSetelahLEADChanged(it))
    },
    modifier = Modifier.fillMaxWidth(),
    error = state.ekspetasiSetelahLEADError
  )
  ColumnTextField(
    label = "Apakah ada hal lain yang ingin anda tanyakan atau sampaikan terkait LEAD Indonesia 2023?",
    value = state.halLainYangInginDisampaikan,
    text = "Jelaskan hal lain yang ingin anda tanyakan atau sampaikan terkait LEAD Indonesia 2023",
    onValueChange = {
      onEvent(RegisterEvent.HalLainYangInginDisampaikanChanged(it))
    },
    modifier = Modifier.fillMaxWidth(),

    error = state.halLainYangInginDisampaikanError
  )


  NextPrevButton(
    nextIndicatorProgress = nextIndicatorProgress,
    prevIndicatorProgress = prevIndicatorProgress
  )


}

@Composable
fun FifthScreen(

  prevIndicatorProgress : () -> Unit,
  state : RegistrasiState,
  onEvent : (RegisterEvent) -> Unit,
  gotoProfilPeserta : () -> Unit
) {
  var showSubmitDialog by remember { mutableStateOf(false) }

  val headerTable = listOf("No", "Provinsi", "Jumlah Penerima Manfaaat", "Rincian")
  val columnWeights = listOf(
    0.5f,
    1f,
    1f,
    1f
  )
  val rowsJangkauanProgram =
    state.jumlahAngkaPenerimaanManfaat.mapIndexed { index, wilayahJangkauan ->
      listOf(
        (index + 1).toString(),
        wilayahJangkauan.kota,
        wilayahJangkauan.jumlah.toString(),
        "Rincian"
      )
    }
  if (showSubmitDialog) {
    CustomConfirmationDialog(
      title = "Apakah anda yakin data yang diisi sudah benar??",
      supportingText = "Pastikan seluruh data yang anda isikan pada proses pendaftaran program ini telah sesuai sebelum melanjutkan",
      confirmButtonText = "Kirim",
      dismissButtonText = "Batal",
      onConfirm = {
        onEvent(RegisterEvent.Submit)
        showSubmitDialog = false

      },
      onDismiss = {
        showSubmitDialog = false
      }
    )
  }
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Title(
      text = "Profil Lembaga"
    )
    ColumnKeyValue(
      key = "Nama Lembaga",
      value = state.namaLembaga,
      error = state.namaLembagaError
    )
    ColumnKeyValue(
      key = "Email Formal Lembaga",
      value = state.emailLembaga,
      error = state.emailLembagaError
    )
    ColumnKeyValue(
      key = "Alamat Lengkap Lembaga",
      value = state.alamatLembaga,
      error = state.alamatLembagaError
    )
    ColumnKeyValue(
      key = "Jenis Lembaga",
      value = state.jenisLembagaSosial,
      error = state.jenisLembagaSosialError
    )
    ColumnKeyValue(
      key = "Fokus Isu",
      value = state.fokusIsu,
      error = state.fokusIsuError
    )
    ColumnKeyValue(
      key = "Alasan Mengikuti Program",
      value = state.alasanKeikutsertaan,
      error = state.alasanKeikutsertaanError
    )
    ColumnKeyValue(
      key = "Cakupan/Jangkauan Program",
      value = state.jangkauanProgram,
      error = state.jangkauanProgramError
    )
    ColumnKeyValue(
      key = "Target Utama Program",
      value = state.targetUtamaProgram,
      error = state.targetUtamaProgramError
    )



    when (state.jumlahAngkaPenerimaanManfaat.isEmpty() || state.jumlahAngkaPenerimaanManfaat.all { it.kota.isEmpty() }) {
      true  -> {
        ColumnKeyValue(
          key = "Jumlah Angka Penerimaan Manfaat",
          value = "",
        )
      }

      false -> {
        Text(
          text = "Wilayah Jangkauan Program",
          style = StyledText.MobileSmallSemibold,
          color = ColorPalette.Monochrome800
        )
        Table(
          headers = headerTable,
          columnWeights = columnWeights,
          rows = rowsJangkauanProgram
        )
      }
    }
    HorizontalDivider(
      modifier = Modifier.fillMaxWidth()
    )
    Title(
      text = "Profil Peserta"
    )
    ColumnKeyValue(
      key = "Nama Lengkap ",
      value = state.namaLengkapPeserta,
      error = state.namaLengkapPesertaError
    )
    ColumnKeyValue(
      key = "Posisi",
      value = state.posisiPeserta,
      error = state.posisiPesertaError
    )
    ColumnKeyValue(
      key = "Pendidikan Terakhir",
      value = state.pendidikanTerakhir,
      error = state.pendidikanTerakhirError
    )
    ColumnKeyValue(
      key = "Nomor Whatsapp",
      value = state.nomorWhatsappPeserta,
      error = state.nomorWhatsappPesertaError
    )
    ColumnKeyValue(
      key = "Email",
      value = state.emailPeserta,
      error = state.emailPesertaError
    )
    SecondaryButton(
      text = "Ubah Profil Peserta",
      onClick = {
        gotoProfilPeserta()
      },
    )
    HorizontalDivider(
      modifier = Modifier.fillMaxWidth()
    )
    Column {
      RowCheckboxButton(
        text = "Saya sudah membaca dan memahami https://bit.ly/LEADBCF-2023",
        checked = state.readAndUnderstandChecked,
        onCheckedChange = {
          onEvent(RegisterEvent.ReadAndUnderstandCheckedChanged(! state.readAndUnderstandChecked))
        },
        error = state.readAndUnderstandError
      )
      RowCheckboxButton(
        text = "Saya mengkonfirmasi bahwa seluruh informasi yang saya berikan di atas adalah akurat dan terkini",
        checked = state.confirmInformationChecked,
        onCheckedChange = {
          onEvent(RegisterEvent.ConfirmInformationCheckedChanged(! state.confirmInformationChecked))
        },
        error = state.confirmInformationError
      )
    }
  }

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
    verticalAlignment = Alignment.CenterVertically
  ) {
    SecondaryButton(
      text = "Sebelumnya",
      onClick = { prevIndicatorProgress() }
    )
    PrimaryButton(
      text = "Kirim",
      onClick = {
        showSubmitDialog = true
      }
    )
  }
}

@Composable
fun Table(
  headers : List<String>,
  columnWeights : List<Float>,
  rows : List<List<String>>
) {
  Column {
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
          if (cell == "Rincian") {
            IconButton(
              onClick = { },
              modifier = Modifier.size(24.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Rincian",
                tint = ColorPalette.PrimaryColor700
              )
            }
          } else {
            TableCell(
              text = cell,
              modifier = Modifier.weight(columnWeights.getOrElse(index) { 1f })
            )
          }
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


@Composable
private fun Title(
  text : String
) {
  Text(
    text = text,
    style = StyledText.MobileSmallSemibold,
    color = ColorPalette.PrimaryColor700
  )
}

@Composable
private fun ColumnKeyValue(
  key : String,
  value : String,
  error : String? = null
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    Text(
      text = key,
      style = StyledText.MobileSmallSemibold,
      color = ColorPalette.Monochrome800
    )

    AnimatedVisibility(visible = error != null) {
      error?.let {
        ErrorMessageTextField(
          it,
        )
      }
    }
    if (value.isNotEmpty()) {
      Text(
        text = value,
        style = StyledText.MobileSmallRegular,
        color = ColorPalette.Monochrome800
      )
    } else {
      Text(
        text = "Belum diisi",
        style = StyledText.MobileSmallRegular,
        color = ColorPalette.Monochrome400
      )
    }
  }

}


@Composable
private fun NextPrevButton(
  nextIndicatorProgress : () -> Unit,
  prevIndicatorProgress : () -> Unit = {},
  isPrevExist : Boolean = true,
  isNextExist : Boolean = true
) {

  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    HorizontalDivider(modifier = Modifier.fillMaxWidth())

    when {
      isPrevExist && isNextExist -> {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
          verticalAlignment = Alignment.CenterVertically
        ) {
          SecondaryButton(
            text = "Sebelumnya",
            onClick = { prevIndicatorProgress() }
          )
          PrimaryButton(
            text = "Berikutnya",
            onClick = { nextIndicatorProgress() }
          )
        }
      }

      isPrevExist                -> {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          SecondaryButton(
            text = "Sebelumnya",
            onClick = { prevIndicatorProgress() }
          )
        }
      }

      isNextExist                -> {
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.CenterEnd
        ) {
          PrimaryButton(
            text = "Berikutnya",
            onClick = { nextIndicatorProgress() }
          )
        }
      }
    }
  }
}

@Composable
private fun ColumnTextField(
  label : String,
  value : String,
  text : String,
  onValueChange : (String) -> Unit,
  modifier : Modifier = Modifier,
  error : String? = null
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp),
    modifier = modifier
  ) {
    Text(
      text = TextWithAsterisk(text),
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
    CustomOutlinedTextField(
      label = label,
      value = value,
      onValueChange = onValueChange,
      modifier = Modifier

        .fillMaxWidth()

        .height(148.dp),
      labelDefaultColor = ColorPalette.Monochrome400,
      labelFocusedColor = ColorPalette.Monochrome800,
      rounded = 20,
      maxLines = 5,
      multiLine = true,
      borderColor = ColorPalette.Outline,
      error = error
    )
  }
}

@Composable
private fun CustomRowWithFields(
  dropdownLabel : String,
  dropdownValue : String,
  onDropdownValueChange : (String) -> Unit,
  dropdownPlaceholder : String,
  dropdownItems : List<String>,
  expanded : Boolean,
  onExpandedChange : (Boolean) -> Unit,
  jumlahValue : String,
  onJumlahValueChange : (String) -> Unit,
  modifier : Modifier = Modifier,
  labelFocusedColor : Color = ColorPalette.PrimaryColor700
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    modifier = modifier.fillMaxWidth(),
  ) {
    Box(
      modifier = Modifier.weight(0.7f)
    ) {
      CustomOutlinedTextFieldDropdown(
        label = dropdownLabel,
        value = dropdownValue,
        onValueChange = onDropdownValueChange,
        placeholder = dropdownPlaceholder,
        labelFocusedColor = labelFocusedColor,
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        dropdownItems = dropdownItems,
        expanded = expanded,
        onChangeExpanded = onExpandedChange
      )
    }

    Box(
      modifier = Modifier.weight(0.3f),
      contentAlignment = Alignment.TopStart
    ) {
      CustomOutlinedTextFieldJumlah(
        value = jumlahValue,
        onValueChange = onJumlahValueChange,
        placeholder = "Jumlah",
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Composable
private fun CustomOutlinedTextFieldJumlah(
  value : String,
  onValueChange : (String) -> Unit,
  placeholder : String,
  modifier : Modifier = Modifier,
  size : Dp = 64.dp,
  textStyle : TextStyle = StyledText.MobileSmallRegular,
  borderColor : Color = ColorPalette.Outline,
  focusedBorderColor : Color = ColorPalette.Outline
) {
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier.size(size),
    shape = RoundedCornerShape(40.dp),
    textStyle = textStyle.copy(
      textAlign = TextAlign.Center,
      lineHeight = size.value.sp
    ),
    placeholder = {
      Text(
        text = placeholder,
        style = textStyle.copy(
          color = ColorPalette.Monochrome400,
          textAlign = TextAlign.Center,
          lineHeight = size.value.sp
        ),
        modifier = Modifier.fillMaxWidth()
      )
    },
    colors = OutlinedTextFieldDefaults.colors(
      unfocusedBorderColor = borderColor,
      focusedBorderColor = focusedBorderColor,
      disabledBorderColor = borderColor,
      unfocusedLabelColor = borderColor,
      focusedLabelColor = focusedBorderColor
    ),
    singleLine = true,

    )
}
