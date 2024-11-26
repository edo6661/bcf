package com.example.slicingbcf.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.slicingbcf.implementation.mentor.jadwal.bulan.JadwalBulanMentorScreen
import com.example.slicingbcf.implementation.mentor.jadwal.detail.DetailJadwalMentorScreen
import com.example.slicingbcf.implementation.mentor.jadwal.minggu.JadwalMingguMentorScreen
import com.example.slicingbcf.implementation.mentor.jadwal.tambah_jadwal.AddJadwalMentorScreen
import com.example.slicingbcf.implementation.mentor.penilaian_peserta.PenilaianPesertaScreen
import com.example.slicingbcf.implementation.peserta.form_feedback_mini_training.FormMiniTrainingScreen
import com.example.slicingbcf.implementation.peserta.jadwal.bulanan.JadwalMentoringBulanScreen
import com.example.slicingbcf.implementation.peserta.jadwal.detail.DetailJadwalScreen
import com.example.slicingbcf.implementation.peserta.jadwal.mingguan.JadwalMentoringMingguScreen
import com.example.slicingbcf.implementation.peserta.pitch_deck.PitchDeckPesertaScreen

fun NavGraphBuilder.kegiatanNavGraph(
    modifier : Modifier,
    navController : NavHostController
) {
    navigation(
        startDestination = Screen.Kegiatan.UmpanBalikKegiatan.route, route = "kegiatan"
    ) {
        composable(
            route = Screen.Kegiatan.UmpanBalikKegiatan.route
        ) {
            FormMiniTrainingScreen(
                modifier = modifier
            )
        }
    }

    composable(
        route = "jadwal-bulan-peserta"
    ) {
        val onNavigateWeeklyCalendar = { id : String ->
            navController.navigateSingleTop("jadwal-minggu-peserta/$id")
        }
        val onNavigateDetailScreen = { id : String ->
            navController.navigateSingleTop("detail-jadwal-mentor/$id")
        }
        JadwalMentoringBulanScreen(
            modifier = modifier,
            onNavigateWeeklyCalendar = onNavigateWeeklyCalendar,
            onNavigateDetailScreen = onNavigateDetailScreen
        )
    }

    composable(
        route = "jadwal-minggu-peserta/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        if (id.isEmpty()) throw IllegalStateException("id must not be empty")

        val onNavigateMonthlyCalendar = { _: String ->
            navController.navigateSingleTop("jadwal-bulan-peserta")
        }

        val onNavigateDetailCalendar = { id: String ->
            navController.navigateSingleTop("detail-jadwal-peserta/$id")
        }
        JadwalMentoringMingguScreen(
            modifier = modifier,
            id = id,
            onNavigateMonthlyCalendar = onNavigateMonthlyCalendar,
            onNavigateDetailCalendar = onNavigateDetailCalendar
        )
    }

    composable(
        route = "detail-jadwal-peserta/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        println("id diterima: $id")
        if (id.isEmpty()) throw IllegalStateException("id must not be empty")

        DetailJadwalScreen(
            modifier = modifier,
            id = id
        )
    }

    composable(
        route = "jadwal-bulan-mentor"
    ) {
        val onNavigateWeeklyCalendar = { id : String ->
            navController.navigateSingleTop("jadwal-minggu-mentor/$id")
        }
        val onNavigateDetailScreen = { id : String ->
            navController.navigateSingleTop("detail-jadwal-mentor/$id")
        }
        val onNavigateAddCalendar = { id: String ->
            navController.navigateSingleTop("add-jadwal-mentor/$id")
        }
        JadwalBulanMentorScreen(
            modifier = modifier,
            onNavigateWeeklyCalendar = onNavigateWeeklyCalendar,
            onNavigateDetailScreen = onNavigateDetailScreen,
            onNavigateAddCalendar = onNavigateAddCalendar
        )
    }

    composable(
        route = "jadwal-minggu-mentor/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        if (id.isEmpty()) throw IllegalStateException("id must not be empty")

        val onNavigateMonthlyCalendar = { _: String ->
            navController.navigateSingleTop("jadwal-bulan-mentor")
        }
        val onNavigateDetailCalendar = { id: String ->
            navController.navigateSingleTop("detail-jadwal-mentor/$id")
        }
        val onNavigateAddCalendar = { id: String ->
            navController.navigateSingleTop("add-jadwal-mentor/$id")
        }

        JadwalMingguMentorScreen(
            modifier = modifier,
            id = id,
            onNavigateMonthlyCalendar = onNavigateMonthlyCalendar,
            onNavigateDetailCalendar = onNavigateDetailCalendar,
            onNavigateAddCalendar = onNavigateAddCalendar
        )
    }

    composable(
        route = "detail-jadwal-mentor/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        println("ID diterima di NavHost: $id")
        if (id.isEmpty()) throw IllegalStateException("id must not be empty")

        DetailJadwalMentorScreen(
            modifier = modifier,
            id = id
        )
    }

    composable(
        route = "add-jadwal-mentor/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        println("ID diterima di NavHost: $id")
        if (id.isEmpty()) throw IllegalStateException("id must not be empty")

        AddJadwalMentorScreen(
            modifier = modifier,
            id = id
        )
    }
}