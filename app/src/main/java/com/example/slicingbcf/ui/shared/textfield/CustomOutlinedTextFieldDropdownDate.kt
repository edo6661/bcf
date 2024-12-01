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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


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
  datePickerState : DatePickerState,
  error : String? = null,
  asteriskAtEnd : Boolean = false
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
        asteriskAtEnd = asteriskAtEnd,
        readOnly = true,

        error = error,
        labelFocusedColor = labelFocusedColor,
        labelDefaultColor = labelDefaultColor,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextFieldDropdownDateAsterisk(
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
        rounded = 50,
        readOnly = true,
        labelFocusedColor = labelFocusedColor,
        labelDefaultColor = labelDefaultColor,
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
      Box(
        modifier = Modifier
          .padding(start = 20.dp)
          .offset(y = (-64).dp)
          .background(Color.White)
      ) {
        Row{
          Text(
            text = "$label",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
          )
          Text(
            text = "*",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.Error,
          )
        }
      }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDate(
  modifier: Modifier = Modifier,
  expanded: Boolean,
  onChangeExpanded: (Boolean) -> Unit,
  datePickerState: DatePickerState,
  selectedDate: LocalDate,
  onDateSelected: (LocalDate) -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .pointerInput(expanded) {
        detectTapGestures {
          if (expanded) onChangeExpanded(false)
        }
      }
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
      IconButton(onClick = {
        onDateSelected(selectedDate.minusMonths(1))
      }) {
        Icon(
          Icons.Default.ArrowBackIosNew,
          contentDescription = "Previous Month",
          modifier = Modifier.size(10.dp)
        )
      }
      Text(
        text = "${selectedDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${selectedDate.year}",
        style = StyledText.MobileSmallRegular,
        modifier = Modifier
          .align(Alignment.CenterVertically)
          .clickable {
            onChangeExpanded(!expanded)
          }
      )
      IconButton(onClick = {
        onDateSelected(selectedDate.plusMonths(1))
      }) {
        Icon(
          Icons.Default.ArrowForwardIos,
          contentDescription = "Next Month",
          modifier = Modifier.size(10.dp)
        )
      }

      // Popup for DatePicker
      Popup(
        alignment = Alignment.TopStart,
        offset = IntOffset(0, 64), // Offset to position the DatePicker below the trigger
        onDismissRequest = { onChangeExpanded(false) },
        properties = PopupProperties(focusable = true)
      ) {
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
}

