package com.example.slicingbcf.ui.shared.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.form_monthly_report.AmPmOption
import com.example.slicingbcf.ui.shared.form_monthly_report.KeyboardTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
  showDialog: Boolean,
  hour: String,
  minute: String,
  amOrPm: String,
  onHourChange: (String) -> Unit,
  onMinuteChange: (String) -> Unit,
  onAmPmChange: (String) -> Unit,
  onDismissRequest: () -> Unit
) {
  if (showDialog) {
    BasicAlertDialog(
      onDismissRequest = { onDismissRequest() },
    ) {
      Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
          .clip(
            RoundedCornerShape(24.dp)
          )
          .background(ColorPalette.OnPrimary)
          .padding(24.dp)
      ) {
        Text(
          text = "Enter time",
          style = StyledText.MobileSmallRegular,
          color = ColorPalette.PrimaryColor700
        )
        Row(
          verticalAlignment = Alignment.Top,
          horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          // Time Input Section
          Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
          ) {
            KeyboardTime(
              isHour = true,
              value = hour,
              onValueChange = onHourChange,
              description = "Hour"
            )
            Text(
              text = ":",
              style = TextStyle(
                fontSize = 57.sp,
                lineHeight = 64.sp,
                fontWeight = FontWeight(400),
                color = ColorPalette.OnSurface,
                textAlign = TextAlign.Center,
              )
            )
            KeyboardTime(
              value = minute,
              onValueChange = onMinuteChange,
              description = "Minute"
            )
          }

          Column(
            verticalArrangement = Arrangement.Center,
          ) {
            AmPmOption(
              text = "AM",
              isActive = amOrPm == "AM",
              isTopRounded = true,
              onClick = { onAmPmChange("AM") }
            )
            HorizontalDivider(
              color = ColorPalette.PrimaryColor700,
              thickness = 1.dp,
              modifier = Modifier.fillMaxWidth()
            )
            AmPmOption(
              text = "PM",
              isActive = amOrPm == "PM",
              isBottomRounded = true,
              onClick = { onAmPmChange("PM") }
            )
          }
        }
      }
    }
  }
}
