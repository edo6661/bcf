package com.example.slicingbcf.ui.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.ui.shared.message.ErrorMessage
import com.example.slicingbcf.ui.shared.message.SuccessMessage

@Composable
fun AnimatedMessage(
  isVisible : Boolean,
  message : String,
  modifier : Modifier = Modifier,
  messageType : MessageType = MessageType.Success
) {
  val messageOffset by animateDpAsState(
    targetValue = if (isVisible) 0.dp else - 100.dp,
    animationSpec = tween(durationMillis = 1000, delayMillis = 500, easing = FastOutSlowInEasing),
    label = "messageOffset"
  )

  AnimatedVisibility(visible = isVisible) {
    when (messageType) {
      MessageType.Success -> {
        SuccessMessage(
          message = message,
          modifier = modifier
            .offset(y = messageOffset)
        )
      }

      MessageType.Error   -> {
        ErrorMessage(
          message = message,
          modifier = modifier
            .offset(y = messageOffset)
        )
      }
    }
  }
}


enum class MessageType {
  Success, Error
}
