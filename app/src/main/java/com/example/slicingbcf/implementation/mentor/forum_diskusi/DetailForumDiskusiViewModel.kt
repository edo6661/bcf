package com.example.slicingbcf.implementation.mentor.forum_diskusi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.local.DataPusatInformasi
import com.example.slicingbcf.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailForumDiskusiViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher : CoroutineDispatcher,
) : ViewModel() {

  private val _state = MutableStateFlow(ForumDiskusiState())
  val state : StateFlow<ForumDiskusiState> = _state.asStateFlow()

  fun onEvent(event : ForumDiskusiEvent) {
    when (event) {
      is ForumDiskusiEvent.PertanyaanChanged -> {
        _state.update {
          it.copy(pertanyaan = event.pertanyaan)
        }
      }

      is ForumDiskusiEvent.AddPertanyaan     -> {
        addPertanyaan(event.newPertanyaan)

      }

      is ForumDiskusiEvent.ClearState        -> {
        _state.update {
          it.copy(pertanyaan = "", isLoading = false)
        }
      }


    }
  }

  private fun addPertanyaan(newPertanyaan : DataPusatInformasi) {
    viewModelScope.launch(ioDispatcher) {
      _state.value = _state.value.copy(isLoading = true)
      val updatedList = _state.value.dataList + newPertanyaan
      _state.value = _state.value.copy(
        dataList = updatedList.sortedByDescending { it.timestamp }
      )
    }
  }
}