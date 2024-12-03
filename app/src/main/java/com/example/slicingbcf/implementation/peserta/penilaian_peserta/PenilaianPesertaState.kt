package com.example.slicingbcf.implementation.peserta.penilaian_peserta

import com.example.slicingbcf.data.local.Penilaian
import com.example.slicingbcf.data.local.PenilaianUmum
import com.example.slicingbcf.data.local.nilaiCapaianClusters as nilaiCapaianClustersData
import com.example.slicingbcf.data.local.penilaianUmums as penilaianUmumsData

data class PenilaianPesertaState(
  val penilaian: Penilaian = Penilaian("Lembaga A", 1, 100),
  val penilaianUmums: List<PenilaianUmum> = penilaianUmumsData,
  val nilaiCapaianClusters: List<PenilaianUmum> = nilaiCapaianClustersData,
  val loading: Boolean = false,
  val error: String? = null
)

sealed class PenilaianPesertaEvent {
  object LoadPenilaian : PenilaianPesertaEvent()
  object Refresh : PenilaianPesertaEvent()
}
