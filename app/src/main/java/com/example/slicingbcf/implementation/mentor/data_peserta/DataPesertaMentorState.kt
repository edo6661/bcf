package com.example.slicingbcf.implementation.mentor.data_peserta


import com.example.slicingbcf.data.local.Participant
import com.example.slicingbcf.data.local.participants as ps

// Data State
data class DataPesertaState(
  val currentPage : Int = 1,
  val itemsPerPage : Int = 5,
  val totalPages : Int = 1,
  val totalItems : Int = ps.size,
  val participants : List<Participant> = ps,
  val currentPageItems : List<Participant> = ps.take(5),
  val searchQuery : String = ""
)


sealed class DataPesertaEvent {
  data class PageChange(val newPage : Int) : DataPesertaEvent()
  data class ItemsPerPageChange(val newLimit : Int) : DataPesertaEvent()
  data class Search(val query : String) : DataPesertaEvent()
}
