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
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.RatingSections
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
            hariKegiatanOnValueChange = { newValue ->
                hariKegiatan = newValue
            }
        )
    }
}

@Composable
fun TopSection(
    onSaveFeedback: (String, String, String, String, String) -> Unit = { _, _, _, _, _ -> },
    hariKegiatan : String,
    hariKegiatanOnValueChange : (String) -> Unit
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

        Text(
            text = "Apakah materi yang disampaikan pembicara mudah dipahami?",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Justify,
        )

        (1..2).forEach { speakerNumber ->
            FeedbackEvaluationSection(
                speakerLabel = "Pemateri $speakerNumber",
            )
        }

        Text(
            text = "Apakah pembicara mampu mengatur waktu dengan baik?",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Justify,
        )

        (1..2).forEach { speakerNumber ->
            FeedbackEvaluationSection(
                speakerLabel = "Pemateri $speakerNumber",
            )
        }

        Text(
            text = "Apakah pembicara memberikan jawaban yang memuaskan atas pertanyaan peserta?",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Justify,
        )

        (1..2).forEach { speakerNumber ->
            FeedbackEvaluationSection(
                speakerLabel = "Pemateri $speakerNumber",
            )
        }

        Text(
            text = "Apakah pembicara menggunakan metode yang interaktif?",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Justify,
        )

        (1..2).forEach { speakerNumber ->
            FeedbackEvaluationSection(
                speakerLabel = "Pemateri $speakerNumber",
            )
        }

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
                )
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
}

@Composable
fun FeedbackEvaluationSection(
    speakerLabel: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val labelPemateri = listOf(
            speakerLabel
        )

        labelPemateri.forEach { question ->
            RatingSections(
                title = question,
                rating = 0,
                onRatingSelected = { /* TODO: Handle rating */ }
            )
        }
    }
}