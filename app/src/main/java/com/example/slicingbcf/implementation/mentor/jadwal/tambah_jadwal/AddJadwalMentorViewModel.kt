package com.example.slicingbcf.implementation.mentor.jadwal.tambah_jadwal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import com.example.slicingbcf.domain.validator.isBlankOrEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddJadwalMentorViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(AddJadwalMentorState())
    val uiState: StateFlow<AddJadwalMentorState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onEvent(event: AddJadwalMentorEvent){
        when(event){
            is AddJadwalMentorEvent.JudulKegiatanChanged -> {
                _uiState.update { it.copy(judulKegiatan = event.judulKegiatan) }
            }
            is AddJadwalMentorEvent.TipeKegiatanChanged -> {
                _uiState.update { it.copy(tipeKegiatan = event.tipeKegiatan) }
            }
            is AddJadwalMentorEvent.SelectedDateChanged -> {
                _uiState.update { it.copy(selectedDate = event.selectedDate) }
            }
            is AddJadwalMentorEvent.WaktuMulaiChanged -> {
                _uiState.update { it.copy(waktuMulai = event.waktuMulai) }
            }
            is AddJadwalMentorEvent.WaktuSelesaiChanged -> {
                _uiState.update { it.copy(waktuSelesai = event.waktuSelesai) }
            }
            is AddJadwalMentorEvent.NamaPemateriChanged -> {
                _uiState.update { it.copy(namaPemateri = event.namaPemateri) }
            }
            is AddJadwalMentorEvent.NamaLembagaChanged -> {
                _uiState.update { it.copy(namaLembaga = event.namaLembaga) }
            }
            is AddJadwalMentorEvent.DeskripsiAgendaChanged -> {
                _uiState.update { it.copy(deskripsiAgenda = event.deskripsiAgenda) }
            }
            is AddJadwalMentorEvent.TautanKegiatanChanged -> {
                _uiState.update { it.copy(tautanKegiatan = event.tautanKegiatan) }
            }
            is AddJadwalMentorEvent.ClearState ->{
                _uiState.update { AddJadwalMentorState() }
            }
            is AddJadwalMentorEvent.Submit -> {
                submitFeedback()
            }
        }
    }
    @Suppress("t")
    private fun validate() : Boolean {
        val state = _uiState.value

        val validationsString = listOf(
            state.judulKegiatan to "Judul kegiatan tidak boleh kosong",
            state.tipeKegiatan to "Tipe kegiatan tidak boleh kosong",
            state.selectedDate to "Tanggal kegiatan tidak boleh kosong",
            state.waktuMulai to "Waktu mulai tidak boleh kosong",
            state.waktuSelesai to "Waktu selesai tidak boleh kosong",
            state.namaPemateri to "Nama pemateri tidak boleh kosong",
            state.namaLembaga to "Nama lembaga tidak boleh kosong",
            state.deskripsiAgenda to "Deskripsi agenda tidak boleh kosong",
            state.tautanKegiatan to "Kritik dan Saran tidak boleh kosong",
        )
        val errorMapString = validationsString.mapNotNull { (field, errorMessage) ->
            if (field.isBlankOrEmpty()) errorMessage else null
        }

        _uiState.update { it ->
            it.copy(
                judulKegiatanError = errorMapString.find{it == "Judul kegiatan tidak boleh kosong"},
                tipeKegiatanError = errorMapString.find{it == "Tipe kegiatan tidak boleh kosong"},
                selectedDateError = errorMapString.find{it == "Tanggal kegiatan tidak boleh kosong"},
                waktuMulaiError = errorMapString.find{it == "Waktu mulai tidak boleh kosong"},
                waktuSelesaiError = errorMapString.find{it == "Waktu selesai tidak boleh kosong"},
                namaPemateriError = errorMapString.find{it == "Nama pemateri tidak boleh kosong"},
                namaLembagaError = errorMapString.find{it == "Nama lembaga tidak boleh kosong"},
                deskripsiAgendaError = errorMapString.find{it == "Deskripsi agenda tidak boleh kosong"},
                tautanKegiatanError = errorMapString.find{it == "Kritik dan Saran tidak boleh kosong"},
                )
        }
        return errorMapString.isEmpty()
    }

    private fun submitFeedback() {
        if (! validate()) {
            _uiState.update {
                it.copy(
                    error = "Mohon isi semua field yang wajib diisi dan dengan format yang benar",
                )
            }
            return
        }
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                Log.d("FormMiniTrainingViewModel", "submitFeedback: ${uiState.value}")
                _isLoading.value = true
            } catch (e: Exception) {
                withContext(mainDispatcher) {
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
            }
        }
    }
}