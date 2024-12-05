package com.example.slicingbcf.implementation.peserta.pengumuman_peserta

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator

@Composable
fun DetailPengumumanPesertaScreen(
  modifier: Modifier = Modifier,
  id: String,
  viewModel: DetailPengumumanPesertaViewModel = hiltViewModel()
) {
  val uiState by viewModel.state.collectAsState()

  when (uiState) {
    is UiState.Loading -> {
      LoadingCircularProgressIndicator()
    }
    is UiState.Success -> {
      val data = (uiState as UiState.Success<PengumumanDetail>).data
      Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
      ) {
        Text(
          text = data.title,
          style = StyledText.MobileLargeMedium,
          textAlign = TextAlign.Center,
        )
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
          Image(
            painter = painterResource(id = data.imageUrl),
            contentDescription = "Pengumuman",
            modifier = Modifier.size(width = 381.dp, height = 147.dp)
          )
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(Icons.Outlined.Favorite, contentDescription = "")
              Text(
                text = data.likeCount.toString(),
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.Monochrome400
              )
            }
            Text(
              text = data.date,
              style = StyledText.MobileSmallRegular,
              color = ColorPalette.Monochrome400
            )
          }
        }
        Text(
          text = data.description,
          style = StyledText.MobileBaseRegular,
          color = ColorPalette.Black,
          textAlign = TextAlign.Center
        )
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(text = "Link Submit MISI:", style = StyledText.MobileBaseMedium)
          Text(
            text = data.submitLink,
            style = StyledText.MobileBaseRegular,
            color = ColorPalette.PrimaryColor400
          )
        }
        Text(
          text = buildAnnotatedString {
            append("Batas waktu submit MISI 2 adalah ")
            withStyle(style = SpanStyle(color = ColorPalette.SecondaryColor400)) {
              append(data.deadline)
            }
            append(". Setelah itu link formulir submit akan ditutup dan dibuka kembali di misi selanjutnya.")
          },
          style = StyledText.MobileBaseRegular,
          textAlign = TextAlign.Center,
        )
      }
    }
    is UiState.Error   -> {
      val errorMessage = (uiState as UiState.Error).message
      ErrorWithReload(
        errorMessage = errorMessage,
        onRetry = {
          viewModel.onEvent(DetailPengumumanPesertaEvent.ReloadData)
        }
      )
    }
  }
}





