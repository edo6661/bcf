package com.example.slicingbcf.ui.shared.dropdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Composable
fun DropdownText(
  expanded : Boolean,
  onExpandedChange : (Boolean) -> Unit,
  onItemSelected : (String) -> Unit,
  items : List<String>,
  currentItem : String
) {

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .pointerInput(Unit) {
        detectTapGestures {
          if (expanded) onExpandedChange(false)
        }
      }
  ) {
    Column(
      modifier = Modifier.animateContentSize()
    ) {
      CustomDropdownMenu(
        expanded = expanded,
        onItemClick = { item ->
          onItemSelected(item)
          onExpandedChange(false)
        },
        items = items,
        currentItem = currentItem
      )
    }
  }
}

@Composable
fun CustomDropdownMenu(
  expanded : Boolean,
  onItemClick : (String) -> Unit,
  items : List<String>,
  currentItem : String
) {
  fun colorCurrentItem(item : String) : Color = if (item == currentItem) {
    ColorPalette.Black
  } else {
    ColorPalette.Monochrome500
  }

  AnimatedVisibility(
    visible = expanded,
    enter = expandVertically(),
    exit = shrinkVertically(),
    label = "Dropdown Menu"
  ) {
    val shape = RoundedCornerShape(16.dp)
    Box(
      modifier = Modifier
        .padding(4.dp)
        .shadow(
          elevation = 4.dp,
          shape = shape
        )
        .background(Color.White, shape)
    ) {
      LazyColumn(
        modifier = Modifier
          .heightIn(max = 300.dp)
          .padding(vertical = 8.dp)
      ) {
        items(items.size) { index ->
          Text(
            text = items[index],
            style = StyledText.MobileSmallMedium,
            color = colorCurrentItem(items[index]),
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                onItemClick(items[index])
              }
              .padding(horizontal = 16.dp, vertical = 8.dp)
          )
        }
      }
    }
  }
}

