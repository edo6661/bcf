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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.implementation.mentor.jadwal.tambah_jadwal.ConstantAddJadwalMentor.Companion.namaLembagas
import com.example.slicingbcf.implementation.mentor.jadwal.tambah_jadwal.ConstantAddJadwalMentor.Companion.namaPemateris
import com.example.slicingbcf.implementation.mentor.jadwal.tambah_jadwal.ConstantAddJadwalMentor.Companion.tipeKegiatans
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdownDate
import com.example.slicingbcf.util.convertMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJadwalMentorScreen(
    modifier: Modifier = Modifier,
    onNavigateBeranda: (Int) -> Unit,
    onNavigateBack: () -> Boolean,
    id: String,
    viewModel: AddJadwalMentorViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var expandedDate by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val formattedDate = convertMillisToDate(millis)
            if (formattedDate != uiState.selectedDate) {
                viewModel.onEvent(AddJadwalMentorEvent.SelectedDateChanged(formattedDate))
            }
        }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TopSection(
            state = uiState,
            onEvent = {event -> viewModel.onEvent(event)},
            expandedDate = expandedDate,
            datePickerState = datePickerState,
            onExpandedDateChange = { expandedDate = it },
            onNavigateBeranda = onNavigateBeranda,
            onNavigateBack = onNavigateBack,
            isLoading = isLoading
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSection(
    state : AddJadwalMentorState,
    onEvent: (AddJadwalMentorEvent) -> Unit,
    isLoading: Boolean,
    datePickerState : DatePickerState,
    expandedDate : Boolean,
    onExpandedDateChange : (Boolean) -> Unit,
    onNavigateBeranda: (Int) -> Unit,
    onNavigateBack: () -> Boolean
) {
    var expandedTipeKegiatan by remember { mutableStateOf(false) }
    var expandedNamaPemateri by remember { mutableStateOf(false) }
    var expandedNamaLembaga by remember { mutableStateOf(false) }

    Text(
        text = "Tambah Jadwal Kegiatan",
        style = StyledText.MobileLargeSemibold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    )

    CustomOutlinedTextField(
        label = "Judul Kegiatan",
        value = state.judulKegiatan,
        error = state.judulKegiatanError,
        onValueChange = {
            onEvent(AddJadwalMentorEvent.JudulKegiatanChanged(it))
        },
        placeholder = "Masukkan judul kegiatan",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        borderColor = ColorPalette.Outline,
        rounded = 40,
    )

    CustomOutlinedTextFieldDropdown(
        label = "Tipe Kegiatan",
        value = state.tipeKegiatan,
        asteriskAtEnd = true,
        onValueChange = {
            onEvent(AddJadwalMentorEvent.TipeKegiatanChanged(it))
        },
        placeholder = "Pilih Tipe Kegiatan",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        dropdownItems = tipeKegiatans,
        expanded = expandedTipeKegiatan,
        onChangeExpanded = {
            expandedTipeKegiatan = it
        },
        error = state.tipeKegiatanError
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomOutlinedTextField(
            label = "Waktu Mulai",
            value = state.waktuMulai,
            error = state.waktuMulaiError,
            onValueChange = {
                onEvent(AddJadwalMentorEvent.WaktuMulaiChanged(it))
            },
            placeholder = "HH:MM",
            modifier = Modifier.width(180.dp),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )
        CustomOutlinedTextField(
            label = "Waktu Selesai",
            value = state.waktuSelesai,
            error = state.waktuSelesaiError,
            onValueChange = {
                onEvent(AddJadwalMentorEvent.WaktuSelesaiChanged(it))
            },
            placeholder = "HH:MM",
            modifier = Modifier
                .width(210.dp),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )
    }
    CustomOutlinedTextFieldDropdown(
        label = "Nama Pemateri",
        value = state.namaPemateri,
        asteriskAtEnd = true,
        onValueChange = {
            onEvent(AddJadwalMentorEvent.NamaPemateriChanged(it))
        },
        placeholder = "Pilih Nama Pemateri",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        dropdownItems = namaPemateris,
        expanded = expandedNamaPemateri,
        onChangeExpanded = {
            expandedNamaPemateri = it
        },
        error = state.namaPemateriError
    )
    CustomOutlinedTextFieldDropdown(
        label = "Nama Lembaga",
        value = state.namaLembaga,
        asteriskAtEnd = true,
        onValueChange = {
            onEvent(AddJadwalMentorEvent.NamaLembagaChanged(it))
        },
        placeholder = "Pilih Nama Lembaga",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        dropdownItems = namaLembagas,
        expanded = expandedNamaLembaga,
        onChangeExpanded = {
            expandedNamaLembaga = it
        },
        error = state.namaLembagaError
    )

    CustomOutlinedTextField(
        label = "Deskripsi Agenda",
        value = state.deskripsiAgenda,
        onValueChange = {
            onEvent(AddJadwalMentorEvent.DeskripsiAgendaChanged(it))
        },
        asteriskAtEnd = true,
        error = state.deskripsiAgendaError,
        placeholder = "Isi detail acara disini",
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
        label = "Tautan Kegiatan",
        value = state.tautanKegiatan,
        error = state.tautanKegiatanError,
        onValueChange = {
            onEvent(AddJadwalMentorEvent.TautanKegiatanChanged(it))
        },
        placeholder = "Masukkan Tautan Kegiatan",
        modifier = Modifier.fillMaxWidth(),
        labelDefaultColor = ColorPalette.Monochrome400,
        labelFocusedColor = ColorPalette.PrimaryColor700,
        borderColor = ColorPalette.Outline,
        rounded = 40,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ){
        Button(
            onClick = {
                onNavigateBack()
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
                onEvent(AddJadwalMentorEvent.Submit)
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