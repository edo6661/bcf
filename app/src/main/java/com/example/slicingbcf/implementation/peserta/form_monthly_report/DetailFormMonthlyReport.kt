package com.example.slicingbcf.implementation.peserta.form_monthly_report

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.NavigateNext
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
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.*
import com.example.slicingbcf.ui.animations.AnimatedContentSlide
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.message.SecondaryButton
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown



@Composable
fun DetailFormMonthlyReportScreen(
  modifier : Modifier,
  id : String,
  onNavigateBack  :() -> Boolean
) {
  var currentScreen by rememberSaveable { mutableIntStateOf(0) }
  val changeScreen : (Int) -> Unit = { screen -> currentScreen = screen }
  var initialState by remember { mutableIntStateOf(0) }
  val onInitialScreenChange = { newInitialState : Int ->
    initialState = newInitialState
  }

  val navigateToRingkasanScreen = {
    changeScreen(1)
  }



  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(40.dp),
  ) {
    AnimatedContentSlide(
      currentScreen = currentScreen,
      initialState = initialState,
      label = "Registrasi Animation Navigate Content",
    ) { targetScreen ->
      when (targetScreen) {
        0 -> {
          ScreenBasedOnId(
            id = id,
            navigateToRingkasanScreen = navigateToRingkasanScreen,
            onNavigateBack = onNavigateBack
          )
        }

        1 -> {
          DetailFormMonthlyReport(
            modifier = Modifier,
            id = id
          )
        }
      }

      LaunchedEffect(currentScreen) { onInitialScreenChange(currentScreen) }
    }

  }
}

@Composable
fun ScreenBasedOnId(
  id : String,
  navigateToRingkasanScreen : () -> Unit,
  onNavigateBack : () -> Boolean
) {
  if(id != "1"){
    AdaMonthlyReportScreen(
      onNavigateChangeReport = {
        // TODO: belum ada di figma
      },
      onNavigateRingkasan = { navigateToRingkasanScreen() }
    )

  }else {
    TidakAdaMonthlyReportScreen(
      onNavigateFormMonthly = {
        navigateToRingkasanScreen()
      },
      onNavigateBack = { onNavigateBack() }

    )
  }
}


@Composable
fun DetailFormMonthlyReport(
  modifier : Modifier,
  id : String
) {
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
    0    -> "Progress Internal"
    1    -> "Desain Program"
    2    -> "Rekapitulasi Kerjasama"
    3    -> "Rekapan Pemasaran"
    else -> "Wrong Screen"
  }
  val onNavigateNext = {
    changeScreen(currentScreen + 1)
  }
  val onNavigatePrevious = {
    changeScreen(currentScreen - 1)
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(40.dp),
  ) {
    when {
      id == "1" -> {
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

      id != "1" -> {
        TopSectionNotForm(
          currentScreen = currentScreen,
          onNavigatePrevious = onNavigatePrevious,
          onNavigateNext = onNavigateNext
        )
        BottomSectionNotForm(
          currentScreen = currentScreen,
          initialState = initialState,
          onInitialScreenChange = { initialState = it }
        )
      }
    }
  }
}

@Composable
private fun TopSectionNotForm(
  currentScreen : Int,
  onNavigatePrevious : () -> Unit,
  onNavigateNext : () -> Unit
) {
  val isNextDisabled = currentScreen == 2
  val isPrevDisabled = currentScreen == 0
  val nextColor = if (isNextDisabled) ColorPalette.Monochrome400 else ColorPalette.Black
  val prevColor = if (isPrevDisabled) ColorPalette.Monochrome400 else ColorPalette.Black
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Laporan Bulan Ke-1",
      style = StyledText.MobileLargeMedium,
      color = ColorPalette.Black
    )
    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        modifier = Modifier.size(24.dp),
        onClick = {
          onNavigatePrevious()
        },
        enabled = ! isPrevDisabled
      )
      {
        Icon(
          imageVector = Icons.AutoMirrored.Default.NavigateBefore,
          contentDescription = "Previous Screen",
          tint = prevColor,
          modifier = Modifier.size(24.dp)
        )
      }
      Text(
        text = (currentScreen + 1).toString(),
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.Black
      )
      Text(
        text = "Dari",
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.Black
      )
      Text(
        text = "3",
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.Black
      )
      IconButton(
        modifier = Modifier.size(24.dp),
        onClick = {
          onNavigateNext()
        },
        enabled = ! isNextDisabled
      )
      {
        Icon(
          imageVector = Icons.AutoMirrored.Default.NavigateNext,
          contentDescription = "Previous Screen",
          tint = nextColor,
          modifier = Modifier.size(24.dp),

          )
      }


    }
  }

}

@Composable
private fun BottomSectionNotForm(
  currentScreen : Int,
  initialState : Int,
  onInitialScreenChange : (Int) -> Unit
) {
  val verticalScroll = rememberScrollState()
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
        0 -> {
          FirstScreenNotForm(
          )
        }

        1 -> {
          SecondScreenNotForm(
          )
        }

        2 -> {
          ThirdScreenNotForm(
          )
        }
      }
      LaunchedEffect(currentScreen) { onInitialScreenChange(currentScreen) }
    }
  }

}


@Composable
private fun FirstScreenNotForm(
) {
  val rowsProgressKegiatan = progressKegiatans.mapIndexed { i, d ->
    listOf((i + 1).toString(), d.kegiatan, d.wilayah)
  }
  val rowsPendanaanDonasi = pendanaanDonasis.map { p ->
    listOf(
      p.tipeDonasi,
      p.nominal,
      p.platform
    )
  }
  val rowsPendanaanSponsorship = pendanaanSponsorship.map { p ->
    listOf(
      p.tipeDonasi,
      p.nominal,
      p.platform
    )
  }
  val rowsPendanaanDonorMitra = pendanaanDonorMitra.map { p ->
    listOf(
      p.tipeDonasi,
      p.nominal,
      p.platform
    )
  }
  val rowsRelawan = relawans.mapIndexed { i, r ->
    listOf(
      (i + 1).toString(),
      r.jumlah,
      r.kendalaDanPenyesalan,
    )
  }
  Title(
    text = "Progress Kegiatan"
  )
  TableAndTotalData(
    headers = headersProgressKegiatan,
    rows = rowsProgressKegiatan,
    titleTotalData = "Total Kegiatan ${progressKegiatans.size}",
    isHeaderHaveBg = true
  )
  Title(
    text = "Pendanaan"
  )
  Column {
    Column {
      TitleTable(
        text = "Pendanaan Donasi",

        )
      TableAndTotalData(
        headers = headerPendanaanDonasi,
        rows = rowsPendanaanDonasi,
        titleTotalData = "Total Data ${pendanaanDonasis.size}",
        columnWeights = listOf(1f, 1f, 0.6f)
      )
    }
    Column {
      TitleTable(
        text = "Pendanaan Sporsorship",

        )
      TableAndTotalData(
        headers = headerPendanaanSponsorship,
        rows = rowsPendanaanSponsorship,
        titleTotalData = "Total Data ${pendanaanSponsorship.size}",
        columnWeights = listOf(1f, 1f, 0.6f)
      )
    }
    Column {
      TitleTable(
        text = "Pendanaan Sporsorship",

        )
      TableAndTotalData(
        headers = headerPendanaanDonorMitra,
        rows = rowsPendanaanDonorMitra,
        titleTotalData = "Total Data ${pendanaanDonorMitra.size}",
        columnWeights = listOf(1f, 1f, 0.6f)
      )
    }
  }
  Title(
    text = "Relawan"
  )
  TableAndTotalData(
    headers = headerRelawan,
    rows = rowsRelawan,
    titleTotalData = "Total Data ${relawans.size}",
    columnWeights = listOf(0.3f, 0.4f, 1f),
    isHeaderHaveBg = true
  )


}

@Composable
private fun SecondScreenNotForm(
) {
  val rowsDesainProgram = desainPrograms.mapIndexed { i, d ->
    listOf(
      (i + 1).toString(), d.aspekCapaian, d.keterangan
    )
  }
  val rowsMediaMassa = mediaMassas.mapIndexed { i, d ->
    listOf(
      (i + 1).toString(), d.lembaga, d.status.toString(), d.keterangan
    )
  }
  val rowsPemerintah = mediaMassaPemerintahs.mapIndexed { i, d ->
    listOf(
      (i + 1).toString(), d.lembaga, d.status.toString(), d.keterangan
    )
  }
  val rowsPerusahaan = mediaMassaPerusahaan.mapIndexed { i, d ->
    listOf(
      (i + 1).toString(), d.lembaga, d.status.toString(), d.keterangan
    )
  }
  val rowsOrganisasi = mediaMassaOrganisasiLain.mapIndexed { i, d ->
    listOf(
      (i + 1).toString(), d.lembaga, d.status.toString(), d.keterangan
    )
  }
  Title(
    text = "Desain Program"
  )
  TableAndTotalData(
    headers = headerDesainProgram,
    rows = rowsDesainProgram,
    titleTotalData = "Total Data ${desainPrograms.size}",
    isHeaderHaveBg = true
  )
  Title(
    "Penjalinan Kerja Sama Lembaga"
  )
  Column {
    TitleTable(
      text = "Media Massa"
    )
    TableAndTotalData(
      headers = headerMediaMassa,
      rows = rowsMediaMassa,
      titleTotalData = "Total Data ${mediaMassas.size}",
      columnWeights = listOf(0.4f, 1f, 1f, 1f)
    )
  }
  Column {
    TitleTable(
      text = "Pemerintah (Pusat/Daerah)"
    )
    TableAndTotalData(
      headers = headerMediaMassa,
      rows = rowsPemerintah,
      titleTotalData = "Total Data ${mediaMassaPemerintahs.size}",
      columnWeights = listOf(0.4f, 1f, 1f, 1f)

    )
  }
  Column {
    TitleTable(
      text = "Perusahaan"
    )
    TableAndTotalData(
      headers = headerMediaMassa,
      rows = rowsPerusahaan,
      titleTotalData = "Total Data ${mediaMassaPerusahaan.size}",
      columnWeights = listOf(0.4f, 1f, 1f, 1f)

    )
  }
  Column {
    TitleTable(
      text = "Organisasi Lain (NGO/Komunitas/Dsb)"
    )
    TableAndTotalData(
      headers = headerMediaMassa,
      rows = rowsPerusahaan,
      titleTotalData = "Total Data ${mediaMassaOrganisasiLain.size}",
      columnWeights = listOf(0.4f, 1f, 1f, 1f)
    )
  }

}

@Composable
private fun ThirdScreenNotForm(
) {
  val rowsMediaSosial = sosialMedias.mapIndexed { i, s ->
    listOf(
      (i + 1).toString(),
      s.waktuPosting,
      s.kontenPosting
    )
  }
  Title(
    text = "Media Sosial"
  )
  Column {
    TitleTable(
      text = "Instagram"
    )
    TableAndTotalData(
      headers = headerMediaSosial,
      rows = rowsMediaSosial,
      titleTotalData = "Total Data ${sosialMedias.size}",
      columnWeights = listOf(0.3f, 1f, 1f)
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


@Composable
private fun BottomSection(
  nextIndicatorProgress : () -> Unit,
  prevIndicatorProgress : () -> Unit,
  initialState : Int,
  onInitialScreenChange : (Int) -> Unit,
  currentScreen : Int
) {
  val verticalScroll = rememberScrollState()

  var expandedDate by remember { mutableStateOf(false) }
  var expandedWilayahPenerimaManfaat by remember { mutableStateOf(false) }
  val onExpandedWilayahPenerimaManfaatChange =
    { expanded : Boolean -> expandedWilayahPenerimaManfaat = expanded }


  Box(
    modifier = Modifier
      .fillMaxSize()
      .pointerInput(expandedDate) {
        detectTapGestures {
          expandedDate = false
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

            nextIndicatorProgress = nextIndicatorProgress,
            onExpandedWilayahPenerimaManfaatChange = onExpandedWilayahPenerimaManfaatChange,
            expandedWilayahPenerimaManfaat = expandedWilayahPenerimaManfaat
          )

          1 -> SecondScreen(
            prevIndicatorProgress = prevIndicatorProgress,
            nextIndicatorProgress = nextIndicatorProgress,

            )

          2 -> ThirdScreen(
            prevIndicatorProgress = prevIndicatorProgress,
            nextIndicatorProgress = nextIndicatorProgress,


            )

          3 -> FourthScreen(
            prevIndicatorProgress = prevIndicatorProgress,
          )

        }
        LaunchedEffect(currentScreen) { onInitialScreenChange(currentScreen) }
      }
    }
  }
}


@Composable
private fun FirstScreen(
  nextIndicatorProgress : () -> Unit,
  onExpandedWilayahPenerimaManfaatChange : (Boolean) -> Unit,
  expandedWilayahPenerimaManfaat : Boolean
) {
  var fields by remember { mutableStateOf(listOf(Pair("", ""))) }

  Column(
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text(
      text = "Kegiatan apa saja yang lembaga anda lakukan beserta peningkatan/penambahan penerima manfaat dari kegiatan yang lembaga anda lakukan?",
      style = StyledText.MobileBaseMedium,
      color = ColorPalette.PrimaryColor700
    )
    CustomOutlinedTextField(
      label = "Kegiatan yang dilakukan",
      value = "",
      onValueChange = {},
      placeholder = "Ketik Kegiatan",
      labelDefaultColor = ColorPalette.Monochrome300,
      modifier = Modifier.fillMaxWidth(),
      rounded = 40
    )

  }
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = Modifier.animateContentSize()
  ) {
    Text(
      text = "Jumlah Angka Penerimaan Manfaat*",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )

    fields.forEachIndexed { index, field ->
      CustomRowLeftDropdownField(
        dropdownLabel = "Penerima Manfaat",
        dropdownValue = field.first,
        onDropdownValueChange = { newValue ->
          fields = fields.toMutableList().apply {
            this[index] = this[index].copy(first = newValue)
          }
        },
        dropdownPlaceholder = "Pilih Wilayah",
        dropdownItems = provinsis,
        expanded = expandedWilayahPenerimaManfaat,
        onExpandedChange = {
          onExpandedWilayahPenerimaManfaatChange(it)
        },
        jumlahValue = field.second,
        onJumlahValueChange = { newValue ->
          fields = fields.toMutableList().apply {
            this[index] = this[index].copy(second = newValue)
          }
        }
      )
    }

    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      SecondaryButton(
        onClick = {
          fields = fields + Pair("", "")
        },
        text = "Tambah"
      )
      AnimatedVisibility(
        visible = fields.size > 1,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        SecondaryButton(
          onClick = {
            fields = fields.dropLast(1)
          },
          text = "Hapus"
        )
      }

    }
  }
  RupiahField(
    title = "Bagaimana pencapaian pencarian dana dengan donasi (secara online maupun offline)?*"
  ) {
    CustomRowRightDropdownField(
      dropdownLabel = "Platform",
      dropdownValue = "",
      onDropdownValueChange = {},
      dropdownPlaceholder = "Pilih Jenis Platform",
      dropdownItems = provinsis,
      expanded = false,
      onExpandedChange = {},
      jumlahValue = "",
      onJumlahValueChange = {},
      text = "Donasi Online"
    )
    CustomRowRightDropdownField(
      dropdownLabel = "Platform",
      dropdownValue = "",
      onDropdownValueChange = {},
      dropdownPlaceholder = "Pilih Jenis Platform",
      dropdownItems = provinsis,
      expanded = false,
      onExpandedChange = {},
      jumlahValue = "",
      onJumlahValueChange = {},
      text = "Donasi Offline"
    )
  }
  RupiahField(
    title = "Bagaimana pencapaian pencarian dana dengan sponsorship (secara online maupun offline) selama periode LEAD Indonesia (periode Juli 2024)?*"
  ) {
    CustomRowRightDropdownField(
      dropdownLabel = "Platform",
      dropdownValue = "",
      onDropdownValueChange = {},
      dropdownPlaceholder = "Pilih Jenis Platform",
      dropdownItems = provinsis,
      expanded = false,
      onExpandedChange = {},
      jumlahValue = "",
      onJumlahValueChange = {},
      text = "Sponsorship Online"
    )
    CustomRowRightDropdownField(
      dropdownLabel = "Platform",
      dropdownValue = "",
      onDropdownValueChange = {},
      dropdownPlaceholder = "Pilih Jenis Platform",
      dropdownItems = provinsis,
      expanded = false,
      onExpandedChange = {},
      jumlahValue = "",
      onJumlahValueChange = {},
      text = "Sponsorship Offline"
    )
  }
  RupiahField(
    title = "Bagaimana progress pendanaan yang berasal dari donor dan mitra (secara online maupun offline) selama periode LEAD Indonesia (periode Juli 2024)?*"
  ) {
    CustomRowRightDropdownField(
      dropdownLabel = "Platform",
      dropdownValue = "",
      onDropdownValueChange = {},
      dropdownPlaceholder = "Pilih Jenis Platform",
      dropdownItems = provinsis,
      expanded = false,
      onExpandedChange = {},
      jumlahValue = "",
      onJumlahValueChange = {},
      text = "Donasi Online"
    )
    CustomRowRightDropdownField(
      dropdownLabel = "Platform",
      dropdownValue = "",
      onDropdownValueChange = {},
      dropdownPlaceholder = "Pilih Jenis Platform",
      dropdownItems = provinsis,
      expanded = false,
      onExpandedChange = {},
      jumlahValue = "",
      onJumlahValueChange = {},
      text = "Donor Offline"
    )
    CustomRowRightDropdownField(
      dropdownLabel = "Platform",
      dropdownValue = "",
      onDropdownValueChange = {},
      dropdownPlaceholder = "Pilih Jenis Platform",
      dropdownItems = provinsis,
      expanded = false,
      onExpandedChange = {},
      jumlahValue = "",
      onJumlahValueChange = {},
      text = "Dukungan Lainnya"
    )
  }
  RupiahField(
    title = "Bagaimana proses open recruitment relawan yang dilakukan (secara online maupun offline) selama periode LEAD Indonesia (periode Juli 2024)?*"
  ) {
    CustomOutlinedTextField(
      label = "Jumlah Penambahan Relawan",
      placeholder = "Jumlah",
      value = "",
      onValueChange = {},
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40,
      modifier = Modifier.fillMaxWidth()
    )
    CustomOutlinedTextField(
      label = "Jelaskan proses open recruitment yang anda lakukan selama periode LEAD Indonesia periode Juli 2024",
      placeholder = "Jelaskan proses open recruitment",
      value = "",
      onValueChange = {},
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40,
      modifier = Modifier.fillMaxWidth()
    )
  }


  NextPrevButton(
    nextIndicatorProgress = nextIndicatorProgress,
    isPrevExist = false
  )

}

@Composable
private fun SecondScreen(
  prevIndicatorProgress : () -> Unit,
  nextIndicatorProgress : () -> Unit,

  ) {
  Text(
    text = "Bagian ini berisi capaian peserta dalam menyusun Proposal Desain Program Sosial dan Impact Report Lembaga selama mengikuti LEAD Indonesia.",
    style = StyledText.MobileBaseRegular,
    color = ColorPalette.Monochrome300
  )
  HorizontalDivider(
    modifier = Modifier.fillMaxWidth()
  )
  RupiahField(
    title = "Bagaimana progress rancangan desain program dan penyusunan impact report lembaga Anda selama periode LEAD Indonesia (periode Juli 2024)?*"
  ) {
    CustomOutlinedTextField(
      placeholder = "Deskripsi",
      label = "Deskripsikan proses dan capaian selama melakukan diskusi internal kelompok/mentoring",
      value = "",
      onValueChange = {},
      labelDefaultColor = ColorPalette.Monochrome400,
      modifier = Modifier.fillMaxWidth(),
      rounded = 40
    )
  }
  RupiahField(
    title = "Apa tantangan maupun hambatan yang terjadi dalam merancang desain program dan menyusun impact report lembaga Anda selama periode LEAD Indonesia (periode Juli 2024)?*"
  ) {
    CustomOutlinedTextField(
      placeholder = "Tantangan",
      label = "Jelaskan tantangan dan kendala yang dihadapi, serta strategi penyelesaiannya",
      value = "",
      onValueChange = {},
      labelDefaultColor = ColorPalette.Monochrome400,
      modifier = Modifier.fillMaxWidth(),
      rounded = 40
    )
  }

  NextPrevButton(
    nextIndicatorProgress = nextIndicatorProgress,
    prevIndicatorProgress = prevIndicatorProgress
  )
}

@Composable
private fun ThirdScreen(
  prevIndicatorProgress : () -> Unit,
  nextIndicatorProgress : () -> Unit,

  ) {

  Text(
    text = "Lakukanlah Mapping Stakeholder yang akan lembaga Anda tuju atau targetkan sebagai mitra yang akan diajak kerja sama.",
    style = StyledText.MobileBaseRegular,
    color = ColorPalette.Monochrome600
  )
  Text(
    text = "Dapat berupa kerja sama untuk mendapatkan pendanaan maupun kerja sama untuk supporting program/kegiatan (media partner, publikasi, narasumber, terlibat dalam kegiatan yang dilakukan, dsb).",
    style = StyledText.MobileBaseRegular,
    color = ColorPalette.Monochrome600
  )
  Text(
    text = "Kedepannya hal ini berguna untuk mempermudah dalam mengidentifikasi stakeholder terkait.",
    style = StyledText.MobileBaseRegular,
    color = ColorPalette.Monochrome600
  )
  HorizontalDivider(
    modifier = Modifier.fillMaxWidth()
  )

  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Mapping Stakeholder lembaga yang ditargetkan untuk penjalinan kerja sama*",
      style = StyledText.MobileBaseMedium,
      color = ColorPalette.PrimaryColor700
    )
    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.Top
    ) {
      CustomOutlinedTextFieldDropdown(
        label = "Jenis Lembaga",
        value = "",
        onValueChange = {},
        expanded = false,
        onChangeExpanded = {},
        placeholder = "Pilih Jenis Lembaga",
        dropdownItems = provinsis,
        modifier = Modifier.weight(0.5f)
      )
      CustomOutlinedTextFieldDropdown(
        label = "Status",
        value = "",
        onValueChange = {},
        expanded = false,
        onChangeExpanded = {},
        placeholder = "Pilih Status",
        dropdownItems = provinsis,
        modifier = Modifier.weight(0.5f)
      )

    }

    CustomOutlinedTextField(
      label = "Nama Lembaga",
      value = "",
      onValueChange = {},
      placeholder = "Ketik Nama Lembaga",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40

    )
    CustomOutlinedTextField(
      label = "Keterangan Tambahan",
      value = "",
      onValueChange = {},
      placeholder = "Ketik Keterangan Tambahan",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40
    )
  }
  NextPrevButton(
    nextIndicatorProgress = nextIndicatorProgress,
    prevIndicatorProgress = prevIndicatorProgress,
  )

}

@Composable
fun FourthScreen(
  prevIndicatorProgress : () -> Unit,

  ) {
  Text(
    text = "Bertujuan untuk memantau bagaimana lembaga Anda dapat menyebarkan informasi terkait isu yang menjadi fokus dengan mengimplementasikan materi terkait pemasaran sosial yang sudah diberikan saat kegiatan Mini Training LEAD Indonesia dan update setiap bulannya dalam agenda mentoring.",
    style = StyledText.MobileSmallRegular,
    color = ColorPalette.Monochrome600
  )
  HorizontalDivider(
    modifier = Modifier.fillMaxWidth()
  )
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text(
      text = "Lakukanlah Mapping Stakeholder yang akan lembaga Anda tuju atau targetkan sebagai mitra yang akan diajak kerja sama.",
      style = StyledText.MobileBaseRegular,
      color = ColorPalette.Monochrome600
    )
    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.Top
    ) {
      CustomOutlinedTextFieldDropdown(
        label = "Media Sosial",
        value = "",
        onValueChange = {},
        expanded = false,
        onChangeExpanded = {},
        placeholder = "Pilih Media Sosial",
        dropdownItems = provinsis,
        modifier = Modifier.weight(0.5f)
      )
      CustomOutlinedTextFieldDropdown(
        label = "Waktu ",
        value = "",
        onValueChange = {},
        expanded = false,
        onChangeExpanded = {},
        placeholder = "Pilih Waktu Posting",
        dropdownItems = provinsis,
        modifier = Modifier.weight(0.5f)
      )

    }
    CustomOutlinedTextField(
      label = "Konten Yang Diposting",
      value = "",
      onValueChange = {},
      placeholder = "Ketik Konten Yang Diposting",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40
    )
    CustomOutlinedTextField(
      label = "Tautan Pasti (Link)",
      value = "",
      onValueChange = {},
      placeholder = "Ketik Tautan Pasti (Link)",
      modifier = Modifier.fillMaxWidth(),
      labelDefaultColor = ColorPalette.Monochrome400,
      rounded = 40
    )
    SecondaryButton(
      onClick = {
      },
      text = "Tambah"
    )

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
      onClick = { }
    )
  }


}


@Composable
private fun TotalData(
  title : String,

  ) {
  TableRow(
    modifier = Modifier
      .fillMaxWidth()

  ) {
    TableCell(
      text = title,
      isHeader = true,
    )
  }


}

@Composable
private fun Title(
  text : String
) {
  Text(
    text = text,
    style = StyledText.MobileBaseMedium,
    color = ColorPalette.PrimaryColor700
  )
}

@Composable
private fun TitleTable(
  text : String
) {
  TableRow(
    isRowHaveBg = true,
    modifier = Modifier
      .fillMaxWidth(),
  ) {
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      text = text,
      style = StyledText.MobileSmallSemibold,
      color = ColorPalette.Black,
      textAlign = TextAlign.Center
    )

  }

}

@Composable
fun TableAndTotalData(
  headers : List<String>,
  columnWeights : List<Float> = listOf(0.3f, 1f, 1f),
  rows : List<List<String>>,
  titleTotalData : String,
  isHeaderHaveBg : Boolean = false

) {
  Column {
    Table(
      headers = headers,
      columnWeights = columnWeights,
      rows = rows,
      isHeaderHaveBg = isHeaderHaveBg
    )
    TotalData(
      title = titleTotalData
    )
  }
}

@Composable
fun Table(
  headers : List<String>,
  columnWeights : List<Float>,
  rows : List<List<String>>,
  isHeaderHaveBg : Boolean = false
) {
  Column(
  ) {
    TableRow(
      modifier = Modifier.then(
        if (isHeaderHaveBg) Modifier.background(ColorPalette.PrimaryColor100)
        else Modifier
      )
    ) {
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
  modifier : Modifier = Modifier,
  isRowHaveBg : Boolean = false,
  content : @Composable () -> Unit,
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier
      .then(
        if (isRowHaveBg) Modifier.background(ColorPalette.PrimaryColor100)
        else Modifier
      )

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
  val isStatus =
    text == "Disetujui" || text == "Ditolak" || text == "Pengajuan Proposal" || text == "Tahap Persetujuan"
  val color = when (text) {
    "Disetujui"          -> ColorPalette.Success500
    "Ditolak"            -> ColorPalette.Danger500
    "Pengajuan Proposal" -> ColorPalette.Warning700
    "Tahap Persetujuan"  -> ColorPalette.PrimaryColor600
    else                 -> ColorPalette.Black
  }
  val bgColor = when (text) {
    "Disetujui"          -> ColorPalette.Success100
    "Ditolak"            -> ColorPalette.Danger100
    "Pengajuan Proposal" -> ColorPalette.Warning200
    "Tahap Persetujuan"  -> ColorPalette.PrimaryColor200
    else                 -> Color.Transparent
  }
  val padding =
    if (isStatus) 8.dp else 0.dp
  val textAlign =
    if (isStatus) TextAlign.Center else TextAlign.Start

  Text(
    text = text,
    style = fontStyle,
    color = color,
    modifier = modifier
      .background(bgColor, RoundedCornerShape(16.dp))
      .padding(
        vertical = padding,
        horizontal = 8.dp
      ),
    textAlign = textAlign
  )
}


@Composable
private fun RupiahField(
  title : String,
  content : @Composable () -> Unit

) {
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = Modifier.animateContentSize()
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = title,
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.PrimaryColor700
      )
      Text(
        text = "Isi dengan \"-\" atau 0 jika tidak ada.",
        style = StyledText.MobileSmallRegular,
        color = ColorPalette.Monochrome400
      )
      content()

    }


  }
}


@Composable
private fun NextPrevButton(
  nextIndicatorProgress : () -> Unit = {},
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
private fun CustomRowLeftDropdownField(
  dropdownLabel : String,
  dropdownValue : String,
  onDropdownValueChange : (String) -> Unit,
  dropdownPlaceholder : String,
  dropdownItems : List<String>,
  expanded : Boolean,
  onExpandedChange : (Boolean) -> Unit,
  jumlahValue : String,
  onJumlahValueChange : (String) -> Unit,
  modifier : Modifier = Modifier
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Top
  ) {
    CustomOutlinedTextFieldDropdown(
      label = dropdownLabel,
      value = dropdownValue,
      onValueChange = onDropdownValueChange,
      placeholder = dropdownPlaceholder,
      modifier = Modifier.weight(0.7f),
      labelDefaultColor = ColorPalette.Monochrome400,
      dropdownItems = dropdownItems,
      expanded = expanded,
      onChangeExpanded = onExpandedChange
    )
    CustomOutlinedTextFieldJumlah(
      value = jumlahValue,
      onValueChange = onJumlahValueChange,
      placeholder = "Jumlah",
      modifier = Modifier.weight(0.3f)
    )
  }
}

@Composable
private fun CustomRowRightDropdownField(
  dropdownLabel : String,
  dropdownValue : String,
  onDropdownValueChange : (String) -> Unit,
  dropdownPlaceholder : String,
  dropdownItems : List<String>,
  expanded : Boolean,
  text : String,
  onExpandedChange : (Boolean) -> Unit,
  jumlahValue : String,
  onJumlahValueChange : (String) -> Unit,
  modifier : Modifier = Modifier
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Top
  ) {
    OutlinedTextField(
      value = jumlahValue,
      onValueChange = onJumlahValueChange,
      leadingIcon = {

        Text(
          text = "Rp",
          style = StyledText.MobileSmallRegular,
          color = ColorPalette.Monochrome400
        )

      },
      modifier = Modifier.weight(0.5f),
      shape = RoundedCornerShape(40.dp),
      placeholder = {
        Text(
          text = text,
          style = StyledText.MobileSmallRegular,
          color = ColorPalette.Monochrome400
        )
      },
      label = {
        Text(
          text = text,
          style = StyledText.MobileSmallRegular,
          color = ColorPalette.Monochrome400
        )
      },

      )
    CustomOutlinedTextFieldDropdown(
      label = dropdownLabel,
      value = dropdownValue,
      onValueChange = onDropdownValueChange,
      placeholder = dropdownPlaceholder,
      modifier = Modifier
        .weight(0.5f),
      labelDefaultColor = ColorPalette.Monochrome400,
      dropdownItems = dropdownItems,
      expanded = expanded,
      onChangeExpanded = onExpandedChange
    )
  }
}


@Composable
private fun CustomOutlinedTextFieldJumlah(
  value : String,
  onValueChange : (String) -> Unit,
  placeholder : String,
  modifier : Modifier = Modifier,
  size : Dp = 62.dp,
  textStyle : TextStyle = StyledText.MobileSmallRegular,
  borderColor : Color = ColorPalette.Monochrome400,
  focusedBorderColor : Color = ColorPalette.PrimaryColor700
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
    singleLine = true
  )
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
