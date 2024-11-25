package com.example.slicingbcf.implementation.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.slicingbcf.domain.validator.ValidationResult
import com.example.slicingbcf.domain.validator.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {

  private val _state = MutableStateFlow(ForgotPasswordState())
  val state get() = _state.asStateFlow()

  fun onEvent(event : ForgotPasswordEvent) {
    when (event) {
      is ForgotPasswordEvent.EmailChanged -> onChangeEmail(event.email)

      is ForgotPasswordEvent.Submit       -> {
        val email = _state.value.email
        onEmailError(email)
        if (_state.value.emailError.isNullOrEmpty()) {
          onSubmit()
        }
      }

      is ForgotPasswordEvent.ClearState   -> onClear()
      else                                -> error("Unexpected event")
    }
  }


  private fun onClear() {
    _state.value = ForgotPasswordState()
  }

  private fun onSubmit() {
    Log.d("ForgotPasswordViewModel", "submit: ")
//    _state.value = _state.value.copy(
//      isSuccess = true,
//      message = "Permintaan berhasil, silakan cek email Anda."
//    )
    _state.value = _state.value.copy(
      isSuccess = false,
      error = "Gagal: Permintaan gagal, silakan coba lagi."
    )

    // TODO: Implement submit
  }

  private fun onChangeEmail(email : String) {
    _state.value = _state.value.copy(email = email)
  }

  private fun onEmailError(emailError : String) {
    when (val emailValidationResult = emailError.validateEmail()) {
      is ValidationResult.Valid   -> {
        _state.value = _state.value.copy(emailError = null)
      }

      is ValidationResult.Invalid -> {
        _state.value = _state.value.copy(emailError = emailValidationResult.message)
      }
    }
  }


}