package com.example.slicingbcf.implementation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.request.auth.LoginRequest
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import com.example.slicingbcf.domain.usecase.auth.LoginUseCaseImplementation
import com.example.slicingbcf.domain.validator.ValidationResult
import com.example.slicingbcf.domain.validator.validateEmail
import com.example.slicingbcf.domain.validator.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModelWithApi @Inject constructor(
  private val loginUseCase : LoginUseCaseImplementation,
  @IODispatcher private val ioDispatcher : CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher : CoroutineDispatcher
) : ViewModel() {

  // ! EMAIL: mzulfanabdillah@gmail.com
  // ! PASSWORD: randomDummy1234

  private val _uiState = MutableStateFlow(LoginState())
  val uiState get() = _uiState.asStateFlow()

  fun onEvent(event : LoginEvent) {
    when (event) {
      is LoginEvent.EmailChanged    -> onChangeEmail(event.email)
      is LoginEvent.PasswordChanged -> onChangePassword(event.password)
      is LoginEvent.Submit          -> handleSubmit()
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
        email = _uiState.value.email
      )
    }
  }

  private fun handleSubmit() {
    val email = _uiState.value.email
    val password = _uiState.value.password

    validate(email, password)

    if (_uiState.value.emailError.isNullOrEmpty() && _uiState.value.passwordError.isNullOrEmpty()) {
      onSubmit()
    }
  }

  private fun onSubmit() {
    resetState()

    val email = _uiState.value.email
    val password = _uiState.value.password

    viewModelScope.launch(ioDispatcher) {
      loginUseCase(
        LoginRequest(
          email = email,
          password = password
        )
      )
        .onStart { updateLoadingState(true) }
        .collect { result ->
          when (result) {
            is UiState.Success -> updateSuccessState("Login Success")
            is UiState.Error   -> updateErrorState(result.message)
            is UiState.Loading -> updateLoadingState(true)

          }
        }
    }
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

  private suspend fun updateLoadingState(isLoading : Boolean) {
    withContext(mainDispatcher) {
      _uiState.update { it.copy(isLoading = isLoading) }
    }
  }

  private suspend fun updateSuccessState(message : String) {
    withContext(mainDispatcher) {
      _uiState.update {
        it.copy(
          isLoading = false,
          isSuccess = true,
          message = message
        )
      }
    }
  }

  private suspend fun updateErrorState(error : String?) {
    withContext(mainDispatcher) {
      _uiState.update {
        it.copy(
          isLoading = false,
          error = error
        )
      }
    }
  }
}
