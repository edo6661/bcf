package com.example.slicingbcf.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.slicingbcf.implementation.peserta.pitch_deck.PitchDeckDetailScreen
import com.example.slicingbcf.implementation.peserta.pitch_deck.PitchDeckPesertaScreen

fun NavGraphBuilder.tugasNavGraph(
    modifier : Modifier,
    navController : NavHostController
) {
    navigation(
        startDestination = Screen.Tugas.PitchDeckDetail("1").route, route = "tugas"
    ) {
        composable(
            route = Screen.Tugas.PitchDeck.route
        ){
            val onNavigateDetailPitchDeckPeserta = { id : String ->
                navController.navigateSingleTop("detail-pitchdeck/$id")
            }
            PitchDeckPesertaScreen(
                modifier = modifier,
                onNavigatePitchDeckPeserta = onNavigateDetailPitchDeckPeserta
            )
        }

        composable(
            route = "detail-pitchdeck/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            if (id.isEmpty()) throw IllegalStateException("id must not be empty")

            val onNavigateBeranda = { id : Int ->
                navController.navigateSingleTop("home")
            }
            PitchDeckDetailScreen(
                modifier = modifier,
                id = id,
                onNavigateBeranda = onNavigateBeranda
            )
        }
    }
}