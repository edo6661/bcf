package com.example.slicingbcf.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.slicingbcf.implementation.peserta.data_peserta.DataPesertaScreen
import com.example.slicingbcf.implementation.peserta.feedback_peserta.FeedbackPesertaScreen
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.FeedbackMentorScreen1
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.FeedbackMentorScreen2
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.FeedbackMentorScreen3
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.FeedbackMentorScreen4
import com.example.slicingbcf.implementation.peserta.kelompok_mentoring.KelompokMentoringScreen
import com.example.slicingbcf.implementation.peserta.pengaturan.PengaturanScreen
import com.example.slicingbcf.implementation.peserta.pengumuman_peserta.DetailPengumumanPesertaScreen
import com.example.slicingbcf.implementation.peserta.pengumuman_peserta.PengumumanPesertaScreen
import com.example.slicingbcf.implementation.peserta.penilaian_peserta.PenilaianPesertaScreen
import com.example.slicingbcf.implementation.peserta.pusat_informasi.DetailPusatInformasiScreen
import com.example.slicingbcf.implementation.peserta.pusat_informasi.PusatInformasiScreen
import com.example.slicingbcf.implementation.peserta.worksheet_peserta.DetailWorksheetPesertaScreen
import com.example.slicingbcf.implementation.peserta.worksheet_peserta.WorksheetPesertaScreen


fun NavGraphBuilder.pesertaNavGraph(
  modifier : Modifier,
  navController : NavHostController
) {
  navigation(
    startDestination = Screen.Peserta.PenilaianPeserta.route, route = "peserta"
  ) {
    // Data Peserta
    composable(Screen.Peserta.DataPeserta.route) {
      DataPesertaScreen(
        modifier = modifier,
      )
    }

    // pusat informasi
    composable(
      route = Screen.Peserta.PusatInformasi.route,
    ) {
      val onNavigateDetailPusatInformasi = { id : String ->
        navController.navigateSingleTop("pusat-informasi/$id")
      }
      PusatInformasiScreen(
        modifier = modifier,
        onNavigateDetailPusatInformasi = onNavigateDetailPusatInformasi
      )
    }
    composable(
      route = "pusat-informasi/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
      val id = backStackEntry.arguments?.getString("id") ?: ""
      if (id.isEmpty()) throw IllegalStateException("id must not be empty")
      DetailPusatInformasiScreen(
        modifier = modifier,
        id = id
      )
    }

    // Penilaian Peserta

    // kelompok mentoring
    composable(Screen.Peserta.KelompokMentoring.route) {
      KelompokMentoringScreen(
        modifier = modifier,
      )
    }

    // pengumuman
    composable(Screen.Peserta.PengumumanPeserta.route) {
      PengumumanPesertaScreen(
        modifier = modifier,
      )
    }
    composable(
      route = "pengumuman-peserta/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
      val id = backStackEntry.arguments?.getString("id") ?: ""
      if (id.isEmpty()) throw IllegalStateException("id must not be empty")
      DetailPengumumanPesertaScreen(
        modifier = modifier.padding(
        ), id = id
      )
    }

    // pengaturan
    composable(
      route = Screen.Peserta.Pengaturan.route,
    ) {
      PengaturanScreen(
        modifier = modifier,
      )
    }

    // worksheet peserta
    composable(Screen.Peserta.WorksheetPeserta.route) {
      val onNavigateDetailWorksheetPeserta = { id : String ->
        navController.navigateSingleTop("worksheet-peserta/$id")
      }
      WorksheetPesertaScreen(
        modifier = modifier,
        onNavigateDetailWorksheetPeserta = onNavigateDetailWorksheetPeserta
      )
    }
    composable(
      route = "worksheet-peserta/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
      val id = backStackEntry.arguments?.getString("id") ?: ""
      if (id.isEmpty()) throw IllegalStateException("id must not be empty")
      DetailWorksheetPesertaScreen(
        modifier = modifier,
        id = id
      )
    }


    composable(
      route = Screen.Peserta.PenilaianPeserta.route,
    ) {
      PenilaianPesertaScreen(
        modifier = modifier,
      )
    }

    // Feedback Peserta
    composable(
      route = Screen.Peserta.FeedbackPeserta.route,
    ) {
      FeedbackPesertaScreen(
        modifier = modifier,
      )
    }

    // form feedback mentor
    composable(
      route = Screen.Peserta.FormFeedbackMentor.route
    ) {

      val onNavigateNext = { id : Int ->
        navController.navigateSingleTop("form-mentor2/$id")
      }

      FeedbackMentorScreen1(
        modifier = modifier,
        onNavigateNextForm = onNavigateNext
      )
    }

    composable(
      route = "form-mentor2/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
      val onNavigateNext = { id : Int ->
        navController.navigateSingleTop("form-mentor3/$id")
      }
      val onNavigateBack = { id : Int ->
        navController.navigateSingleTop("form-mentor")
      }
      FeedbackMentorScreen2(
        modifier = modifier,
        onNavigateNextForm = onNavigateNext,
        onNavigateBackForm = onNavigateBack,
        id = it.arguments?.getString("id") ?: "1"
      )
    }

    composable(
      route = "form-mentor3/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
      val onNavigateNext = { id : Int ->
        navController.navigateSingleTop("form-mentor4/$id")
      }
      val onNavigateBack = { id : Int ->
        navController.navigateSingleTop("form-mentor2/$id")
      }
      FeedbackMentorScreen3(
        modifier = modifier,
        onNavigateNextForm = onNavigateNext,
        onNavigateBackForm = onNavigateBack,
        id = it.arguments?.getString("id") ?: "1"
      )
    }
//
    composable(
      route = "form-mentor4/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
      val id = it.arguments?.getString("id") ?: "1"

      FeedbackMentorScreen4(
        modifier = modifier,
        id = it.arguments?.getString("id") ?: "1"
      )
    }

  }


}
