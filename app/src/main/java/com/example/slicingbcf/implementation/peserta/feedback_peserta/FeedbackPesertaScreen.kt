package com.example.slicingbcf.implementation.peserta.feedback_peserta

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.EvaluasiLembaga
import com.example.slicingbcf.data.local.EvaluasiMentoring
import com.example.slicingbcf.data.local.JadwalMentoring
import com.example.slicingbcf.data.local.JawabanPertanyaan
import com.example.slicingbcf.data.local.KepuasanMentoring
import com.example.slicingbcf.data.local.dokumentasiMentoring
import com.example.slicingbcf.data.local.evaluasiLembaga
import com.example.slicingbcf.data.local.evaluasiMentoring
import com.example.slicingbcf.data.local.jadwalMentoring
import com.example.slicingbcf.data.local.jawabanMentoring
import com.example.slicingbcf.data.local.kepuasanMentoring
import com.example.slicingbcf.data.local.mentoringPeserta
import com.example.slicingbcf.ui.animations.AnimatedContentSlide
import com.example.slicingbcf.ui.shared.dropdown.DropdownText
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator
import java.time.format.DateTimeFormatter
import java.util.Locale
import android.net.Uri

@Composable
@Preview(showSystemUi = true)
fun FeedbackPesertaScreen(
    modifier: Modifier = Modifier,
    viewModel : FeedbackPesertaViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    var currentScreen by rememberSaveable { mutableIntStateOf(0) }
    var initialState by remember { mutableIntStateOf(0) }
    var currentTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        when (uiState) {
            is UiState.Loading -> {
                LoadingCircularProgressIndicator()
            }

            is UiState.Success -> {
                Text(
                    text = "Umpan Balik Peserta",
                    style = StyledText.MobileLargeSemibold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                TabRow (
                    selectedTabIndex = currentTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = ColorPalette.PrimaryColor700,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[currentTabIndex]),
                            color = ColorPalette.PrimaryColor700,

                            )
                    }
                ) {
                    Tab(
                        selected = currentTabIndex == 0,
                        onClick = {
                            if (currentTabIndex != 0) {
                                currentTabIndex = 0
                                currentScreen = 0
                            }
                        },
                        text = { Text(text = "Cluster", maxLines = 2, overflow = TextOverflow.Ellipsis) }
                    )
                    Tab(
                        selected = currentTabIndex == 1,
                        onClick = {
                            if (currentTabIndex != 1) {
                                currentTabIndex = 1
                                currentScreen = 1
                            }
                        },
                        text = {
                            Text(
                                text = "Desain Program",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }

                AnimatedContentSlide(
                    currentScreen = currentScreen,
                    initialState = initialState,
                    label = "Feedback Peserta Animation Content",
                ) { targetScreen ->
                    when (targetScreen) {
                        0 -> FirstScreen()
                        1 -> SecondScreen()
                    }
                }

                LaunchedEffect(currentScreen) {
                    initialState = currentScreen
                }
            }
            is UiState.Error   -> {
                val errorMessage = (uiState as UiState.Error).message
                ErrorWithReload(
                    errorMessage = errorMessage,
                    onRetry = {
                        viewModel.onEvent(FeedbackPesertaEvent.ReloadData)
                    }
                )
            }
        }
    }
}

@Composable
fun FirstScreen(
    modifier : Modifier = Modifier
){
    var capaianMentoring by remember { mutableStateOf("") }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(40.dp),

        ) {
        TopSectionFirstScreen(
            capaianMentoringOnValueChange = { newValue -> capaianMentoring = newValue },
            capaianMentoring = capaianMentoring
        )
    }
}

@Composable
fun SecondScreen(
    modifier : Modifier = Modifier
){
    var capaianMentoring by remember { mutableStateOf("") }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
        TopSectionSecondScreen(
            capaianMentoringOnValueChange = { newValue -> capaianMentoring = newValue },
        )
    }
}

@Composable
fun TopSectionFirstScreen(
    capaianMentoringOnValueChange : (String) -> Unit,
    capaianMentoring: String
) {
    var expandedCapaianMentoring by remember { mutableStateOf(false) }
    var mentorNameCluster = mentoringPeserta.find{it.mentoringType == "Cluster"}?.mentorName
    var mentoringTypeCluster = mentoringPeserta.find{it.mentoringType == "Cluster"}?.mentoringType
    var batchCluster = mentoringPeserta.find{it.mentoringType == "Cluster"}?.batch
    var capaianProgramCluster = mentoringPeserta.find{it.mentoringType == "Cluster"}?.capaianProgram

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier.padding(top=16.dp),
                text = "Capaian Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
                color = ColorPalette.PrimaryColor700,
            )
            CustomDropdownMenu(
                label = " ",
                value = capaianMentoring,
                placeholder = "Februari 2023",
                onValueChange = capaianMentoringOnValueChange,
                expanded = expandedCapaianMentoring,
                onChangeExpanded = { expandedCapaianMentoring = it },
                dropdownItems = listOf("Februari 2023", "Maret 2023", "April 2023", "Mei 2023")
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Bakrie Center Foundation",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                Column(
                    modifier = Modifier.size(132.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Nama Mentor",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Tipe Mentoring",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Batch",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Capaian Program",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = ": $mentorNameCluster" ,
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = ": $mentoringTypeCluster" ,
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = ": $batchCluster",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = ": $capaianProgramCluster",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                }
            }
        }

        // Tabel Jadwal Mentoring
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Jadwal Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(jadwal_headers)
                    jadwalMentoring.forEachIndexed { index, jadwalMentoring ->
                        JadwalMentoringRow(jadwalMentoring, index)
                    }
                }
            }
        }

         // Tabel Evaluasi Capaian Mentoring
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Evaluasi Capaian Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(evaluasi_headers)
                    evaluasiMentoring.forEachIndexed { index, evaluasiMentoring ->
                        EvaluasiCapaianRow(evaluasiMentoring, index)
                    }
                }
            }
        }

        // Tabel Evaluasi Lembaga
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Evaluasi Lembaga",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(evaluasi_headers)
                    evaluasiLembaga.forEachIndexed { index, evaluasiLembaga ->
                        EvaluasiLembagaRow(evaluasiLembaga, index)
                    }
                }
            }
        }

        // Tabel Kepuasan Mentoring
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Kepuasan Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(evaluasi_headers)
                    kepuasanMentoring.forEachIndexed { index, kepuasanMentoring ->
                        KepuasanMentoringRow(kepuasanMentoring, index)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            jawabanMentoring.forEach { pertanyaan ->
                JawabanPertanyaanRow(pertanyaan)
            }
            DokumentasiMentoringSection()
        }
    }
}

@Composable
fun TopSectionSecondScreen(
    capaianMentoringOnValueChange : (String) -> Unit,
) {
    var capaianMentoring by remember { mutableStateOf("") }
    var expandedCapaianMentoring by remember { mutableStateOf(false) }
    var mentorNameDesain = mentoringPeserta.find{it.mentoringType == "Desain Program"}?.mentorName
    var mentoringTypeDesain = mentoringPeserta.find{it.mentoringType == "Desain Program"}?.mentoringType
    var batchDesain = mentoringPeserta.find{it.mentoringType == "Desain Program"}?.batch
    var capaianProgramDesain = mentoringPeserta.find{it.mentoringType == "Desain Program"}?.capaianProgram

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Capaian Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
                color = ColorPalette.PrimaryColor700,
            )
            CustomDropdownMenu(
                label = " ",
                value = capaianMentoring,
                placeholder = "Februari 2023",
                onValueChange = capaianMentoringOnValueChange,
                expanded = expandedCapaianMentoring,
                onChangeExpanded = { expandedCapaianMentoring = it },
                dropdownItems = listOf("Februari 2023", "Maret 2023", "April 2023", "Mei 2023")
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Bakrie Center Foundation",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Column(
                    modifier = Modifier.size(132.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Nama Mentor",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Tipe Mentoring",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Batch",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Capaian Program",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = ": $mentorNameDesain",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = ": $mentoringTypeDesain" ,
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = ": $batchDesain",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = ": $capaianProgramDesain",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                }
            }
        }

        // Tabel Jadwal Mentoring
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Jadwal Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(jadwal_headers)
                    jadwalMentoring.forEachIndexed { index, jadwalMentoring ->
                        JadwalMentoringRow(jadwalMentoring, index)
                    }
                }
            }
        }

        // Tabel Evaluasi Capaian Mentoring
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Evaluasi Capaian Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(evaluasi_headers)
                    evaluasiMentoring.forEachIndexed { index, evaluasiMentoring ->
                        EvaluasiCapaianRow(evaluasiMentoring, index)
                    }
                }
            }
        }

        // Tabel Evaluasi Lembaga
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Evaluasi Lembaga",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(evaluasi_headers)
                    evaluasiLembaga.forEachIndexed { index, evaluasiLembaga ->
                        EvaluasiLembagaRow(evaluasiLembaga, index)
                    }
                }
            }
        }

        // Tabel Kepuasan Mentoring
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Evaluasi Lembaga",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(evaluasi_headers)
                    kepuasanMentoring.forEachIndexed { index, kepuasanMentoring ->
                        KepuasanMentoringRow(kepuasanMentoring, index)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            jawabanMentoring.forEach { pertanyaan ->
                JawabanPertanyaanRow(pertanyaan)
            }

            DokumentasiMentoringSection()
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
    ){
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
fun JadwalMentoringRow(jadwalMentoring : JadwalMentoring, index : Int) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH.mm")
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale("id", "ID"))

    Row(
        modifier = Modifier
            .background(ColorPalette.SurfaceContainerLowest)
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RectangleShape
            )
    ) {
        TableCell(
            text = jadwalMentoring.no.toString(),
            weight = 0.5f
        )
        TableCell(
            text = jadwalMentoring.tanggal.format(dateFormatter),
            weight = 1.5f
        )
        TableCell(
            text = "${jadwalMentoring.waktuMulai.format(timeFormatter)} - ${jadwalMentoring.waktuSelesai.format(timeFormatter)}",
            weight = 1.3f
        )
    }
}

@Composable
fun EvaluasiCapaianRow(evaluasiMentoring: EvaluasiMentoring, index : Int) {
    Row(
        modifier = Modifier
            .background(ColorPalette.SurfaceContainerLowest)
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RectangleShape
            )
    ) {
        TableCellEvaluasiMentoring(
            text = evaluasiMentoring.no.toString(),
            weight = 0.5f,
            evaluasiMentoring = evaluasiMentoring
        )
        TableCellEvaluasiMentoring(
            text = evaluasiMentoring.aspek_penilaian,
            weight = 1.7f,
            evaluasiMentoring = evaluasiMentoring
        )
        TableCellEvaluasiMentoring(
            text = evaluasiMentoring.penilaian,
            weight = 1f,
            evaluasiMentoring = evaluasiMentoring,
            isPenilaian = true
        )
    }
}

@Composable
fun EvaluasiLembagaRow(evaluasiLembaga: EvaluasiLembaga, index : Int) {
    Row(
        modifier = Modifier
            .background(ColorPalette.SurfaceContainerLowest)
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RectangleShape
            )
    ) {
        TableCellEvaluasiLembaga(
            text = evaluasiLembaga.no.toString(),
            weight = 0.5f,
            evaluasiLembaga = evaluasiLembaga
        )
        TableCellEvaluasiLembaga(
            text = evaluasiLembaga.aspek_penilaian,
            weight = 1.7f,
            evaluasiLembaga = evaluasiLembaga
        )
        TableCellEvaluasiLembaga(
            text = evaluasiLembaga.penilaian,
            weight = 1f,
            evaluasiLembaga = evaluasiLembaga,
            isPenilaian = true
        )
    }
}

@Composable
fun KepuasanMentoringRow(kepuasanMentoring: KepuasanMentoring, index : Int) {
    Row(
        modifier = Modifier
            .background(ColorPalette.SurfaceContainerLowest)
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RectangleShape
            )
    ) {
        TableCellKepuasanMentoring(
            text = kepuasanMentoring.no.toString(),
            weight = 0.5f,
            kepuasanMentoring = kepuasanMentoring
        )
        TableCellKepuasanMentoring(
            text = kepuasanMentoring.aspek_penilaian,
            weight = 1.7f,
            kepuasanMentoring = kepuasanMentoring
        )
        TableCellKepuasanMentoring(
            text = kepuasanMentoring.penilaian,
            weight = 1f,
            isPenilaian = true,
            kepuasanMentoring = kepuasanMentoring
        )
    }
}

@Composable
fun TableCell(
    text : String,
    isHeader : Boolean = false,
    weight : Float,
    color : Color = ColorPalette.Monochrome900,
) {
    Text(
        text = text,
        style = if (isHeader) StyledText.MobileXsBold else StyledText.MobileXsRegular,
        color = color,
        modifier = Modifier
            .width(120.dp * weight)
            .padding(8.dp)
    )
}

@Composable
fun TableCellEvaluasiMentoring(
    text : String,
    isHeader : Boolean = false,
    weight : Float,
    color : Color = ColorPalette.Monochrome900,
    isPenilaian: Boolean = false,
    evaluasiMentoring: EvaluasiMentoring
) {
    if(isPenilaian){
        Box(modifier = Modifier
                .width(120.dp * weight)
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (evaluasiMentoring.penilaian == "Sangat Baik") ColorPalette.Success100 else ColorPalette.Warning100,
                    )
                    .border(
                        width = 1.dp,
                        color = if (evaluasiMentoring.penilaian == "Sangat Baik") ColorPalette.Success600 else ColorPalette.Warning700,
                    )
                    .width(72.dp)
                    .height(20.dp)
            ){
                Text(
                    text = evaluasiMentoring.penilaian,
                    color = if (evaluasiMentoring.penilaian == "Sangat Baik") ColorPalette.Success600 else ColorPalette.Warning700,
                    style = StyledText.Mobile2xsRegular,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    } else {
        Text(
            text = text,
            style = if (isHeader) StyledText.MobileXsBold else StyledText.MobileXsRegular,
            color = color,
            modifier = Modifier
                .width(120.dp * weight)
                .padding(8.dp)
        )
    }
}

@Composable
fun TableCellEvaluasiLembaga(
    text : String,
    isHeader : Boolean = false,
    weight : Float,
    color : Color = ColorPalette.Monochrome900,
    isPenilaian: Boolean = false,
    evaluasiLembaga: EvaluasiLembaga
) {
    if(isPenilaian){
        Box(modifier = Modifier
            .width(120.dp * weight)
            .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (evaluasiLembaga.penilaian == "Sangat Baik") ColorPalette.Success100 else ColorPalette.Warning100,
                    )
                    .border(
                        width = 1.dp,
                        color = if (evaluasiLembaga.penilaian == "Sangat Baik") ColorPalette.Success600 else ColorPalette.Warning700,
                    )
                    .width(72.dp)
                    .height(20.dp)
            ){
                Text(
                    text = evaluasiLembaga.penilaian,
                    color = if (evaluasiLembaga.penilaian == "Sangat Baik") ColorPalette.Success600 else ColorPalette.Warning700,
                    style = StyledText.Mobile2xsRegular,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    } else {
        Text(
            text = text,
            style = if (isHeader) StyledText.MobileXsBold else StyledText.MobileXsRegular,
            color = color,
            modifier = Modifier
                .width(120.dp * weight)
                .padding(8.dp)
        )
    }
}

@Composable
fun TableCellKepuasanMentoring(
    text : String,
    isHeader : Boolean = false,
    weight : Float,
    color : Color = ColorPalette.Monochrome900,
    isPenilaian: Boolean = false,
    kepuasanMentoring: KepuasanMentoring
) {
    if(isPenilaian){
        Box(modifier = Modifier
            .width(120.dp * weight)
            .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (kepuasanMentoring.penilaian == "Sangat Puas") ColorPalette.Success100 else ColorPalette.Warning100,
                    )
                    .border(
                        width = 1.dp,
                        color = if (kepuasanMentoring.penilaian == "Sangat Puas") ColorPalette.Success600 else ColorPalette.Warning700,
                    )
                    .width(72.dp)
                    .height(20.dp)
            ){
                Text(
                    text = kepuasanMentoring.penilaian,
                    color = if (kepuasanMentoring.penilaian == "Sangat Puas") ColorPalette.Success600 else ColorPalette.Warning700,
                    style = StyledText.Mobile2xsRegular,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    } else {
        Text(
            text = text,
            style = if (isHeader) StyledText.MobileXsBold else StyledText.MobileXsRegular,
            color = color,
            modifier = Modifier
                .width(120.dp * weight)
                .padding(8.dp)
        )
    }
}


@Composable
fun JawabanPertanyaanRow(pertanyaan: JawabanPertanyaan) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = pertanyaan.aspek,
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Justify
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, ColorPalette.Monochrome300, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = pertanyaan.jawaban,
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.Black,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun DokumentasiMentoringSection() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Dokumentasi Mentoring",
            style = StyledText.MobileBaseSemibold,
        )

        dokumentasiMentoring.forEach { dokumen ->
            OutlinedButton(
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(Uri.parse(dokumen.uri), getMimeType(dokumen.namaFile))
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Tidak dapat membuka dokumen: ${dokumen.namaFile}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(45.dp),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, ColorPalette.PrimaryColor400),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.png_filetype),
                        contentDescription = "File PNG Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = dokumen.namaFile,
                        style = StyledText.MobileSmallRegular,
                        color = ColorPalette.Black,
                    )
                }
            }
        }
    }
}


fun getMimeType(fileName: String): String {
    return when (fileName.substringAfterLast('.', "").lowercase()) {
        "pdf" -> "application/pdf"
        "png" -> "image/png"
        "jpg", "jpeg" -> "image/jpeg"
        "doc", "docx" -> "application/msword"
        "xls", "xlsx" -> "application/vnd.ms-excel"
        else -> "*/*"
    }
}

data class Header(
    val name : String,
    val weight : Float
)

val jadwal_headers = listOf(
    Header("No.", 0.5f),
    Header("Tanggal", 1.5f),
    Header("Waktu", 1.3f)
)

val evaluasi_headers = listOf(
    Header("No.", 0.5f),
    Header("Aspek Penilaian", 1.7f),
    Header("Penilaian", 1f)
)

@Composable
fun CustomDropdownMenu(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    dropdownItems: List<String>,
    expanded: Boolean,
    onChangeExpanded: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { onChangeExpanded(!expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, ColorPalette.Monochrome400),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (value.isEmpty()) placeholder else value,
                        style = StyledText.MobileSmallRegular,
                        color = if (value.isEmpty()) ColorPalette.Monochrome400 else ColorPalette.Monochrome900
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowRight,
                        contentDescription = null,
                        tint = ColorPalette.Monochrome900
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
