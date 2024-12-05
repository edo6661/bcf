package com.example.slicingbcf.ui.sidenav

import com.example.slicingbcf.ui.navigation.Screen


fun dropdownItemsPendaftaran(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Registrasi Peserta",
      onClick = {
      },
      route = null
    ),
    DropdownItem(
      text = "Cek Status Peserta",
      onClick = {
      },
      route = null
    )
  )
}

// ! ROLE GUEST
fun dropdownItemsPeserta_Guest(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Pusat Informasi",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.PusatInformasi.route)
      },
      route = Screen.Peserta.PusatInformasi.route
    ),
    DropdownItem(
      text = "Data Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.DataPeserta.route)
      },
      route = Screen.Peserta.DataPeserta.route
    ),
    DropdownItem(
      text = "Penilaian Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.PenilaianPeserta.route)
      },
      route = Screen.Peserta.PenilaianPeserta.route
    ),
    DropdownItem(
      text = "Kelompok Mentoring",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.KelompokMentoring.route)
      },
      route = Screen.Peserta.KelompokMentoring.route
    ),
    DropdownItem(
      text = "Pengumuman",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.PengumumanPeserta.route)
      },
      route = Screen.Peserta.PengumumanPeserta.route
    ),
    DropdownItem(
      text = "Feedback Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.FeedbackPeserta.route)
      },
      route = Screen.Peserta.FeedbackPeserta.route
    ),
    DropdownItem(
      text = "Form Umpan Balik Mentor",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.FormFeedbackMentor.route)
      },
      route = Screen.Peserta.FormFeedbackMentor.route
    ),

    DropdownItem(
      text = "Pengaturan",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.Pengaturan.route)
      },
      route = Screen.Peserta.Pengaturan.route
    ),
    DropdownItem(
      text = "Worksheet Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.WorksheetPeserta.route)
      },
      route = Screen.Peserta.WorksheetPeserta.route
    ),
  )
}

fun dropdownItemsMentor_Guest(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Kelompok Mentor",
      onClick = {
      },
      route = null
    ),
    DropdownItem(
      text = "Umpan Balik Mentor",
      onClick = {
      },
      route = null
    ),
    DropdownItem(
      text = "Form Umpan Balik Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.FormFeedbackPeserta.route)
      },
      route = Screen.Mentor.FormFeedbackPeserta.route
    ),
    DropdownItem(
      text = "Pitchdeck",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.Pitchdeck.route)
      },
      route = Screen.Mentor.Pitchdeck.route
    ),
    DropdownItem(
      text = "Forum Diskusi",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.ForumDiskusi.route)
      },
      route = Screen.Mentor.ForumDiskusi.route
    ),
    DropdownItem(
      text = "Data Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.DataPeserta.route)
      },
      route = Screen.Mentor.DataPeserta.route
    ),
    DropdownItem(
      text = "Penilaian Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.PenilaianPeserta.route)
      },
      route = Screen.Mentor.PenilaianPeserta.route
    )
  )
}

fun dropdownItemsTugas_Guest(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Modul",
      onClick = {
      },
      route = null
    ),
    DropdownItem(
      text = "Laporan",
      onClick = {
      },
      route = null
    ),
    DropdownItem(
      text = "Lembar Kerja",
      onClick = {
      },
      route = null
    ),
    DropdownItem(
      text = "Pitch Deck",
      onClick = {
        navigateAndCloseSideNav(Screen.Tugas.PitchDeck.route)
      },
      route = Screen.Tugas.PitchDeck.route
    ),
  )
}

fun dropdownItemsKegiatan_Guest(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Jadwal Kegiatan",
      onClick = {
        navigateAndCloseSideNav(Screen.Kegiatan.JadwalBulanPeserta.route)
      },
      route = Screen.Kegiatan.JadwalBulanPeserta.route
    ),
    DropdownItem(
      text = "Jadwal Kegiatan (Mentor)",
      onClick = {
        navigateAndCloseSideNav(Screen.Kegiatan.JadwalBulanMentor.route)
      },
      route = Screen.Kegiatan.UmpanBalikKegiatan.route
    ),

    DropdownItem(
      text = "Umpan Balik Kegiatan",
      onClick = {
        navigateAndCloseSideNav(Screen.Kegiatan.UmpanBalikKegiatan.route)
      },
      route = Screen.Kegiatan.UmpanBalikKegiatan.route
    )
  )
}
