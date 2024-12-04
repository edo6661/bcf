package com.example.slicingbcf.implementation.mentor.worksheet_mentor


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.LembarKerjaPeserta
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DetailWorksheetMentorViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher : CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher : CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow<UiState<WorksheetMentorDetail>>(UiState.Loading)
  val state : StateFlow<UiState<WorksheetMentorDetail>> = _state.asStateFlow()

  init {
    loadDetailData()
  }

  fun onEvent(event : DetailWorksheetMentorEvent) {
    when (event) {
      is DetailWorksheetMentorEvent.ReloadData -> {
        loadDetailData()
      }
    }
  }

  private fun loadDetailData() {
    viewModelScope.launch(ioDispatcher) {
      _state.value = UiState.Loading
      try {
        delay(1500)

        val data = WorksheetMentorDetail(
          judulLembarKerja = "[Capacity Building] Hari Ke-4 Lembar Kerja - Topik: Sustainability and Sustainable Development Kerja",
          deskripsiLembarkKerja = "Lembar kerja ini akan membahas seputar media sosial, sasaran, persona , dan strategi yang dapat diaplikasikan dalam melakukan pemasaran sosial program.",
          tautanLembarKerja = "bit.ly/LembarKerjaMiniTrainingDay5",
          batasSubmisi = "Selasa, 2 April 2024 13.55 WIB",
          submisiPeserta = listOf(
            LembarKerjaPeserta("Asep", "Senin, 1 April 2024 13.55 WIB"),
            LembarKerjaPeserta("Budi", "Selasa, 2 April 2024 13.55 WIB"),
            LembarKerjaPeserta("Cecep", "Rabu, 3 April 2024 13.55 WIB"),
          )
        )
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
}

sealed class DetailWorksheetMentorEvent {
  object ReloadData : DetailWorksheetMentorEvent()
}

data class WorksheetMentorDetail(
  val judulLembarKerja : String,
  val tautanLembarKerja : String,
  val deskripsiLembarkKerja : String,
  val batasSubmisi : String,
  val submisiPeserta : List<LembarKerjaPeserta>
)
