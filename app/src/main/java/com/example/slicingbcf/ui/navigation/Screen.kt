package com.example.slicingbcf.ui.navigation

sealed class Screen(val route : String) {

  object Home : Screen("home")

  sealed class Auth(route : String) : Screen(route) {
    object Login : Auth("login")
    object ForgotPassword : Auth("forgot-password")
    object Registrasi : Peserta("registrasi")
  }

  sealed class Peserta(route : String) : Screen(route) {
    object DataPeserta : Peserta("data-peserta")
    object KelompokMentoring : Peserta("kelompok-mentoring")
    object PengumumanPeserta : Peserta("pengumuman-peserta")
    data class DetailPengumumanPeserta(val id : String) : Peserta("pengumuman-peserta/$id")
    object WorksheetPeserta : Peserta("worksheet-peserta")
    data class DetailWorksheetPeserta(val id : String) : Peserta("worksheet-peserta/$id")
    object Pengaturan : Peserta("pengaturan")
    object PusatInformasi : Mentor("pusat-informasi")
    data class DetailPusatInformasi(val id : String) : Mentor("pusat-informasi/$id")
    // form feedback mentor
    object FormFeedbackMentor : Peserta("form-mentor")
    // feedback peserta
    object FeedbackPeserta: Peserta("feedback-peserta")

    object PenilaianPeserta : Peserta("penilaian-peserta")

  }

  sealed class Mentor(route : String) : Screen(route) {
    object PenilaianPeserta : Mentor("penilaian-peserta")
    data class DetailPenilaianPeserta(val id : String) : Mentor("penilaian-peserta/$id")
    object FormFeedbackPeserta : Mentor("form-feedback-peserta")
    object Pitchdeck : Mentor("pitchdeck")
    data class DetailPitchdeck(val id : String) : Mentor("pitchdeck/$id")
    data class MoreDetailPitchdeck(val id : String) : Mentor("pitchdeck/$id/more")
    object ForumDiskusi : Mentor("forum-diskusi")
    data class DetailForumDiskusi(val id : String) : Mentor("forum-diskusi/$id")
    object DataPeserta : Mentor("data-peserta")

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
    data class PitchDeckExpanded(val id: String) : Tugas("expanded-pitchdeck/$id")
    data class PitchDeckDetail(val id: String) : Tugas("detail-pitchdeck/$id")

  }

  object ProfilPeserta : Screen("profil-peserta")
}
