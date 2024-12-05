package com.example.slicingbcf.implementation.peserta.data_peserta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.preferences.mockDataPesertaData
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
class DataPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
  val state: StateFlow<UiState<List<String>>> = _state.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  init {
    loadData()
  }

  fun onEvent(event: DataPesertaEvent) {
    when (event) {
      is DataPesertaEvent.Search -> {
        _searchQuery.value = event.query
        filterData(event.query)
      }
      is DataPesertaEvent.Reload -> {
        loadData()
      }
    }
  }

  private fun loadData() {
    viewModelScope.launch(ioDispatcher) {
      _state.value = UiState.Loading
      try {
        delay(1000)
        val data = mockDataPesertaData
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

  private fun filterData(query: String) {
    viewModelScope.launch(ioDispatcher) {
      val originalData = mockDataPesertaData
      val filteredData = if (query.isEmpty()) {
        originalData
      } else {
        originalData.filter { it.contains(query, ignoreCase = true) }
      }
      _state.value = UiState.Success(filteredData)
    }
  }
}

sealed class DataPesertaEvent {
  data class Search(val query: String) : DataPesertaEvent()
  object Reload : DataPesertaEvent()
}
