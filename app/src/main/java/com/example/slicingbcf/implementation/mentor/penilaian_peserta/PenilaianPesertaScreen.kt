package com.example.slicingbcf.implementation.mentor.penilaian_peserta

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.PenilaianPeserta
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator
import com.example.slicingbcf.ui.shared.textfield.SearchBarCustom

@Composable
fun PenilaianPesertaScreenMentor(
  modifier: Modifier = Modifier,
  viewModel: PenilaianPesertaViewModel = hiltViewModel(),
  onNavigateDetailPenilaianPeserta: (String) -> Unit
) {
  val state by viewModel.state.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()

  Column(
    modifier = modifier
      .padding(
        horizontal = 16.dp,
        vertical = 24.dp
      ),
    verticalArrangement = Arrangement.spacedBy(28.dp)
  ) {
    TopSection(
      onSearch = { query -> viewModel.onEvent(PenilaianPesertaEvent.Search(query)) },
      query = searchQuery
    )
    when (state) {
      is UiState.Loading -> LoadingCircularProgressIndicator()
      is UiState.Success -> {
        val data = (state as UiState.Success<List<PenilaianPeserta>>).data
        BottomSection(
          penilaianPesertas = data,
          onNavigateDetailPenilaianPeserta = onNavigateDetailPenilaianPeserta
        )
      }
      is UiState.Error -> {
        val errorMessage = (state as UiState.Error).message
        ErrorWithReload(
          errorMessage = errorMessage,
          onRetry = { viewModel.onEvent(PenilaianPesertaEvent.Reload) }
        )
      }
    }
  }
}


@Composable
fun TopSection(
  onSearch : (String) -> Unit,
  query : String,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text(
      text = "Penilaian Peserta",
      style = StyledText.MobileLargeMedium,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      SearchBarCustom(
        onSearch = onSearch,
        title = "Cari Penilaian",
        query = query,
        bgColor = ColorPalette.PrimaryColor100
      )

      SmallFloatingActionButton(
        onClick = {  },
        modifier = Modifier.size(40.dp),
        containerColor = ColorPalette.PrimaryColor100
      ) {
        Image(
          painter = painterResource(id = R.drawable.filter),
          contentDescription = "Filter",
          modifier = Modifier.size(20.dp)
        )
      }

      SmallFloatingActionButton(
        onClick = {  },
        modifier = Modifier.size(40.dp),
        containerColor = ColorPalette.PrimaryColor700
      ) {
        Image(
          painter = painterResource(id = R.drawable.download),
          contentDescription = "Download",
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}
@Composable
fun BottomSection(
  penilaianPesertas: List<PenilaianPeserta>,
  onNavigateDetailPenilaianPeserta: (String) -> Unit
) {
  LazyColumn(
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(penilaianPesertas.size) { index ->
      PenilaianPesertaItem(
        penilaianPeserta = penilaianPesertas[index],
        onClick = onNavigateDetailPenilaianPeserta
      )
    }
  }
}


@Composable
fun PenilaianPesertaItem(
  penilaianPeserta : PenilaianPeserta,
  onClick : (String) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(ColorPalette.PrimaryColor100)
      .clickable { onClick(penilaianPeserta.title) }
      .padding(
        end = 16.dp,

        )
  ) {
    ListItem(
      colors = ListItemDefaults.colors(
        containerColor = Color.Transparent
      ),
      headlineContent = {
        Text(
          text = penilaianPeserta.title,
          style = StyledText.MobileSmallMedium,
          color = ColorPalette.PrimaryColor400
        )
      },
      supportingContent = {
        Text(
          text = penilaianPeserta.description,
          style = StyledText.MobileBaseSemibold,
          color = ColorPalette.PrimaryColor700
        )
      },
      trailingContent = {
        Icon(
          Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = "Arrow Forward",
          modifier = Modifier.size(24.dp),
          tint = ColorPalette.PrimaryColor700
        )
      },
    )
  }
}
