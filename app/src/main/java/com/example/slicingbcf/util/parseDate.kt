package com.example.slicingbcf.util


fun parseDate(dateString : String) : Long {
  val currentTime = System.currentTimeMillis()
  val timeUnit = dateString.split(" ")

  return when {
    dateString.contains("day")    -> {
      val daysAgo = timeUnit[0].toInt()
      currentTime - (daysAgo * 24 * 60 * 60 * 1000)
    }

    dateString.contains("hour")   -> {
      val hoursAgo = timeUnit[0].toInt()
      currentTime - (hoursAgo * 60 * 60 * 1000)
    }

    dateString.contains("minute") -> {
      val minutesAgo = timeUnit[0].toInt()
      currentTime - (minutesAgo * 60 * 1000)
    }

    else                          -> currentTime
  }
}
