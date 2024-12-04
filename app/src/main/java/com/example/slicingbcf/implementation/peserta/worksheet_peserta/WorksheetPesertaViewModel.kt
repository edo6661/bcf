package com.example.slicingbcf.implementation.peserta.worksheet_peserta


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.WorksheetPeserta
import com.example.slicingbcf.data.local.worksheetsPeserta
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
class WorksheetPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow<UiState<List<WorksheetPeserta>>>(UiState.Loading)
  val state: StateFlow<UiState<List<WorksheetPeserta>>> = _state.asStateFlow()

  init {
    loadData()
  }

  fun onEvent(event: WorksheetPesertaEvent) {
    when (event) {
      is WorksheetPesertaEvent.ReloadData -> {
        loadData()
      }
    }
  }

  private fun loadData() {
    viewModelScope.launch(ioDispatcher) {
      _state.value = UiState.Loading
      try {
        delay(1500)


        val data = worksheetsPeserta
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
}

sealed class WorksheetPesertaEvent {
  object ReloadData : WorksheetPesertaEvent()
}
