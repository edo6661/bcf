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

    object PenilaianPeserta : Peserta("penilaian-peserta")

  }

  sealed class Mentor(route : String) : Screen(route) {
    object PenilaianPeserta : Mentor("penilaian-peserta")
    data class DetailPenilaianPeserta(val id : String) : Mentor("penilaian-peserta/$id")
    object FeedbackPeserta : Mentor("feedback-peserta")
    object Pitchdeck : Mentor("pitchdeck")
    data class DetailPitchdeck(val id : String) : Mentor("pitchdeck/$id")
    data class MoreDetailPitchdeck(val id : String) : Mentor("pitchdeck/$id/more")
    object ForumDiskusi : Mentor("forum-diskusi")
    data class DetailForumDiskusi(val id : String) : Mentor("forum-diskusi/$id")
    object DataPeserta : Mentor("data-peserta")

  }


}
