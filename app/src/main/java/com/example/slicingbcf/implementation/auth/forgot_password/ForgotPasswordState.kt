package com.example.slicingbcf.implementation.auth.login

data class ForgotPasswordState(
  val email : String = "",
  val emailError : String? = null,
  val isLoading : Boolean = false,
  val error : String? = null,
  val message : String? = null,
  val isSuccess : Boolean = false
)

sealed class ForgotPasswordEvent {
  data class EmailChanged(val email : String) : ForgotPasswordEvent()
  data object Submit : ForgotPasswordEvent()
  data object ClearState : ForgotPasswordEvent()
}