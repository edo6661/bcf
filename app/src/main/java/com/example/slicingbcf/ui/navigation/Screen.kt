package com.example.slicingbcf.ui.navigation

sealed class Screen(val route : String) {

  object Home : Screen("home")
  object SplashScreen : Screen("splash-screen")

  sealed class Auth(route : String) : Screen(route) {
    object Login : Auth("login")
    object ForgotPassword : Auth("forgot-password")
    object Registrasi : Peserta("registrasi")
    object UmpanBalikRegistrasi : Auth("umpan-balik-registrasi")

  }

  sealed class Peserta(route : String) : Screen(route) {
    object DataPeserta : Peserta("data-peserta")
    data class DetailDataPeserta(val id : String) : Peserta("data-peserta/$id")
    object KelompokMentoring : Peserta("kelompok-mentoring")
    object PengumumanPeserta : Peserta("pengumuman-peserta")
    data class DetailPengumumanPeserta(val id : String) : Peserta("pengumuman-peserta/$id")
    object WorksheetPeserta : Peserta("worksheet-peserta")
    data class DetailWorksheetPeserta(val id : String) : Peserta("worksheet-peserta/$id")
    object Pengaturan : Peserta("pengaturan")
    object PusatInformasi : Peserta("pusat-informasi")
    data class DetailPusatInformasi(val id : String) : Peserta("pusat-informasi/$id")
    object FormFeedbackMentor : Peserta("form-mentor")
    object FeedbackPeserta: Peserta("feedback-peserta")

    object SearchPusatInformasi : Mentor("search-pusat-informasi")
    object PenilaianPeserta : Peserta("penilaian-peserta")
    object FormMonthlyReport : Peserta("form-monthly-report")
    data class DetailFormMonthlyReport(val id : String) : Peserta("form-monthly-report/$id")
    data class AdaMonthlyReport(val id : String) : Peserta("ada-monthly-report/$id")
    data class TidakAdaMonthlyReport(val id : String) : Peserta("tidak-ada-monthly-report/$id")
    object CheckStatusRegistrasi : Peserta("check-status-registrasi")

  }

  sealed class Mentor(route : String) : Screen(route) {
    object PenilaianPeserta : Mentor("penilaian-peserta-mentor")
    data class DetailPenilaianPeserta(val id : String) : Mentor("penilaian-peserta/$id")
    object FormFeedbackPeserta : Mentor("form-feedback-peserta")
    object Pitchdeck : Mentor("pitchdeck-mentor")
    data class DetailPitchdeck(val id : String) : Mentor("pitchdeck-mentor/$id")
    data class MoreDetailPitchdeck(val id : String) : Mentor("pitchdeck-mentor/$id/more")
    object ForumDiskusi : Mentor("forum-diskusi")
    data class DetailForumDiskusi(val id : String) : Mentor("forum-diskusi/$id")
    object DataPeserta : Mentor("data-peserta-mentor")

    object FeedbackPeserta : Mentor("feedback-peserta")
    object SearchForumDiskusi : Mentor("search-forum-diskusi")
    object Pengaturan : Mentor("pengaturan-mentor")
    object Pengumuman : Mentor("pengumuman-mentor")
    data class DetailPengumumanPeserta(val id : String) : Peserta("pengumuman-mentor/$id")
    object KelompokMentoring : Mentor("kelompok-mentoring-mentor")

    object Laporan : Mentor("laporan")
    object ProfilMentor : Mentor("profil-mentor")
    data class UbahProfilMentor(val id : String) : Mentor("ubah-profil-mentor/$id")
  }

  sealed class Kegiatan(route: String) : Screen(route){
    object UmpanBalikKegiatan: Kegiatan("mini-training")
    object JadwalBulanPeserta: Kegiatan ("jadwal-bulan-peserta")
    data class JadwalMingguPeserta(val id : String): Kegiatan("jadwal_minggu-peserta/$id")
    data class DetailJadwalPeserta(val id : String): Kegiatan("detail-jadwal-peserta/$id")
    object JadwalBulanMentor: Kegiatan ("jadwal-bulan-mentor")
    data class JadwalMingguMentor(val id : String): Kegiatan("jadwal_minggu-mentor/$id")
    data class DetailJadwalMentor(val id : String): Kegiatan("detail-jadwal-mentor/$id")
    data class AddJadwalMentor(val id : String): Kegiatan("add-jadwal-mentor/$id")
  }

  sealed class Tugas(route: String) : Screen(route){
    object PitchDeck: Tugas ("pitch-deck")
    data class PitchDeckDetail(val id: String) : Tugas("detail-pitchdeck/$id")
  }

  object ProfilPeserta : Screen("profil-peserta")
  object ProfilLembaga : Screen("profil-lembaga")
  object Pengumuman : Screen("pengumuman")
  data class DetailPengumuman(val id : String) : Screen("pengumuman/$id")
  object ProfilMentor : Screen("profil-mentor")

}
