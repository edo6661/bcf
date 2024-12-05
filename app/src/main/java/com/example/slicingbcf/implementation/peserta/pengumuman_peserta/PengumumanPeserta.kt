package com.example.slicingbcf.implementation.peserta.pengumuman_peserta

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.Pengumuman
import com.example.slicingbcf.data.local.pengumumans
import com.example.slicingbcf.ui.shared.pengumuman.TabWithBadge
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator
import com.example.slicingbcf.ui.shared.tabs.MainTabIndicator

@Composable
fun PengumumanPesertaScreen(
  modifier: Modifier = Modifier,
  onNavigateDetailPengumuman: (String) -> Unit,
  viewModel: PengumumanPesertaViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()
  val currentTab by viewModel.currentTab.collectAsState()

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(
        horizontal = 16.dp,
        vertical = 24.dp
      ),
    verticalArrangement = Arrangement.spacedBy(28.dp)
  ) {
    when (state) {
      is UiState.Loading -> {
        LoadingCircularProgressIndicator()
      }
      is UiState.Success -> {

        val pengumumans = (state as UiState.Success<List<Pengumuman>>).data
        val filteredPengumumans = when (currentTab) {
          0 -> pengumumans
          1 -> pengumumans.filter { it.category == "Berita" }
          2 -> pengumumans.filter { it.category == "LEAD" }
          3 -> pengumumans.filter { it.category == "BCF" }
          else -> emptyList()
        }
        TopSection(currentTab) { selectedTab ->
          viewModel.onEvent(PengumumanPesertaEvent.TabChanged(selectedTab))
        }
        BottomSection(
          onNavigateDetailPengumuman,
          pengumumans = filteredPengumumans
        )
      }
      is UiState.Error -> {
        val errorMessage = (state as UiState.Error).message
        ErrorWithReload(
          errorMessage = errorMessage,
          onRetry = {
            viewModel.onEvent(PengumumanPesertaEvent.ReloadData)
          }
        )
      }
    }


  }
}

@Composable
fun BottomSection(
  onNavigateDetailPengumuman: (String) -> Unit,
  pengumumans: List<Pengumuman>
) {



    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .animateContentSize()
    ) {
      items(pengumumans.size) { index ->
        PengumumanItem(
          pengumuman = pengumumans[index],
          onNavigateDetailPengumuman = onNavigateDetailPengumuman
        )
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
  currentTab: Int,
  onTabSelected: (Int) -> Unit
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

@Composable
fun Tabs(
  currentTab: Int,
  onTabSelected: (Int) -> Unit
) {
  val categories = listOf(
    "Semua" to pengumumans.size,
    "Berita" to pengumumans.filter { it.category == "Berita" }.size,
    "LEAD" to pengumumans.filter { it.category == "LEAD" }.size,
    "BCF" to pengumumans.filter { it.category == "BCF" }.size
  )

  TabRow(
    selectedTabIndex = currentTab,
    indicator = { tabPositions ->
      MainTabIndicator(
        tabPositions = tabPositions,
        currentTab = currentTab,
      )
    }
  ) {
    categories.forEachIndexed { index, data->
      TabWithBadge(
        selected = currentTab == index,
        onClick = { onTabSelected(index) },
        text = data.first,
        badgeNumber = data.second.toString()
      )
    }

  }
}
