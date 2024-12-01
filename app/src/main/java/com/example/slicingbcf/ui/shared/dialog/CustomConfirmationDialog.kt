package com.example.slicingbcf.ui.shared.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.PrimaryButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomConfirmationDialog(
  title : String,
  confirmButtonText : String,
  dismissButtonText : String,
  supportingText : String,
  onConfirm : () -> Unit,
  onDismiss : () -> Unit
) {

  BasicAlertDialog(onDismissRequest = onDismiss) {
    Column(
      verticalArrangement = Arrangement.spacedBy(24.dp),
      modifier = Modifier
        .clip(RoundedCornerShape(24.dp))
        .background(ColorPalette.OnPrimary)
        .padding(24.dp)
    ) {
      Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),

        ) {
        Text(
          text = title,
          style = StyledText.MobileMediumMedium,
          color = ColorPalette.Black
        )
        Text(
          text = supportingText,
          style = StyledText.MobileBaseRegular,
          color = ColorPalette.Monochrome400
        )

      }



      Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        TextButton(
          onClick = onDismiss,
          enabled = true
        ) {
          Text(
            text = dismissButtonText,
            style = StyledText.MobileBaseRegular,
            color = ColorPalette.PrimaryColor700
          )
        }
        PrimaryButton(
          text = confirmButtonText,
          onClick = onConfirm,
          style = StyledText.MobileBaseRegular,
          textColor = ColorPalette.PrimaryColor100,
          color = ColorPalette.PrimaryColor700
        )
      }
    }
  }
}
