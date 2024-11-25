package com.example.slicingbcf.implementation.mentor.forum_diskusi

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.scaffold.DiscussionScaffold
import com.example.slicingbcf.ui.shared.pusat_informasi.DataPusatInformasi
import com.example.slicingbcf.ui.shared.pusat_informasi.PusatInformasiContent
import com.example.slicingbcf.ui.shared.pusat_informasi.mockDataPusatInformasi


@Composable
fun DetailForumDiskusiScreen(
  modifier : Modifier,
  id : String
) {

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
        .background(ColorPalette.OnPrimary),
      verticalArrangement = Arrangement.spacedBy(36.dp)

    ) {
      TopSection()
      BottomSection()
    }
  }
}

@Composable
private fun BottomSection(
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    CurrentPusatInformasi(
      data = mockDataPusatInformasi[0],

      )
    mockDataPusatInformasi.forEach {
      CommentsPusatInformasi(
        data = it,
      )
    }

  }
}


@Composable
private fun TopSection(
) {

  Text(
    text = "Forum Diskusi",
    style = StyledText.MobileLargeSemibold,
    textAlign = TextAlign.Center,
    modifier = Modifier.fillMaxWidth()
  )

}


@Composable
private fun CurrentPusatInformasi(
  data : DataPusatInformasi,
  onSubmitComment : () -> Unit = {},
  onCancelComment : () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .clip(
        shape = RoundedCornerShape(32.dp)
      )
      .background(ColorPalette.PrimaryColor100)
      .padding(
        horizontal = 16.dp,
        vertical = 20.dp
      )
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(32.dp)
  ) {
    PusatInformasiContent(
      data,
      true,
      true,
      onSubmitComment,
      onCancelComment
    )
  }
}

@Composable
private fun CommentsPusatInformasi(
  data : DataPusatInformasi,
) {
  Column(
    modifier = Modifier
      .clip(
        shape = RoundedCornerShape(32.dp)
      )
      .background(ColorPalette.Monochrome100)
      .padding(
        horizontal = 16.dp,
        vertical = 20.dp
      )
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(32.dp)
  ) {
    PusatInformasiContent(
      data,
      false,

      )
  }
}

