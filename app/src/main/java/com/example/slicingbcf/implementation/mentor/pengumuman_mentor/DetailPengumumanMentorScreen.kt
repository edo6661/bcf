package com.example.slicingbcf.implementation.mentor.pengumuman_mentor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText


@Composable
fun DetailPengumumanMentorScreen(
  modifier : Modifier = Modifier,
  id : String
) {
  Column(
    modifier = modifier
      .padding(
        horizontal = 16.dp,

        ),
    verticalArrangement = Arrangement.spacedBy(30.dp)
  ) {
    Text(
      text = "MISI 2: Share Momen Onboarding Nasional LEAD 8",
      style = StyledText.MobileLargeMedium,
      textAlign = TextAlign.Center,
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      Image(
        painter = painterResource(id = R.drawable.ex_pengumuman),
        contentDescription = "Pengumuman",
        modifier = Modifier
          .size(
            width = 381.dp,
            height = 147.dp
          )
      )
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            Icons.Outlined.Favorite,
            contentDescription = ""
          )
          Text(
            text = "55",
            style = StyledText.MobileSmallRegular,
            color = ColorPalette.Monochrome400
          )
        }
        Text(
          text = "31 Maret 2023, 10.00 WIB",
          style = StyledText.MobileSmallRegular,
          color = ColorPalette.Monochrome400
        )

      }
    }
    Text(
      text = "Teman-teman jangan lupa untuk share momen onboarding nasional di media sosial IG/TikTok/LinkedIn dengan ketentuan di atas. Setiap misi harus disubmit pada link formulir misi untuk nantinya akhir semester diakumulasikan menjadi nilai tambahan bagi nilai akhir mahasiswa 🥰🙏",

      // Mobile/base/Regular
      style = StyledText.MobileBaseRegular,
      color = ColorPalette.Black,
      textAlign = TextAlign.Center
    )
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "Link Submit MISI:",
        style = StyledText.MobileBaseMedium
      )
      Text(
        text = "https://bit.ly/MISICLP8",
        style = StyledText.MobileBaseRegular,
        color = ColorPalette.PrimaryColor400
      )
    }
    Text(
      text = buildAnnotatedString {
        append("Batas waktu submit MISI 2 adalah ")
        withStyle(
          style = SpanStyle(color = ColorPalette.SecondaryColor400)
        ) {
          append("Minggu, 7 April 2023 pukul 23.30 WIB.")
        }
        append("Setelah itu link formulir submit akan ditutup dan dibuka kembali di misi selanjutnya.")
      },
      style = StyledText.MobileBaseRegular,
      textAlign = TextAlign.Center,
    )

  }
}