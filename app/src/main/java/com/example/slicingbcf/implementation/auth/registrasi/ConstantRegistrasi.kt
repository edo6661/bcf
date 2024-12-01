package com.example.slicingbcf.implementation.auth.registrasi

import com.example.slicingbcf.R

class ConstantRegistrasi {
  companion object {

    val provinsis = listOf(
      "Aceh",
      "Sumatera Utara",
      "Sumatera Barat",
      "Riau",
      "Jambi",
      "Sumatera Selatan",
      "Bengkulu",
      "Lampung",
      "Kepulau"
    )
    val kotas = listOf(
      "Jakarta",
      "Bandung",
      "Surabaya",
      "Semarang",
      "Yogyakarta",
      "Bali",
      "Makassar",
      "Manado",
      "Medan"
    )
    val lembagaSosials = listOf(
      "Gerakan",
      "Yayasan",
      "Lembaga",
      "Komunitas"
    )
    val jenisClusterLembagaSosials = listOf(
      "Pendidikan",
      "Kesehatan",
      "Energi Terbarukan",
      "Sustainability",
    )
    val fokusIsus = listOf(
      "HIV/AIDS",
      "Tubercolosis",
      "Pendidikan"
    )
    val pendidikanTerakhirs = listOf(
      "SD",
      "SMP",
      "SMA",
      "D3",
      "S1",
      "S2",
      "S3"
    )


    val jangkauanPrograms = listOf(
      "Provinsi",
      "Kota / Kabupaten",
      "Nasional",
      "Internasional",
      "Kelurahan"
    )
    val wilayahJangkauanPrograms = listOf(
      "DKI Jakarta",
      "Jawa Barat",
      "Jawa Tengah",
      "Jawa Timur",
      "Bali",
    )

    val jenisKelamins = listOf(
      "Pria",
      "Wanita"
    )

    val pernahs = listOf(
      "Sudah",
      "Belum"
    )


  }

}

object RegistrasiValidationMessages {

  const val NAMA_LEMBAGA_REQUIRED = "Nama lembaga tidak boleh kosong"
  const val EMAIL_LEMBAGA_REQUIRED = "Email lembaga tidak boleh kosong"
  const val ALAMAT_LEMBAGA_REQUIRED = "Alamat lembaga tidak boleh kosong"
  const val PROVINSI_REQUIRED = "Provinsi tidak boleh kosong"
  const val KOTA_REQUIRED = "Kota tidak boleh kosong"
  const val JENIS_LEMBAGA_SOSIAL_REQUIRED = "Jenis lembaga sosial tidak boleh kosong"
  const val FOKUS_ISU_REQUIRED = "Fokus isu tidak boleh kosong"
  const val PROFIL_LEMBAGA_REQUIRED = "Profil lembaga tidak boleh kosong"
  const val ALASAN_KEIKUTSERTAAN_REQUIRED = "Alasan keikutsertaan tidak boleh kosong"
  const val JANGKAUAN_PROGRAM_REQUIRED = "Jangkauan program tidak boleh kosong"
  const val WILAYAH_JANGKAUAN_PROGRAM_REQUIRED = "Wilayah jangkauan program tidak boleh kosong"
  const val TARGET_UTAMA_PROGRAM_REQUIRED = "Target utama program tidak boleh kosong"
  const val NAMA_LENGKAP_PESERTA_REQUIRED = "Nama lengkap peserta tidak boleh kosong"
  const val POSISI_PESERTA_REQUIRED = "Posisi peserta tidak boleh kosong"
  const val PENDIDIKAN_TERAKHIR_REQUIRED = "Pendidikan terakhir tidak boleh kosong"
  const val JURUSAN_PENDIDIKAN_TERAKHIR_REQUIRED = "Jurusan pendidikan terakhir tidak boleh kosong"
  const val JENIS_KELAMIN_REQUIRED = "Jenis kelamin tidak boleh kosong"
  const val NOMOR_WHATSAPP_PESERTA_REQUIRED = "Nomor WhatsApp peserta tidak boleh kosong"
  const val EMAIL_PESERTA_REQUIRED = "Email peserta tidak boleh kosong"
  const val ALASAN_TIDAK_MENGIKUTI_AGENDA_REQUIRED =
    "Alasan tidak mengikuti agenda tidak boleh kosong"
  const val EKSPETASI_SETELAH_LEAD_REQUIRED = "Ekspetasi setelah LEAD tidak boleh kosong"


}


object ConstantUmpanBalik {
  data class UmpanBalik(
    val title : String,
    val emote : Int
  )

  val emotes = listOf(
    UmpanBalik("Sangat Kecewa", R.drawable.emote_angry),
    UmpanBalik("Kecewa", R.drawable.emote_sad),
    UmpanBalik("Biasa Saja", R.drawable.emote_flat),
    UmpanBalik("Bagus", R.drawable.emote_smile),
    UmpanBalik("Sangat Bagus", R.drawable.emote_love),
  )
}