package com.example.slicingbcf.ui.shared.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextFieldDropdownDate(
  modifier : Modifier = Modifier,
  value : String,
  expanded : Boolean,
  onChangeExpanded : (Boolean) -> Unit,
  label : String,
  placeholder : String,
  labelDefaultColor : Color = ColorPalette.PrimaryColor700,
  labelFocusedColor : Color = ColorPalette.PrimaryColor700,
  datePickerState : DatePickerState
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .pointerInput(expanded) {
        detectTapGestures {
          if (expanded) onChangeExpanded(false)
        }
      }
  ) {
    Column(
      modifier = modifier
        .fillMaxWidth()
        .animateContentSize()
    ) {
      CustomOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {},
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
        }
      )

      AnimatedVisibility(
        visible = expanded,
        enter = expandVertically(),
        exit = shrinkVertically(),
        label = "DatePicker Dropdown"
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
            .pointerInput(Unit) {
              detectTapGestures { }
            }
        ) {
          DatePicker(
            state = datePickerState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
              containerColor = ColorPalette.OnPrimary,
              selectedDayContainerColor = ColorPalette.PrimaryColor700,
              todayDateBorderColor = ColorPalette.PrimaryColor700,
              todayContentColor = ColorPalette.PrimaryColor700
            )
          )
        }
      }
    }
  }
}
