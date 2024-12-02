package com.example.slicingbcf.implementation.peserta.form_monthly_report

import com.example.slicingbcf.data.local.*

data class FormMonthlyReportState (

  val peningkatanPenerimaManfaatDariKegiatan : String = "",
  val peningkatanPenerimaManfaatDariKegiatanError : String? = null,
  val jumlahAngkaPenerimaanManfaat : List<JumlahAngkaPenerimaanManfaat> = listOf(
    JumlahAngkaPenerimaanManfaat("", ""),
  ),
  val jumlahAngkaPenerimaanManfaatError : String? = null,
  val pencapaianPencarianDanaDenganDonasi : List<Donasi> = emptyDonasis,
  val pencapaianPencarianDanaDenganDonasiError : String? = null,
  val pencapaianPencarianDanaDenganSponsorship : List<Donasi> = emptyDonasis,
  val pencapaianPencarianDanaDenganSponsorshipError : String? = null,
  val pencapaianPencarianDanaDenganDonasiMitra : List<DonasiMitra> = emptyDonasiMitras,
  val pencapaianPencarianDanaDenganDonasiMitraError : String? = null,
  val prosesOpenRecruitmentRelawan : ProsesOpenRecruitmentRelawan =
    ProsesOpenRecruitmentRelawan("", ""),
  val prosesOpenRecruitmentRelawanError : String? = null,
  val prosesRancanganDesainProgram : String = "",
  val prosesRancanganDesainProgramError : String? = null,
  val tantanganHambatanMerancangDesainProgram :String = "",
  val tantanganHambatanMerancangDesainProgramError : String? = null,
  val mappingStakeholderLembagaYangDitargetkanUntukPerjalinanKerjasama : List<KerjaSama> = listOf(
    KerjaSama("", "", "", ""),
  ),
  val mappingStakeholderLembagaYangDitargetkanUntukPerjalinanKerjasamaError : String? = null,
  val rekapanPemasaranSosialMedia : List<PemasaranSosialMedia> = listOf(
    PemasaranSosialMedia("", "", "", ""),
  ),
  val rekapanPemasaranSosialMediaError : String? = null,
  val isLoading : Boolean = false,
  val error : String? = null,
  val message : String? = null,
  val isSuccess : Boolean = false,

)

sealed class FormMonthlyReportEvent {
  data class UpdatePeningkatanPenerimaManfaat(val value: String) : FormMonthlyReportEvent()
  data class UpdateJumlahAngkaPenerimaanManfaat(
    val index: Int,
    val field: JumlahAngkaPenerimaanManfaat
  ) : FormMonthlyReportEvent()
  data class AddJumlahAngkaPenerimaanManfaat(val newField : JumlahAngkaPenerimaanManfaat) : FormMonthlyReportEvent()
  object RemoveJumlahAngkaPenerimaanManfaat : FormMonthlyReportEvent()
  data class UpdateDonasi(
    val index: Int,
    val donasi: Donasi,
  ) : FormMonthlyReportEvent()
  data class UpdateDonasiSponsorship(
    val index: Int,
    val donasi: Donasi,
  ) : FormMonthlyReportEvent()
  data class UpdateDonasiMitra(
    val index: Int,
    val donasiMitra: DonasiMitra,
  ) : FormMonthlyReportEvent()
  data class UpdateProsesOpenRecruitmentRelawan(val proses: ProsesOpenRecruitmentRelawan) : FormMonthlyReportEvent()
  data class UpdateProsesRancanganDesainProgram(val value: String) : FormMonthlyReportEvent()
  data class UpdateTantanganHambatan(val value: String) : FormMonthlyReportEvent()
  data class UpdateMappingStakeholder(
    val index: Int,
    val field: KerjaSama
  ) : FormMonthlyReportEvent()
  data class AddMappingStakeholder(val newField : KerjaSama) : FormMonthlyReportEvent()
  object RemoveLastMappingStakeholder : FormMonthlyReportEvent()
  data class UpdateRekapanPemasaranSosialMedia(
    val index : Int,
    val socialMedia : PemasaranSosialMedia
  ) : FormMonthlyReportEvent()
  object RemoveLastRekapanPemasaranSosialMedia : FormMonthlyReportEvent()
  data class AddRekapanPemasaranSosialMedia(
    val newField : PemasaranSosialMedia
  ) : FormMonthlyReportEvent()
  object Submit : FormMonthlyReportEvent()
  object ClearForm : FormMonthlyReportEvent()

}

enum class DonasiType {
  DONASI, SPONSORSHIP, DONASI_MITRA
}







