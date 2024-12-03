package com.example.slicingbcf.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.slicingbcf.data.local.listBatch
import com.example.slicingbcf.data.local.mentor
import com.example.slicingbcf.implementation.LandingPageScreen
import com.example.slicingbcf.implementation.SplashScreen
import com.example.slicingbcf.implementation.mentor.profil.ProfilMentorScreen
import com.example.slicingbcf.implementation.peserta.pengumuman_peserta.DetailPengumumanPesertaScreen
import com.example.slicingbcf.implementation.peserta.pengumuman_peserta.PengumumanPesertaScreen
import com.example.slicingbcf.implementation.peserta.profil.profil_lembaga.ProfilLembagaScreen
import com.example.slicingbcf.implementation.peserta.profil.profil_peserta.ProfilPesertaScreen

@Composable
fun NavGraph(
  navController : NavHostController,
  startDestination : String = "peserta",
  modifier : Modifier,
) {
  NavHost(navController = navController, startDestination = startDestination) {
    composable("splash-screen") {
      SplashScreen(
        onNavigateToMain = { navController.navigate("home") }
      )
    }
    composable(
      route = Screen.Home.route,
//      ! Uncomment kalo dibutuhin
//      enterTransition = {
//        slideInHorizontally(
//          initialOffsetX = { it },
//          animationSpec = tween(700)
//        )
//      }
    ) {
      LandingPageScreen(
        modifier = modifier,
      )
    }
    authNavGraph(
      modifier = modifier,
      navController = navController
    )
    pesertaNavGraph(
      modifier = modifier,
      navController = navController
    )
    mentorNavGraph(
      modifier = modifier,
      navController = navController
    )
    tugasNavGraph(
      modifier = modifier,
      navController = navController
    )
    kegiatanNavGraph(
      modifier = modifier,
      navController = navController
    )


    composable(
      route = "pengumuman/{id}",
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
      val id = backStackEntry.arguments?.getString("id") ?: ""
      if (id.isEmpty()) throw IllegalStateException("id must not be empty")

      DetailPengumumanPesertaScreen(
        modifier = modifier.padding(
        ), id = id
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
      val onPreviousClick = { navController.navigateSingleTop("profil-lembaga")
      }
      ProfilPesertaScreen(
        modifier = modifier,
        onPreviousClick = onPreviousClick
      )
    }

    composable(
      route = Screen.ProfilMentor.route ) {
      ProfilMentorScreen(
        modifier = modifier,
        mentor = mentor,
        batches = listBatch
      )
    }
  }
}
