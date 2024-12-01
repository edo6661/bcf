package com.example.slicingbcf.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.slicingbcf.implementation.peserta.pitch_deck.PitchDeckDetailScreen
import com.example.slicingbcf.implementation.peserta.pitch_deck.PitchDeckPesertaScreen

fun NavGraphBuilder.tugasNavGraph(
    modifier : Modifier,
    navController : NavHostController
) {
    navigation(
        startDestination = Screen.Tugas.PitchDeck.route, route = "tugas"
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

            PitchDeckDetailScreen(
                modifier = modifier,
                id = id
            )
        }
    }
}