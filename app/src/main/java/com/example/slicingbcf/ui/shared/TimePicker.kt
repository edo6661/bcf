package com.example.slicingbcf.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.ui.shared.dialog.TimePickerDialog
import com.example.slicingbcf.ui.shared.form_monthly_report.ClickableTextField

@Composable
fun CustomTimePicker(
  modifier: Modifier = Modifier,
  initialHour: String = "00",
  initialMinute: String = "00",
  initialAmPm: String = "AM",
  onTimeSelected: (String) -> Unit = {}
) {
  var showDialog by remember { mutableStateOf(false) }
  var hour by remember { mutableStateOf(initialHour) }
  var minute by remember { mutableStateOf(initialMinute) }
  var amOrPm by remember { mutableStateOf(initialAmPm) }

  val formattedTime = "$hour:$minute $amOrPm"

  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    ClickableTextField(
      value = formattedTime,
      placeholder = "Waktu Posting",
      onClick = { showDialog = true },

    )

    TimePickerDialog(
      showDialog = showDialog,
      hour = hour,
      minute = minute,
      amOrPm = amOrPm,
      onHourChange = { hour = it },
      onMinuteChange = { minute = it },
      onAmPmChange = { amOrPm = it },
      onDismissRequest = {
        showDialog = false
        onTimeSelected("$hour:$minute $amOrPm")
      }
    )
  }
}
