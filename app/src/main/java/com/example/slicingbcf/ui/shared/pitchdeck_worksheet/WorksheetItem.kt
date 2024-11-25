package com.example.slicingbcf.ui.shared.pitchdeck_worksheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.WorksheetPeserta


@Composable
fun WorksheetItem(
  worksheet : WorksheetPeserta,
  onClick : () -> Unit = {},
  isClickable : Boolean = true,
  bgColor : Color = ColorPalette.OnPrimary,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .then(
        if (isClickable) Modifier.clickable(onClick = onClick)
        else Modifier
      )
      .border(
        width = 1.dp,
        color = ColorPalette.Monochrome200,
        shape = RoundedCornerShape(16.dp),
      )
      .background(
        color = bgColor,
        shape = RoundedCornerShape(16.dp),
      )
      .padding(
        horizontal = 4.dp,
        vertical = 24.dp
      ),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    IconButton(
      onClick = { /*TODO*/ }
    ) {
      Icon(
        Icons.Outlined.Folder,
        contentDescription = ""
      )
    }
    Column(
      modifier = Modifier.weight(1f),


      ) {
      Text(
        text = worksheet.title,
        style = StyledText.MobileSmallSemibold,
        color = ColorPalette.OnSurface
      )
      Text(
        text = worksheet.description,
        style = StyledText.MobileSmallRegular,
        color = ColorPalette.OnSurface
      )
    }
    IconButton(
      onClick = { /*todo*/ }
    ) {
      Icon(
        Icons.AutoMirrored.Default.NavigateNext,
        contentDescription = ""
      )
    }
  }
}