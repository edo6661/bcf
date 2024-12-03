package com.example.slicingbcf.implementation.mentor.jadwal.tambah_jadwal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.dropdown.CustomDropdownMenuAsterisk
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextAsterisk
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdownDateAsterisk
import com.example.slicingbcf.ui.shared.textfield.TextFieldLong
import com.example.slicingbcf.util.convertMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJadwalMentorScreen(
    modifier: Modifier = Modifier,
    onNavigateBeranda: (Int) -> Unit,
    id: String
){
    var tipeKegiatan by remember { mutableStateOf("") }
    var namaLembaga by remember { mutableStateOf("") }
    var namaPemateri by remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()
    var expandedDate by remember { mutableStateOf(false) }
    val selectedDate = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""


    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TopSection(
            onSaveFeedback = { tipeKegiatan, namaPemateri, namaLembaga, eventDate, judulKegiatan, tautanKegiatan, deskripsiAgenda ->
                // TODO simpan data
            },
            tipeKegiatan = tipeKegiatan,
            namaPemateri = namaPemateri,
            namaLembaga = namaLembaga,
            tipeKegiatanOnValueChange = { newValue ->
                tipeKegiatan = newValue
            },
            namaPemateriOnValueChange = { newValue ->
                namaPemateri = newValue
            },
            namaLembagaOnValueChange = { newValue ->
                namaLembaga = newValue
            },
            selectedDate = selectedDate,
            expandedDate = expandedDate,
            datePickerState = datePickerState,
            onExpandedDateChange = { expandedDate = it },
            onNavigateBeranda = onNavigateBeranda
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSection(
    onSaveFeedback: (String, String, String, String, String, String, String) -> Unit = { _, _, _, _, _, _, _ -> },
    tipeKegiatan : String,
    namaPemateri : String,
    namaLembaga: String,
    tipeKegiatanOnValueChange : (String) -> Unit,
    namaPemateriOnValueChange : (String) -> Unit,
    namaLembagaOnValueChange : (String) -> Unit,
    selectedDate: String,
    datePickerState : DatePickerState,
    expandedDate : Boolean,
    onExpandedDateChange : (Boolean) -> Unit,
    onNavigateBeranda: (Int) -> Unit,
) {
    var eventDate by remember { mutableStateOf(TextFieldValue("")) }
    var judulKegiatan by remember { mutableStateOf(TextFieldValue("")) }
    var waktuMulai by remember { mutableStateOf(TextFieldValue("")) }
    var waktuSelesai by remember { mutableStateOf(TextFieldValue("")) }
    var tautanKegiatan by remember { mutableStateOf(TextFieldValue("")) }
    var deskripsiAgenda by remember { mutableStateOf(TextFieldValue("")) }
    var expandedTipeKegiatan by remember { mutableStateOf(false) }
    var expandedNamaPemateri by remember { mutableStateOf(false) }
    var expandedNamaLembaga by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Text(
        text = "Tambah Jadwal Kegiatan",
        style = StyledText.MobileLargeSemibold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
    )
    CustomOutlinedTextAsterisk(
        label = "Judul Kegiatan",
        value = judulKegiatan,
        placeholder = "Masukkan judul kegiatan",
        onValueChange = { judulKegiatan = it }
    )
    CustomDropdownMenuAsterisk(
        label = "Tipe Kegiatan",
        value = tipeKegiatan,
        placeholder = "Pilih Tipe Kegiatan",
        onValueChange = tipeKegiatanOnValueChange,
        expanded = expandedTipeKegiatan,
        onChangeExpanded = { expandedTipeKegiatan = it },
        dropdownItems = listOf("Cluster", "Desain Program")
    )
    CustomOutlinedTextFieldDropdownDateAsterisk(
        label = "Tanggal Kegiatan",
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


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomOutlinedTextAsterisk(
            label = "Waktu Mulai",
            value = waktuMulai,
            placeholder = "HH:MM",
            onValueChange = { waktuMulai = it },
            modifier = Modifier
                .weight(1f)
        )

        CustomOutlinedTextAsterisk(
            label = "Waktu Selesai",
            value = waktuSelesai,
            placeholder = "HH:MM",
            onValueChange = { waktuSelesai = it },
            modifier = Modifier
                .weight(1f)
        )
    }
    CustomDropdownMenuAsterisk(
        label = "Nama Pemateri",
        value = namaPemateri,
        placeholder = "Pilih Nama Pemateri",
        onValueChange = namaPemateriOnValueChange,
        expanded = expandedNamaPemateri,
        onChangeExpanded = { expandedNamaPemateri = it },
        dropdownItems = listOf("Lisa Blekpink", "Bruno Maret", "Jukowaw")
    )
    CustomDropdownMenuAsterisk(
        label = "Lembaga",
        value = namaLembaga,
        placeholder = "Pilih Nama Lembaga",
        onValueChange = namaLembagaOnValueChange,
        expanded = expandedNamaLembaga,
        onChangeExpanded = { expandedNamaLembaga = it },
        dropdownItems = listOf("Bakrie CenterFoundation", "The Next Gen", "Indonesia Jaya")
    )
    TextFieldLong(
        label = "Deskripsi Agenda",
        placeholder = "Isi detail acara disini",
        value = deskripsiAgenda,
        onValueChange = { deskripsiAgenda = it }
    )
    CustomOutlinedTextAsterisk(
        label = "Tautan Kegiatan",
        value = tautanKegiatan,
        placeholder = "Masukkan Tautan Kegiatan",
        onValueChange = { tautanKegiatan = it }
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ){
        Button(
            onClick = {
                // TODO KEMBALIIIIII
            },
            modifier = Modifier
                .width(120.dp)
                .height(50.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorPalette.PrimaryColor100,
                contentColor = ColorPalette.PrimaryColor700
            )
        ) {
            Text("Kembali", style = StyledText.MobileBaseSemibold)
        }
        Button(
            onClick = {
                onSaveFeedback(
                    tipeKegiatan,
                    namaPemateri,
                    namaLembaga,
                    eventDate.text,
                    judulKegiatan.text,
                    tautanKegiatan.text,
                    deskripsiAgenda.text,
                )
                isLoading = true
            },
            modifier = Modifier
                .width(120.dp)
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
}