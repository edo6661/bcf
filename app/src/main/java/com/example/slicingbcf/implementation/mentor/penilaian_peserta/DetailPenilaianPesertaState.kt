package com.example.slicingbcf.implementation.mentor.penilaian_peserta

import com.example.slicingbcf.data.local.Penilaian
import com.example.slicingbcf.data.local.PenilaianFormData
import com.example.slicingbcf.data.local.PenilaianUmum

data class DetailPenilaianState(
  val isEdit: Boolean = false,
  val loadingData: Boolean = false,
  val loadingSubmit: Boolean = false,
  val error: String? = null,
  val penilaian: Penilaian = Penilaian("Lembaga A", 1, 100),
  val penilaianUmums: List<PenilaianUmum> = emptyList(),
  val nilaiCapaianClusters: List<PenilaianUmum> = emptyList(),
  val penilaianMentorCluster: PenilaianFormData = PenilaianFormData(
    "", ""
  ),
  val penilaianMentorDesainProgram: PenilaianFormData = PenilaianFormData(
    "", ""
  )
)

sealed class DetailPenilaianEvent {

  object ToggleEdit : DetailPenilaianEvent()
  object SubmitForm : DetailPenilaianEvent()
  object Refresh : DetailPenilaianEvent()
  object ClearState : DetailPenilaianEvent()
  data class PenilaianMentorClusterChanged(val penilaianMentorCluster: PenilaianFormData) : DetailPenilaianEvent()
  data class PenilaianMentorDesainProgramChanged(val penilaianMentorDesainProgram: PenilaianFormData) : DetailPenilaianEvent()
}

