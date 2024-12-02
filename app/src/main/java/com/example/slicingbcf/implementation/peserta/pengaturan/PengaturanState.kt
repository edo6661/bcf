package com.example.slicingbcf.implementation.peserta.pengaturan


data class PengaturanState(
  val oldPassword: String = "",
  val oldPasswordError: String? = null,
  val newPassword: String = "",
  val newPasswordError: String? = null,
  val confirmPassword: String = "",
  val confirmPasswordError: String? = null,
  val isLoading: Boolean = false,
  val successMessage: String? = null,
  val errorMessage: String? = null
)

sealed class PengaturanEvent {
  data class OldPasswordChanged(val oldPassword: String) : PengaturanEvent()
  data class NewPasswordChanged(val newPassword: String) : PengaturanEvent()
  data class ConfirmPasswordChanged(val confirmPassword: String) : PengaturanEvent()
  object Submit : PengaturanEvent()
  object ClearState : PengaturanEvent()
}
