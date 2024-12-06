package com.example.slicingbcf.implementation.mentor.laporan

import com.example.slicingbcf.data.local.FokusIsu
import com.example.slicingbcf.data.local.Lembaga

data class LaporanPenilaianPesertaState(
  val listOfLembaga: List<Lembaga> = emptyList(),
  val listFokusIsu: List<FokusIsu> = emptyList(),
  val isLoading: Boolean = false,
  val error: String? = null,
  val query: String = "",
  val tab: Int = 0
)

sealed class LaporanPenilaianPesertaEvent{
  data class OnChangeLoading(val isLoading: Boolean) : LaporanPenilaianPesertaEvent()
  data class OnChangeError(val error: String) : LaporanPenilaianPesertaEvent()
  data class OnChangeSearch(val query: String) : LaporanPenilaianPesertaEvent()
  data class OnChangeTab(val tab: Int) : LaporanPenilaianPesertaEvent()
  object Reload : LaporanPenilaianPesertaEvent()
}