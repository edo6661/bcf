package com.example.slicingbcf.implementation.mentor.profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.local.Batch
import com.example.slicingbcf.data.local.Mentor
import com.example.slicingbcf.data.local.listBatch
import com.example.slicingbcf.data.local.mentor
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
class ProfilMentorViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow(
    ProfilMentorState()
  )
  val state= _state.asStateFlow()

  init {
    loadPitchdeckData()
  }

  fun onEvent(event: ProfilMentorEvent) {
    when (event) {

      is ProfilMentorEvent.Reload -> {
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
          _state.value = ProfilMentorState(
            mentors = mentor,
            batches = listBatch
          )
        }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _state.value = ProfilMentorState(
            error = e.message,
            isLoading = false
          )
        }
      }
    }
  }


}

sealed class ProfilMentorEvent {
  object Reload : ProfilMentorEvent()
}

data class ProfilMentorState (
  val mentors: Mentor = Mentor(
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    emptyList()
  ),
  val batches: List<Batch> = emptyList(),
  val isLoading: Boolean = false,
  val error: String? = null
)
