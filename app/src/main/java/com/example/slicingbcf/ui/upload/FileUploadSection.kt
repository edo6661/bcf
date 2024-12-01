package com.example.slicingbcf.ui.upload

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.message.ErrorMessageTextField

@Composable
fun FileUploadSection(
  title : String,
  buttonText : String,
  onFileSelect : () -> Unit,
  selectedFileUri : Uri?,
  deleteFile : () -> Unit,
  error : String? = null,
  asteriskAtEnd : Boolean = false
) {
  val color = if (error != null) ColorPalette.Error else ColorPalette.PrimaryColor400
  val displayText = buildAnnotatedString {
    append(title)
    if (asteriskAtEnd) {
      withStyle(SpanStyle(color = ColorPalette.Danger500)) {
        append("*")
      }
    }
  }

  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = Modifier.animateContentSize()
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Text(
        text = displayText,
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.PrimaryColor700,
        modifier = Modifier.fillMaxWidth()
      )
      AnimatedVisibility(
        visible = selectedFileUri != null,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        Row(
          modifier = Modifier
            .background(ColorPalette.PrimaryColor100, RoundedCornerShape(8.dp))
            .padding(
              horizontal = 20.dp,
              vertical = 8.dp
            ),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.FileCopy,
            contentDescription = "File Copy",
            tint = ColorPalette.PrimaryColor400,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = "File terpilih: ${selectedFileUri?.lastPathSegment}",
            style = StyledText.MobileSmallRegular,
            color = ColorPalette.PrimaryColor400,
            textAlign = TextAlign.Left,
          )

          Text(
            text = "X",
            style = StyledText.MobileSmallMedium,
            color = ColorPalette.PrimaryColor400,
            modifier = Modifier
              .padding(
                start = 8.dp
              )
              .clickable {
                deleteFile()
              }
          )
        }
      }


    }
    Column(
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(70.dp)
          .drawBehind {
            val dashWidth = 10.dp.toPx()
            val gapWidth = 5.dp.toPx()

            val stroke = Stroke(
              width = 1.dp.toPx(),
              pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashWidth, gapWidth),
                0f
              )
            )

            drawRoundRect(
              color = color,
              size = size,
              cornerRadius = CornerRadius(12.dp.toPx()),
              style = stroke
            )
          }
          .clickable {
            onFileSelect()
          },
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = buttonText,
          style = StyledText.MobileSmallMedium,
          textAlign = TextAlign.Center,
          color = ColorPalette.PrimaryColor400
        )
      }
      Text(
        text = "Maks Size: 5MB, Format File: PDF",
        style = StyledText.MobileSmallRegular,
        color = ColorPalette.Monochrome400,
        textAlign = TextAlign.Left
      )
    }
    AnimatedVisibility(visible = error != null) {
      error?.let {
        ErrorMessageTextField(it)
      }
    }

  }
}
