package com.example.slicingbcf.implementation.peserta.check_status_registrasi

data class CheckStatusRegistrasiState(
  val email : String = "",
  val isLoading : Boolean = false,
  val isCheckedStatus : Boolean = false,
  val error : String? = null,
  val message : String? = null
)

sealed class CheckStatusRegistrasiEvent {

  data class EmailChanged(val email : String) : CheckStatusRegistrasiEvent()
  data object CheckStatus : CheckStatusRegistrasiEvent()
  data object ClearState : CheckStatusRegistrasiEvent()
}
