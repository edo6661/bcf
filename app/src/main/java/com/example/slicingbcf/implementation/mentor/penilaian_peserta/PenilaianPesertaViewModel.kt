package com.example.slicingbcf.implementation.mentor.penilaian_peserta


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.KelompokMentoring
import com.example.slicingbcf.data.local.PenilaianPeserta
import com.example.slicingbcf.data.local.kelompoksMentoring
import com.example.slicingbcf.data.local.penilaianPesertas
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
class PenilaianPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow<UiState<List<PenilaianPeserta>>>(UiState.Loading)
  val state: StateFlow<UiState<List<PenilaianPeserta>>> = _state.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  init {
    loadPenilaianPeserta()
  }

  fun onEvent(event: PenilaianPesertaEvent) {
    when (event) {
      is PenilaianPesertaEvent.Search -> {
        _searchQuery.value = event.query
        filterData(event.query)
      }
      is PenilaianPesertaEvent.Reload -> {
        loadPenilaianPeserta()
      }
    }
  }

  private fun loadPenilaianPeserta() {
    viewModelScope.launch(ioDispatcher) {
      _state.value = UiState.Loading
      try {
        delay(1000)
        val data = penilaianPesertas
        withContext(mainDispatcher) {
          _state.value = UiState.Success(data)
        }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _state.value = UiState.Error(e.localizedMessage ?: "Gagal memuat data")
        }
      }
    }
  }

  private fun filterData(query: String) {
    val filtered = penilaianPesertas.filter {
      it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
    }
    _state.value = UiState.Success(filtered)
  }
}

sealed class PenilaianPesertaEvent {
  data class Search(val query: String) : PenilaianPesertaEvent()
  object Reload : PenilaianPesertaEvent()
}
