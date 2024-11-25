package com.example.slicingbcf.ui.sidenav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.navigation.Screen
import com.example.slicingbcf.ui.navigation.navigateSingleTop
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.dialog.CustomAlertDialog

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
  isActiveRoute : (String) -> Boolean
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
        isActiveRoute = isActiveRoute
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
        append("LEAD\n")
        withStyle(
          style = SpanStyle(
            color = ColorPalette.PrimaryColor700,
          )
        ) {
          append("INDONESIA")
        }
      },
      style = StyledText.MobileXlBold,
    )
  }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSideNav(
  navigateAndCloseSideNav : (String) -> Unit,
  isActiveRoute : (String) -> Boolean
) {

  val scroll = rememberScrollState()

  var showLogoutDialog by remember { mutableStateOf(false) }
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
        text = "Masuk",
        onClick = {
          navigateAndCloseSideNav(Screen.Auth.Login.route)
        }
      )
      PrimaryButton(
        text = "Daftar",
        onClick = {
          navigateAndCloseSideNav(Screen.Auth.Registrasi.route)
        }
      )
      SideNavDropdownGuest(
        navigateAndCloseSideNav,
        isActiveRoute
      )
    }


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

  if (showLogoutDialog) {
    CustomAlertDialog(
      title = "Apakah anda yakin ingin keluar?",
      confirmButtonText = "Keluar",
      dismissButtonText = "Batal",
      onConfirm = {
        showLogoutDialog = false
      },
      onDismiss = {
        showLogoutDialog = false
      }
    )
  }

}


@Composable
private fun SideNavDropdown(
  title : String,
  items : List<DropdownItem>? = null,
  onClickDropdown : () -> Unit = {},
  isActiveRoute : (String) -> Boolean
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



  Column(
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Column {
      DropdownMenuItem(
        text = {
          Text(
            title,
            style = StyledText.MobileBaseMedium
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


// TODO: nanti ganti sesuai dengan role user
@Composable
private fun SideNavDropdownGuest(
  navigateAndCloseSideNav : (String) -> Unit,
  isActiveRoute : (String) -> Boolean
) {
  SideNavDropdown(
    "Registrasi",
    items = dropdownItemsPendaftaran(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )
  SideNavDropdown(
    "Kegiatan",
    onClickDropdown = {
//      navigateAndCloseSideNav(Screen.Kegiatan.route)
    },
    isActiveRoute = isActiveRoute
  )
  SideNavDropdown(
    "Mentor",
    items = dropdownItemsMentor(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )
  SideNavDropdown(
    "Tugas",
    items = dropdownItemsTugas(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )

  SideNavDropdown(
    "Peserta",
    items = dropdownItemsPeserta(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )


  SideNavDropdown(
    "Kegiatan",
    items = dropdownItemsKegiatan(
      navigateAndCloseSideNav
    ),
    isActiveRoute = isActiveRoute
  )
}

