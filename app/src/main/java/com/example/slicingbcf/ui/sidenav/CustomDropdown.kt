package com.example.slicingbcf.ui.sidenav

import android.util.Log
import com.example.slicingbcf.ui.navigation.Screen

data class DropdownItem(
  val text : String,
  val onClick : () -> Unit,
  val route : String?
)


fun dropdownItemsPendaftaran_Guest(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Registrasi Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Auth.Registrasi.route)
      },
      route = Screen.Auth.Registrasi.route
    ),
    DropdownItem(
      text = "Cek Status Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.CheckStatusRegistrasi.route)
      },
      route = Screen.Peserta.CheckStatusRegistrasi.route
    )
  )
}


// ! DROPDOWN ROLE PESERTA
fun dropdownItemsPeserta_Peserta(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
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
      text = "Umpan Balik Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.FeedbackPeserta.route)
      },
      route = Screen.Peserta.FeedbackPeserta.route
    ),

  )
}

fun dropdownItemsMentor_Peserta(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Kelompok Mentoring",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.KelompokMentoring.route)
      },
      route = Screen.Peserta.KelompokMentoring.route
    ),
    DropdownItem(
      text = "Umpan Balik Mentor",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.FormFeedbackMentor.route)
      },
      route = Screen.Peserta.FormFeedbackMentor.route
    )
  )
}

fun dropdownItemsTugas_Peserta(
  navigateAndCloseSideNav : (String) -> Unit,
  onNavigateModul : () -> Unit

) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Modul",
      onClick = {
        onNavigateModul()
      },
      route = null
    ),
    DropdownItem(
      text = "Laporan",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.FormMonthlyReport.route)
      },
      route = Screen.Peserta.FormMonthlyReport.route
    ),
    DropdownItem(
      text = "Lembar Kerja",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.WorksheetPeserta.route)
      },
      route = Screen.Peserta.WorksheetPeserta.route
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

fun dropdownItemsKegiatan_Peserta(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Jadwal Kegiatan",
      onClick = {
        Log.d("SideNav", "Jadwal clicked")
        navigateAndCloseSideNav(Screen.Kegiatan.JadwalBulanPeserta.route)
      },
      route = Screen.Kegiatan.JadwalBulanPeserta.route
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


// ! DROPDOWN ROLE MENTOR
fun dropdownItemsPeserta_Mentor(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Data Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.DataPeserta.route)
      },
      route = Screen.Mentor.DataPeserta.route
    ),
    DropdownItem(
      text = "Umpan Balik Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.FormFeedbackPeserta.route)
      },
      route = Screen.Mentor.FormFeedbackPeserta.route
    ),
    DropdownItem(
      text = "Penilaian Peserta",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.PenilaianPeserta.route)
      },
      route = Screen.Mentor.PenilaianPeserta.route
    ),
    DropdownItem(
      text = "Laporan",
        onClick = {
            navigateAndCloseSideNav(Screen.Mentor.Laporan.route)
        },
      route = Screen.Mentor.Laporan.route
    )
  )
}

fun dropdownItemsMentor_Mentor(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Kelompok Mentoring",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.KelompokMentoring.route)
      },
      route = Screen.Peserta.KelompokMentoring.route
    ),
    DropdownItem(
      text = "Umpan Balik Mentor",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.UmpanBalikMentor.route)
      },
      route = Screen.Mentor.UmpanBalikMentor.route
    ),
    DropdownItem(
      text = "Profil Mentor",
        onClick = {
            navigateAndCloseSideNav(Screen.Mentor.ProfilMentor.route)
        },
        route = Screen.Mentor.ProfilMentor.route
    )
  )
}

fun dropdownItemsTugas_Mentor(
  navigateAndCloseSideNav : (String) -> Unit,
  onNavigateModul : () -> Unit

) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Modul",
      onClick = {
        onNavigateModul()
      },
      route = null
    ),
    // ! screen sari
    DropdownItem(
      text = "Laporan",
      onClick = {
        navigateAndCloseSideNav(Screen.Peserta.FormMonthlyReport.route)
      },
      route = null
    ),
    // TODO: REMINDER (GATAU BEDANYA SM PITCHDECK APAAN [ku comment dlu])
//    DropdownItem(
//      text = "Lembar Kerja",
//      onClick = {
//        Log.d("SideNavMentor", "Lembar Kerja clicked")
//      },
//      route = null
//    ),
    DropdownItem(
      text = "Pitch Deck",
      onClick = {
        navigateAndCloseSideNav(Screen.Mentor.Pitchdeck.route)
      },
      route = Screen.Mentor.Pitchdeck.route
    ),
  )
}

fun dropdownItemsKegiatan_Mentor(
  navigateAndCloseSideNav : (String) -> Unit
) : List<DropdownItem> {
  return listOf(
    DropdownItem(
      text = "Jadwal Kegiatan",
      onClick = {
        navigateAndCloseSideNav(Screen.Kegiatan.JadwalBulanMentor.route)
      },
      route = Screen.Kegiatan.JadwalBulanMentor.route
    )
  )
}
