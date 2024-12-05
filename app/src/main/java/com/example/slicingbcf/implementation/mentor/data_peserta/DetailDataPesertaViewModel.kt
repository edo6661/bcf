package com.example.slicingbcf.implementation.mentor.data_peserta


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.ProfilLembaga
import com.example.slicingbcf.data.local.profilLembaga
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DetailDataPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher : CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher : CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow<UiState<ProfilLembaga>>(UiState.Loading)
  val state : StateFlow<UiState<ProfilLembaga>> = _state.asStateFlow()

  init {
    loadDetailData()
  }

  fun onEvent(event : DetailDataPesertaEvent) {
    when (event) {
      is DetailDataPesertaEvent.ReloadData -> {
        loadDetailData()
      }
    }
  }

  private fun loadDetailData() {

    viewModelScope.launch(ioDispatcher) {
      try {
//        delay(1500)
        val data = profilLembaga[0]
        withContext(mainDispatcher) {
          _state.value = UiState.Success(data)
        }
      } catch (e : Exception) {
        withContext(mainDispatcher) {
          _state.value = UiState.Error(e.message ?: "Terjadi kesalahan")
        }
      }
    }
  }
}

sealed class DetailDataPesertaEvent {
  object ReloadData : DetailDataPesertaEvent()
}
