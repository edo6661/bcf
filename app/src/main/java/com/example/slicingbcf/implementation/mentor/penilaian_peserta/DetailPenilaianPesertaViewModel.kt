package com.example.slicingbcf.implementation.mentor.penilaian_peserta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.local.Penilaian
import com.example.slicingbcf.data.local.PenilaianFormData
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.slicingbcf.data.local.nilaiCapaianClusters as nilaiCapaianClustersData
import com.example.slicingbcf.data.local.penilaianUmums as penilaianUmumsData

@HiltViewModel
class DetailPenilaianPesertaScreenMentorViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow(DetailPenilaianState())
  val state: StateFlow<DetailPenilaianState> = _state.asStateFlow()

  init {
    loadPenilaianData()
  }

  fun onEvent(event: DetailPenilaianEvent) {
    when (event) {
      is DetailPenilaianEvent.ToggleEdit -> toggleEdit()
      is DetailPenilaianEvent.SubmitForm -> submitForm()
      is DetailPenilaianEvent.Refresh -> loadPenilaianData()
      is DetailPenilaianEvent.PenilaianMentorClusterChanged -> updatePenilaianMentorCluster(event.penilaianMentorCluster)
      is DetailPenilaianEvent.PenilaianMentorDesainProgramChanged -> updatePenilaianMentorDesainProgram(event.penilaianMentorDesainProgram)
      is DetailPenilaianEvent.ClearState -> _state.update {
        it.copy(
          penilaianMentorCluster = PenilaianFormData("", ""),
          penilaianMentorDesainProgram = PenilaianFormData("", ""),
          loadingSubmit = false,
          isEdit = false,

        )
      }
    }
  }

  private fun toggleEdit() {
    _state.update { currentState ->
      currentState.copy(isEdit = !currentState.isEdit)
    }
  }

  private fun submitForm() {
    viewModelScope.launch(ioDispatcher) {
      try {
        _state.update { it.copy(loadingSubmit = true, error = null) }
//        delay(1000)
//        _state.update { currentState ->
//          currentState.copy(loadingSubmit = false, error = null)
//        }
      } catch (e: Exception) {
        _state.update {
          it.copy(
            loadingSubmit = false,
            error = e.message ?: "An error occurred during submission"
          )
        }
      }
    }
  }

  private fun loadPenilaianData() {
    viewModelScope.launch(ioDispatcher) {
      _state.update { it.copy(loadingData = true) }
      delay(3000)

      try {
        val penilaian = Penilaian("Lembaga ABC", 1, penilaianUmumsData.sumOf { it.penilaian })
        _state.update { currentState ->
          currentState.copy(
            loadingData = false,
            penilaian = penilaian,
            penilaianUmums = penilaianUmumsData,
            nilaiCapaianClusters = nilaiCapaianClustersData
          )
        }
      } catch (e: Exception) {
        _state.update {
          it.copy(
            loadingData = false,
            error = e.message ?: "Failed to load penilaian data"
          )
        }
      }
    }
  }

  private fun updatePenilaianMentorCluster(penilaianMentorCluster: PenilaianFormData) {
    _state.update { currentState ->
      currentState.copy(penilaianMentorCluster = penilaianMentorCluster)
    }
  }

  private fun updatePenilaianMentorDesainProgram(penilaianMentorDesainProgram: PenilaianFormData) {
    _state.update { currentState ->
      currentState.copy(penilaianMentorDesainProgram = penilaianMentorDesainProgram)
    }
  }
}
