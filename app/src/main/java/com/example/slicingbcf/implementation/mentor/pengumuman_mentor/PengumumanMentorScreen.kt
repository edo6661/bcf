package com.example.slicingbcf.implementation.mentor.pengumuman_mentor

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.Pengumuman
import com.example.slicingbcf.data.local.pengumumans

@Composable
fun PengumumanMentorScreen(
  modifier: Modifier = Modifier,
  onNavigateDetailPengumuman: (String) -> Unit
) {
  var currentTab by remember { mutableIntStateOf(0) }
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(28.dp)
  ) {
    TopSection(currentTab) { selectedTab ->
      currentTab = selectedTab
    }
    BottomSection(
      currentTab,
      onNavigateDetailPengumuman
    )
  }
}

@Composable
fun BottomSection(
  currentTab: Int,
  onNavigateDetailPengumuman: (String) -> Unit
) {
  val filteredPengumumans = when (currentTab) {
    0 -> pengumumans
    1 -> pengumumans.filter { it.category == "Berita" }
    2 -> pengumumans.filter { it.category == "LEAD" }
    3 -> pengumumans.filter { it.category == "BCF" }
    else -> emptyList()
  }

  if (filteredPengumumans.isEmpty()) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = 32.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "Tidak ada pengumuman untuk kategori ini.",
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.Monochrome400
      )
    }
  } else {
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .animateContentSize()
    ) {
      items(filteredPengumumans.size) { index ->
        PengumumanItem(
          pengumuman = filteredPengumumans[index],
          onNavigateDetailPengumuman = onNavigateDetailPengumuman
        )
      }
    }
  }
}

@Composable
fun PengumumanItem(
  pengumuman: Pengumuman,
  onNavigateDetailPengumuman: (String) -> Unit
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(28.dp),
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.clickable {
      Log.d("PengumumanItem", "PengumumanItem: ${pengumuman.title}")
      onNavigateDetailPengumuman(pengumuman.title)
    }
  ) {
    Box(
      modifier = Modifier
        .size(40.dp)
        .background(ColorPalette.PrimaryColor100, CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        Icons.Outlined.Notifications,
        contentDescription = "",
        modifier = Modifier.size(20.dp),
        tint = ColorPalette.PrimaryColor400
      )
      Badge(
        modifier = Modifier
          .size(16.dp)
          .align(Alignment.TopEnd),
        contentColor = ColorPalette.OnError,
      ) {
        val badgeNumber = ""
        Text(
          badgeNumber,
          modifier = Modifier.semantics {
            contentDescription = "$badgeNumber new notifications"
          }
        )
      }
    }


    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Text(
        text = pengumuman.title,
        color = ColorPalette.Black,
        style = StyledText.MobileSmallRegular,
      )
      Text(
        text = pengumuman.date.toString(),
        style = StyledText.MobileXsRegular,
        color = ColorPalette.Monochrome400
      )
    }
  }
}



@Composable
fun TopSection(
  currentTab : Int,
  onTabSelected : (Int) -> Unit
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Pengumuman",
      style = StyledText.MobileLargeMedium,
      color = ColorPalette.Black
    )
    Text(
      text = "Tandai telah dibaca",
      style = StyledText.MobileSmallMedium,
      color = ColorPalette.PrimaryColor700
    )
  }
  Tabs(currentTab, onTabSelected)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tabs(
  currentTab : Int,
  onTabSelected : (Int) -> Unit
) {
  TabRow(
    selectedTabIndex = currentTab,
    indicator = { tabPositions ->
      SecondaryIndicator(
        Modifier.tabIndicatorOffset(tabPositions[currentTab]),
        height = 3.dp,
        color = ColorPalette.PrimaryColor700
      )
    }
  ) {
    com.example.slicingbcf.implementation.peserta.pengumuman_peserta.TabWithBadge(
      selected = currentTab == 0,
      onClick = { onTabSelected(0) },
      text = "Semua",
      badgeNumber = pengumumans.size.toString()
    )
    com.example.slicingbcf.implementation.peserta.pengumuman_peserta.TabWithBadge(
      selected = currentTab == 1,
      onClick = { onTabSelected(1) },
      text = "Berita",
      badgeNumber = pengumumans.filter { it.category == "Berita" }.size.toString()
    )
    com.example.slicingbcf.implementation.peserta.pengumuman_peserta.TabWithBadge(
      selected = currentTab == 2,
      onClick = { onTabSelected(2) },
      text = "LEAD",
      badgeNumber = pengumumans.filter { it.category == "LEAD" }.size.toString()
    )
    com.example.slicingbcf.implementation.peserta.pengumuman_peserta.TabWithBadge(
      selected = currentTab == 3,
      onClick = { onTabSelected(3) },
      text = "BCF",
      badgeNumber = pengumumans.filter { it.category == "BCF" }.size.toString()
    )
  }

}
@Composable
fun TabWithBadge(
  selected : Boolean = false,
  onClick : () -> Unit = { },
  text : String,
  badgeNumber : String,
) {
  Tab(
    selected = selected,
    onClick = onClick,
    text = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable { onClick() }
      ) {
        Text(
          text = text,
          style = StyledText.MobileXsMedium,
          color = if (selected) ColorPalette.PrimaryColor700 else ColorPalette.Monochrome400
        )
        Badge(
          modifier = Modifier.size(20.dp),
          contentColor = ColorPalette.OnError,
        ) {
          Text(
            badgeNumber,
            textAlign = TextAlign.Center,
            style = StyledText.Mobile2xsRegular,
            color = ColorPalette.OnError
          )
        }
      }
    }
  )
}
