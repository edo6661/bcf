package com.example.slicingbcf.implementation.mentor.umpan_balik

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.*
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import com.example.slicingbcf.implementation.mentor.pitchdeck.PitchdeckConstant.Companion.mockUpLembarKerjaPeserta
import com.example.slicingbcf.implementation.mentor.pitchdeck.PitchdeckConstant.Companion.mockUpPitchdeckMentor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



@HiltViewModel
class UmpanBalikMentorViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow<UiState<List<Lembaga>>>(UiState.Loading)
  val state: StateFlow<UiState<List<Lembaga>>> = _state.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  init {
    loadUmpanBalik()
  }

  fun onEvent(event: UmpanBalikEvent) {
    when (event) {
      is UmpanBalikEvent.Search -> {
        _searchQuery.value = event.query
        filterData(event.query)
      }
      is UmpanBalikEvent.Reload -> {
        loadUmpanBalik()
      }
    }
  }

  private fun loadUmpanBalik() {
    viewModelScope.launch(ioDispatcher) {
      _state.value = UiState.Loading
      try {
        delay(1000)
        withContext(mainDispatcher) {
          _state.value = UiState.Success(listOfLembaga)
        }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _state.value = UiState.Error(e.localizedMessage ?: "Gagal memuat data")
        }
      }
    }
  }

  private fun filterData(query: String) {
    val filtered = listOfLembaga.filter {
      it.namaLembaga.contains(query, ignoreCase = true)
    }
    _state.value = UiState.Success(filtered)
  }
}

sealed class UmpanBalikEvent {
  data class Search(val query: String) : UmpanBalikEvent()
  object Reload : UmpanBalikEvent()
}
