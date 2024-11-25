package com.example.slicingbcf.implementation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Composable
@Preview(showSystemUi = true)
fun LandingPageScreen(
  modifier : Modifier = Modifier
) {
  val scrollState = rememberScrollState()


  Column(
    verticalArrangement = Arrangement.spacedBy(48.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .verticalScroll(scrollState)
  ) {
    Image(
      painter = painterResource(R.drawable.banner),
      contentDescription = "Banner",
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxWidth()
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(horizontal = 16.dp)
    ) {
      sections.forEach {
        Section(
          title = it.title,
          description = it.description
        )
      }
      Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        images.forEach {
          ImageSdg(
            image = it
          )
        }
      }
    }
    Footer()

  }
}

private data class SectionData(
  val title : String,
  val description : String
)

private val sections = listOf(
  SectionData(
    title = "Deskripsi Program",
    description = "LEAD Indonesia merupakan program intensif pengembangan Kepemimpinan dan penguatan kapasitas bagi profesional muda terhadap pemecahan masalah sosial yang terjadi di Indonesia demi terwujudnya masa depan Pembangunan dan Pertumbuhan Ekonomi Indonesia.",
  ),
  SectionData(
    "Tema Kegiatan",
    "Tahun 2023, LEAD Indonesia mengusung tema “Meningkatkan Kemampuan Desain Program untuk Keberhasilan dan Keberlanjutan Organisasi dalam Menghimpun Dana” dalam rangka mencapai Sustainable Development Goals (SDGs)."
  ),
  SectionData(
    "Cluster Kesehatan",
    "Gerakan/komunitas/lembaga sosial yang berfokus pada isu kesehatan, seperti identifikasi, pendampingan, hingga pemulihan pasien yang mengidap tuberkulosis (TBC), HIV/AIDS, dan stunting."
  ),
)
private val images = listOf(
  R.drawable.sdg1,
  R.drawable.sdg2,
  R.drawable.sdg3,
)

@Composable
private fun Section(
  title : String,
  description : String
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = title,
      style = StyledText.MobileLargeBold,
      color = ColorPalette.PrimaryColor700,
      textAlign = TextAlign.Center
    )
    Text(
      text = description,
      style = StyledText.MobileSmallRegular,
      textAlign = TextAlign.Center,
      color = ColorPalette.Muted
    )
  }
}

@Composable
private fun ImageSdg(
  image : Int,
) {
  Image(
    modifier = Modifier
      .width(68.4138.dp)
      .height(68.4138.dp),
    painter = painterResource(image),
    contentDescription = "SDG",
    contentScale = ContentScale.Crop,

    )
}

@Composable
private fun Footer() {
  Column(
    modifier = Modifier
      .background(ColorPalette.PrimaryColor700)
      .padding(vertical = 12.dp)
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),

      ) {
      icons.forEach() {
        Icon(
          onClick = { },
          image = it
        )
      }
    }
    Text(
      text = "Copyright 2024 Bakrie Center Foundation, All Right Reserved",
      style = StyledText.MobileSmallRegular,
      fontSize = 10.sp,
      color = ColorPalette.OnPrimary,
      textAlign = TextAlign.Center,
    )

  }
}

@Composable
private fun Icon(
  onClick : () -> Unit,
  image : Int
) {
  IconButton(
    onClick = onClick,
    modifier = Modifier.size(14.dp)
  ) {
    Image(
      painter = painterResource(image),
      contentDescription = "Icon",
      modifier = Modifier.size(12.dp)
    )
  }
}


private val icons = listOf(
  R.drawable.instagram,
  R.drawable.youtube,
  R.drawable.facebook,
  R.drawable.twitter,
  R.drawable.tiktok,
)