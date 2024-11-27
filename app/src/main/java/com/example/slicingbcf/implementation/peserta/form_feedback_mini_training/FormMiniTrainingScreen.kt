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
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.RatingSection
import com.example.slicingbcf.ui.shared.dropdown.CustomDropdownMenuAsterisk
import com.example.slicingbcf.ui.shared.dropdown.DropdownText
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextAsterisk
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.shared.textfield.TextFieldLong

@Preview
@Composable
fun FormMiniTrainingScreen(
    modifier: Modifier = Modifier,
){
    var hariKegiatan by remember { mutableStateOf("") }
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
            hariKegiatan = hariKegiatan,
            hariKegiatanOnValueChange = { newValue -> hariKegiatan = newValue },
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
        )
    }
}

@Composable
fun TopSection(
    onSaveFeedback: (String, String, String, String, String) -> Unit = { _, _, _, _, _ -> },
    hariKegiatan : String,
    hariKegiatanOnValueChange : (String) -> Unit,

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
    var speaker1Name by remember { mutableStateOf(TextFieldValue("")) }
    var speaker2Name by remember { mutableStateOf(TextFieldValue("")) }
    var eventDate by remember { mutableStateOf(TextFieldValue("")) }
    var kritikSaran by remember { mutableStateOf(TextFieldValue("")) }
    var expandedHariKegiatan by remember { mutableStateOf(false) }

    Text(
        text = "Umpan Balik Mini Training",
        style = StyledText.MobileLargeSemibold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
    )

    CustomDropdownMenuAsterisk(
        label = "Hari Kegiatan Mentoring",
        value = hariKegiatan,
        placeholder = "Pilih Hari",
        onValueChange = hariKegiatanOnValueChange,
        expanded = expandedHariKegiatan,
        onChangeExpanded = { expandedHariKegiatan = it },
        dropdownItems = listOf("Mini Training hari ke-1", "Mini Training hari ke-2", "Mini Training hari ke-3")
    )

    CustomOutlinedTextAsterisk(
        label = "Nama Pemateri 1",
        value = speaker1Name,
        placeholder = "Masukkan nama pemateri",
        onValueChange = { speaker1Name = it }
    )

    CustomOutlinedTextAsterisk(
        label = "Nama Pemateri 2",
        value = speaker2Name,
        placeholder = "Masukkan nama pemateri",
        onValueChange = { speaker2Name = it }
    )
    CustomOutlinedTextAsterisk(
        label = "Tanggal Kegiatan",
        value = eventDate,
        placeholder = "DD/MM/YYYY",
        onValueChange = { eventDate = it }
    )

    Spacer(modifier = Modifier.height(16.dp))

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

    TextFieldLong(
        label = "Silakan berikan kritik dan saran Anda mengenai kualitas sesi Mini Training secara keseluruhan",
        placeholder = "Tuliskan kritik dan saran setelah mengikuti kegiatan Mini Training hari ini!",
        value = kritikSaran,
        onValueChange = { kritikSaran = it }
    )

    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = {
            onSaveFeedback(
                hariKegiatan,
                speaker1Name.text,
                speaker2Name.text,
                kritikSaran.text,
                eventDate.text
            ) },
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
}