package com.example.slicingbcf.util

import androidx.core.text.isDigitsOnly

fun isValidTimeInput(input: String, isHour: Boolean): Boolean {
  return when {
    input.isEmpty() -> true
    input.isDigitsOnly() -> {
      val value = input.toIntOrNull() ?: return false
      if (isHour) {
        value in 1..12
      } else {
        value in 0..59
      }
    }
    else -> false // klo bkn angka
  }
}
