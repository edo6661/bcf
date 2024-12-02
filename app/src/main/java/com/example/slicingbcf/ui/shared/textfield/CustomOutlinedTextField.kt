package com.example.slicingbcf.ui.shared.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.message.ErrorMessageTextField

@Suppress("t")
@Composable
fun CustomOutlinedTextField(
  modifier : Modifier = Modifier,
  asteriskAtEnd : Boolean = false,
  value : String,
  onValueChange : (String) -> Unit,
  label : String? = "",
  placeholder : String? = "",
  isPassword : Boolean = false,
  isPasswordVisible : MutableState<Boolean>? = null,
  leadingIcon : @Composable (() -> Unit)? = null,
  keyboardType : KeyboardType = KeyboardType.Text,
  error : String? = null,
  rounded : Int = 16,
  multiLine : Boolean = false,
  maxLines : Int = 1,
  isEnabled : Boolean = true,
  labelFocusedColor : Color = ColorPalette.PrimaryColor700,
  labelDefaultStyle : TextStyle = StyledText.MobileSmallRegular,
  labelDefaultColor : Color = ColorPalette.Monochrome300,
  trailingIcon : @Composable (() -> Unit)? = null,
  readOnly : Boolean = false,
  borderColor : Color = ColorPalette.Outline,
  bgColor : Color = Color.White,
  borderFocusedColor : Color = ColorPalette.Monochrome400,
  isFocused : Boolean? = null,
  onFocusChange : (Boolean) -> Unit = {}
) {

  val focusRequester = remember { FocusRequester() }
  var isFocusedDefault by remember { mutableStateOf(false) }

  val actualIsFocus = isFocused ?: isFocusedDefault

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start

  ) {
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      modifier = modifier
        .focusRequester(focusRequester)
        .onFocusChanged { focusState ->
          isFocused.let {
            if (it != null) {
              onFocusChange(focusState.isFocused)
            } else {
              isFocusedDefault = focusState.isFocused
            }
          }
        },
      singleLine = ! multiLine,
      maxLines = if (multiLine) maxLines else 1,
      shape = RoundedCornerShape(rounded),
      leadingIcon = leadingIcon,
      trailingIcon = {
        when {
          isPasswordVisible != null -> PasswordVisibilityIcon(isPasswordVisible)
          trailingIcon != null      -> trailingIcon()
        }
      },
      visualTransformation = getVisualTransformation(isPassword, isPasswordVisible),
      keyboardOptions = getKeyboardOptions(isPassword, keyboardType),
      label = {
        if (label != null) {
          TextFieldSupport(
            label = label,
            asteriskAtEnd = asteriskAtEnd,
            valueNotEmpty = value.isNotEmpty(),
            labelDefaultStyle = labelDefaultStyle,
            labelDefaultColor = labelDefaultColor,
            isFocused = actualIsFocus,
            labelFocusedColor = labelFocusedColor,
            isError = error != null


          )
        }
      },
      placeholder = {
        if (placeholder != null) {
          TextFieldSupport(
            label = placeholder,
            valueNotEmpty = value.isNotEmpty(),
            labelDefaultStyle = labelDefaultStyle,
            labelDefaultColor = labelDefaultColor,
            isFocused = actualIsFocus,
            isPlaceholder = true,
            labelFocusedColor = labelFocusedColor,
            isError = error != null


            )
        }
      },
      textStyle = StyledText.MobileSmallRegular,
      isError = error != null,
      colors = getTextFieldColors(
        borderColor = borderColor,
        borderFocusedColor = borderFocusedColor,
        bgColor = bgColor,
        labelFocusedColor = labelFocusedColor,
        labelDefaultColor = labelDefaultColor,
        isError = error != null
      ),
      enabled = isEnabled,
      readOnly = readOnly,

      )

    AnimatedVisibility(visible = error != null) {
      error?.let {
        ErrorMessageTextField(it)
      }
    }
  }
}


@Composable
private fun PasswordVisibilityIcon(isPasswordVisible : MutableState<Boolean>) {
  IconButton(onClick = { isPasswordVisible.value = ! isPasswordVisible.value }) {
    Icon(
      imageVector = if (isPasswordVisible.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
      contentDescription = if (isPasswordVisible.value) "Hide Password" else "Show Password"
    )
  }
}

@Composable
private fun getVisualTransformation(
  isPassword : Boolean,
  isPasswordVisible : MutableState<Boolean>?
) : VisualTransformation {
  return if (isPassword && isPasswordVisible?.value == false) {
    PasswordVisualTransformation()
  } else {
    VisualTransformation.None
  }
}

@Composable
private fun getKeyboardOptions(
  isPassword : Boolean,
  keyboardType : KeyboardType
) : KeyboardOptions {
  return KeyboardOptions.Default.copy(
    keyboardType = if (isPassword) KeyboardType.Password else keyboardType
  )
}

@Composable
private fun TextFieldSupport(
  label : String,
  asteriskAtEnd : Boolean = false,
  valueNotEmpty : Boolean,
  labelDefaultStyle : TextStyle,
  labelDefaultColor : Color,
  labelFocusedColor : Color,
  isFocused : Boolean = false,
  isPlaceholder : Boolean = false,
  isError : Boolean = false
) {
  val fontWeight = if (isPlaceholder) FontWeight.Medium else when {
    valueNotEmpty -> FontWeight.Medium
    isFocused     -> FontWeight.Medium
    else          -> FontWeight.Normal
  }

  val color = if (isPlaceholder) labelDefaultColor else when {
    valueNotEmpty -> labelFocusedColor
    isError        -> ColorPalette.Error
    isFocused     -> labelFocusedColor
    else          -> labelDefaultColor
  }
  val displayText = buildAnnotatedString {
    append(label)
    if (asteriskAtEnd && (isFocused || valueNotEmpty)) {
      withStyle(SpanStyle(color = ColorPalette.Danger500)) {
        append("*")
      }
    }
  }




  Text(
    text = displayText,
    style = labelDefaultStyle,
    modifier = Modifier.padding(
      horizontal = 4.dp,
    ),
    color = color,
    fontWeight = fontWeight

  )
}

@Composable
private fun getTextFieldColors(
  borderColor : Color,
  borderFocusedColor : Color,
  bgColor : Color,
  labelFocusedColor : Color,
  labelDefaultColor : Color,
  isError : Boolean = false
) : TextFieldColors {

  val focusedLabelColor = if (isError) ColorPalette.Error else labelFocusedColor

  return OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = borderColor,
    focusedBorderColor = borderFocusedColor,
    disabledBorderColor = borderColor,
    unfocusedContainerColor = bgColor,
    focusedContainerColor = bgColor,
    disabledContainerColor = bgColor,
    focusedLabelColor = focusedLabelColor,
    unfocusedLabelColor = labelDefaultColor,
    errorBorderColor = ColorPalette.Error,
    errorLabelColor = ColorPalette.Error,
    errorLeadingIconColor = ColorPalette.Error,
    errorTrailingIconColor = ColorPalette.Error
  )
}


@Composable
fun CustomOutlinedTextAsterisk(
  label: String,
  value: TextFieldValue,
  placeholder: String,
  onValueChange: (TextFieldValue) -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .padding(top = 8.dp)
  ) {
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      placeholder = {
        Text(
          text = placeholder,
          style = StyledText.MobileSmallRegular,
          color = ColorPalette.Monochrome400
        )
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .height(56.dp),
      shape = RoundedCornerShape(50),
      colors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedIndicatorColor = ColorPalette.Monochrome400,
        focusedIndicatorColor = ColorPalette.PrimaryColor700
      ),
      singleLine = true
    )
    Box(
      modifier = Modifier
        .padding(start = 20.dp)
        .offset(y = (-3).dp)
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
  }
}

@Composable
fun TextFieldLong(
  label: String,
  placeholder: String,
  value: TextFieldValue,
  onValueChange: (TextFieldValue) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Box(
      modifier = Modifier
        .offset(y = (-3).dp)
        .background(Color.White)
    ) {
      Row{
        Text(
          text = label,
          style = StyledText.MobileBaseSemibold,
          color = ColorPalette.PrimaryColor700,
          textAlign = TextAlign.Justify
        )
        Text(
          text = "*",
          style = StyledText.MobileBaseSemibold,
          color = ColorPalette.Error,
        )
      }
    }

    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      placeholder = {
        Text(
          text = placeholder,
          style = StyledText.MobileSmallRegular,
          color = ColorPalette.Monochrome400
        )
      },
      modifier = Modifier
        .fillMaxWidth()
        .height(120.dp),
      shape = RoundedCornerShape(8.dp),
      colors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedIndicatorColor = ColorPalette.Monochrome400,
        focusedIndicatorColor = ColorPalette.PrimaryColor700
      )
    )
  }
}
