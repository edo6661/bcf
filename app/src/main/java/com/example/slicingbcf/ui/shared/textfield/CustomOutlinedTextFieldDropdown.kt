package com.example.slicingbcf.ui.shared.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.dropdown.DropdownText

@Composable
fun CustomOutlinedTextFieldDropdown(
  modifier : Modifier = Modifier,
  value : String,
  onValueChange : (String) -> Unit,
  expanded : Boolean,
  onChangeExpanded : (Boolean) -> Unit,
  label : String,
  placeholder : String,
  dropdownItems : List<String>,
  labelDefaultColor : Color = ColorPalette.PrimaryColor700,
  labelFocusedColor : Color = ColorPalette.PrimaryColor700,

  ) {

  Column {
    CustomOutlinedTextField(
      modifier = modifier.fillMaxWidth(),
      value = value,
      onValueChange = onValueChange,
      label = label,
      placeholder = placeholder,
      rounded = 40,
      readOnly = true,
      labelFocusedColor = labelFocusedColor,
      labelDefaultColor = labelDefaultColor,
      labelFocusedStyle = StyledText.MobileSmallMedium,
      trailingIcon = {
        Icon(
          imageVector = Icons.Default.ArrowDropDown,
          contentDescription = "Arrow Drop Down",
          tint = ColorPalette.PrimaryColor700,
          modifier = Modifier
            .size(48.dp)
            .padding(end = 24.dp)
            .clickable {
              onChangeExpanded(! expanded)
            }
        )
      },

      )

    DropdownText(
      expanded = expanded,
      onExpandedChange = {
        onChangeExpanded(it)
      },
      onItemSelected = { item ->
        onValueChange(item)
        onChangeExpanded(false)
      },
      items = dropdownItems,
      currentItem = value
    )
  }
}