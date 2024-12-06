package com.example.slicingbcf.implementation.mentor.laporan


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.local.listFokusIsu
import com.example.slicingbcf.data.local.listOfLembaga
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



@HiltViewModel
class LaporanPenilaianPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow(
    LaporanPenilaianPesertaState(

    )
  )
  val state: StateFlow<LaporanPenilaianPesertaState> = _state.asStateFlow()

  init {
    loadData()
  }

  fun onEvent(event: LaporanPenilaianPesertaEvent) {
    when (event) {
      is LaporanPenilaianPesertaEvent.OnChangeLoading -> {
        _state.update { it.copy(isLoading = event.isLoading) }
      }
      is LaporanPenilaianPesertaEvent.OnChangeError -> {
        _state.update { it.copy(error = event.error) }
      }
      is LaporanPenilaianPesertaEvent.OnChangeSearch -> {
        _state.update { it.copy(query = event.query) }
        filterData(event.query)
      }
      is LaporanPenilaianPesertaEvent.OnChangeTab -> {
        _state.update { it.copy(tab = event.tab, query = "") }
        filterData("")
      }
      is LaporanPenilaianPesertaEvent.Reload -> {
        loadData()
      }
    }
  }

  private fun loadData() {
    viewModelScope.launch(ioDispatcher) {
      _state.update { it.copy(isLoading = true, error = null) }
      try {
        delay(2000)

        val lembagaData = listOfLembaga
        val fokusIsuData = listFokusIsu

        withContext(mainDispatcher) {
          _state.update {
            it.copy(
              listOfLembaga = lembagaData,
              listFokusIsu = fokusIsuData,
              isLoading = false
            )
          }
        }
      } catch (e: Exception) {
        withContext(mainDispatcher) {
          _state.update {
            it.copy(
              error = e.localizedMessage ?: "Gagal memuat data",
              isLoading = false
            )
          }
        }
      }
    }
  }

  private fun filterData(query: String) {
    val currentTab = _state.value.tab
    if (currentTab == 0) {
      val filteredLembaga = listOfLembaga.filter { lembaga ->
        lembaga.namaLembaga.contains(query, ignoreCase = true) ||
        lembaga.fokusIsu.contains(query, ignoreCase = true) ||
        lembaga.provinsi.contains(query, ignoreCase = true)
      }
      _state.update { it.copy(listOfLembaga = filteredLembaga) }
    } else if (currentTab == 1) {
      val filteredFokusIsu = listFokusIsu.filter { fokusIsu ->
        fokusIsu.fokusIsu.contains(query, ignoreCase = true) ||
        fokusIsu.cluster.contains(query, ignoreCase = true) ||
        fokusIsu.batch.contains(query, ignoreCase = true)
      }
      _state.update { it.copy(
        listFokusIsu = filteredFokusIsu
      ) }
    }
  }
}
