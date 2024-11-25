package com.example.slicingbcf.implementation.auth.login

import androidx.lifecycle.ViewModel
import com.example.slicingbcf.domain.validator.ValidationResult
import com.example.slicingbcf.domain.validator.validateEmail
import com.example.slicingbcf.domain.validator.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

  val _uiState = MutableStateFlow(LoginState())
  val uiState get() = _uiState.asStateFlow()

  fun onEvent(event : LoginEvent) {
    when (event) {
      is LoginEvent.EmailChanged    -> onChangeEmail(event.email)
      is LoginEvent.PasswordChanged -> onChangePassword(event.password)

      is LoginEvent.Submit          -> {

        val email = _uiState.value.email
        val password = _uiState.value.password
        validate(email, password)
        if (_uiState.value.emailError.isNullOrEmpty() && _uiState.value.passwordError.isNullOrEmpty()) {
          onSubmit()
        }
      }

      is LoginEvent.ClearState      -> onClear()
    }
  }

  private fun onClear() {
    _uiState.value = LoginState()
  }

  private fun resetState() {
    _uiState.update {
      it.copy(
        isSuccess = false,
        error = null,
        message = null,
        email = _uiState.value.email,
      )
    }
  }


  private fun onSubmit() {
    resetState()
//    _uiState.value = _uiState.value.copy(
//      isSuccess = false,
//      error = "Login Gagal! Akun Tidak Dikenal!"
//    )
    _uiState.value = _uiState.value.copy(
      isSuccess = true,
      message = "Permintaan berhasil, silakan cek email Anda."
    )
  }


  private fun onChangeEmail(email : String) {
    _uiState.update { it.copy(email = email) }
  }

  private fun onChangePassword(password : String) {
    _uiState.update { it.copy(password = password) }
  }


  private fun validate(email : String, password : String) {
    val emailValidationResult = email.validateEmail()
    val passwordValidationResult = password.validatePassword()

    _uiState.update {
      it.copy(
        emailError = if (emailValidationResult is ValidationResult.Invalid) emailValidationResult.message else null,
        passwordError = if (passwordValidationResult is ValidationResult.Invalid) passwordValidationResult.message else null
      )
    }
  }


}