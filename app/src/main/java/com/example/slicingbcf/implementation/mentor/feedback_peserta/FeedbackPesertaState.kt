package com.example.slicingbcf.implementation.mentor.feedback_peserta

import android.net.Uri

data class FeedbackPesertaState(
  val namaLembaga : String = "",
  val periodeCapaianMentoring : String = "",
  val evaluasiCapaianMentoring : List<String> = listOf(
    "",
    "",
    "",
    "",
    "",
    "",
    ""
  ),
  val evaluasiLembaga : String = "",
  val evaluasiKepuasan : String = "",
  val halhalYangDibahas : String = "",
  val tantanganUtama : String = "",
  val dokumentasiSesiMentoringCluster : Uri? = null,
  val isLoading : Boolean = false,
  val error : String? = null,
  val isSuccess : Boolean = false
)

sealed class FeedbackPesertaEvent {
  data class NamaLembagaChanged(val namaLembaga : String) : FeedbackPesertaEvent()
  data class ChangeScreen(val screen: Int) : FeedbackPesertaEvent()
  data class PeriodeCapaianMentoringChanged(val periodeCapaianMentoring : String) :
    FeedbackPesertaEvent()

  data class EvaluasiCapaianMentoringChanged(val index : Int, val evaluasiKepuasan : String) :
    FeedbackPesertaEvent()

  data class EvaluasiLembagaChanged(val evaluasiLembaga : String) : FeedbackPesertaEvent()
  data class EvaluasiKepuasanChanged(val evaluasiKepuasan : String) : FeedbackPesertaEvent()
  data class HalHalYangDibahasChanged(val halHalYangDibahas : String) : FeedbackPesertaEvent()
  data class TantanganUtamaChanged(val tantanganUtama : String) : FeedbackPesertaEvent()
  data class DokumentasiSesiMentoringClusterChanged(val uri : Uri?) : FeedbackPesertaEvent()
  object ClearState : FeedbackPesertaEvent()
  object Submit : FeedbackPesertaEvent()
}
