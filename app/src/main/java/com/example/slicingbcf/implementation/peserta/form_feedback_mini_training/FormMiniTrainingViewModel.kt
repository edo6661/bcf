package com.example.slicingbcf.implementation.peserta.form_feedback_mini_training

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
class FormMiniTrainingViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormMiniTrainingState())
    val uiState: StateFlow<FormMiniTrainingState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onEvent(event: FormMiniTrainingEvent){
        when(event){
            is FormMiniTrainingEvent.HariKegiatanChanged -> {
                _uiState.update { it.copy(hariKegiatan = event.hariKegiatan) }
            }
            is FormMiniTrainingEvent.Speaker1NameChanged -> {
                _uiState.update { it.copy(speaker1Name = event.speaker1Name) }
            }
            is FormMiniTrainingEvent.Speaker2NameChanged -> {
                _uiState.update { it.copy(speaker2Name = event.speaker2Name) }
            }
            is FormMiniTrainingEvent.SelectedDateChanged -> {
                _uiState.update { it.copy(selectedDate = event.selectedDate) }
            }
            is FormMiniTrainingEvent.ratingMateriChanged -> {
                _uiState.update {
                    it.copy(
                        ratingMateri = it.ratingMateri.mapIndexed { index, evaluasiMateri ->
                            if (index == event.index) event.evaluasiMateri else evaluasiMateri
                        }
                    )
                }
            }
            is FormMiniTrainingEvent.ratingWaktuChanged -> {
                _uiState.update {
                    it.copy(
                        ratingWaktu = it.ratingWaktu.mapIndexed { index, evaluasiWaktu ->
                            if (index == event.index) event.evaluasiWaktu else evaluasiWaktu
                        }
                    )
                }
            }
            is FormMiniTrainingEvent.ratingJawabanChanged -> {
                _uiState.update {
                    it.copy(
                        ratingJawaban = it.ratingJawaban.mapIndexed { index, evaluasiJawaban ->
                            if (index == event.index) event.evaluasiJawaban else evaluasiJawaban
                        }
                    )
                }
            }
            is FormMiniTrainingEvent.ratingMetodeChanged -> {
                _uiState.update {
                    it.copy(
                        ratingMetode = it.ratingMetode.mapIndexed { index, evaluasiMetode ->
                            if (index == event.index) event.evaluasiMetode else evaluasiMetode
                        }
                    )
                }
            }
            is FormMiniTrainingEvent.KritikSaranChanged -> {
                _uiState.update { it.copy(kritikSaran = event.kritikSaran) }
            }
            is FormMiniTrainingEvent.ClearState ->{
                _uiState.update { FormMiniTrainingState() }
            }
            is FormMiniTrainingEvent.Submit -> {
                submitFeedback()
            }
        }
    }

    @Suppress("t")
    private fun validate() : Boolean {
        val state = _uiState.value

        val validationsString = listOf(
            state.selectedDate to "Hari Kegiatan tidak boleh kosong",
            state.hariKegiatan to "Tanggal Kegiatan tidak boleh kosong",
            state.speaker1Name to "Nama Pemateri tidak boleh kosong",
            state.speaker2Name to "Nama Pemateri tidak boleh kosong",
            state.kritikSaran to "Kritik dan Saran tidak boleh kosong",
        )
        val errorMapString = validationsString.mapNotNull { (field, errorMessage) ->
            if (field.isBlankOrEmpty()) errorMessage else null
        }

        val validationsArray = listOf(
            state.ratingMateri to "Rating tidak boleh kosong",
            state.ratingWaktu to "Rating tidak boleh kosong",
            state.ratingJawaban to "Rating tidak boleh kosong",
            state.ratingMetode to "Rating tidak boleh kosong",
        )
        val errorMapArray = validationsArray.mapNotNull { (field, errorMessage) ->
            if (field.isEmpty()) errorMessage else null
        }

        val allErrorsMap = errorMapString + errorMapArray


        _uiState.update { it ->
            it.copy(
                selectedDateError = errorMapString.find{it == "Hari Kegiatan tidak boleh kosong"},
                hariKegiatanError = errorMapString.find{it == "Tanggal Kegiatan tidak boleh kosong"},
                speaker1NameError = errorMapString.find{it == "Nama Pemateri tidak boleh kosong"},
                speaker2NameError = errorMapString.find{it == "Nama Pemateri tidak boleh kosong"},
                ratingMateriError = errorMapArray.find{it == "Rating tidak boleh kosong"},
                ratingWaktuError = errorMapArray.find{it == "Rating tidak boleh kosong"},
                ratingJawabanError = errorMapArray.find{it == "Rating tidak boleh kosong"},
                ratingMetodeError = errorMapArray.find{it == "Rating tidak boleh kosong"},
                kritikSaranError = errorMapString.find{it == "Kritik dan Saran tidak boleh kosong"},

            )
        }
        return allErrorsMap.isEmpty()
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