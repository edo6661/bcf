package com.example.slicingbcf.ui.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable

private fun slideAnimation(targetState : Int, initialState : Int) : ContentTransform {
  return slideInHorizontally(
    initialOffsetX = { if (targetState > initialState) it else - it },
    animationSpec = tween(800)
  ) togetherWith slideOutHorizontally(
    targetOffsetX = { if (targetState > initialState) - it else it },
    animationSpec = tween(800)
  )
}

@Composable
fun AnimatedContentSlide(
  currentScreen : Int,
  initialState : Int,
  label : String,
  content : @Composable (Int) -> Unit,
) {
  AnimatedContent(
    targetState = currentScreen,
    transitionSpec = {
      slideAnimation(targetState, initialState)
    },
    label = label
  ) { targetScreen ->
    content(targetScreen)
  }
}
