package com.example.slicingbcf.ui.shared.tabs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette

@Composable
fun MainTabIndicator(
  tabPositions: List<TabPosition>,
  currentTab: Int,
  color: Color = ColorPalette.PrimaryColor700,
  width: Dp = 80.dp,
  shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
) {
  TabRowDefaults.PrimaryIndicator(
    color = color,
    width = width,
    shape = shape,
    modifier = Modifier.tabIndicatorOffset(tabPositions[currentTab])
  )
}
