package com.example.slicingbcf.implementation.peserta.penilaian_peserta


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.local.Penilaian
import com.example.slicingbcf.data.local.nilaiCapaianClusters
import com.example.slicingbcf.data.local.penilaianUmums
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PenilaianPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow(PenilaianPesertaState())
  val state: StateFlow<PenilaianPesertaState> = _state

  init {
    loadPenilaian()
  }

  private fun loadPenilaian() {
    viewModelScope.launch(ioDispatcher) {
      _state.value = _state.value.copy(loading = true)

      try {
        delay(1500)
        _state.value = _state.value.copy(
          penilaian = Penilaian("Lembaga A", 1, 100),
          penilaianUmums = penilaianUmums,
          nilaiCapaianClusters = nilaiCapaianClusters,
          loading = false
        )
      } catch (e: Exception) {
        _state.value = _state.value.copy(
          loading = false,
          error = "Error loading data"
        )
      }
    }
  }

  fun onEvent(event: PenilaianPesertaEvent) {
    when (event) {
      is PenilaianPesertaEvent.LoadPenilaian -> loadPenilaian()
      is PenilaianPesertaEvent.Refresh -> loadPenilaian()
    }
  }
}
