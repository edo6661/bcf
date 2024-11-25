package com.example.slicingbcf.implementation.mentor.penilaian_peserta

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.textfield.SearchBarCustom

@Composable
fun PenilaianPesertaScreen(
  modifier : Modifier,
  onNavigateDetailPenilaianPeserta : (String) -> Unit
) {
  Column(
    modifier = modifier
      .statusBarsPadding()
      .padding(
        horizontal = 16.dp,
      ),
    verticalArrangement = Arrangement.spacedBy(28.dp)
  ) {
    TopSection(
      onSearch = { Log.d("search", it) }
    )
    BottomSection(
      onNavigateDetailPenilaianPeserta
    )
  }
}

@Composable
fun TopSection(
  onSearch : (String) -> Unit
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
      )

      SmallFloatingActionButton(
        onClick = { Log.d("filter", "filter clicked") },
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
        onClick = { Log.d("download", "download clicked") },
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
  onNavigateDetailPenilaianPeserta : (String) -> Unit
) {
  LazyColumn(
    verticalArrangement =
    Arrangement.spacedBy(16.dp),
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


data class PenilaianPeserta(
  val title : String,
  val description : String,
)

val penilaianPesertas = listOf(
  PenilaianPeserta(
    title = "Penilaian 1",
    description = "Deskripsi Penilaian 1"
  ),
  PenilaianPeserta(
    title = "Penilaian 2",
    description = "Deskripsi Penilaian 2"
  ),
  PenilaianPeserta(
    title = "Penilaian 3",
    description = "Deskripsi Penilaian 3"
  ),
  PenilaianPeserta(
    title = "Penilaian 4",
    description = "Deskripsi Penilaian 4"
  ),
  PenilaianPeserta(
    title = "Penilaian 5",
    description = "Deskripsi Penilaian 5"
  ),
  PenilaianPeserta(
    title = "Penilaian 6",
    description = "Deskripsi Penilaian 6"
  ),
  PenilaianPeserta(
    title = "Penilaian 7",
    description = "Deskripsi Penilaian 7"
  ),
  PenilaianPeserta(
    title = "Penilaian 8",
    description = "Deskripsi Penilaian 8"
  ),
  PenilaianPeserta(
    title = "Penilaian 9",
    description = "Deskripsi Penilaian 9"
  ),
  PenilaianPeserta(
    title = "Penilaian 10",
    description = "Deskripsi Penilaian 10"
  ),
  PenilaianPeserta(
    title = "Penilaian 11",
    description = "Deskripsi Penilaian 11"
  ),

  )