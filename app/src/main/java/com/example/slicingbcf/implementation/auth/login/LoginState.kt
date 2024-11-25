package com.example.slicingbcf.implementation.auth.login

data class LoginState(
  val email : String = "",
  val emailError : String? = null,
  val password : String = "",
  val passwordError : String? = null,
  val isLoading : Boolean = false,
  val error : String? = null,
  val message : String? = null,
  val isSuccess : Boolean = false,
)

sealed class LoginEvent {
  data class EmailChanged(val email : String) : LoginEvent()
  data class PasswordChanged(val password : String) : LoginEvent()
  data object Submit : LoginEvent()
  data object ClearState : LoginEvent()
}