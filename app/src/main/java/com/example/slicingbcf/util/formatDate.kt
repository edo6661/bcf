package com.example.slicingbcf.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatDate(timestamp : Long) : String {
  val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
  val date = Date(timestamp)
  return sdf.format(date)
}
