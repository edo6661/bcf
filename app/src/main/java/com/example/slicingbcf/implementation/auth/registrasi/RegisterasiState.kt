package com.example.slicingbcf.implementation.auth.registrasi

import android.net.Uri
import com.example.slicingbcf.data.dao.model.JangkauanPenerimaManfaat

data class RegistrasiState(
  val namaLembaga : String = "",
  val namaLembagaError : String? = null,
  val selectedDate : String = "",
  val selectedDateError : String? = null,
  val emailLembaga : String = "",
  val emailLembagaError : String? = null,
  val alamatLembaga : String = "",
  val alamatLembagaError : String? = null,
  val provinsi : String = "",
  val provinsiError : String? = null,
  val kota : String = "",
  val kotaError : String? = null,
  val jenisLembagaSosial : String = "",
  val jenisLembagaSosialError : String? = null,
  val jenisClusterLembagaSosial : String = "",
  val jenisClusterLembagaSosialError : String? = null,
  val fokusIsu : String = "",
  val fokusIsuError : String? = null,
  val profilLembaga : String = "",
  val profilLembagaError : String? = null,
  val alasanKeikutsertaan : String = "",
  val alasanKeikutsertaanError : String? = null,
  val selectedFileUriDokumentasiSesiMentoringCluster : Uri? = null,
  val jangkauanProgram : String = "",
  val jangkauanProgramError : String? = null,
  val wilayahJangkauanProgram : String = "",
  val wilayahJangkauanProgramError : String? = null,
  val jumlahAngkaPenerimaanManfaat : List<JangkauanPenerimaManfaat> = listOf(
    JangkauanPenerimaManfaat(kota = "", jumlah = 0)
  ),
  val jumlahAngkaPenerimaanManfaatError : String? = null,
  val targetUtamaProgram : String = "",
  val targetUtamaProgramError : String? = null,
  val selectedFileUriProposalProgram : Uri? = null,
  val selectedFileUriProposalProgramError : String? = null,
  val namaLengkapPeserta : String = "",
  val namaLengkapPesertaError : String? = null,
  val posisiPeserta : String = "",
  val posisiPesertaError : String? = null,
  val pendidikanTerakhir : String = "",
  val pendidikanTerakhirError : String? = null,
  val jurusanPendidikanTerakhir : String = "",
  val jurusanPendidikanTerakhirError : String? = null,
  val jenisKelamin : String = "",
  val jenisKelaminError : String? = null,
  val nomorWhatsappPeserta : String = "",
  val nomorWhatsappPesertaError : String? = null,
  val emailPeserta : String = "",
  val emailPesertaError : String? = null,
  val selectedFileUriKTP : Uri? = null,
  val selectedFileUriKTPError : String? = null,
  val selectedFileUriCV : Uri? = null,
  val selectedFileUriCVError : String? = null,
  val adaPengurusLain : Boolean = false,
  val alasanTidakMengikutiAgenda : String = "",
  val alasanTidakMengikutiAgendaError : String? = null,
  val bersediaMengikutiAgenda : Boolean = false,
  val pernahMengikutiPelatihanDesainProgram : String = "",
  val pernahMengikutiPelatihanDesainProgramError : String? = null,
  val sumberInformasiLEAD : Set<String> = emptySet(),
  val errorSumberInformasiLEAD : String? = null,
  val pengetahuanLead : String = "",
  val pengetahuanLeadError : String? = null,
  val pengetahuanDesainProgram : String = "",
  val pengetahuanDesainProgramError : String? = null,
  val pengetahuanSustainability : String = "",
  val pengetahuanSustainabilityError : String? = null,
  val pengetahuanSocialReport : String = "",
  val pengetahuanSocialReportError : String? = null,
  val selectedFileUriLaporanAkhirTahun : Uri? = null,
  val selectedFileUriLaporanAkhirTahunError : String? = null,
  val ekspetasiSetelahLEAD : String = "",
  val ekspetasiSetelahLEADError : String? = null,
  val halLainYangInginDisampaikan : String = "",
  val halLainYangInginDisampaikanError : String? = null,
  val miniTrainingChecked : Boolean = false,
  val initialMentoringChecked : Boolean = false,
  val pendampinganIntensifChecked : Boolean = false,
  val readAndUnderstandChecked : Boolean = false,
  val readAndUnderstandError : String? = null,
  val confirmInformationChecked : Boolean = false,
  val confirmInformationError : String? = null,
  val miniTrainingCheckedError : String? = null,
  val initialMentoringCheckedError : String? = null,
  val pendampinganIntensifCheckedError : String? = null,
  val selectedFileUriDokumentasiSesiMentoringClusterError : String? = null,

  val isLoading : Boolean = false,
  val error : String? = null,
  val isSuccess : Boolean = false,
  val message : String? = null
) {

  val sumberInformasiLEADAsString : String
    get() = sumberInformasiLEAD.joinToString(", ")
}

sealed class RegisterEvent {
  data class ReadAndUnderstandCheckedChanged(val isChecked : Boolean) : RegisterEvent()
  data class ConfirmInformationCheckedChanged(val isChecked : Boolean) : RegisterEvent()
  data class MiniTrainingCheckedChanged(val isChecked : Boolean) : RegisterEvent()
  data class InitialMentoringCheckedChanged(val isChecked : Boolean) : RegisterEvent()
  data class PendampinganIntensifCheckedChanged(val isChecked : Boolean) : RegisterEvent()
  data class NamaLembagaChanged(val namaLembaga : String) : RegisterEvent()
  data class SelectedDateChanged(val selectedDate : String) : RegisterEvent()
  data class EmailLembagaChanged(val emailLembaga : String) : RegisterEvent()
  data class AlamatLembagaChanged(val alamatLembaga : String) : RegisterEvent()
  data class ProvinsiChanged(val provinsi : String) : RegisterEvent()
  data class KotaChanged(val kota : String) : RegisterEvent()
  data class JenisLembagaSosialChanged(val jenisLembagaSosial : String) : RegisterEvent()
  data class JenisClusterLembagaSosialChanged(val jenisLembagaSosial : String) : RegisterEvent()
  data class FokusIsuChanged(val fokusIsu : String) : RegisterEvent()
  data class ProfilLembagaChanged(val profilLembaga : String) : RegisterEvent()
  data class AlasanKeikutsertaanChanged(val alasanKeikutsertaan : String) : RegisterEvent()
  data class SelectedFileUriDokumentasiSesiMentoringClusterChanged(val uri : Uri?) : RegisterEvent()
  data class JangkauanProgramChanged(val jangkauanProgram : String) : RegisterEvent()
  data class WilayahJangkauanProgramChanged(val wilayahJangkauanProgram : String) : RegisterEvent()
  data class UpdateJumlahAngkaPenerimaanManfaat(
    val index : Int,
    val field : JangkauanPenerimaManfaat
  ) : RegisterEvent()

  data class AddJumlahAngkaPenerimaanManfaat(
    val newField : JangkauanPenerimaManfaat
  ) : RegisterEvent()

  object RemoveLastJumlahAngkaPenerimaanManfaat : RegisterEvent()


  data class TargetUtamaProgramChanged(val targetUtamaProgram : String) : RegisterEvent()
  data class SelectedFileUriProposalProgramChanged(val uri : Uri?) : RegisterEvent()
  data class NamaLengkapPesertaChanged(val namaLengkap : String) : RegisterEvent()
  data class PosisiPesertaChanged(val posisiPeserta : String) : RegisterEvent()
  data class PendidikanTerakhirChanged(val pendidikanTerakhir : String) : RegisterEvent()
  data class JurusanPendidikanTerakhirChanged(val jurusanPendidikan : String) : RegisterEvent()
  data class JenisKelaminChanged(val jenisKelamin : String) : RegisterEvent()
  data class NomorWhatsappPesertaChanged(val nomorWhatsapp : String) : RegisterEvent()
  data class EmailPesertaChanged(val email : String) : RegisterEvent()
  data class SelectedFileUriKTPChanged(val uri : Uri?) : RegisterEvent()
  data class SelectedFileUriCVChanged(val uri : Uri?) : RegisterEvent()
  data class AdaPengurusLainChanged(val adaPengurusLain : Boolean) : RegisterEvent()
  data class AlasanTidakMengikutiAgendaChanged(val alasan : String) : RegisterEvent()
  data class BersediaMengikutiAgendaChanged(val bersedia : Boolean) : RegisterEvent()
  data class PernahMengikutiPelatihanDesainProgramChanged(val pelatihan : String) : RegisterEvent()
  data class SumberInformasiLEADChanged(val sumber : String, val isChecked : Boolean) :
    RegisterEvent()

  data class PengetahuanDesainProgramChanged(val pengetahuan : String) : RegisterEvent()
  data class PengetahuanLeadChanged(val pengetahuan : String) : RegisterEvent()
  data class PengetahuanSustainabilityChanged(val pengetahuan : String) : RegisterEvent()
  data class PengetahuanSocialReportChanged(val pengetahuan : String) : RegisterEvent()
  data class SelectedFileUriLaporanAkhirTahunChanged(val uri : Uri?) : RegisterEvent()
  data class EkspetasiSetelahLEADChanged(val ekspetasi : String) : RegisterEvent()
  data class HalLainYangInginDisampaikanChanged(val halLain : String) : RegisterEvent()
  object Submit : RegisterEvent()
  object ClearState : RegisterEvent()
  object ClearError : RegisterEvent()
}
