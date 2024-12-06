package com.example.slicingbcf.implementation.peserta.pitch_deck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.PitchDeck
import com.example.slicingbcf.data.local.pitchDeck
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import com.example.slicingbcf.domain.validator.isBlankOrEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PitchDeckPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher : CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher : CoroutineDispatcher
) : ViewModel() {

  private val _uiState = MutableStateFlow(PitchDeckState())
  val uiState : StateFlow<PitchDeckState> = _uiState.asStateFlow()

  private val _state = MutableStateFlow<UiState<List<PitchDeck>>>(UiState.Loading)
  val state : StateFlow<UiState<List<PitchDeck>>> = _state.asStateFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading : StateFlow<Boolean> = _isLoading

  init {
    loadData()
  }

  fun onEvent(event : PitchDeckEvent) {
    when (event) {
      is PitchDeckEvent.TautanKegiatanChanged -> {
        _uiState.update { it.copy(tautanKegiatan = event.tautanKegiatan) }
      }

      is PitchDeckEvent.ClearState            -> {
        _uiState.update { PitchDeckState() }
      }

      is PitchDeckEvent.Submit                -> {
        submitFeedback()
      }

      is PitchDeckEvent.ReloadData            -> {
        loadData()
      }

    }
  }

  private fun loadData() {
    viewModelScope.launch(ioDispatcher) {
      _state.value = UiState.Loading
      try {
        delay(1500)
        val data = pitchDeck
        withContext(mainDispatcher) {
          _state.value = UiState.Success(data)
        }
      } catch (e : Exception) {
        withContext(mainDispatcher) {
          _state.value = UiState.Error(e.localizedMessage ?: "Unknown Error")
        }
      }
    }
  }

  @Suppress("t")
  private fun validate() : Boolean {
    val state = _uiState.value
    val validationsString = listOf(
      state.tautanKegiatan to "Tautan Kegiatan tidak boleh kosong"
    )
    val errorMapString = validationsString.mapNotNull { (field, errorMessage) ->
      if (field.isBlankOrEmpty()) errorMessage else null
    }
    _uiState.update { it ->
      it.copy(
        tautanKegiatanError = errorMapString.find { it == "Tautan Kegiatan tidak boleh kosong" },
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
    _uiState.update { it.copy(isLoading = true, error = null) }
    viewModelScope.launch(ioDispatcher) {
      try {
        Log.d("PitchDeckViewModel", "submitFeedback: ${uiState.value}")
      } catch (e : Exception) {
        withContext(mainDispatcher) {
          _uiState.update { it.copy(isLoading = false, error = e.message) }
        }
      }
    }
  }

}
