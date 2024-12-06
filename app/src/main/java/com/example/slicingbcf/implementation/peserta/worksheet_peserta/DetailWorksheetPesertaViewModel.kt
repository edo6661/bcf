package com.example.slicingbcf.implementation.peserta.worksheet_peserta

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.local.DetailWorksheetPeserta
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DetailWorksheetPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow(
    DetailWorksheetPesertaState()
  )
  val state= _state.asStateFlow()

  init {
    loadPitchdeckData()
  }

  fun onEvent(event: DetailWorksheetPesertaEvent) {
    when (event) {

      is DetailWorksheetPesertaEvent.Reload -> {
        loadPitchdeckData()
      }
      is DetailWorksheetPesertaEvent.OnChangeTautanLembarKerjaPeserta -> {
        _state.update {
          it.copy(tautanLembarKerjaPeserta = event.tautanLembarKerjaPeserta)
        }
      }
      is DetailWorksheetPesertaEvent.OnChangeSubmitLoading -> {
        _state.update {
          it.copy(
            submitLoading = event.isLoading
          )
        }
      }
      is DetailWorksheetPesertaEvent.Submit -> {
        _state.update {
          it.copy(submitLoading = true)
        }
        Log.d("DetailWorksheetPesertaViewModel", "Submit: ${_state.value.tautanLembarKerjaPeserta}")
      }
    }
  }

  private fun loadPitchdeckData() {
    _state.update {
      it.copy(isLoading = true)
    }
    viewModelScope.launch(ioDispatcher) {
      try {
        delay(1500)


        withContext(mainDispatcher) {
          _state.value = DetailWorksheetPesertaState(
            worksheet = DetailWorksheetPeserta(
              judulLembarKerja = "[Capacity Building] Hari Ke-4 Lembar Kerja - Topik: Sustainability and Sustainable Development Kerja",
              deskripsiLembarKerja = "Lembar kerja ini akan membahas seputar media sosial, sasaran, persona , dan strategi yang dapat diaplikasikan dalam melakukan pemasaran sosial program.",
              batasSubmisi = "Selasa, 2 April 2024 13.55 WIB",
              tautanLembarKerja = "bit.ly/LembarKerjaMiniTrainingDay5"
            )
          )
        }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _state.value = DetailWorksheetPesertaState(
            error = e.message,
            isLoading = false
          )
        }
      }
    }
  }


}

sealed class DetailWorksheetPesertaEvent {
  data class OnChangeTautanLembarKerjaPeserta(val tautanLembarKerjaPeserta: String) : DetailWorksheetPesertaEvent()
  data class OnChangeSubmitLoading(val isLoading: Boolean) : DetailWorksheetPesertaEvent()
  object Reload : DetailWorksheetPesertaEvent()
  object Submit : DetailWorksheetPesertaEvent()
}

data class DetailWorksheetPesertaState (
  val worksheet: DetailWorksheetPeserta = DetailWorksheetPeserta("","","",""),
  val tautanLembarKerjaPeserta: String = "",
  val isLoading: Boolean = false,
  val submitLoading: Boolean = false,
  val error: String? = null
)
