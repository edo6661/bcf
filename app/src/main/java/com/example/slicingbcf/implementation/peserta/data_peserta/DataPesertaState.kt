package com.example.slicingbcf.implementation.peserta.data_peserta


import com.example.slicingbcf.data.local.Participant
import com.example.slicingbcf.data.local.participants as ps

// Data State
data class DataPesertaState(
  val currentPage : Int = 1, // Halaman saat ini
  val itemsPerPage : Int = 5, // Jumlah item per halaman
  val totalPages : Int = 1, // Total halaman
  val totalItems : Int = ps.size, // Total item peserta
  val participants : List<Participant> = ps, // Daftar semua peserta
  val currentPageItems : List<Participant> = ps.take(5), // Daftar item di halaman saat ini
  val searchQuery : String = "" // Query pencarian
)


sealed class DataPesertaEvent {
  data class PageChange(val newPage : Int) : DataPesertaEvent()
  data class ItemsPerPageChange(val newLimit : Int) : DataPesertaEvent()
  data class Search(val query : String) : DataPesertaEvent()
}
