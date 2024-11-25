package com.example.slicingbcf.domain.validator

import android.util.Patterns

sealed class ValidationResult {
  object Valid : ValidationResult()
  data class Invalid(val message : String) : ValidationResult()
}

fun String.validateEmail() : ValidationResult {
  return if (Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
    ValidationResult.Valid
  } else {
    ValidationResult.Invalid("Invalid email")
  }
}

fun String.validatePassword() : ValidationResult {
  return if (this.length >= 6) {
    ValidationResult.Valid
  } else {
    ValidationResult.Invalid("Password must be at least 6 characters")
  }
}

fun String.validateConfirmPassword(password : String) : ValidationResult {
  return if (this == password) {
    ValidationResult.Valid
  } else {
    ValidationResult.Invalid("Password does not match")
  }
}

fun String.validateNotEmpty() : ValidationResult {
  return if (this.isNotEmpty()) {
    ValidationResult.Valid
  } else {
    ValidationResult.Invalid("Field must not be empty")
  }
}

