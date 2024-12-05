package com.example.slicingbcf.implementation.mentor.data_peserta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil
import com.example.slicingbcf.data.local.participants as ps

@HiltViewModel
class DataPesertaMentorViewModel @Inject constructor() : ViewModel() {

  private val _uiState =
    MutableStateFlow(DataPesertaState())
  val uiState = _uiState.asStateFlow()

  init {
    refreshPageData()
  }

  private fun refreshPageData() {
    updateTotalPages(_uiState.value.itemsPerPage)
    updateCurrentPageItems()
  }

  private fun updateTotalPages(itemsPerPage : Int) {
    val totalPages =
      ceil(_uiState.value.totalItems.toFloat() / itemsPerPage).toInt()
    _uiState.update { it.copy(totalPages = totalPages) }
  }

  private fun updateCurrentPageItems() {
    val startIndex =
      (_uiState.value.currentPage - 1) * _uiState.value.itemsPerPage
    val endIndex = minOf(
      startIndex + _uiState.value.itemsPerPage,
      _uiState.value.totalItems
    )

    val currentItems = _uiState.value.participants.subList(
      startIndex,
      endIndex
    )
    _uiState.update { it.copy(currentPageItems = currentItems) }
  }

  private fun filterParticipants() {
    val filteredParticipants = if (_uiState.value.searchQuery.isEmpty()) {
      ps
    } else {
      ps.filter {
        it.namaLembaga.contains(
          _uiState.value.searchQuery,
          ignoreCase = true
        )
      }
    }

    _uiState.update {
      it.copy(
        totalItems = filteredParticipants.size,
        participants = filteredParticipants,
        currentPageItems = filteredParticipants.take(_uiState.value.itemsPerPage)
      )
    }
    refreshPageData()
  }

  fun onEvent(event : DataPesertaEvent) {
    viewModelScope.launch {
      when (event) {
        is DataPesertaEvent.PageChange         -> {
          _uiState.update {
            it.copy(
              currentPage = event.newPage.coerceIn(
                1,
                _uiState.value.totalPages
              )
            )
          }
          updateCurrentPageItems()
        }

        is DataPesertaEvent.ItemsPerPageChange -> {
          _uiState.update {
            it.copy(
              itemsPerPage = event.newLimit,
              currentPage = 1
            )
          }
          refreshPageData()
        }

        is DataPesertaEvent.Search             -> {
          _uiState.update {
            it.copy(
              searchQuery = event.query,
              currentPage = 1
            )
          }
          filterParticipants()
        }
      }
    }
  }
}
