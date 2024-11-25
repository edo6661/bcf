package com.example.slicingbcf.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.slicingbcf.implementation.mentor.data_peserta.DataPesertaMentorScreen
import com.example.slicingbcf.implementation.mentor.feedback_peserta.FeedbackPesertaScreen
import com.example.slicingbcf.implementation.mentor.forum_diskusi.DetailForumDiskusiScreen
import com.example.slicingbcf.implementation.mentor.forum_diskusi.ForumDiskusiScreen
import com.example.slicingbcf.implementation.mentor.penilaian_peserta.DetailPenilaianPesertaScreen
import com.example.slicingbcf.implementation.mentor.penilaian_peserta.PenilaianPesertaScreen
import com.example.slicingbcf.implementation.mentor.pitchdeck.DetailPitchdeckScreen
import com.example.slicingbcf.implementation.mentor.pitchdeck.MoreDetailPitchdeckScreen
import com.example.slicingbcf.implementation.mentor.pitchdeck.PitchdeckScreen

fun NavGraphBuilder.mentorNavGraph(
  modifier : Modifier,
  navController : NavHostController
) {
  navigation(
    startDestination = Screen.Mentor.MoreDetailPitchdeck("1").route, route = "mentor"
  ) {
    composable(
      route = Screen.Mentor.PenilaianPeserta.route
    ) {

      val onNavigateDetailPenilaianPeserta = { id : String ->
        navController.navigate("penilaian-peserta/$id")
      }

      PenilaianPesertaScreen(
        modifier = modifier,
        onNavigateDetailPenilaianPeserta = onNavigateDetailPenilaianPeserta
      )
    }
    composable(
      route = "penilaian-peserta/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {


      DetailPenilaianPesertaScreen(
        modifier = modifier,
        id = it.arguments?.getString("id") ?: "1"
      )
    }
    composable(
      route = Screen.Mentor.FeedbackPeserta.route
    ) {
      FeedbackPesertaScreen(
        modifier = modifier
      )
    }
    composable(
      route = Screen.Mentor.Pitchdeck.route
    ) {

      val onNavigateDetailPitchdeck = { id : String ->
        navController.navigateSingleTop("pitchdeck/$id")
      }

      PitchdeckScreen(
        modifier = modifier,
        onNavigateDetailPitchdeck = onNavigateDetailPitchdeck
      )
    }
    composable(
      route = "pitchdeck/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {

      val onNavigateMoreDetailPitchdeck = { id : String ->
        navController.navigateSingleTop("pitchdeck/$id/more")
      }

      DetailPitchdeckScreen(
        modifier = modifier,
        onNavigateMoreDetailPitchdeck = onNavigateMoreDetailPitchdeck,
        id = it.arguments?.getString("id") ?: "1"
      )
    }

    composable(
      route = "pitchdeck/{id}/more",
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
      ForumDiskusiScreen(
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
  }


}