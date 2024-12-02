package com.example.slicingbcf.data.local


data class ProgressKegiatan(
  val kegiatan : String,
  val wilayah : String,
)

data class DesainProgram(
  val aspekCapaian : String,
  val keterangan : String,
)

data class Pendanaan(
  val tipeDonasi : String,
  val nominal : String,
  val platform : String,
)

data class Relawan(
  val jumlah : String,
  val kendalaDanPenyesalan : String,
)

data class MediaMassa(
  val lembaga : String,
  val status : StatusMediaMassa,
  val keterangan : String,
)

sealed class StatusMediaMassa {
  object Disetujui : StatusMediaMassa() {

    override fun toString() = "Disetujui"
  }

  object PengajuanProposal : StatusMediaMassa() {

    override fun toString() = "Pengajuan Proposal"
  }

  object TahapPersetujuan : StatusMediaMassa() {

    override fun toString() = "Tahap Persetujuan"
  }

  object Ditolak : StatusMediaMassa() {

    override fun toString() = "Ditolak"
  }
}


data class MediaSosial(
  val waktuPosting : String,
  val kontenPosting : String,
)


val progressKegiatans = listOf(
  ProgressKegiatan(
    kegiatan = "Rapat Koordinasi Lembaga",
    wilayah = "Jakarta Timur",
  ),
  ProgressKegiatan(
    kegiatan = "Pembagian Sembako",
    wilayah = "Jakarta Utara",
  ),
  ProgressKegiatan(
    kegiatan = "Donasi Buku",
    wilayah = "Jakarta Barat",
  ),
)
val pendanaanDonasis = listOf(
  Pendanaan(
    tipeDonasi = "Online",
    platform = "X",
    nominal = "Rp 1.000.000",
  ),
  Pendanaan(
    tipeDonasi = "Offline",
    platform = "Y",
    nominal = "Rp 2.000.000",
  ),
)

val pendanaanSponsorship = listOf(
  Pendanaan(
    tipeDonasi = "Online",
    platform = "X",
    nominal = "Rp 10.000.000",
  ),
  Pendanaan(
    tipeDonasi = "Offline",
    platform = "Y",
    nominal = "Rp 20.000.000",
  ),
)
val pendanaanDonorMitra = listOf(
  Pendanaan(
    tipeDonasi = "Online",
    platform = "X",
    nominal = "Rp 15.000.000",
  ),
  Pendanaan(
    tipeDonasi = "Offline",
    platform = "Y",
    nominal = "Rp 25.000.000",
  ),
  Pendanaan(
    tipeDonasi = "Lainnya",
    platform = "Z",
    nominal = "Rp 3.000.000",
  ),
)

val relawans = listOf(
  Relawan(
    jumlah = "10",
    kendalaDanPenyesalan = "Relawan sulit ditemui karena merupakan komunitas kecil sehingga harus menyebarluaskan informasi melalui pendekatan khusus.",
  ),
  Relawan(
    jumlah = "20",
    kendalaDanPenyesalan = "-",
  ),
  Relawan(
    jumlah = "30",
    kendalaDanPenyesalan = "-",
  ),
)
val desainPrograms = listOf(
  DesainProgram(
    aspekCapaian = "Progress rancangan desain program dan impact report",
    keterangan = "Sudah memasuki tahap pengisian laporan FFF",
  ),
  DesainProgram(
    aspekCapaian = "Tantangan dan penyelesaian",
    keterangan = "Kendala yang dihadapi adalah GGG yang dapat diatasi dengan cara HHH",
  ),
  DesainProgram(
    aspekCapaian = "Progress rancangan desain program dan impact report",
    keterangan = "Sudah memasuki tahap pengisian laporan FFF",
  ),
)

val mediaMassas = listOf(
  MediaMassa(
    lembaga = "BCF",
    status = StatusMediaMassa.Disetujui,
    keterangan = "-",
  ),
  MediaMassa(
    lembaga = "BCFV",
    status = StatusMediaMassa.PengajuanProposal,
    keterangan = "-",
  ),
  MediaMassa(
    lembaga = "BCF",
    status = StatusMediaMassa.TahapPersetujuan,
    keterangan = "-",
  ),
  MediaMassa(
    lembaga = "BCF",
    status = StatusMediaMassa.Ditolak,
    keterangan = "-",
  ),
)
val mediaMassaPemerintahs = listOf(
  MediaMassa(
    lembaga = "BCF",
    status = StatusMediaMassa.Disetujui,
    keterangan = "-"
  ),
  MediaMassa(
    lembaga = "BCF",
    status = StatusMediaMassa.PengajuanProposal,
    keterangan = "-"
  ),
  MediaMassa(
    lembaga = "BCF",
    status = StatusMediaMassa.TahapPersetujuan,
    keterangan = "-"
  ),
)
val mediaMassaPerusahaan = listOf(
  MediaMassa(
    lembaga = "BCF",
    status = StatusMediaMassa.Ditolak,
    keterangan = "-"
  ),

  )

val sosialMedias = listOf(
  MediaSosial(
    waktuPosting = "04-02-2024",
    kontenPosting = "Profil YAMALI"
  ),
  MediaSosial(
    waktuPosting = "05-02-2024",
    kontenPosting = "Hari Kanker"
  ),
)

val mediaMassaOrganisasiLain = listOf(
  MediaMassa(
    lembaga = "Bersumber dari kemitraan GF TB dengan Konsorsium Penabulu-STPI",
    status = StatusMediaMassa.Disetujui,
    keterangan = "-"
  ),
  MediaMassa(
    lembaga = "Keluarga Besar Kita",
    status = StatusMediaMassa.PengajuanProposal,
    keterangan = "-"
  ),
  MediaMassa(
    lembaga = "Dinas Kesehatan Makassar",
    status = StatusMediaMassa.TahapPersetujuan,
    keterangan = "-"
  ),

  )

val headersProgressKegiatan = listOf(
  "No",
  "Kegiatan",
  "Wilayah",
)

val headerPendanaanDonasi = listOf(
  "Tipe Donasi",
  "Nominal",
  "Platform",
)

val headerPendanaanSponsorship = listOf(
  "Tipe Sponsorship",
  "Nominal",
  "Platform",
)

val headerPendanaanDonorMitra = listOf(
  "Tipe Donor & Mitra",
  "Nominal",
  "Platform",
)

val headerRelawan = listOf(
  "No",
  "Jumlah",
  "Kendala dan Penyesalan",
)
val headerDesainProgram = listOf(
  "No",
  "Aspek Capaian",
  "Keterangan",
)
val headerMediaMassa = listOf(
  "No",
  "Lembaga",
  "Status",
  "Keterangan",
)

val headerMediaSosial = listOf(
  "No",
  "Waktu Posting",
  "Konten Posting",
)

// form

data class JumlahAngkaPenerimaanManfaat(
  val wilayahPenerimaManfaat: String,
  val jumlah: String,
)

data class WilayahPenerimaManfaat (
  val wilayah : String,
  val jumlah: String,
)

data class Donasi(
  val jumlah: String,
  val platform: String,
)

data class ProsesOpenRecruitmentRelawan(
  val jumlah: String,
  val prosesYangDilakukan: String
)

val emptyDonasis =  listOf(
  Donasi(
    "", "",
  ),
  Donasi(
    "", "",
  ),
)

data class DonasiMitra(
  val jumlah: String,
  val keterangan: String,
)

val emptyDonasiMitras = listOf(
  DonasiMitra("", ""),
  DonasiMitra("", ""),
  DonasiMitra("", ""),
)

data class KerjaSama(
  val jenisLembaga:String,
  val status: String,
  val namaLembaga: String,
  val keterangan: String,
)

data class  PemasaranSosialMedia(
  val mediaSosial: String,
  val waktu: String,
  val konten: String,
  val tautan: String,
)

