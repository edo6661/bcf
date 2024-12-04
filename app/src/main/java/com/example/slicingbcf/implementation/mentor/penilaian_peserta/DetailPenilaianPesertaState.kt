package com.example.slicingbcf.implementation.mentor.penilaian_peserta

import com.example.slicingbcf.data.local.Penilaian
import com.example.slicingbcf.data.local.PenilaianFormData

data class DetailPenilaianState(
  val isEdit: Boolean = false,
  val loading: Boolean = false,
  val isError: Boolean = false,
  val errorMessage: String? = null,
  val isSuccess: Boolean = false,
  val successMessage: String? = null,
  val penilaian: Penilaian? = null,
  val penilaianMentorCluster: PenilaianFormData = PenilaianFormData("", ""),
  val penilaianMentorDesainProgram: PenilaianFormData = PenilaianFormData("", ""),
)

sealed class DetailPenilaianEvent {

  object ToggleEdit : DetailPenilaianEvent()
  object SubmitForm : DetailPenilaianEvent()
  object Refresh : DetailPenilaianEvent()
  data class PenilaianMentorClusterChanged(val penilaianMentorCluster: PenilaianFormData) : DetailPenilaianEvent()
  data class PenilaianMentorDesainProgramChanged(val penilaianMentorDesainProgram: PenilaianFormData) : DetailPenilaianEvent()
}

