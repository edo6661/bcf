package com.example.slicingbcf.implementation.peserta.data_peserta

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
class DataPesertaViewModel @Inject constructor() : ViewModel() {

  private val _uiState =
    MutableStateFlow(DataPesertaState())
  val uiState = _uiState.asStateFlow()

  init {
    refreshPageData() // Memanggil fungsi untuk menghitung total halaman dan mengisi item halaman saat ini
  }

  private fun refreshPageData() {
    updateTotalPages(_uiState.value.itemsPerPage) // Menghitung total halaman berdasarkan itemsPerPage
    updateCurrentPageItems() // Mengisi item sesuai halaman saat ini
  }

  private fun updateTotalPages(itemsPerPage : Int) {
    // totalItems dibagi itemsPerPage dan dibulatkan ke atas : 21 / 5 = 5, akan ada 5 page
    val totalPages =
      ceil(_uiState.value.totalItems.toFloat() / itemsPerPage).toInt() // Menghitung jumlah total halaman
    _uiState.update { it.copy(totalPages = totalPages) } // Memperbarui total halaman di UI state
  }

  private fun updateCurrentPageItems() {
    // Menghitung indeks awal dan akhir item untuk halaman saat ini agar sesuai dengan itemsPerPage
    // contoh: startIndex = (2 - 1) * 5 = 1 * 5 = 5, di page 2, item dimulai dari index 5
    val startIndex =
      (_uiState.value.currentPage - 1) * _uiState.value.itemsPerPage // Indeks awal halaman saat ini
    // endIndex = minOf(5 + 5, 15) = minOf(10, 15) = 10, di page 2, item berakhir di index 10
    val endIndex = minOf(
      startIndex + _uiState.value.itemsPerPage,
      _uiState.value.totalItems
    ) // Indeks akhir halaman saat ini

    // Mengambil sublist dari peserta berdasarkan startIndex dan endIndex
    // startIndex =5, endIndex =10, maka item yang diambil adalah item dari index 5 sampai 9 (5 item terakhir).
    val currentItems = _uiState.value.participants.subList(
      startIndex,
      endIndex
    )
    // Mengambil item untuk halaman saat ini
    // contoh: di page 2, item yang diambil adalah item dari index 5 sampai 9 (5 item terakhir).
    _uiState.update { it.copy(currentPageItems = currentItems) } // Memperbarui item halaman saat ini di UI state
  }

  private fun filterParticipants() {
    val filteredParticipants = if (_uiState.value.searchQuery.isEmpty()) {
      ps // Jika query kosong, tampilkan semua peserta
    } else {
      ps.filter {
        it.namaLembaga.contains(
          _uiState.value.searchQuery,
          ignoreCase = true
        )
      } // Filter data berdasarkan query
    }

    _uiState.update {
      it.copy(
        totalItems = filteredParticipants.size, // Memperbarui total item hasil filter
        participants = filteredParticipants, // Memperbarui daftar peserta
        currentPageItems = filteredParticipants.take(_uiState.value.itemsPerPage) // Mengambil item sesuai jumlah per halaman
      )
    }
    refreshPageData() // Perbarui total halaman dan item halaman saat ini
  }

  fun onEvent(event : DataPesertaEvent) {
    viewModelScope.launch {
      when (event) {
        is DataPesertaEvent.PageChange         -> {
          _uiState.update {
            it.copy(
              // ! coerceIn = mastiin nilai berada dalam rentang tertentu (min, max).
              currentPage = event.newPage.coerceIn(
                1,
                _uiState.value.totalPages
              )
            ) // Ubah halaman saat ini dalam rentang total halaman
          }
          updateCurrentPageItems() // Perbarui item untuk halaman baru
        }

        is DataPesertaEvent.ItemsPerPageChange -> {
          _uiState.update {
            it.copy(
              itemsPerPage = event.newLimit,
              currentPage = 1
            )
          } // Atur ulang jumlah item per halaman dan halaman ke 1
          refreshPageData() // Perbarui total halaman dan item halaman saat ini
        }

        is DataPesertaEvent.Search             -> {
          _uiState.update {
            it.copy(
              searchQuery = event.query,
              currentPage = 1
            )
          } // Set query dan reset ke halaman pertama
          filterParticipants() // Filter data berdasarkan query
        }
      }
    }
  }
}
