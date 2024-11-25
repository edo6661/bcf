package com.example.slicingbcf.ui.shared.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText


@Composable
fun TextFieldWithTitle(
  heading : String? = null,
  title : String,
  onChange : (String) -> Unit,
  value : String,
  placeholder : String,
  label : String,
  isEdit : Boolean = true,
  styleTitle : TextStyle = StyledText.MobileBaseMedium,
) {
  Column {
    heading?.let {
      Text(
        text = heading,
        style = StyledText.MobileBaseSemibold,
        color = ColorPalette.PrimaryColor700,
      )
      Spacer(
        modifier = Modifier.height(20.dp)
      )
    }

    Text(
      text = title,
      style = styleTitle,
      color = ColorPalette.Black,
    )
    Spacer(
      modifier = Modifier.height(8.dp)
    )

    CustomOutlinedTextField(
      value = value,
      onValueChange = onChange,
      modifier = Modifier
        .fillMaxWidth(),
      multiLine = true,
      maxLines = 5,

      placeholder = placeholder,
      label = label,
      isEnabled = isEdit
    )
  }
}