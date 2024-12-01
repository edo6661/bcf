package com.example.slicingbcf.data.dao.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "users")
data class User(
  @PrimaryKey(autoGenerate = true)
  val id : Long = 0,
  val namaLembaga : String?,

  val emailLembaga : String?,
  val alamatLembaga : String?,
  val provinsi : String?,
  val kota : String?,
  val tanggalBerdiri : String?,
  val jenisLembagaSosial : String?,
  val jenisClusterLembagaSosial : String?,
  val fokusIsu : String?,
  val profilSingkatLembaga : String?,
  val alasanMengikutiLead : String?,
  val dokumenProfilPerusahaan : String?,
  val jangkauanProgram : String?,
  val wilayahJangkauanProgram : String?,
  val jumlahAngkaPenerimaanManfaat : List<JangkauanPenerimaManfaat>,
  val targetUtamaProgram : String?,
  val proposalProgramMitra : String?,
  val namaPeserta : String?,
  val posisi : String?,
  val pendidikanTerakhir : String?,
  val jenisKelamin : String?,
  val nomorWhatsapp : String?,
  val emailPeserta : String?,
  val password : String?,
  val ktp : String?,
  val cv : String?,
  val adaPengurusLainYangAkanDiikutSertakanSebagaiPeserta : Boolean,
  val alasanMengikutiAgenda : String?,
  val pernahMengikutiPelatihan : Boolean,
  val darimanaMengetahuiLead : String?,
  val yangDiketahuiTerkaitDesainProgram : String?,
  val yangDiketahuiTerkaitKeberlanjutan : String?,
  val yangDiketahuiTerkaitLaporanSosial : String?,
  val laporanAkhirTahun : String?,
  val ekspetasiMengikutiLead : String?,
  val halYangInginDitanyakanKeLead : String?,
  val umpanBalik : String?,
  val pengalamanMendaftarLead : String?,
  val role : String
)

enum class Role {
  PESERTA,
  MENTOR,
  SUPERADMIN
}

@Serializable
data class JangkauanPenerimaManfaat(
  val kota : String,
  val jumlah : Int
)