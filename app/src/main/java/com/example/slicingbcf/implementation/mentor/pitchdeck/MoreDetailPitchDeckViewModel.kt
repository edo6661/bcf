package com.example.slicingbcf.implementation.mentor.pitchdeck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.local.LembarKerjaPeserta
import com.example.slicingbcf.data.local.WorksheetPeserta
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import com.example.slicingbcf.implementation.mentor.pitchdeck.PitchdeckConstant.Companion.mockUpLembarKerjaPeserta
import com.example.slicingbcf.implementation.mentor.pitchdeck.PitchdeckConstant.Companion.mockUpPitchdeckMentor
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
class MoreDetailPitchDeckViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow(
    MoreDetailPitchDeckState()
  )
  val state= _state.asStateFlow()

  init {
    loadPitchdeckData()
  }

  fun onEvent(event: MoreDetailPitchDeckEvent) {
    when (event) {

      is MoreDetailPitchDeckEvent.Reload -> {
        loadPitchdeckData()
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
          _state.value = MoreDetailPitchDeckState(
            pitchDecks = mockUpPitchdeckMentor,
            lembarKerjas = mockUpLembarKerjaPeserta
          )
        }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _state.value = MoreDetailPitchDeckState(
            error = e.message,
            isLoading = false
          )
        }
      }
    }
  }


}

sealed class MoreDetailPitchDeckEvent {
  object Reload : MoreDetailPitchDeckEvent()
}

data class MoreDetailPitchDeckState (
  val pitchDecks: List<WorksheetPeserta> = emptyList(),
  val lembarKerjas: List<LembarKerjaPeserta> = emptyList(),
  val isLoading: Boolean = false,
  val error: String? = null
)
