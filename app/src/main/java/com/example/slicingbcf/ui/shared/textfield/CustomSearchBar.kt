package com.example.slicingbcf.ui.shared.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Composable
fun SearchBarCustom(
  onSearch : (String) -> Unit,
  query : String? = null,
  modifier : Modifier = Modifier,
  textStyle : TextStyle = StyledText.MobileSmallRegular,
  color : Color = ColorPalette.Monochrome300,
  title : String,
  bgColor : Color = ColorPalette.PrimaryColor100,
  onClick : () -> Unit = { },
  isEnable : Boolean = true
) {

  Box(
    modifier = modifier
      .shadow(
        elevation = 4.dp,
        shape = RoundedCornerShape(40.dp),
        clip = false
      )
      .background(
        color = bgColor,
        shape = RoundedCornerShape(40.dp)
      )
      .clickable {
        if (! isEnable) {
          onClick()
        }
      }
  ) {
    TextField(
      textStyle = StyledText.MobileSmallRegular,
      value = query ?: "",
      onValueChange = {

        onSearch(it)
      },
      placeholder = {
        Text(
          text = title,
          style = textStyle,
          color = color
        )
      },
      leadingIcon = {
        Icon(
          Icons.Default.Search,
          contentDescription = "Search Icon",
          modifier = Modifier.size(20.dp),
          tint = color
        )
      },
      enabled = isEnable,

      singleLine = true,
      shape = RoundedCornerShape(40.dp),
      colors = TextFieldDefaults.colors(
        focusedIndicatorColor = bgColor,
        unfocusedIndicatorColor = bgColor,
        disabledIndicatorColor = bgColor,
        errorIndicatorColor = bgColor,
        unfocusedContainerColor = bgColor,
        focusedContainerColor = bgColor,
        disabledContainerColor = bgColor,
      ),
    )
  }

}
