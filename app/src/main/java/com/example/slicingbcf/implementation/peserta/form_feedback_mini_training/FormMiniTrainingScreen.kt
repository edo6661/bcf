package com.example.slicingbcf.implementation.peserta.form_feedback_mini_training

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.implementation.peserta.form_feedback_mini_training.ConstantFormMiniTraining.Companion.hariKegiatans
import com.example.slicingbcf.implementation.peserta.form_feedback_mini_training.ConstantFormMiniTraining.Companion.miniTrainingQuestion
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.TextWithAsterisk
import com.example.slicingbcf.ui.shared.rating.RatingField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdownDate
import com.example.slicingbcf.util.convertMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMiniTrainingScreen(
    modifier: Modifier = Modifier,
    onNavigateBeranda: (Int) -> Unit,
    viewModel: FormMiniTrainingViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val datePickerState = rememberDatePickerState()
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val formattedDate = convertMillisToDate(millis)
            if (formattedDate != uiState.selectedDate) {
                viewModel.onEvent(FormMiniTrainingEvent.SelectedDateChanged(formattedDate))
            }
        }
    }
    var expandedDate by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TopSection(
            state = uiState,
            expandedDate = expandedDate,
            datePickerState = datePickerState,
            onExpandedDateChange = { expandedDate = it },
            onNavigateBeranda = onNavigateBeranda,
            onEvent = { event -> viewModel.onEvent(event)},
            isLoading = isLoading
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSection(
    state : FormMiniTrainingState,
    onEvent: (FormMiniTrainingEvent) -> Unit,
    isLoading: Boolean,
    datePickerState : DatePickerState,
    expandedDate : Boolean,
    onExpandedDateChange : (Boolean) -> Unit,
    onNavigateBeranda: (Int) -> Unit,
) {
    var expandedHariKegiatan by remember { mutableStateOf(false) }

    Text(
        text = "Umpan Balik Mini Training",
        style = StyledText.MobileLargeSemibold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    )

    CustomOutlinedTextFieldDropdown(
        label = "Hari Kegiatan Mentoring",
        value = state.hariKegiatan,
        asteriskAtEnd = true,
        onValueChange = {
            onEvent(FormMiniTrainingEvent.HariKegiatanChanged(it))
        },
        placeholder = "Pilih Periode Capaian Mentoring",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        dropdownItems = hariKegiatans,
        expanded = expandedHariKegiatan,
        onChangeExpanded = {
            expandedHariKegiatan = it
        },
        error = null
    )

    CustomOutlinedTextField(
        label = "Nama Pemateri 1",
        value = state.speaker1Name,
        error = null,
        onValueChange = {
            onEvent(FormMiniTrainingEvent.Speaker1NameChanged(it))
        },
        placeholder = "Masukkan nama pemateri",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        borderColor = ColorPalette.Outline,
        rounded = 40,
    )

    CustomOutlinedTextField(
        label = "Nama Pemateri 2",
        value = state.speaker2Name,
        error = null,
        onValueChange = {
            onEvent(FormMiniTrainingEvent.Speaker2NameChanged(it))
        },
        placeholder = "Masukkan nama pemateri",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        borderColor = ColorPalette.Outline,
        rounded = 40,
    )

    CustomOutlinedTextFieldDropdownDate(
        label = "Tanggal Kegiatan",
        value = state.selectedDate,
        placeholder = "DD/MM/YYYY",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        datePickerState = datePickerState,
        asteriskAtEnd = true,
        error = state.selectedDateError,
        expanded = expandedDate,
        onChangeExpanded = {
            onExpandedDateChange(it)
        },
    )

    Text(
        text = TextWithAsterisk(miniTrainingQuestion[0]),
        style = StyledText.MobileBaseSemibold,
        color = ColorPalette.PrimaryColor700,
        textAlign = TextAlign.Justify,
        )
    state.ratingMateri.forEachIndexed { i, ratingMateri ->
        RatingField(
            description = "Pemateri ${i + 1}",
            rating = state.ratingMateri[i],
            onRatingChange = {
                onEvent(
                    FormMiniTrainingEvent.ratingMateriChanged(
                        index = i,
                        evaluasiMateri = it
                    )
                )
            }
        )
    }

    Text(
        text = TextWithAsterisk(miniTrainingQuestion[1]),
        style = StyledText.MobileBaseSemibold,
        color = ColorPalette.PrimaryColor700,
        textAlign = TextAlign.Justify,
        )
    state.ratingWaktu.forEachIndexed { i, ratingWaktu ->
        RatingField(
            description = "Pemateri ${i + 1}",
            rating = state.ratingWaktu[i],
            onRatingChange = {
                onEvent(
                    FormMiniTrainingEvent.ratingWaktuChanged(
                        index = i,
                        evaluasiWaktu = it
                    )
                )
            }
        )
    }
    Text(
        text = TextWithAsterisk(miniTrainingQuestion[2]),
        style = StyledText.MobileBaseSemibold,
        color = ColorPalette.PrimaryColor700,
        textAlign = TextAlign.Justify,
        )
    state.ratingJawaban.forEachIndexed { i, ratingJawaban ->
        RatingField(
            description = "Pemateri ${i + 1}",
            rating = state.ratingJawaban[i],
            onRatingChange = {
                onEvent(
                    FormMiniTrainingEvent.ratingJawabanChanged(
                        index = i,
                        evaluasiJawaban = it
                    )
                )
            }
        )
    }
    Text(
        text = TextWithAsterisk(miniTrainingQuestion[3]),
        style = StyledText.MobileBaseSemibold,
        color = ColorPalette.PrimaryColor700,
        textAlign = TextAlign.Justify,
        )
    state.ratingMetode.forEachIndexed { i, ratingMetode ->
        RatingField(
            description = "Pemateri ${i + 1}",
            rating = state.ratingMetode[i],
            onRatingChange = {
                onEvent(
                    FormMiniTrainingEvent.ratingMetodeChanged(
                        index = i,
                        evaluasiMetode = it
                    )
                )
            }
        )
    }
    CustomOutlinedTextField(
        label = "Kritik dan Saran",
        value = state.kritikSaran,
        onValueChange = {
            onEvent(FormMiniTrainingEvent.KritikSaranChanged(it))
        },
        asteriskAtEnd = true,
        error = state.kritikSaranError,
        placeholder = "Tuliskan kritik dan saran setelah mengikuti kegiatan Mini Training hari ini!",
        modifier = Modifier
            .fillMaxWidth()
            .height(148.dp),
        multiLine = true,
        maxLines = 5,
        labelDefaultColor = ColorPalette.Monochrome400,
        rounded = 20,
        borderColor = ColorPalette.Outline,
    )

    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = {
            onEvent(FormMiniTrainingEvent.Submit)
            },

        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorPalette.PrimaryColor700,
            contentColor = Color.White
        )
    ) {
        Text("Simpan", style = StyledText.MobileBaseSemibold)
    }

    if(isLoading){
        SubmitLoadingIndicatorDouble(
            isLoading = isLoading,
            title = "Memproses Umpan Balik Anda...",
            onAnimationFinished = {onNavigateBeranda(1)},
            titleBerhasil = "Umpan Balik Anda Berhasil Terkirim!",
        )
    }
}