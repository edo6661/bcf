package com.example.slicingbcf.implementation.peserta.worksheet_peserta

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.message.SecondaryButton
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField

// TODO: RAPIHIN LAYOUT + TAMBAHIN ITEMS ITEMS LAINNYA YANG ADA DI COLUMN JUDUL DLL
@Composable
fun DetailWorksheetPesertaScreen(
  modifier : Modifier = Modifier,
  id : String,
  viewModel : DetailWorksheetPesertaViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()
  when {
    state.isLoading -> {
      LoadingCircularProgressIndicator()
    }

    state.error != null -> {
      ErrorWithReload(
        modifier = modifier,
        errorMessage = state.error,
        onRetry = {
          viewModel.onEvent(DetailWorksheetPesertaEvent.Reload)
        }
      )
    }
    else -> {
      Column(
        modifier = modifier.padding(
          horizontal = 16.dp,
          vertical = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(36.dp),
      ) {
        Text(
          text = "Submisi Lembar Kerja",
          style = StyledText.MobileLargeMedium,
          color = ColorPalette.Black,
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Center,
        )
        Column(
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          Text(
            text = "Judul Lembar Kerja",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700
          )
          Text(
            text = state.worksheet.judulLembarKerja,
            style = StyledText.MobileBaseRegular,
          )
        }
        Column(
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          Text(
            text = "Deskripsi",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700
          )
          Text(
            text = state.worksheet.deskripsiLembarKerja,
            style = StyledText.MobileBaseRegular,
          )
        }
        Column(
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          Text(
            text = "Tautan Lembar Kerja",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700
          )
          Text(
            text = state.worksheet.tautanLembarKerja,
            style = StyledText.MobileBaseRegular,
            color = ColorPalette.PrimaryColor400
          )
        }
        Column(
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          Text(
            text = "Batas Submisi Lembar Kerja",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700
          )
          Text(
            text = state.worksheet.batasSubmisi,
            style = StyledText.MobileBaseRegular,
          )
        }
        Column(
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          Text(
            text = "Tautan Lembar Kerja Peserta",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700
          )
          CustomOutlinedTextField(
            value = state.tautanLembarKerjaPeserta,
            onValueChange = {
              viewModel.onEvent(DetailWorksheetPesertaEvent.OnChangeTautanLembarKerjaPeserta(it))
            },
            label = "Masukkan tautan lembar kerja",
            placeholder = "Masukkan tautan lembar kerja",
            rounded = 40,
            modifier = Modifier.fillMaxWidth(),
          )
        }
        HorizontalDivider()
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {

          PrimaryButton(
            onClick = {
              viewModel.onEvent(DetailWorksheetPesertaEvent.Submit)
            },
            text = "Simpan",
            color = ColorPalette.PrimaryColor700,
            style = StyledText.MobileSmallMedium,
          )
          SecondaryButton(
            onClick = {},
            text = "Batal",
            color = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.PrimaryColor700,
          )

        }
      }

    }


  }

  SubmitLoadingIndicatorDouble(
    isLoading = state.submitLoading,
    title = "Mohon Tunggu",
    titleColor = ColorPalette.PrimaryColor700,
    description = "Memproses Submisi Lembar Kerja Anda...",
    descriptionColor = ColorPalette.OnSurface,
    onAnimationFinished = {
      viewModel.onEvent(DetailWorksheetPesertaEvent.OnChangeSubmitLoading(false))

    },
    titleBerhasil = "Lembar Kerja Anda Berhasil Dikirim!",
    titleStyle = StyledText.MobileMediumSemibold,
    descriptionStyle = StyledText.MobileSmallRegular,
    titleBerhasilStyle = StyledText.MobileMediumMedium,

  )
}