package com.example.slicingbcf.ui.shared.pitchdeck_worksheet

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.WorksheetPeserta
import com.example.slicingbcf.ui.shared.message.SecondaryButton


@Suppress("t")
@Composable
fun WorksheetItem(
  data: WorksheetPeserta,
  onClick : (String) -> Unit,
  bgColor: Color,
  id : String
) {
  val isExpanded = remember { mutableStateOf(false) }

  Column(
    verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = Modifier
      .fillMaxWidth()
      .animateContentSize()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .border(
          width = if (isExpanded.value) 1.dp else 0.dp,
          color = if (isExpanded.value) ColorPalette.Monochrome200 else Color.Transparent,
          shape = RoundedCornerShape(16.dp)
        )
        .background(
          color = bgColor,
          shape = RoundedCornerShape(16.dp)
        )
    ) {
      Card(
        onClick = {
          isExpanded.value = !isExpanded.value
        },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isExpanded.value) ColorPalette.PrimaryColor100 else bgColor,
        ),
        border = BorderStroke(1.dp, ColorPalette.Monochrome200)
      ) {
        Row(
          modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            modifier = Modifier.weight(6f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            Icon(
              imageVector = Icons.Outlined.Folder,
              contentDescription = null,
              modifier = Modifier.size(24.dp)
            )
            Column {
              Text(
                text = data.title,
                style = StyledText.MobileSmallMedium
              )
              Text(
                text = data.subTitle,
                style = StyledText.MobileXsRegular
              )
              Text(
                text = "Dibuat ${getCurrentTime()} WIB",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
              )
            }
          }
          Icon(
            imageVector = if (isExpanded.value) Icons.Default.MoreVert else
              Icons.AutoMirrored.Default.NavigateNext,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
          )
        }
      }

      AnimatedVisibility(
        visible = isExpanded.value,
        enter = expandVertically(animationSpec = tween(durationMillis = 300)) + fadeIn(),
        exit = shrinkVertically(animationSpec = tween(durationMillis = 300)) + fadeOut()
      ) {
        Column(
          verticalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .background(
              color = bgColor,
              shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            Image(
              painter = painterResource(id = R.drawable.icon_link_45deg),
              contentDescription = "",
              modifier = Modifier.size(24.dp)
            )
            Text(
              text = data.link,
              style = StyledText.MobileSmallRegular,
              color = ColorPalette.PrimaryColor400
            )
          }
          Text(
            text = data.description,
            style = StyledText.MobileSmallRegular
          )
          Text(
            text = buildAnnotatedString {
              append("Batas Submisi: ")
              withStyle(
                style = SpanStyle(
                  color = ColorPalette.SecondaryColor400
                )
              ) {
                append(data.submissionDeadline)
              }
            },
            style = StyledText.MobileSmallMedium
          )
        }
      }

    }

    AnimatedVisibility(
      visible = isExpanded.value,
      enter = slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn(),
      exit = slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
    ) {
      Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
      ) {
        SecondaryButton(
          text = "Lihat Detail",
          onClick = { onClick(id) },
          style = StyledText.MobileSmallMedium
        )
      }
    }

  }

}