package com.example.slicingbcf.implementation.mentor.forum_diskusi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.scaffold.DiscussionScaffold
import com.example.slicingbcf.ui.shared.pusat_informasi.PusatInformasiContent


@Composable
fun DetailForumDiskusiScreen(
  modifier : Modifier,
  id : String,
  viewModel : DetailForumDiskusiViewModel = hiltViewModel()

) {

  val verticalScroll = rememberScrollState()
  val state by viewModel.state.collectAsState()


  val addData = { newMockDataPusatInformasi : com.example.slicingbcf.data.local.DataPusatInformasi ->
    viewModel.onEvent(ForumDiskusiEvent.ClearState)
    viewModel.onEvent(ForumDiskusiEvent.AddPertanyaan(newMockDataPusatInformasi))
  }

  DiscussionScaffold(
    onClick = {  }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .padding(innerPadding)
        .statusBarsPadding()
        .padding(
          horizontal = 16.dp,
        )
        .background(ColorPalette.OnPrimary)
        .verticalScroll(verticalScroll),
      verticalArrangement = Arrangement.spacedBy(36.dp)

    ) {

      TopSection()
      BottomSection(
        addData = addData,
        onChangePertanyaan = { viewModel.onEvent(ForumDiskusiEvent.PertanyaanChanged(it)) },
        state = state


      )
    }
  }
}

@Composable
private fun BottomSection(
  addData : (com.example.slicingbcf.data.local.DataPusatInformasi) -> Unit,
  onChangePertanyaan : (String) -> Unit,
  state : ForumDiskusiState
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    CurrentPusatInformasi(
      data = com.example.slicingbcf.data.local.mockDataPusatInformasi[0],
      onSubmitComment = {
        addData(
          it
        )
      },
      question = state.pertanyaan,
      onChangeQuestion = onChangePertanyaan


    )
    state.dataList.forEach {
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
  data : com.example.slicingbcf.data.local.DataPusatInformasi,
  onSubmitComment : (com.example.slicingbcf.data.local.DataPusatInformasi) -> Unit = {},
  question : String = "",
  onChangeQuestion : (String) -> Unit = {}
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
      isCommentable = true,
      isEnabledTextField = true,
      question = question,
      onChangeQuestion = onChangeQuestion,
      onSubmitComment = {
        onSubmitComment(
          it
        )
      }
    )
  }
}

@Composable
private fun CommentsPusatInformasi(
  data : com.example.slicingbcf.data.local.DataPusatInformasi,
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


