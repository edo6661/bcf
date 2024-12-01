package com.example.slicingbcf.implementation.peserta.check_status_registrasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckStatusRegistrasiViewModel @Inject constructor() : ViewModel() {

  private val _state = MutableStateFlow(CheckStatusRegistrasiState())
  val state : StateFlow<CheckStatusRegistrasiState> = _state

  fun onEvent(event : CheckStatusRegistrasiEvent) {
    when (event) {
      is CheckStatusRegistrasiEvent.EmailChanged -> {
        _state.value = _state.value.copy(email = event.email)
      }

      is CheckStatusRegistrasiEvent.CheckStatus  -> {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
          delay(4000)
          _state.value = _state.value.copy(
            isCheckedStatus = true,
            isLoading = false,
            message = "SELAMAT ANDA LOLOS"
          )
        }
      }

      is CheckStatusRegistrasiEvent.ClearState   -> {
        _state.value = CheckStatusRegistrasiState()
      }
    }
  }
}
