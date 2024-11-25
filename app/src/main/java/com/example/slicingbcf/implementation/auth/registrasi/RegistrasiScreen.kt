package com.example.slicingbcf.implementation.auth.registrasi

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.animations.AnimatedContentSlide
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.message.SecondaryButton
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdownDate
import com.example.slicingbcf.util.convertMillisToDate


// TODO: build second screen - latest screen

@Composable
fun RegistrasiScreen(modifier : Modifier) {
  var currentScreen by rememberSaveable { mutableIntStateOf(0) }
  var indicatorProgress by remember { mutableFloatStateOf(0.2f) }
  var initialState by remember { mutableIntStateOf(0) }
  val changeScreen : (Int) -> Unit = { screen -> currentScreen = screen }

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

  val titleBasedOnScreen = when (currentScreen) {
    0 -> "Profil Lembaga"
    1 -> "Program Lembaga"
    2 -> "Pendaftaran Peserta"
    3 -> "Pertanyaan Umum"
    4 -> "Ringkasan Data Pendaftaran"
    else -> "Wrong Screen"
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(40.dp),
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
      onInitialScreenChange = { initialState = it }
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
  currentScreen : Int
) {
  val verticalScroll = rememberScrollState()

  var expandedProvinsi by remember { mutableStateOf(false) }
  var expandedKota by remember { mutableStateOf(false) }
  var expandedDate by remember { mutableStateOf(false) }
  var expandedLembagaSosial by remember { mutableStateOf(false) }
  var expandedFokusIsu by remember { mutableStateOf(false) }
  var expandedPilihKota by remember { mutableStateOf(false) }
  var expandedWilayahJangkauanProgram by remember { mutableStateOf(false) }
  var expandedJangkauanProgram by remember { mutableStateOf(false) }
  var expandedJenisKelamin by remember { mutableStateOf(false) }
  var expandedPendidikan by remember { mutableStateOf(false) }


  val datePickerState = rememberDatePickerState()
  val selectedDate = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""

  Box(
    modifier = Modifier
      .fillMaxSize()
      .pointerInput(expandedDate, expandedProvinsi) {
        detectTapGestures {
          expandedDate = false
          expandedProvinsi = false
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
        when (targetScreen) {
          0 -> FirstScreen(
            selectedDate = selectedDate,
            datePickerState = datePickerState,
            expandedDate = expandedDate,
            onExpandedDateChange = { expandedDate = it },
            expandedProvinsi = expandedProvinsi,
            onExpandedProvinsiChange = { expandedProvinsi = it },
            expandedKota = expandedKota,
            onExpandedKotaChange = { expandedKota = it },
            expandedLembagaSosial = expandedLembagaSosial,
            onExpandedLembagaSosialChange = { expandedLembagaSosial = it },
            expandedFokusIsu = expandedFokusIsu,
            onExpandedFokusIsuChange = { expandedFokusIsu = it },
            nextIndicatorProgress = nextIndicatorProgress
          )

          1 -> SecondScreen(
            prevIndicatorProgress = prevIndicatorProgress,
            nextIndicatorProgress = nextIndicatorProgress,
            expandedPilihKota = expandedPilihKota,
            expandedJangkauanProgram = expandedJangkauanProgram,
            expandedWilayahJangkauanProgram = expandedJangkauanProgram,
            onExpandedJangkauanProgramChange = {
              expandedJangkauanProgram = it
            },
            onExpandedPilihKotaChange = {
              expandedPilihKota = it
            },
            onExpandedWilayahJangkauanProgramChange = {
              expandedWilayahJangkauanProgram = it
            }
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
            }

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
  onExpandedLembagaSosialChange : (Boolean) -> Unit,
  expandedFokusIsu : Boolean,
  onExpandedFokusIsuChange : (Boolean) -> Unit,
  nextIndicatorProgress : () -> Unit,
) {

  CustomOutlinedTextField(
    label = "Nama Lembaga",
    value = "",
    onValueChange = {},
    placeholder = "Masukkan nama lembaga",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )
  CustomOutlinedTextFieldDropdownDate(
    label = "Tanggal Berdiri",
    value = selectedDate,
    placeholder = "DD/MM/YYYY",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    datePickerState = datePickerState,
    expanded = expandedDate,
    onChangeExpanded = {
      onExpandedDateChange(it)
    }
  )
  CustomOutlinedTextField(
    label = "Email Formal Lembaga",
    value = "",
    onValueChange = {},
    placeholder = "contoh@bcf.or.id",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )
  CustomOutlinedTextField(
    label = "Masukkan alamat lengkap lembaga",
    value = "",
    onValueChange = {},
    placeholder = "contoh@bcf.or.id",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40

  )
  CustomOutlinedTextFieldDropdown(
    label = "Provinsi",
    value = "",
    onValueChange = {},
    placeholder = "Pilih Provinsi",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = provinsis,
    expanded = expandedProvinsi,
    onChangeExpanded = {
      onExpandedProvinsiChange(it)
    }
  )
  CustomOutlinedTextFieldDropdown(
    label = "Kota / Kabupaten",
    value = "",
    onValueChange = {},
    placeholder = "Pilih kota/kabupaten",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = provinsis,
    expanded = expandedKota,
    onChangeExpanded = {
      onExpandedKotaChange(it)
    }
  )
  CustomOutlinedTextFieldDropdown(
    label = "Jenis Lembaga Sosial",
    value = "",
    onValueChange = {},
    placeholder = "Pilih jenis cluster lembaga sosial",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    expanded = expandedLembagaSosial,
    onChangeExpanded = {
      onExpandedLembagaSosialChange(it)
    },
    dropdownItems = provinsis
  )
  CustomOutlinedTextFieldDropdown(
    label = "Fokus Isu",
    value = "",
    onValueChange = {},
    placeholder = "Pilih fokus isu",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = provinsis,
    expanded = expandedFokusIsu,
    onChangeExpanded = {
      onExpandedFokusIsuChange(it)
    }
  )
  CustomOutlinedTextField(
    label = "Profil Singkat Lembaga",
    value = "",
    onValueChange = {},
    placeholder = "Masukkan profil singkat lembaga",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )
  CustomOutlinedTextField(
    label = "Apa alasan anda mengikuti LEAD Indonesia 2023?",
    value = "",
    onValueChange = {},
    placeholder = "Masukkan alasan anda mengikuti LEAD Indonesia 2023",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )
  // TODO: tambahin unggah proposal program dengan mitra pake dotten button, klik untuk unggah file
  NextPrevButton(
    nextIndicatorProgress = nextIndicatorProgress,
    isPrevExist = false
  )

}


@Composable
private fun SecondScreen(
  prevIndicatorProgress : () -> Unit,
  nextIndicatorProgress : () -> Unit,
  expandedJangkauanProgram : Boolean,
  onExpandedJangkauanProgramChange : (Boolean) -> Unit,
  expandedWilayahJangkauanProgram : Boolean,
  onExpandedWilayahJangkauanProgramChange : (Boolean) -> Unit,
  expandedPilihKota : Boolean,
  onExpandedPilihKotaChange : (Boolean) -> Unit,
) {
  CustomOutlinedTextFieldDropdown(
    label = "Jangkauan Program",
    value = "",
    onValueChange = {},
    placeholder = "Pilih Jangkauan Program",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = provinsis,
    expanded = expandedJangkauanProgram,
    onChangeExpanded = {
      onExpandedJangkauanProgramChange(it)
    }
  )
  CustomOutlinedTextFieldDropdown(
    label = "Wilayah Jangkauan Program",
    value = "",
    onValueChange = {},
    placeholder = "Pilih Wilayah Jangkauan Program",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = provinsis,
    expanded = expandedWilayahJangkauanProgram,
    onChangeExpanded = {
      onExpandedWilayahJangkauanProgramChange(it)
    }
  )
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Jumlah Angka Penerimaan Manfaat*",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
    // TODO: benerin agar ada jumlah di sebelah pilih kota / kabupaten
    Column(
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        CustomOutlinedTextFieldDropdown(
          label = "DKI Jakarta",
          value = "",
          onValueChange = {},
          placeholder = "Pilih kota/kabupaten",
          modifier = Modifier
            .fillMaxWidth(), labelDefaultColor = ColorPalette.Monochrome400,
          dropdownItems = provinsis,
          expanded = expandedPilihKota,
          onChangeExpanded = {
            onExpandedPilihKotaChange(it)
          }
        )
//      CustomOutlinedTextField(
//        modifier = Modifier
//          .weight(0.3f),
//        value = "",
//        onValueChange = {},
//        label = "Jumlah",
//        placeholder = "Masukkan Jumlah"
//      )
      }
      // TODO: tambahin agar ada trailing icon PLUS di kiri tambah
      SecondaryButton(
        onClick = {},
        text = "Tambah"
      )
    }
  }
  CustomOutlinedTextField(
    label = "Target Utama Program",
    value = "",
    onValueChange = {},
    placeholder = "Ketik target utama program",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )
  // TODO: tambahin unggah proposal program dengan mitra pake dotten button, klik untuk unggah file

  NextPrevButton(
    nextIndicatorProgress = nextIndicatorProgress,
    prevIndicatorProgress = prevIndicatorProgress,
  )

}

@Composable
private fun ThirdScreen(
  prevIndicatorProgress : () -> Unit,
  nextIndicatorProgress : () -> Unit,
  expandedPendidikan : Boolean,
  onExpandedPendidikanChange : (Boolean) -> Unit,
  expandedJenisKelamin : Boolean,
  onExpandedJenisKelaminChange : (Boolean) -> Unit,


  ) {
  CustomOutlinedTextField(
    label = "Nama Lengkap Peserta",
    value = "",
    onValueChange = {},
    placeholder = "Masukkan nama lengkap",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )
  CustomOutlinedTextField(
    label = "Posisi",
    value = "",
    onValueChange = {},
    placeholder = "Masukkan posisi",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )

  CustomOutlinedTextFieldDropdown(
    label = "Pendidikan Terakhir",
    value = "",
    onValueChange = {},
    placeholder = "Pilih Pendidikan Terakhir",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = provinsis,
    expanded = expandedPendidikan,
    onChangeExpanded = {
      onExpandedPendidikanChange(it)
    }
  )
  CustomOutlinedTextField(
    label = "Jurusan Pendidikan Terakhir",
    value = "",
    onValueChange = {},
    placeholder = "Ketik jurusan pendidikan terakhir anda",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )

  CustomOutlinedTextFieldDropdown(
    label = "Jenis Kelamin",
    value = "",
    onValueChange = {},
    placeholder = "Pilih jenis kelamin anda",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    dropdownItems = provinsis,
    expanded = expandedJenisKelamin,
    onChangeExpanded = {
      onExpandedJenisKelaminChange(it)
    }
  )
  CustomOutlinedTextField(
    label = "Nomor Whatsapp Peserta",
    value = "",
    onValueChange = {},
    placeholder = "Contoh: 08980861214",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )

  CustomOutlinedTextField(
    label = "Email Peserta",
    value = "",
    onValueChange = {},
    placeholder = "Contoh: @gmail.com",
    modifier = Modifier.fillMaxWidth(),
    labelDefaultColor = ColorPalette.Monochrome400,
    rounded = 40
  )
  // TODO: tambahin unggah ktp, cv pake dotten button, klik untuk unggah file dan lain lain
  NextPrevButton(
    nextIndicatorProgress = nextIndicatorProgress,
    prevIndicatorProgress = prevIndicatorProgress,
  )


}

@Composable
private fun ThirdScreen(
  prevIndicatorProgress : () -> Unit,
  nextIndicatorProgress : () -> Unit,
  expandedPernahMengikutiPelatihan : Boolean,
  onExpandedPernahMengikutiPelatihanChange : (Boolean) -> Unit

) {
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Apakah anda pernah mengikuti pelatihan atau memiliki pengetahuan terkait desain program sebelum mendaftar LEAD Indonesia 2024",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
    CustomOutlinedTextFieldDropdown(
      label = "Jenis Kelamin",
      value = "",
      onValueChange = {},
      placeholder = "Pilih jenis kelamin anda",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      dropdownItems = provinsis,
      expanded = expandedPernahMengikutiPelatihan,
      onChangeExpanded = {
        onExpandedPernahMengikutiPelatihanChange(it)
      }
    )

  }
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Apakah anda ketahui terkat desain program?",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
    CustomOutlinedTextField(
      label = "Pengetahuanmu terkait LEAD Indonesia",
      value = "",
      onValueChange = {},
      placeholder = "Ketik Pengetahuanmu terkait LEAD Indonesia",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40
    )


  }
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Apakah yang anda ketahui terkait sustainability atau keberlanjutan?",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
    CustomOutlinedTextField(
      label = "Pengetahuanmu terkait sustainability atau keberlanjutan",
      value = "",
      onValueChange = {},
      placeholder = "Ketik Pengetahuanmu terkait sustainability atau keberlanjutan",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40
    )
  }
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Apakah yang anda ketahui terkait social report atau laporan social?",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
    CustomOutlinedTextField(
      label = "Pengetahuanmu terkait social report atau laporan social",
      value = "",
      onValueChange = {},
      placeholder = "Ketik Pengetahuanmu terkait social report atau laporan social",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40
    )
  }
  // TODO: unggah laporan (dotted button)

  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Jelaskan ekspetasi anda setelah mengikuti kegiatan LEAD Indonesia 2023?",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
    CustomOutlinedTextField(
      label = "Ekspetasi anda",
      value = "",
      onValueChange = {},
      placeholder = "Ketik ekspetasi anda",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40
    )
  }
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Apakah ada hal lain yang ingin anda tanyakan atau sampaikan terkait LEAD Indonesia 2023?",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
    CustomOutlinedTextField(
      label = "Pertanyaan anda",
      value = "",
      onValueChange = {},
      placeholder = "Ketik pertanyaan anda disini",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40
    )
  }
}


@Composable
private fun NextPrevButton(
  nextIndicatorProgress : () -> Unit,
  prevIndicatorProgress : () -> Unit = {},
  isPrevExist : Boolean = true,
) {
  Column(
    modifier = Modifier.padding(
      top = 24.dp
    ),
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    HorizontalDivider(
      modifier = Modifier.fillMaxWidth()
    )

    if (! isPrevExist) {
      Box(
        modifier = Modifier
          .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
      ) {
        PrimaryButton(
          text = "Berikutnya",
          onClick = {
            nextIndicatorProgress()
          }
        )
      }
    } else {
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
      ) {
        SecondaryButton(
          text = "Sebelumnya",
          onClick = {
            prevIndicatorProgress()
          },
        )
        PrimaryButton(
          text = "Berikutnya",
          onClick = {
            nextIndicatorProgress()
          }
        )

      }
    }


  }
}

val provinsis = listOf(
  "Aceh",
  "Sumatera Utara",
  "Sumatera Barat",
  "Riau",
  "Jambi",
  "Sumatera Selatan",
  "Bengkulu",
  "Lampung",
  "Kepulau"
)
