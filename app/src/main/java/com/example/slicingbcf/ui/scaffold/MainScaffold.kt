package com.example.slicingbcf.ui.scaffold

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.navigation.Screen
import com.example.slicingbcf.ui.navigation.navigateSingleTop
import com.example.slicingbcf.ui.sidenav.OverlayNav
import com.example.slicingbcf.ui.sidenav.SideNav
import com.example.slicingbcf.ui.sidenav.SideNavContent

// TODO: benerin agar jika pertama kali klik menu, itu tidak lag
@Composable
fun MainScaffold(
  config : ScaffoldConfig,
  navController : NavHostController,
  isActiveRoute : (String) -> Boolean,
  content : @Composable (PaddingValues) -> Unit,
) {

  var isSideNavVisible by remember { mutableStateOf(false) }
  val offSetXSideNav by animateFloatAsState(
    targetValue = if (isSideNavVisible) 0f else 1f,
    animationSpec = tween(durationMillis = 800),
    label = "SideNavOffSet"
  )
  val closeSideNavVisible = {
    isSideNavVisible = false
  }
  val onNavigateHome = {
    navController.navigateSingleTop(Screen.Home.route)
  }
  val onNavigateBack = {
    navController.popBackStack()
  }
  Box(
    modifier = Modifier.fillMaxSize()

  ) {
    Scaffold(
      topBar = {
        when {
          config.showMainNav -> PrimaryNav(
            onMenuClick = {
              isSideNavVisible = ! isSideNavVisible
            },
            onNavigateHome = onNavigateHome
          )

          config.showBackNav -> BackNav(
            onBackClick = {
              onNavigateBack()
            }

          )
        }
      }
    ) {
      content(it)
    }

    OverlayNav(
      isSideNavVisible = isSideNavVisible,
      onTap = {
        isSideNavVisible = ! isSideNavVisible
      }
    )
    SideNav(
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .zIndex(1f),
      isSideNavVisible = isSideNavVisible,
      offSetX = offSetXSideNav,
    ) {
      SideNavContent(
        navController = navController,
        closeSideNavVisible = closeSideNavVisible,
        isActiveRoute = isActiveRoute
      )
    }


  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryNav(
  onMenuClick : () -> Unit,
  onNavigateHome : () -> Unit
) {
  TopAppBar(
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = ColorPalette.Monochrome100,
    ),
    modifier = Modifier
      .fillMaxWidth()
      .statusBarsPadding(),
    title = {
      Image(
        painter = painterResource(id = R.drawable.logo_lead),
        contentDescription = stringResource(id = R.string.logo),
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(
            width = 56.dp,
            height = 35.dp
          )
          .clickable {
            onNavigateHome()

          }
      )
    },
    actions = {
      IconButton(
        onClick = onMenuClick
      ) {
        Icon(
          imageVector = Icons.Default.Menu,
          contentDescription = "Menu"
        )
      }
    }
  )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackNav(
  onBackClick : () -> Unit
) {
  TopAppBar(
    title = {
      Card(
        onClick = {
          onBackClick()
        },
        colors = CardDefaults.cardColors(
          containerColor = Color.Transparent,
          contentColor = ColorPalette.PrimaryColor700
        )
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp),

          ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
          )
          Text(
            text = "Kembali",
            style = StyledText.MobileBaseRegular
          )
        }

      }
    }
  )

}
