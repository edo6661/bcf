package com.example.slicingbcf.implementation.mentor.feedback_peserta


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
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
class FeedbackPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _uiState = MutableStateFlow(FeedbackPesertaState())
  val uiState: StateFlow<FeedbackPesertaState> = _uiState.asStateFlow()


  private val _currentScreen = MutableStateFlow(0)
  val currentScreen: StateFlow<Int> = _currentScreen.asStateFlow()


  fun onEvent(event: FeedbackPesertaEvent) {
    when (event) {
      is FeedbackPesertaEvent.NamaLembagaChanged -> {
        _uiState.update { it.copy(namaLembaga = event.namaLembaga) }
      }
      is FeedbackPesertaEvent.ChangeScreen -> {
        _currentScreen.value = event.screen
      }
      is FeedbackPesertaEvent.PeriodeCapaianMentoringChanged -> {
        _uiState.update { it.copy(periodeCapaianMentoring = event.periodeCapaianMentoring) }
      }
      is FeedbackPesertaEvent.EvaluasiCapaianMentoringChanged -> {
        _uiState.update {
          it.copy(
            evaluasiCapaianMentoring = it.evaluasiCapaianMentoring.mapIndexed { index, evaluasiKepuasan ->
              if (index == event.index) event.evaluasiKepuasan else evaluasiKepuasan
            }
          )
        }
      }
      is FeedbackPesertaEvent.EvaluasiLembagaChanged -> {
        _uiState.update { it.copy(evaluasiLembaga = event.evaluasiLembaga) }
      }
      is FeedbackPesertaEvent.EvaluasiKepuasanChanged -> {
        _uiState.update { it.copy(evaluasiKepuasan = event.evaluasiKepuasan) }
      }
      is FeedbackPesertaEvent.HalHalYangDibahasChanged -> {
        _uiState.update { it.copy(halhalYangDibahas = event.halHalYangDibahas) }
      }
      is FeedbackPesertaEvent.TantanganUtamaChanged -> {
        _uiState.update { it.copy(tantanganUtama = event.tantanganUtama) }
      }
      is FeedbackPesertaEvent.DokumentasiSesiMentoringClusterChanged -> {
        _uiState.update { it.copy(dokumentasiSesiMentoringCluster = event.uri) }
      }
      is FeedbackPesertaEvent.ClearState ->{
        _uiState.update { FeedbackPesertaState() }
      }
      is FeedbackPesertaEvent.Submit -> {
        submitFeedback()
      }
    }
  }

  private fun submitFeedback() {
    viewModelScope.launch(ioDispatcher) {
      _uiState.update { it.copy(isLoading = true, error = null) }
      try {
        Log.d("FeedbackPesertaViewModel", "submitFeedback: ${uiState.value}")
//        delay(1000)
//        _uiState.update { it.copy(isLoading = false, isSuccess = true) }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _uiState.update { it.copy(isLoading = false, error = e.message) }
        }
      }
    }
  }
}
