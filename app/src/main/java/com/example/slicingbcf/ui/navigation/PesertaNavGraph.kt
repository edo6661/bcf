package com.example.slicingbcf.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.slicingbcf.implementation.peserta.check_status_registrasi.CheckStatusRegistrasiScreen
import com.example.slicingbcf.implementation.peserta.data_peserta.DataPesertaScreen
import com.example.slicingbcf.implementation.peserta.data_peserta.RingkasanDataPeserta
import com.example.slicingbcf.implementation.peserta.feedback_peserta.FeedbackPesertaScreen
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.FeedbackMentorScreen
import com.example.slicingbcf.implementation.peserta.form_monthly_report.DetailFormMonthlyReportScreen
import com.example.slicingbcf.implementation.peserta.form_monthly_report.FormMonthlyReportScreen
import com.example.slicingbcf.implementation.peserta.kelompok_mentoring.KelompokMentoringScreen
import com.example.slicingbcf.implementation.peserta.pengaturan.PengaturanScreen
import com.example.slicingbcf.implementation.peserta.pengumuman_peserta.DetailPengumumanPesertaScreen
import com.example.slicingbcf.implementation.peserta.pengumuman_peserta.PengumumanPesertaScreen
import com.example.slicingbcf.implementation.peserta.penilaian_peserta.PenilaianPesertaScreen
import com.example.slicingbcf.implementation.peserta.profil.profil_lembaga.ProfilLembagaScreen
import com.example.slicingbcf.implementation.peserta.profil.profil_peserta.ProfilPesertaScreen
import com.example.slicingbcf.implementation.peserta.pusat_informasi.DetailPusatInformasiScreen
import com.example.slicingbcf.implementation.peserta.pusat_informasi.PusatInformasiScreen
import com.example.slicingbcf.implementation.peserta.pusat_informasi.SearchPusatInformasiScreen
import com.example.slicingbcf.implementation.peserta.worksheet_peserta.DetailWorksheetPesertaScreen
import com.example.slicingbcf.implementation.peserta.worksheet_peserta.WorksheetPesertaScreen


@Suppress("t")
fun NavGraphBuilder.pesertaNavGraph(
  modifier : Modifier,
  navController : NavHostController
) {
  navigation(
    startDestination = Screen.Peserta.DataPeserta.route, route = "peserta"
  ) {
    // Data Peserta
    composable(Screen.Peserta.DataPeserta.route) {

      val onNavigateDetailDataPeserta = { id : String ->
        navController.navigateSingleTop("data-peserta/$id")
      }
      DataPesertaScreen(
        modifier = modifier,
        onNavigateDetailDataPeserta = onNavigateDetailDataPeserta
      )
    }

    composable(
      route = "data-peserta/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
      val id = backStackEntry.arguments?.getString("id") ?: ""
      if (id.isEmpty()) throw IllegalStateException("id must not be empty")

      val onNextClick = { navController.navigateSingleTop("profil-peserta")
      }

      RingkasanDataPeserta(
        modifier = modifier,
        onNextClick = onNextClick
      )
    }

    composable(Screen.ProfilLembaga.route) {
      val onNextClick = { navController.navigateSingleTop("profil-peserta")
      }
      ProfilLembagaScreen(
        modifier = modifier,
        onNextClick = onNextClick
      )
    }


    composable(Screen.ProfilPeserta.route) {
      val onPreviousClick = { navController.navigateSingleTop("data-peserta/$id")
      }
      ProfilPesertaScreen(
        modifier = modifier,
        onPreviousClick = onPreviousClick
      )
    }

    // pusat informasi
    composable(
      route = Screen.Peserta.PusatInformasi.route,
    ) {
      val onNavigateDetailPusatInformasi = { id : String ->
        navController.navigateSingleTop("pusat-informasi/$id")
      }
      val onNavigateSearchPusatInformasi = {  ->
        navController.navigateSingleTop(Screen.Peserta.SearchPusatInformasi.route)
      }
      PusatInformasiScreen(
        modifier = modifier,
        onNavigateDetailPusatInformasi = onNavigateDetailPusatInformasi,
        onNavigateSearchPusatInformasi = onNavigateSearchPusatInformasi

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
    composable(
      route = Screen.Peserta.SearchPusatInformasi.route
    ) {

      val onNavigateDetailPusatInformasi = { id : String ->
        navController.navigateSingleTop("forum-diskusi/$id")
      }

      SearchPusatInformasiScreen (
        modifier = modifier,
        onNavigateDetailPusatInformasi = onNavigateDetailPusatInformasi
      )
    }


    // Penilaian Peserta
    composable(
      route = Screen.Peserta.PenilaianPeserta.route,
    ) {
      PenilaianPesertaScreen(
        modifier = modifier,
      )
    }



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
        onNavigateDetailPengumuman = { id : String ->
          navController.navigateSingleTop("pengumuman-peserta/$id")
        }
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
      FeedbackMentorScreen(
        modifier = modifier
      )
    }
    // check status registrasi
    composable(
      route = Screen.Peserta.CheckStatusRegistrasi.route
    ) {
      CheckStatusRegistrasiScreen(
        modifier = modifier
      )
    }
    // form monthly report
    composable(
      route = Screen.Peserta.FormMonthlyReport.route,
    ) {
      val onNavigateDetailFormMonthlyReport = { id : String ->
        navController.navigateSingleTop("form-monthly-report/$id")
      }
      FormMonthlyReportScreen(
        modifier = modifier,
        onNavigateDetailFormMonthlyReport = onNavigateDetailFormMonthlyReport
      )
    }
    // detail form monthly report
    composable(
      route = "form-monthly-report/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType }),
      enterTransition = {
        slideInHorizontally(
          initialOffsetX = { it },
          animationSpec = tween(700)
        )
      },
      exitTransition = {
        slideOutHorizontally(
          targetOffsetX = { it },
          animationSpec = tween(700)
        )
      }

    ) { backStackEntry ->
      val id = backStackEntry.arguments?.getString("id") ?: ""
      if (id.isEmpty()) throw IllegalStateException("id must not be empty")

      val onNavigateBack = {
        navController.popBackStack()
      }

      DetailFormMonthlyReportScreen(
        modifier = modifier,
        id = id,
        onNavigateBack = onNavigateBack
      )
    }
  }
}
