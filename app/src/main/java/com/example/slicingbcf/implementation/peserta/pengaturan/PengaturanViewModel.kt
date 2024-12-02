package com.example.slicingbcf.implementation.peserta.pengaturan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PengaturanViewModel @Inject constructor() : ViewModel() {

  private val _uiState = MutableStateFlow(PengaturanState())
  val uiState = _uiState.asStateFlow()

  fun onEvent(event : PengaturanEvent) {
    when (event) {
      is PengaturanEvent.OldPasswordChanged -> {
        _uiState.value = _uiState.value.copy(oldPassword = event.oldPassword)
      }

      is PengaturanEvent.NewPasswordChanged -> {
        _uiState.value = _uiState.value.copy(newPassword = event.newPassword)
      }

      is PengaturanEvent.ConfirmPasswordChanged -> {
        _uiState.value = _uiState.value.copy(confirmPassword = event.confirmPassword)
      }

      PengaturanEvent.Submit ->
        handleSubmit()
//        submitError()
      PengaturanEvent.ClearState -> {
        _uiState.value = PengaturanState()
      }
    }
  }

  private fun clearState() {
    _uiState.value = PengaturanState()
  }

  private fun validate(
    oldPassword : String,
    newPassword : String,
    confirmPassword : String
  ) : Boolean {
    val oldPasswordError = if (oldPassword.isBlank()) "Kata sandi lama tidak boleh kosong" else null
    val newPasswordError = if (newPassword.isBlank()) "Kata sandi baru tidak boleh kosong" else null
    val confirmPasswordError =
      if (confirmPassword != newPassword) "Konfirmasi kata sandi tidak sesuai" else null

    _uiState.value = _uiState.value.copy(
      oldPasswordError = oldPasswordError,
      newPasswordError = newPasswordError,
      confirmPasswordError = confirmPasswordError
    )
    return oldPasswordError == null && newPasswordError == null && confirmPasswordError == null
  }

  private fun handleSubmit() {
    val state = _uiState.value

    if (! validate(state.oldPassword, state.newPassword, state.confirmPassword)) return


    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isLoading = true)
      delay(10000)
      _uiState.value = _uiState.value.copy(
        isLoading = false,
        successMessage = "Kata sandi berhasil diubah!"
      )
      clearState()
    }
  }
  private fun submitError() {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = "Terjadi kesalahan, silahkan coba lagi"
    )
  }
}
