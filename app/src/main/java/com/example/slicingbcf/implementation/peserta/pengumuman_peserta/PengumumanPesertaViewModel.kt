package com.example.slicingbcf.implementation.peserta.pengumuman_peserta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.Pengumuman
import com.example.slicingbcf.data.local.pengumumans
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
class PengumumanPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow<UiState<List<Pengumuman>>>(UiState.Loading)
  val state: StateFlow<UiState<List<Pengumuman>>> = _state.asStateFlow()

  private val _currentTab = MutableStateFlow(0)
  val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

  init {
    loadData()
  }

  fun onEvent(event: PengumumanPesertaEvent) {
    when (event) {
      is PengumumanPesertaEvent.TabChanged -> {
        _currentTab.value = event.tabIndex
      }
      is PengumumanPesertaEvent.ReloadData -> {
        loadData()
      }
    }
  }

  private fun loadData() {
    viewModelScope.launch(ioDispatcher) {
      _state.value = UiState.Loading
      try {
        delay(1500)
        val data = pengumumans
        withContext(mainDispatcher) {
          _state.value = UiState.Success(data)
        }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _state.value = UiState.Error(e.localizedMessage ?: "Unknown Error")
        }
      }
    }
  }
}

sealed class PengumumanPesertaEvent {
  data class TabChanged(val tabIndex: Int) : PengumumanPesertaEvent()
  object ReloadData : PengumumanPesertaEvent()
}