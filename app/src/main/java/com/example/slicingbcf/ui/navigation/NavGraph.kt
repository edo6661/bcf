package com.example.slicingbcf.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.slicingbcf.implementation.LandingPageScreen

@Composable
fun NavGraph(
  navController : NavHostController,
  startDestination : String = "auth",
  modifier : Modifier,
) {
  NavHost(navController = navController, startDestination = startDestination) {
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

  }
}
