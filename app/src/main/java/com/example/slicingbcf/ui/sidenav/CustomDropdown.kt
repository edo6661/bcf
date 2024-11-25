package com.example.slicingbcf.ui.sidenav

import android.util.Log
import com.example.slicingbcf.ui.navigation.Screen

data class DropdownItem(
  val text : String,
  val onClick : () -> Unit,
  val route : String?
)

fun dropdownItemsPendaftaran(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Registrasi Peserta",
      onClick = {
        Log.d("SideNav", "Registrasi Peserta clicked")
      },
      route = null
    ),
    DropdownItem(
      text = "Cek Status Peserta",
      onClick = {
        Log.d("SideNav", "Cek Status Peserta clicked")
      },
      route = null
    )
  )
}

fun dropdownItemsPeserta(
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
    DropdownItem(
      text = "Penilaian Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.PenilaianPeserta.route)
      },
      route = Screen.Peserta.PenilaianPeserta.route
    ),
  )
}

fun dropdownItemsMentor(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Kelompok Mentor",
      onClick = {
        Log.d("SideNav", "Kelompok Mentor clicked")
      },
      route = null
    ),
    DropdownItem(
      text = "Umpan Balik Mentor",
      onClick = {
        Log.d("SideNav", "Umpan Balik Mentor clicked")
      },
      route = null
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
    ),
    DropdownItem(
      text = "Feedback Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.FeedbackPeserta.route)
      },
      route = Screen.Mentor.FeedbackPeserta.route
    ),
  )
}

fun dropdownItemsTugas(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Modul",
      onClick = {
        Log.d("SideNav", "Modul clicked")
      },
      route = null
    ),
    DropdownItem(
      text = "Laporan",
      onClick = {
        Log.d("SideNav", "Laporan clicked")
      },
      route = null
    ),
    DropdownItem(
      text = "Lembar Kerja",
      onClick = {
        Log.d("SideNav", "Lembar Kerja clicked")
      },
      route = null
    ),
    DropdownItem(
      text = "Lembar Kerja",
      onClick = {
        Log.d("SideNav", "Lembar Kerja clicked")
      },
      route = null
    ),
    DropdownItem(
      text = "Pitch Deck",
      onClick = {
        Log.d("SideNav", "Pitch Deck clicked")
      },
      route = null
    ),
  )
}

fun dropdownItemsKegiatan(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Jadwal Kegiatan",
      onClick = {
        Log.d("SideNav", "Jadwal Kegiatan clicked")
      },
      route = null
    ),
    DropdownItem(
      text = "Umpan Balik Kegiatan",
      onClick = {
        Log.d("SideNav", "Umpan Balik Kegiatan clicked")
      },
      route = null
    )
  )
}
