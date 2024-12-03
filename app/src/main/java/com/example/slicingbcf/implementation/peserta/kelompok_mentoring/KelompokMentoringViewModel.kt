package com.example.slicingbcf.implementation.peserta.kelompok_mentoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.KelompokMentoring
import com.example.slicingbcf.data.local.kelompoksMentoring
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
class KelompokMentoringViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow<UiState<List<KelompokMentoring>>>(UiState.Loading)
  val state: StateFlow<UiState<List<KelompokMentoring>>> = _state.asStateFlow()

  private val _currentTabIndex = MutableStateFlow(0)
  val currentTabIndex: StateFlow<Int> = _currentTabIndex.asStateFlow()

  private val _currentMentor = MutableStateFlow<Mentor?>(null)
  val currentMentor: StateFlow<Mentor?> = _currentMentor.asStateFlow()

  init {
    loadData()
    updateMentor(0)
  }

  fun onEvent(event: KelompokMentoringEvent) {
    when (event) {
      is KelompokMentoringEvent.TabChanged -> {
        _currentTabIndex.value = event.tabIndex
        updateMentor(event.tabIndex)
      }
      is KelompokMentoringEvent.ReloadData -> {
        loadData()
      }
      else -> Unit
    }
  }

  private fun loadData() {
    viewModelScope.launch(ioDispatcher) {
      _state.value = UiState.Loading
      try {
        delay(1500)

        val data = kelompoksMentoring
        withContext(mainDispatcher) {
          _state.value = UiState.Success(data)
        }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _state.value = UiState.Error(e.localizedMessage ?: "Unknown Error")
        }
      }
    }
  }

  private fun updateMentor(tabIndex: Int) {
    val mentor = when (tabIndex) {
      0 -> Mentor(
        name = "Dody Supriyadi",
        role = "Mentor Cluster • Kesehatan",
        expertise = "Tuberculosis (TBC) • Stunting • HIV/AIDS"
      )
      1 -> Mentor(
        name = "Rini Wijayanti",
        role = "Mentor Desain Program • Pendidikan",
        expertise = "Pengajaran Anak Usia Dini • Literasi Dasar"
      )
      else -> null
    }
    _currentMentor.value = mentor
  }
}

data class Mentor(
  val name: String,
  val role: String,
  val expertise: String
)
