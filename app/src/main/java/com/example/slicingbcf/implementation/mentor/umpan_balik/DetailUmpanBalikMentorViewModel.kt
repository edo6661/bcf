package com.example.slicingbcf.implementation.mentor.umpan_balik

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.local.*
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
class DetailUmpanBalikMentorViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow(
    DetailUmpanBalikMentorState()
  )
  val state= _state.asStateFlow()

  init {
    loadPitchdeckData()
  }

  fun onEvent(event: DetailUmpanBalikMentorEvent) {
    when (event) {

      is DetailUmpanBalikMentorEvent.Reload -> {
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
          _state.value = DetailUmpanBalikMentorState(
            batch = listBatch[0],
            lembaga = listOfLembaga[0],
            jadwalMentorings = jadwalMentoring,
            evaluasiMentoringMentor = evaluasiMentoringMentor
          )
        }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _state.value = DetailUmpanBalikMentorState(
            error = e.message,
            isLoading = false
          )
        }
      }
    }
  }


}

sealed class DetailUmpanBalikMentorEvent {
  object Reload : DetailUmpanBalikMentorEvent()
}

data class DetailUmpanBalikMentorState (
  val lembaga: Lembaga = Lembaga("","","",""),
  val batch: Batch = Batch("","","","", emptyList()),
  val jadwalMentorings : List<JadwalMentoring> = emptyList(),
  val evaluasiMentoringMentor: List<EvaluasiMentoring> = emptyList(),


  val isLoading: Boolean = false,
  val error: String? = null
)
