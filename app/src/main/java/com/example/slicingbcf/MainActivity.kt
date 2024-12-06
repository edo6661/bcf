package com.example.slicingbcf

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.slicingbcf.data.viewmodel.UserEvent
import com.example.slicingbcf.data.viewmodel.UserViewModel
import com.example.slicingbcf.ui.animations.AnimatedMessage
import com.example.slicingbcf.ui.animations.MessageType
import com.example.slicingbcf.ui.navigation.NavGraph
import com.example.slicingbcf.ui.navigation.Screen
import com.example.slicingbcf.ui.navigation.navigateAndClearStackButHome
import com.example.slicingbcf.ui.scaffold.MainScaffold
import com.example.slicingbcf.ui.scaffold.scaffoldConfig
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator
import com.example.slicingbcf.ui.theme.SlicingBcfTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  // test branch
  private val userViewModel : UserViewModel by viewModels()


  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()


    setContent {
      val state by userViewModel.state.collectAsState()

      Log.d("MainActivity", "onCreate USER: ${state.user?.username}")


      val navController = rememberNavController()
      val currentBackStackEntry = navController.currentBackStackEntryAsState()
      val currentRoute = currentBackStackEntry.value?.destination?.route
      fun isActiveRoute(route : String) : Boolean {
        return currentRoute == route
      }
      SlicingBcfTheme {

        Box() {

          MainScaffold(
            config = scaffoldConfig(currentRoute),
            isActiveRoute = ::isActiveRoute,
            user = state.user,
            logout = {
              userViewModel.onEvent(UserEvent.Logout)
              navController.navigateAndClearStackButHome(Screen.Home.route)
            },
            getNewAccessToken = {
              userViewModel.onEvent(UserEvent.GetNewAccessToken)
            },
            navController = navController,

            ) { paddingValues ->
            NavGraph(
              navController = navController,
              modifier = Modifier
                .padding(paddingValues)


            )

          }
          AnimatedMessage(
            isVisible = state.error != null,
            message = state.error ?: "",
            messageType = MessageType.Error,
            modifier = Modifier
              .padding(top = 16.dp)
              .align(Alignment.TopCenter)
          )
          if (state.isLoading) {
            LoadingCircularProgressIndicator()
          }
        }
      }
    }
  }


}
