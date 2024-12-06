package com.example.slicingbcf.ui.sidenav

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.domain.model.User
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicator
import com.example.slicingbcf.ui.navigation.Screen
import com.example.slicingbcf.ui.navigation.navigateSingleTop
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.dialog.CustomAlertDialog
import kotlinx.coroutines.delay

@Composable
fun SideNav(
  modifier : Modifier,
  isSideNavVisible : Boolean,
  offSetX : Float,
  content : @Composable () -> Unit,
) {
  val width = 275
  val x = width * offSetX
  Box(
    modifier = modifier
      .clip(
        RoundedCornerShape(
          topStart = 20.dp,
        )
      )
      .fillMaxHeight()
      .width(width.dp)
      .offset(x.dp)
      .background(
        ColorPalette.OnPrimary
      )
      .animateContentSize()
      .pointerInput(Unit) {
        if (isSideNavVisible) {
          detectTapGestures(
            onTap = {}
          )
        }
      }

  ) {
    content()
  }
}

@Composable
fun SideNavContent(
  navController : NavHostController,
  closeSideNavVisible : () -> Unit,
  isActiveRoute : (String) -> Boolean,
  logout : () -> Unit,
  user : User?,
  getNewAccessToken : () -> Unit

) {
  val navigateAndCloseSideNav : (String) -> Unit = { route ->
    closeSideNavVisible()
    navController.navigateSingleTop(route)
  }
  Column(
    modifier = Modifier
      .fillMaxHeight()
      .padding(
        top = 60.dp,
        bottom = 60.dp,
        start = 40.dp,
        end = 26.dp
      ),

    verticalArrangement = Arrangement.spacedBy(36.dp)
  ) {

    TopSideNav()

    Box(
      modifier = Modifier
        .fillMaxHeight()
        .weight(1f)
    ) {
      BottomSideNav(
        navigateAndCloseSideNav = navigateAndCloseSideNav,
        isActiveRoute = isActiveRoute,
        logout = logout,
        getNewAccessToken = getNewAccessToken,
        closeSideNavVisible = closeSideNavVisible,
        user = user
      )
    }


  }
}

@Composable
private fun TopSideNav() {
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Image(
      painter = painterResource(id = R.drawable.logo_lead),
      contentDescription = "image description",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .width(56.dp)
        .height(34.dp)
    )
    Text(
      text = buildAnnotatedString {
        withStyle(
          style = SpanStyle(
            color = ColorPalette.PrimaryColor700,
          )
        ) {
        append("LEAD\n")
        }
          append("INDONESIA")
      },
      style = StyledText.MobileXlBold,
    )
  }


}

@Composable
private fun BottomSideNav(
  navigateAndCloseSideNav : (String) -> Unit,
  isActiveRoute : (String) -> Boolean,
  logout : () -> Unit,
  closeSideNavVisible : () -> Unit,
  user : User?,
  getNewAccessToken : () -> Unit
) {

  val scroll = rememberScrollState()

  var showLogoutDialog by remember { mutableStateOf(false) }

  var isLoading by remember { mutableStateOf(false) }

  val context = LocalContext.current


  val onNavigateModul = {
    isLoading = true
  }

  LaunchedEffect(
    isLoading
  ) {
    if(isLoading) {
      delay(3000)
      isLoading = false
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/new?tgif=d"))
      context.startActivity(intent)
    }
  }

  Box(
  ) {
    Column(
      modifier = Modifier
        .fillMaxHeight()
        .verticalScroll(
          scroll
        ),
      verticalArrangement = Arrangement.SpaceBetween,
    ) {
      Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        PrimaryButton(
          text = "New Access Token",
          onClick = {
            getNewAccessToken()
          }
        )
        when {
          user == null -> {
            SideNavDropdownGuest(
              navigateAndCloseSideNav,
              isActiveRoute
            )
          }

          user.role == "USER" -> {
            SideNavDropdownPeserta(
              navigateAndCloseSideNav,
              isActiveRoute,
              onNavigateModul = onNavigateModul
            )
          }



        }
      }

      if (user != null) {
        Column(
          verticalArrangement = Arrangement.spacedBy(16.dp),
          modifier = Modifier
            .clickable {
              showLogoutDialog = true
            }
        ) {
          Text(
            text = "Logout",
            style = StyledText.MobileBaseMedium,
            color = Color(0xFFB02A37),
            modifier = Modifier
              .padding(
                horizontal = 16.dp,
              )
              .padding(top = 16.dp)
          )
          HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
          )
        }
      }

    }

    if (showLogoutDialog) {
      CustomAlertDialog(
        title = "Apakah anda yakin ingin keluar?",
        confirmButtonText = "Keluar",
        dismissButtonText = "Batal",
        onConfirm = {
          logout()
          showLogoutDialog = false
          closeSideNavVisible()

        },
        onDismiss = {
          showLogoutDialog = false
        }
      )
    }
    SubmitLoadingIndicator(
      isLoading = isLoading,
      modifier = Modifier.align(Alignment.Center),
      title = "Mengrahkan anda ke:",
      description = "https://sites.google.com/new?tgif=d",
    )
  }

}

@Composable
private fun SideNavDropdown(
  title : String,
  items : List<DropdownItem>? = null,
  onClickDropdown : () -> Unit = {},
  isActiveRoute : (String) -> Boolean,
  route : String ? = null
) {
  var expanded by remember { mutableStateOf(false) }

  val expandedIcon = items?.let {
    when {
      expanded -> Icons.Filled.ExpandLess
      else     -> Icons.Filled.ExpandMore
    }
  }

  fun onClick() {
    when {
      items != null -> expanded = ! expanded
      else          -> onClickDropdown()
    }
  }
  val colorIsActive = if (isActiveRoute(route ?: title)) {
    ColorPalette.PrimaryColor700
  } else {
    ColorPalette.OnSurface
  }
  val textStyle = if (isActiveRoute(route ?: title)) {
    StyledText.MobileBaseSemibold
  } else {
    StyledText.MobileBaseMedium
  }





  Column(
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Column {
      DropdownMenuItem(
        text = {
          Text(
            title,
            style = textStyle,
            color = colorIsActive
          )
        },
        onClick = {
          onClick()
        },
        trailingIcon = {
          if (expandedIcon != null) {
            Icon(
              imageVector = expandedIcon,
              contentDescription = "Expand"
            )
          }
        },
        modifier = Modifier.padding()
      )

      HorizontalDivider()
    }

    items?.let {
      AnimatedVisibility(visible = expanded) {
        Column {
          items.forEach { item ->
            SideNavDropdownItem(
              text = item.text,
              route = item.route,
              onClick = item.onClick,
              isActiveRoute = isActiveRoute
            )
          }
        }
      }
    }

  }
}

@Composable
private fun SideNavDropdownItem(
  text : String,
  route : String?,
  onClick : () -> Unit = {},
  isActiveRoute : (String) -> Boolean
) {
  val colorIsActive = if (isActiveRoute(route ?: text)) {
    ColorPalette.PrimaryColor700
  } else {
    ColorPalette.OnSurface
  }
  val textStyle = if (isActiveRoute(route ?: text)) {
    StyledText.MobileBaseMedium
  } else {
    StyledText.MobileBaseRegular
  }
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(
        horizontal = 4.dp,
        vertical = 4.dp
      ),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Text(
        text = text,
        style = textStyle,
        modifier = Modifier
          .padding(
            8.dp
          ),
        color = colorIsActive
      )
      HorizontalDivider()
    }
  }
}


// TODO: GUEST

@Composable
private fun SideNavDropdownGuest(
  navigateAndCloseSideNav : (String) -> Unit,
  isActiveRoute : (String) -> Boolean
) {
  PrimaryButton(
    text = "Masuk",
    onClick = {
      navigateAndCloseSideNav(Screen.Auth.Login.route)
    }
  )
  SideNavDropdown(
    "Beranda",
    items = null,
    isActiveRoute = isActiveRoute,
    onClickDropdown = {
      navigateAndCloseSideNav(Screen.Home.route)
    },

  )

  SideNavDropdown(
    "Pendaftaran",
    items = dropdownItemsPendaftaran_Guest(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )

  SideNavDropdown(
    "Pusat Informasi",
    items = null,
    isActiveRoute = isActiveRoute,
    onClickDropdown = {
      navigateAndCloseSideNav(Screen.Peserta.PusatInformasi.route)
    }
  )

}


// TODO: SEMUA ROLE BIAR ENAK BISA LIAT SEMUA

//@Composable
//private fun SideNavDropdownGuest(
//  navigateAndCloseSideNav : (String) -> Unit,
//  isActiveRoute : (String) -> Boolean
//) {
//  PrimaryButton(
//    text = "Masuk",
//    onClick = {
//      navigateAndCloseSideNav(Screen.Auth.Login.route)
//    }
//  )
//  SideNavDropdown(
//    "Registrasi",
//    items = dropdownItemsPendaftaran(
//      navigateAndCloseSideNav
//    ),
//    isActiveRoute = isActiveRoute
//  )
//  SideNavDropdown(
//    "Mentor",
//    items = dropdownItemsMentor_Guest(
//      navigateAndCloseSideNav
//    ),
//    isActiveRoute = isActiveRoute
//  )
//  SideNavDropdown(
//    "Tugas",
//    items = dropdownItemsTugas_Guest(
//      navigateAndCloseSideNav
//    ),
//    isActiveRoute = isActiveRoute
//  )
//
//  SideNavDropdown(
//    "Peserta",
//    items = dropdownItemsPeserta_Guest(
//      navigateAndCloseSideNav
//    ),
//    isActiveRoute = isActiveRoute
//  )
//
//  SideNavDropdown(
//    "Kegiatan",
//    items = dropdownItemsKegiatan_Guest(
//      navigateAndCloseSideNav
//    ),
//    isActiveRoute = isActiveRoute
//  )
//}


@Composable
private fun SideNavDropdownMentor(
  navigateAndCloseSideNav : (String) -> Unit,
  isActiveRoute : (String) -> Boolean,
  onNavigateModul : () -> Unit
) {

  SideNavDropdown(
    "Peserta",
    items = dropdownItemsPeserta_Mentor(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )
  SideNavDropdown(
    "Mentor",
    items = dropdownItemsMentor_Mentor(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )
  SideNavDropdown(
    "Tugas",
    items = dropdownItemsTugas_Mentor(
      navigateAndCloseSideNav,
      onNavigateModul = onNavigateModul
    ),
    isActiveRoute = isActiveRoute
  )
  SideNavDropdown(
    "Kegiatan",
    items = null,
    isActiveRoute = isActiveRoute,
    onClickDropdown = {
      navigateAndCloseSideNav(Screen.Kegiatan.JadwalBulanPeserta.route)
    },
    route = Screen.Kegiatan.JadwalBulanPeserta.route
  )
  SideNavDropdown(
    "Forum Diskusi",
    items = null,
    isActiveRoute = isActiveRoute,
    onClickDropdown = {
      navigateAndCloseSideNav(Screen.Mentor.ForumDiskusi.route)
    },
    route = Screen.Mentor.ForumDiskusi.route
  )

  SideNavDropdown(
    "Pengaturan",
    items = null,
    isActiveRoute = isActiveRoute,
    onClickDropdown = {
      navigateAndCloseSideNav(Screen.Mentor.Pengaturan.route)
    },
    route = Screen.Mentor.Pengaturan.route
  )
}

@Composable
private fun SideNavDropdownPeserta(
  navigateAndCloseSideNav : (String) -> Unit,
  isActiveRoute : (String) -> Boolean,
  onNavigateModul : () -> Unit
) {

  SideNavDropdown(
    "Peserta",
    items = dropdownItemsPeserta_Peserta(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute,

  )
  SideNavDropdown(
    "Mentor",
    items = dropdownItemsMentor_Peserta(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )
  SideNavDropdown(
    "Tugas",
    items = dropdownItemsTugas_Peserta(
      navigateAndCloseSideNav,
      onNavigateModul = onNavigateModul
    ),
    isActiveRoute = isActiveRoute,
    onClickDropdown = {
      onNavigateModul()
    }

  )
  SideNavDropdown(
    "Kegiatan",
    items = dropdownItemsKegiatan_Peserta(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )
  SideNavDropdown(
    "Forum Diskusi",
    items = null,
    isActiveRoute = isActiveRoute,
    onClickDropdown = {
      navigateAndCloseSideNav(Screen.Peserta.PusatInformasi.route)
    },
    route = Screen.Peserta.PusatInformasi.route
  )

      SideNavDropdown(
    "Pengaturan",
    items = null,
    isActiveRoute = isActiveRoute,
    onClickDropdown = {
      navigateAndCloseSideNav(Screen.Peserta.Pengaturan.route)
    },
    route = Screen.Peserta.Pengaturan.route
  )

}