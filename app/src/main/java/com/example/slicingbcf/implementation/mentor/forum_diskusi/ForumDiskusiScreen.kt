package com.example.slicingbcf.implementation.mentor.forum_diskusi

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.scaffold.DiscussionScaffold
import com.example.slicingbcf.ui.shared.pusat_informasi.DataPusatInformasi
import com.example.slicingbcf.ui.shared.pusat_informasi.PusatInformasiContent
import com.example.slicingbcf.ui.shared.pusat_informasi.mockDataPusatInformasi
import com.example.slicingbcf.ui.shared.textfield.OutlineTextFieldComment
import com.example.slicingbcf.ui.shared.textfield.SearchBarCustom

@Composable
fun ForumDiskusiScreen(
  modifier : Modifier,
  onNavigateDetailForumDiskusi : (String) -> Unit
) {
  val scrollState = rememberScrollState()

  DiscussionScaffold(
    onClick = { Log.d("discussion", "discussion") }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .padding(innerPadding)
        .statusBarsPadding()
        .padding(
          horizontal = 16.dp,
        )
        .verticalScroll(scrollState),
      verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
      TopSection()
      BottomSection(
        onNavigateDetailForumDiskusi = onNavigateDetailForumDiskusi
      )
    }
  }
}

@Composable
private fun BottomSection(
  onNavigateDetailForumDiskusi : (String) -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    mockDataPusatInformasi.forEach {
      PusatInformasiItem(
        data = it,
        onClick = { onNavigateDetailForumDiskusi(it.username ?: "") }
      )
    }
  }

}

@Composable
private fun TopSection(
) {
  Column(
    modifier = Modifier
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(36.dp)
  ) {
    Text(
      text = "Forum Diskusi",
      style = StyledText.MobileLargeSemibold,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        SearchBarCustom(
          onSearch = { Log.d("search", it) },
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
          color = ColorPalette.PrimaryColor700,
          textStyle = StyledText.MobileSmallMedium,
          title = "Cari Pertanyaan",
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
      }
      Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Text(
          text = "Butuh diskusi? ",
          style = StyledText.MobileMediumSemibold,
          color = ColorPalette.PrimaryColor700,
        )
        Text(
          text = "Ajukan pertanyaan dan buka forum untuk memulai obrolan!",
          style = StyledText.MobileBaseRegular,
          color = ColorPalette.PrimaryColor700,
        )
      }
      OutlineTextFieldComment(
        value = "",
        onValueChange = {},
        onSubmit = { Log.d("submit", "submit") },
        label = "Buka Obrolan...",
        placeholder = "Buka Obrolan...",
        isEnabled = true
      )
    }
    HorizontalDivider(
      modifier = Modifier.fillMaxWidth()
    )
  }
}


@Composable
private fun PusatInformasiItem(
  data : DataPusatInformasi,
  onClick : () -> Unit,
) {
  Column(
    modifier = Modifier
      .clip(
        shape = RoundedCornerShape(32.dp)
      )
      .clickable(onClick = onClick)
      .background(ColorPalette.Monochrome100)
      .padding(
        horizontal = 16.dp,
        vertical = 20.dp
      )
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(32.dp)
  ) {
    PusatInformasiContent(data)
  }
}