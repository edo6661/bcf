package com.example.slicingbcf.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.slicingbcf.implementation.mentor.data_peserta.DataPesertaMentorScreen
import com.example.slicingbcf.implementation.mentor.feedback_peserta.FormFeedbackPesertaMentorScreen
import com.example.slicingbcf.implementation.mentor.forum_diskusi.DetailForumDiskusiScreen
import com.example.slicingbcf.implementation.mentor.forum_diskusi.ForumDiskusiScreen
import com.example.slicingbcf.implementation.mentor.forum_diskusi.SearchForumDiskusiScreen
import com.example.slicingbcf.implementation.mentor.kelompok_mentoring_mentor.KelompokMentoringMentorScreen
import com.example.slicingbcf.implementation.mentor.laporan.LaporanDataPesertaScreen
import com.example.slicingbcf.implementation.mentor.pengaturan_mentor.PengaturanMentorScreen
import com.example.slicingbcf.implementation.mentor.pengumuman_mentor.DetailPengumumanMentorScreen
import com.example.slicingbcf.implementation.mentor.pengumuman_mentor.PengumumanMentorScreen
import com.example.slicingbcf.implementation.mentor.penilaian_peserta.DetailPenilaianPesertaScreenMentor
import com.example.slicingbcf.implementation.mentor.penilaian_peserta.PenilaianPesertaScreenMentor
import com.example.slicingbcf.implementation.mentor.pitchdeck.MoreDetailPitchdeckScreen
import com.example.slicingbcf.implementation.mentor.pitchdeck.PitchdeckScreen
import com.example.slicingbcf.implementation.mentor.umpan_balik.DetailUmpanBalikMentorScreen
import com.example.slicingbcf.implementation.mentor.umpan_balik.UmpanBalikMentorScreen
import com.example.slicingbcf.implementation.mentor.worksheet_mentor.DetailWorksheetMentorScreen
import com.example.slicingbcf.implementation.mentor.worksheet_mentor.WorksheetMentorScreen

fun NavGraphBuilder.mentorNavGraph(
  modifier : Modifier,
  navController : NavHostController
) {
  navigation(
    startDestination = Screen.Mentor.PenilaianPeserta.route, route = "mentor"
  ) {
    composable(
      route = Screen.Mentor.PenilaianPeserta.route
    ) {

      val onNavigateDetailPenilaianPeserta = { id : String ->
        navController.navigate("penilaian-peserta-mentor/$id")
      }
      PenilaianPesertaScreenMentor (
        modifier = modifier,
        onNavigateDetailPenilaianPeserta = onNavigateDetailPenilaianPeserta
      )
    }
    composable(
      route = "penilaian-peserta-mentor/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {


      DetailPenilaianPesertaScreenMentor(
        modifier = modifier,
        id = it.arguments?.getString("id") ?: "1"
      )
    }
    composable(
      route = Screen.Mentor.FormFeedbackPeserta.route
    ) {
      FormFeedbackPesertaMentorScreen(
        modifier = modifier
      )
    }
    composable(
      route = Screen.Mentor.Pitchdeck.route
    ) {

      val onNavigateDetailPitchdeck = { id : String ->
        navController.navigateSingleTop("pitchdeck-mentor/$id/more")
      }

      PitchdeckScreen(
        modifier = modifier,
        onNavigateDetailPitchdeck = onNavigateDetailPitchdeck
      )
    }
    composable(
      route = "pitchdeck-mentor/{id}/more",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
      val id = it.arguments?.getString("id") ?: "1"

      MoreDetailPitchdeckScreen(
        modifier = modifier,

        )
    }

    composable(
      route = Screen.Mentor.ForumDiskusi.route
    ) {
      val onNavigateDetailForumDiskusi = { id : String ->
        navController.navigateSingleTop("forum-diskusi/$id")
      }
      val onNavigateSearchForumDiskusi = {
        navController.navigateSingleTop(Screen.Mentor.SearchForumDiskusi.route)
      }
      ForumDiskusiScreen(
        modifier = modifier,
        onNavigateDetailForumDiskusi = onNavigateDetailForumDiskusi,
        onNavigateSearchForumDiskusi = onNavigateSearchForumDiskusi
      )
    }
    composable(
      route = Screen.Mentor.SearchForumDiskusi.route
    ) {

      val onNavigateDetailForumDiskusi = { id : String ->
        navController.navigateSingleTop("forum-diskusi/$id")
      }

      SearchForumDiskusiScreen(
        modifier = modifier,
        onNavigateDetailForumDiskusi = onNavigateDetailForumDiskusi
      )
    }
    composable(
      route = "forum-diskusi/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
      DetailForumDiskusiScreen(
        modifier = modifier,
        id = it.arguments?.getString("id") ?: "1"
      )
    }
    composable(
      route = Screen.Mentor.DataPeserta.route
    ) {
      DataPesertaMentorScreen(
        modifier = modifier,

        )
    }
    composable(
      route = Screen.Mentor.Laporan.route
    ) {
      LaporanDataPesertaScreen(
        modifier = Modifier
      )
    }

    composable(
      route = Screen.Mentor.UmpanBalikMentor.route
    ){
      val onNavigateDetailUmpanBalikMentor = { id : String ->
        navController.navigateSingleTop("umpan-balik-mentor/$id")
      }

      UmpanBalikMentorScreen(
        modifier = modifier,
        onNavigateDetailUmpanBalikMentor = onNavigateDetailUmpanBalikMentor
      )
    }

    composable(
      route = "umpan-balik-mentor/{id}",
      arguments = listOf(navArgument("id"){type = NavType.StringType})
    ) {
      DetailUmpanBalikMentorScreen(
        modifier = modifier,
        id = it.arguments?.getString("id") ?: "1"
      )
    }

    composable(
      route = Screen.Mentor.KelompokMentoring.route,
    ) {
      KelompokMentoringMentorScreen(
        modifier = modifier,
      )
    }
    composable(
      route = Screen.Mentor.Pengaturan.route,
    ) {
      PengaturanMentorScreen(
        modifier = modifier,
      )
    }

    composable(
      route = Screen.Mentor.Pengumuman.route
    ) {
      val onNavigateDetailPengumuman = { id : String ->
        navController.navigateSingleTop("pengumuman-mentor/$id")
      }
      PengumumanMentorScreen(
        modifier = modifier,
        onNavigateDetailPengumuman = onNavigateDetailPengumuman
      )
    }
    composable(
      route = "pengumuman-mentor/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
      val id = backStackEntry.arguments?.getString("id") ?: ""
      if (id.isEmpty()) throw IllegalStateException("id must not be empty")
      DetailPengumumanMentorScreen(
        modifier = modifier.padding(
        ), id = id
      )
    }
    composable(
      route = Screen.Mentor.WorksheetMentor.route
    ) {
      val onNavigateDetailWorksheet = { id : String ->
        navController.navigateSingleTop("worksheet-mentor/$id")
      }
      WorksheetMentorScreen (
        modifier = modifier,
        onNavigateDetailWorksheet = onNavigateDetailWorksheet
      )
    }
    composable(
      route = "worksheet-mentor/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
      val id = backStackEntry.arguments?.getString("id") ?: ""
      if (id.isEmpty()) throw IllegalStateException("id must not be empty")
      DetailWorksheetMentorScreen(
        modifier = modifier,
        id = id
      )
    }


  }


}