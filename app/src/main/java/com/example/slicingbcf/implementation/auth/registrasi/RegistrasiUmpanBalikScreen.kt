package com.example.slicingbcf.implementation.auth.registrasi

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField


@Composable
fun RegistrasiUmpanBalikScreen(
  modifier : Modifier = Modifier,
  navigateToLogin : () -> Unit,
) {
  var currentEmote by remember { mutableStateOf("") }
  val onChangeEmote = { emote : String ->
    currentEmote = emote
  }
  var pengalamanPendaftaranLead by remember { mutableStateOf("") }
  var isLoading by remember { mutableStateOf(false) }

  val onSend = {
    isLoading = true
  }

  Box(
    modifier = modifier
      .background(ColorPalette.OnPrimary)
      .statusBarsPadding()
      .padding(horizontal = 16.dp)
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
      TopSection(
        currentEmote = currentEmote,
        onChangeEmote = onChangeEmote
      )
      BottomSection(
        pengalamanPendaftaranLead = pengalamanPendaftaranLead,
        onChangePengalamanPendaftaranLead = { pengalamanPendaftaranLead = it },
        onSend = onSend
      )
    }

    SubmitLoadingIndicatorDouble(
      isLoading = isLoading,
      title = "Memproses Umpan Balik Anda...",
      onAnimationFinished = navigateToLogin,
      titleBerhasil = "Umpan Balik Anda Berhasil Terkirim!",
    )
  }
}


@Composable
private fun TopSection(
  currentEmote : String,
  onChangeEmote : (String) -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(24.dp),
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(16.dp),

      ) {
      Text(
        text = "Berikan Umpan Balik Anda!",
        style = StyledText.MobileXlMedium,
        color = ColorPalette.Black
      )
      Text(
        text = "Bagaimana pengalaman anda ketika melakukan pendaftaran LEAD Indonesia 2024?",
        style = StyledText.MobileMediumRegular,
        color = ColorPalette.Monochrome700
      )
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .animateContentSize(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically


    ) {
      ConstantUmpanBalik.emotes.forEach {
        ReactEmote(
          title = it.title,
          isActive = currentEmote == it.title,
          onClick = { onChangeEmote(it.title) },
          emote = it.emote
        )
      }
    }


  }
}

@Composable
private fun ReactEmote(
  title : String,
  isActive : Boolean,
  onClick : () -> Unit,
  emote : Int
) {
  val color by animateColorAsState(
    targetValue = if (isActive) ColorPalette.PrimaryColor400 else ColorPalette.PrimaryColor100,
    label = "color emote"
  )
  val alpha by animateFloatAsState(
    targetValue = if (isActive) 1f else 0f,
    label = "text emote"
  )


  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
  ) {
    Box(
      modifier = Modifier
        .size(52.dp)
        .background(
          color = color,
          shape = RoundedCornerShape(100)
        )
        .clickable { onClick() },
      contentAlignment = Alignment.Center
    ) {
      Image(
        painter = painterResource(id = emote),
        contentDescription = "React Emote",
        modifier = Modifier.size(
          32.dp
        )
      )
    }

    Box(
      modifier = Modifier
        .height(40.dp)
        .width(68.dp),
      contentAlignment = Alignment.TopCenter
    ) {
      Text(
        text = title,
        style = StyledText.MobileSmallMedium,
        color = if (isActive) ColorPalette.PrimaryColor400 else Color.Transparent,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .alpha(alpha)


      )
    }
  }
}


@Composable
private fun BottomSection(
  pengalamanPendaftaranLead : String,
  onChangePengalamanPendaftaranLead : (String) -> Unit,
  onSend : () -> Unit,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(32.dp)
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      Text(
        text = "Ceritakan pengalaman anda ketika melakukan pendaftaran LEAD Indonesia 2024*",
        style = StyledText.MobileBaseRegular,
        color = ColorPalette.PrimaryColor700
      )
      CustomOutlinedTextField(
        value = pengalamanPendaftaranLead,
        onValueChange = onChangePengalamanPendaftaranLead,
        placeholder = "Ketik umpan balik anda disini",
        labelDefaultColor = ColorPalette.Monochrome400,
        borderColor = ColorPalette.Monochrome400,
        labelDefaultStyle = StyledText.MobileSmallRegular,
        modifier = Modifier
          .fillMaxWidth(),
        label = "Umpan Balik",
        rounded = 24
      )
    }
    PrimaryButton(
      text = "Kirim",
      onClick = onSend,
      modifier = Modifier.fillMaxWidth()
    )
  }
}