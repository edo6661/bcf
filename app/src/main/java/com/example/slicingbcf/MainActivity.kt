package com.example.slicingbcf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.slicingbcf.ui.navigation.NavGraph
import com.example.slicingbcf.ui.scaffold.MainScaffold
import com.example.slicingbcf.ui.scaffold.scaffoldConfig
import com.example.slicingbcf.ui.theme.SlicingBcfTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {

      val navController = rememberNavController()
      val currentBackStackEntry = navController.currentBackStackEntryAsState()
      val currentRoute = currentBackStackEntry.value?.destination?.route

      fun isActiveRoute(route : String) : Boolean {
        return currentRoute == route
      }


      SlicingBcfTheme {

        MainScaffold(
          config = scaffoldConfig(currentRoute),
          isActiveRoute = ::isActiveRoute,
          navController = navController,
        ) { paddingValues ->
          NavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
          )
        }
      }
    }
  }
}
