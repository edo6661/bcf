package com.example.slicingbcf.implementation.peserta.form_feedback_mini_training

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.formMentor
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.ConstantFormFeedbackMentor.Companion.periodeCapaians
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.RatingSection
import com.example.slicingbcf.implementation.peserta.form_feedback_mini_training.ConstantFormMiniTraining.Companion.hariKegiatans
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.dialog.CustomAlertDialog
import com.example.slicingbcf.ui.shared.dropdown.CustomDropdownMenuAsterisk
import com.example.slicingbcf.ui.shared.dropdown.DropdownText
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextAsterisk
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdownDate
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdownDateAsterisk
import com.example.slicingbcf.ui.shared.textfield.TextFieldLong
import com.example.slicingbcf.util.convertMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMiniTrainingScreen(
    modifier: Modifier = Modifier,
    onNavigateBeranda: (Int) -> Unit,
){
    val datePickerState = rememberDatePickerState()
    var expandedDate by remember { mutableStateOf(false) }
    val selectedDate = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
    var ratingMateriPemateri1 by remember { mutableStateOf(0) }
    var ratingMateriPemateri2 by remember { mutableStateOf(0) }
    var ratingWaktuPemateri1 by remember { mutableStateOf(0) }
    var ratingWaktuPemateri2 by remember { mutableStateOf(0) }
    var ratingJawabanPemateri1 by remember { mutableStateOf(0) }
    var ratingJawabanPemateri2 by remember { mutableStateOf(0) }
    var ratingMetodePemateri1 by remember { mutableStateOf(0) }
    var ratingMetodePemateri2 by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TopSection(
            onSaveFeedback = { dateOfEvent, speaker1, speaker2, kritik, eventDate ->
                // TODO simpan data
            },
            selectedDate = selectedDate,
            expandedDate = expandedDate,
            datePickerState = datePickerState,
            onExpandedDateChange = { expandedDate = it },
            ratingMateriPemateri1 = ratingMateriPemateri1,
            onRatingMateriPemateri1Change = {ratingMateriPemateri1 = it},
            ratingMateriPemateri2 = ratingMateriPemateri2,
            onRatingMateriPemateri2Change = {ratingMateriPemateri2 = it},
            ratingWaktuPemateri1 = ratingWaktuPemateri1,
            onRatingWaktuPemateri1Change = {ratingWaktuPemateri1 = it},
            ratingWaktuPemateri2 = ratingWaktuPemateri2,
            onRatingWaktuPemateri2Change = {ratingWaktuPemateri2 = it},
            ratingJawabanPemateri1 = ratingJawabanPemateri1,
            onRatingJawabanPemateri1Change = {ratingJawabanPemateri1 = it},
            ratingJawabanPemateri2 = ratingJawabanPemateri2,
            onRatingJawabanPemateri2Change = {ratingJawabanPemateri2 = it},
            ratingMetodePemateri1 = ratingMetodePemateri1,
            onRatingMetodePemateri1Change = {ratingMetodePemateri1 = it},
            ratingMetodePemateri2 = ratingMetodePemateri2,
            onRatingMetodePemateri2Change = {ratingMetodePemateri2 = it},
            onNavigateBeranda = onNavigateBeranda,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSection(
    onSaveFeedback: (String, String, String, String, String) -> Unit = { _, _, _, _, _ -> },
    selectedDate : String,
    datePickerState : DatePickerState,
    expandedDate : Boolean,
    onExpandedDateChange : (Boolean) -> Unit,
    onNavigateBeranda: (Int) -> Unit,
    onRatingMateriPemateri1Change: (Int) -> Unit,
    ratingMateriPemateri1: Int,
    onRatingMateriPemateri2Change: (Int) -> Unit,
    ratingMateriPemateri2: Int,
    onRatingWaktuPemateri1Change: (Int) -> Unit,
    ratingWaktuPemateri1: Int,
    onRatingWaktuPemateri2Change: (Int) -> Unit,
    ratingWaktuPemateri2: Int,
    onRatingJawabanPemateri1Change: (Int) -> Unit,
    ratingJawabanPemateri1: Int,
    onRatingJawabanPemateri2Change: (Int) -> Unit,
    ratingJawabanPemateri2: Int,
    onRatingMetodePemateri1Change: (Int) -> Unit,
    ratingMetodePemateri1: Int,
    onRatingMetodePemateri2Change: (Int) -> Unit,
    ratingMetodePemateri2: Int,
) {
    var hariKegiatan by remember { mutableStateOf("") }
    var speaker1Name by remember { mutableStateOf("") }
    var speaker2Name by remember { mutableStateOf("") }
    var kritikSaran by remember { mutableStateOf("") }
    var expandedHariKegiatan by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

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
        value = hariKegiatan,
        asteriskAtEnd = true,
        onValueChange = {
            hariKegiatan = it
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
        value = speaker1Name,
        error = null,
        onValueChange = {speaker1Name = it
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
        value = speaker2Name,
        error = null,
        onValueChange = {speaker2Name = it
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
        value = selectedDate,
        placeholder = "DD/MM/YYYY",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        datePickerState = datePickerState,
        asteriskAtEnd = true,
        error = null,
        expanded = expandedDate,
        onChangeExpanded = {
            onExpandedDateChange(it)
        },
    )

    Row(
    ) {
        Text(
            text = "*",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.Error,
        )
        Text(
            text = "Apakah materi yang disampaikan pembicara mudah dipahami?",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Justify,
        )
    }

    RatingSection(
        title = "Pemateri 1",
        rating = ratingMateriPemateri1,
        onRatingSelected = onRatingMateriPemateri1Change,
    )

    RatingSection(
        title = "Pemateri 2",
        rating = ratingMateriPemateri2,
        onRatingSelected = onRatingMateriPemateri2Change,
    )
    Row {
        Text(
            text = "*",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.Error,
        )
        Text(
            text = "Apakah pembicara mampu mengatur waktu dengan baik?",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Justify,
        )
    }

    RatingSection(
        title = "Pemateri 1",
        rating = ratingWaktuPemateri1,
        onRatingSelected = onRatingWaktuPemateri1Change,
    )

    RatingSection(
        title = "Pemateri 2",
        rating = ratingWaktuPemateri2,
        onRatingSelected = onRatingWaktuPemateri2Change,
    )

    Row {
        Text(
            text = "*",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.Error,
        )
        Text(
            text = "Apakah pembicara memberikan jawaban yang memuaskan atas pertanyaan peserta?",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Justify,
        )
    }

    RatingSection(
        title = "Pemateri 1",
        rating = ratingJawabanPemateri1,
        onRatingSelected = onRatingJawabanPemateri1Change,
    )

    RatingSection(
        title = "Pemateri 2",
        rating = ratingJawabanPemateri2,
        onRatingSelected = onRatingJawabanPemateri2Change,
    )

    Row {
        Text(
            text = "*",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.Error,
        )
        Text(
            text = "Apakah pembicara menggunakan metode yang interaktif?",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Justify,
        )
    }

    RatingSection(
        title = "Pemateri 1",
        rating = ratingMetodePemateri1,
        onRatingSelected = onRatingMetodePemateri1Change,
    )

    RatingSection(
        title = "Pemateri 2",
        rating = ratingMetodePemateri2,
        onRatingSelected = onRatingMetodePemateri2Change,
    )

    CustomOutlinedTextField(
        label = "Kritik dan Saran",
        value = kritikSaran,
        onValueChange = {
            kritikSaran = it
        },
        asteriskAtEnd = true,
        error = null,
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
            isLoading = true
            onSaveFeedback(
                hariKegiatan,
                speaker1Name,
                speaker2Name,
                kritikSaran,
                selectedDate)
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