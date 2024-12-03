package com.example.slicingbcf.ui.shared.pengumuman

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText


@Composable
fun TabWithBadge(
  selected : Boolean = false,
  onClick : () -> Unit = { },
  text : String,
  badgeNumber : String,
) {
  Tab(
    selected = selected,
    onClick = onClick,
    text = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable { onClick() }
      ) {
        Text(
          text = text,
          style = StyledText.MobileXsMedium,
          color = if (selected) ColorPalette.PrimaryColor700 else ColorPalette.Monochrome400
        )
        Badge(
          modifier = Modifier.size(16.dp),
          contentColor = ColorPalette.OnError,
        ) {
          Text(
            badgeNumber,
            textAlign = TextAlign.Center,
            style = Typography().labelSmall,
            color = ColorPalette.OnError
          )
        }
      }
    }
  )
}
