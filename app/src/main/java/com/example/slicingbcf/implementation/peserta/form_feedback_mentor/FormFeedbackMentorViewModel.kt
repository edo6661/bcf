package com.example.slicingbcf.implementation.peserta.form_feedback_mentor

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
class FormFeedbackMentorViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormFeedbackMentorState())
    val uiState: StateFlow<FormFeedbackMentorState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onEvent(event: FormFeedbackMentorEvent) {
        when (event) {
            is FormFeedbackMentorEvent.SelectedEvaluasiChanged -> {
                _uiState.update { it.copy(selectedEvaluasi = event.selectedEvaluasi) }
            }
            is FormFeedbackMentorEvent.NamaMentorChanged -> {
                _uiState.update { it.copy(namaMentor = event.namaMentor) }
            }
            is FormFeedbackMentorEvent.IssueSharingRatingChanged ->{
                _uiState.update {
                    it.copy(
                        evaluasiCapaian = it.evaluasiCapaian.mapIndexed { index, evaluasiCapaian ->
                            if (index == event.index) event.evaluasiCapaian else evaluasiCapaian
                        }
                    )
                }
            }
            is FormFeedbackMentorEvent.SelectedPeriodeChanged -> {
                _uiState.update { it.copy(selectedPeriode = event.selectedPeriode) }
            }
            is FormFeedbackMentorEvent.EvaluasiMentorChanged ->{
                _uiState.update {
                    it.copy(
                        evaluasiMentor = it.evaluasiMentor.mapIndexed { index, evaluasiMentor ->
                            if (index == event.index) event.evaluasiMentor else evaluasiMentor
                        }
                    )
                }
            }
            is FormFeedbackMentorEvent.DiscussionTextChanged -> {
                _uiState.update { it.copy(discussionText = event.discussionText) }
            }
            is FormFeedbackMentorEvent.SuggestionTextChanged -> {
                _uiState.update { it.copy(suggestionText = event.suggestionText) }
            }
            is FormFeedbackMentorEvent.SelectedFileUriChanged -> {
                _uiState.update { it.copy(selectedFileUri = event.selectedFileUri)
                }
            }
            is FormFeedbackMentorEvent.Submit -> {
                submitFeedback()
            }
        }
    }

    @Suppress("t")
    private fun validate() : Boolean {
        val state = _uiState.value

        val validationsString = listOf(
            state.selectedEvaluasi to "Evaluasi Capaian tidak boleh kosong",
            state.namaMentor to "Nama Mentor tidak boleh kosong",
            state.selectedPeriode to "Periode tidak boleh kosong",
            state.discussionText to "Pembahasan tidak boleh kosong",
            state.suggestionText to "Kritik dan Saran tidak boleh kosong",
        )
        val errorMapString = validationsString.mapNotNull { (field, errorMessage) ->
            if (field.isBlankOrEmpty()) errorMessage else null
        }

        val validationsArray = listOf(
            state.evaluasiCapaian to "Rating tidak boleh kosong",
            state.evaluasiMentor to "Rating tidak boleh kosong",
        )
        val errorMapArray = validationsArray.mapNotNull { (field, errorMessage) ->
            if (field.isEmpty()) errorMessage else null
        }

        val validationsUri = listOf(
            state.selectedFileUri to "Dokumentasi tidak boleh kosong",
            )

        val errorMapUri = validationsUri.mapNotNull { (field, errorMessage) ->
            if (field == null) errorMessage else null
        }

        val allErrorsMap = errorMapString + errorMapArray + errorMapUri

        _uiState.update { it ->
            it.copy(
                selectedEvaluasiError = errorMapString.find{it == "Evaluasi Capaian tidak boleh kosong"},
                namaMentorError = errorMapString.find{it == "Nama Mentor tidak boleh kosong"},
                selectedPeriodeError = errorMapString.find{it == "Periode tidak boleh kosong"},
                evaluasiCapaianError = errorMapArray.find{it == "Rating tidak boleh kosong"},
                evaluasiMentorError = errorMapArray.find{it == "Rating tidak boleh kosong"},
                discussionTextError = errorMapString.find{it == "Pembahasan tidak boleh kosong"},
                suggestionTextError = errorMapString.find{it == "Kritik dan Saran tidak boleh kosong"},
                selectedFileUriError = errorMapUri.find { it == "Dokumentasi tidak boleh kosong" },
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