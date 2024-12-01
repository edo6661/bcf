package com.example.slicingbcf.implementation.peserta.check_status_registrasi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.PrimaryButton

@Composable
fun CheckStatusRegistrasiScreen(
  modifier : Modifier = Modifier,
  viewModel : CheckStatusRegistrasiViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()

  Column(
    modifier = modifier
      .padding(horizontal = 16.dp, vertical = 24.dp)
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .animateContentSize(),
    verticalArrangement = Arrangement.Center,
  ) {
    TopSection(state = state, onEvent = viewModel::onEvent)

    Spacer(modifier = Modifier.height(40.dp))

    BottomSection(state = state, onEvent = viewModel::onEvent)
  }
}

@Composable
fun TopSection(state : CheckStatusRegistrasiState, onEvent : (CheckStatusRegistrasiEvent) -> Unit) {
  Column(
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Cek Status Registrasimu Disini!",
      style = StyledText.MobileLargeSemibold,
      color = ColorPalette.PrimaryColor700,
      textAlign = TextAlign.Center
    )
    Text(
      text = "Masukkan email yang telah didaftarkan sebelumnya",
      style = StyledText.MobileBaseRegular,
      color = ColorPalette.Monochrome300,
      textAlign = TextAlign.Center
    )
  }

  Spacer(modifier = Modifier.height(40.dp))

  OutlinedTextField(
    value = state.email,
    onValueChange = { onEvent(CheckStatusRegistrasiEvent.EmailChanged(it)) },
    placeholder = {
      Text(
        text = "Ketikkan email anda disini",
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.Monochrome300,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )
    },
    textStyle = StyledText.MobileBaseRegular.copy(textAlign = TextAlign.Center),
    modifier = Modifier.fillMaxWidth(),
    colors = OutlinedTextFieldDefaults.colors(
      unfocusedContainerColor = ColorPalette.PrimaryColor100,
      focusedContainerColor = ColorPalette.PrimaryColor100,
      unfocusedLabelColor = ColorPalette.Monochrome300,
      unfocusedBorderColor = Color.Transparent,
      focusedBorderColor = ColorPalette.PrimaryColor100,
    ),
    shape = RoundedCornerShape(32.dp),
    singleLine = true,
    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
  )
  Spacer(
    modifier = Modifier.height(24.dp)
  )

  PrimaryButton(
    text = "Cek Status",
    textColor = ColorPalette.OnPrimary,
    onClick = { onEvent(CheckStatusRegistrasiEvent.CheckStatus) },
    modifier = Modifier.fillMaxWidth(),
    modifierText = Modifier.padding(vertical = 6.dp),
    style = StyledText.MobileBaseSemibold,
    isEnabled = state.email.isNotEmpty() && ! state.isCheckedStatus
  )
}

@Composable
fun BottomSection(
  state : CheckStatusRegistrasiState,
  onEvent : (CheckStatusRegistrasiEvent) -> Unit
) {
  Spacer(modifier = Modifier.height(40.dp))

  Box(
    modifier = Modifier
      .height(if (state.isCheckedStatus) 0.dp else 172.dp)
      .fillMaxWidth(),
    contentAlignment = Alignment.Center
  ) {
    if (state.isLoading) {
      val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_preloader))
      val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
      )
      LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(172.dp)
      )
    }
  }

  AnimatedVisibility(
    visible = state.isCheckedStatus,
    enter = fadeIn(
      animationSpec = tween(
        durationMillis = 500
      )
    ),
    exit = fadeOut(
      animationSpec = tween(
        durationMillis = 500
      )
    ),

    ) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
      Text(
        text = "Status Registrasi Peserta",
        style = StyledText.MobileBaseSemibold,
        color = ColorPalette.PrimaryColor700
      )
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(24.dp))
          .background(ColorPalette.StatusSuccessBg)
          .height(56.dp)
          .fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = state.message ?: "",
          style = StyledText.MobileBaseSemibold,
          color = ColorPalette.Success500,
        )
      }
      Text(
        text = "Data Registrasi Peserta",
        style = StyledText.MobileBaseSemibold,
        color = ColorPalette.PrimaryColor700
      )
      DataTable(
        headers = CheckStatusRegistrasiConstant.headers,
        data = CheckStatusRegistrasiConstant.data
      )
    }
  }
}


@Composable
fun TableCell(
  modifier : Modifier = Modifier,
  title : String,
  isHeader : Boolean = false
) {

  val style = if (isHeader) StyledText.MobileSmallSemibold else StyledText.MobileXsMedium

  Box(
    modifier = modifier
      .fillMaxHeight()
      .padding(horizontal = 8.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = title,
      style = style,
      color = ColorPalette.Monochrome800,
      maxLines = 4,
      overflow = TextOverflow.Ellipsis,
    )
  }
}

@Composable
fun TableRow(
  isHeader : Boolean,
  content : @Composable RowScope.() -> Unit
) {


  val bg = if (isHeader) ColorPalette.PrimaryColor100 else Color.Transparent
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        color = bg,
        shape = RoundedCornerShape(
          topStart = 30.dp,
          topEnd = 30.dp
        )
      )
      .then(
        if (! isHeader) Modifier.drawBehind {
          val strokeWidth = 1.dp.toPx()
          val y = size.height - strokeWidth / 2
          drawLine(
            color = ColorPalette.Monochrome200,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = strokeWidth
          )
        } else Modifier
      )

      .padding(
        8.dp
      ),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    content = content
  )
}

@Composable
fun DataTable(
  headers : List<String>,
  data : List<CheckStatusRegistrasiConstant.Companion.CheckStatusRegistrasiPeserta>
) {

  Column(modifier = Modifier.fillMaxWidth()) {
    TableRow(isHeader = true) {
      headers.forEach { header ->
        TableCell(
          modifier = Modifier.weight(1f),
          title = header,
          isHeader = true
        )
      }
    }

    data.forEachIndexed { index, item ->
      TableRow(isHeader = false) {
        TableCell(modifier = Modifier.weight(1f), title = (index + 1).toString())
        TableCell(modifier = Modifier.weight(1f), title = item.namaInstansi)
        TableCell(modifier = Modifier.weight(1f), title = item.provinsi)
        TableCell(modifier = Modifier.weight(1f), title = item.namaPeserta)
      }
    }
  }
}

