package com.example.slicingbcf.ui.scaffold

import android.util.Log
import com.example.slicingbcf.ui.navigation.Screen

data class ScaffoldConfig(
  val showMainNav : Boolean = true,
  val showBackNav : Boolean = false,
)

fun scaffoldConfig(currentRoute : String?)
  : ScaffoldConfig {
  Log.d("scaffoldConfig", "currentRoute: $currentRoute")
  return when (currentRoute) {
    Screen.SplashScreen.route -> ScaffoldConfig(
      showMainNav = false,
      showBackNav = false,
    )


    Screen.Auth.Login.route          -> ScaffoldConfig(
      showMainNav = false,
    )

    Screen.Auth.ForgotPassword.route -> ScaffoldConfig(
      showMainNav = false,
    )

    "pengumuman-peserta/{id}"        -> ScaffoldConfig(
      showMainNav = false,
      showBackNav = true,
    )

    "worksheet-peserta/{id}"         -> {
      Log.d("scaffoldConfig", "test")
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "penilaian-peserta/{id}"         -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }
    "penilaian-peserta-mentor/{id}"         -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "pitchdeck/{id}"                 -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "pitchdeck/{id}/more"            -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "pitchdeck-mentor/{id}"                 -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "pitchdeck-mentor/{id}/more"            -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "pusat-informasi/{id}"           -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "forum-diskusi/{id}"             -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "expanded-pitchdeck/{id}"            -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "detail-pitchdeck/{id}"            -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }
    "detail-jadwal-peserta/{id}"            -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }
    "detail-jadwal-mentor/{id}"            -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }
    "add-jadwal-mentor/{id}"            -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    "pengumuman-mentor/{id}"               -> ScaffoldConfig(
      showMainNav = false,
      showBackNav = true,
    )


    "data-peserta-mentor/{id}"             -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    Screen.Peserta.SearchPusatInformasi.route        -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    } Screen.Peserta.DataPeserta.route        -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }



    "form-monthly-report/{id}"             -> {
      ScaffoldConfig(
        showMainNav = false,
        showBackNav = true,
      )
    }

    Screen.Mentor.SearchForumDiskusi.route ->
      ScaffoldConfig(
      showMainNav = false,
      showBackNav = true,
    )

    "ada-monthly-report/{id}" -> ScaffoldConfig(
      showMainNav = false,
      showBackNav = false,
    )

    "tidak-ada-monthly-report/{id}" -> ScaffoldConfig(
      showMainNav = false,
      showBackNav = false,
    )


    else                             -> ScaffoldConfig()
  }
}