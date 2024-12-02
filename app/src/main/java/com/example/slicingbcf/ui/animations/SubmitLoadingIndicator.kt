package com.example.slicingbcf.ui.animations

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Composable
fun SubmitLoadingIndicator(
  modifier : Modifier = Modifier,
  isLoading : Boolean,
  title : String = "Memproses Pendaftaran Anda...",
  titleColor : Color = ColorPalette.OnSurface,
  description: String? = null,
  descriptionColor: Color = ColorPalette.PrimaryColor400
) {
  if (isLoading) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_preloader))
    val progress by animateLottieCompositionAsState(
      composition,
      iterations = LottieConstants.IterateForever
    )


    if (isLoading) {
      Dialog(onDismissRequest = { }) {

        Column(
          modifier = Modifier
            .wrapContentSize()
            .background(
              color = ColorPalette.OnPrimary,
              shape = RoundedCornerShape(32.dp)
            )
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = title,
              style = StyledText.MobileMediumMedium,
              color = titleColor,
              textAlign = TextAlign.Center
            )
            description?.let {
              Text(
                text = it,
                style = StyledText.MobileMediumMedium,
                color = descriptionColor,
                textAlign = TextAlign.Center
              )
            }

          }
          LottieAnimation(
            composition = composition,
            progress = {
              progress
            },
            modifier = Modifier.size(150.dp)
          )
        }
      }
    }
  }
}

@Composable
fun SubmitLoadingIndicatorDouble(
  modifier : Modifier = Modifier,
  isLoading : Boolean,
  title : String,
  titleBerhasil : String,
  titleColor : Color = ColorPalette.OnSurface,

  description: String? = null,
  descriptionColor: Color = ColorPalette.PrimaryColor400,
  onAnimationFinished : () -> Unit,
) {
  if (! isLoading) return

  var isShowingPreloader by remember { mutableStateOf(true) }

  val (preloaderComposition, checkedComposition) = remember {
    Pair(
      LottieCompositionSpec.RawRes(R.raw.animation_preloader),
      LottieCompositionSpec.RawRes(R.raw.animation_checked)
    )
  }

  LottieAnimationContainer(
    title = title,
    titleBerhasil = titleBerhasil,
    description = description,
    titleColor = titleColor,
    descriptionColor = descriptionColor,
    isShowingPreloader = isShowingPreloader,
    preloaderSpec = preloaderComposition,
    checkedSpec = checkedComposition,
    onPreloaderFinished = {
      isShowingPreloader = false
    },
    onCheckedFinished = onAnimationFinished
  )
}

@Composable
private fun LottieAnimationContainer(
  title : String,
  titleBerhasil : String,
  description: String? = null,
  descriptionColor : Color,
  titleColor : Color,
  isShowingPreloader : Boolean,
  preloaderSpec : LottieCompositionSpec,
  checkedSpec : LottieCompositionSpec,
  onPreloaderFinished : () -> Unit,
  onCheckedFinished : () -> Unit,
) {
  val preloaderComposition by rememberLottieComposition(preloaderSpec)
  val checkedComposition by rememberLottieComposition(checkedSpec)

  val preloaderProgress by animateLottieCompositionAsState(
    composition = preloaderComposition,
    iterations = 1,
    restartOnPlay = false
  )

  val checkedProgress by animateLottieCompositionAsState(
    composition = checkedComposition,
    iterations = 1,
    restartOnPlay = false,
    isPlaying = ! isShowingPreloader
  )

  LaunchedEffect(preloaderProgress) {
    if (preloaderProgress == 1f && isShowingPreloader) {
      onPreloaderFinished()
    }
  }

  LaunchedEffect(checkedProgress) {
    if (checkedProgress == 1f && ! isShowingPreloader) {
      onCheckedFinished()
    }
  }

  Dialog(onDismissRequest = {}) {
    Column(
      modifier = Modifier
        .wrapContentSize()
        .background(
          color = ColorPalette.OnPrimary,
          shape = RoundedCornerShape(32.dp)
        )
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      AnimatedVisibility(
        visible = isShowingPreloader,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,

        ) {
          Text(
            text = title,
            style = StyledText.MobileMediumMedium,
            color = titleColor,
            textAlign = TextAlign.Center
          )
          Text(
            text = description ?: "",
            style = StyledText.MobileSmallRegular,
            color = descriptionColor,
            textAlign = TextAlign.Center
          )
        }
      }

      AnimatedVisibility(
        visible = ! isShowingPreloader,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
      ) {
        Text(
          text = titleBerhasil,
          style = StyledText.MobileMediumMedium,
          color = ColorPalette.OnSurface,
          textAlign = TextAlign.Center
        )
      }

      AnimationContent(
        isShowingPreloader = isShowingPreloader,
        preloaderComposition = preloaderComposition,
        checkedComposition = checkedComposition,
        preloaderProgress = preloaderProgress,
        checkedProgress = checkedProgress
      )
    }
  }
}

@Composable
private fun AnimationContent(
  isShowingPreloader : Boolean,
  preloaderComposition : LottieComposition?,
  checkedComposition : LottieComposition?,
  preloaderProgress : Float,
  checkedProgress : Float
) {
  if (isShowingPreloader) {
    LottieAnimation(
      composition = preloaderComposition,
      progress = { preloaderProgress },
      modifier = Modifier.size(150.dp)
    )
  } else {
    LottieAnimation(
      composition = checkedComposition,
      progress = { checkedProgress },
      modifier = Modifier.size(150.dp)
    )
  }
}

