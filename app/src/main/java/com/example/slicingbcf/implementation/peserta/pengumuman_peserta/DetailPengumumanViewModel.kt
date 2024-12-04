package com.example.slicingbcf.implementation.peserta.pengumuman_peserta


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.R
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailPengumumanPesertaViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _state = MutableStateFlow<UiState<PengumumanDetail>>(UiState.Loading)
  val state: StateFlow<UiState<PengumumanDetail>> = _state.asStateFlow()

  init {
    loadDetailPengumuman()
  }

  fun onEvent(event: DetailPengumumanPesertaEvent) {
    when (event) {
      is DetailPengumumanPesertaEvent.ReloadData -> loadDetailPengumuman()
    }
  }

  private fun loadDetailPengumuman() {
    viewModelScope.launch(ioDispatcher) {
      try {
        delay(1000)
        val mockData = PengumumanDetail(
          id = "1",
          title = "MISI 2: Share Momen Onboarding Nasional LEAD 8",
          imageUrl = R.drawable.ex_pengumuman,
          likeCount = 55,
          date = "31 Maret 2023, 10.00 WIB",
          description = "Teman-teman jangan lupa untuk share momen onboarding nasional di media sosial IG/TikTok/LinkedIn dengan ketentuan di atas. Setiap misi harus disubmit pada link formulir misi untuk nantinya akhir semester diakumulasikan menjadi nilai tambahan bagi nilai akhir mahasiswa 🥰🙏",
          submitLink = "https://bit.ly/MISICLP8",
          deadline = "Minggu, 7 April 2023 pukul 23.30 WIB"
        )
        _state.emit(UiState.Success(mockData))
      } catch (e: Exception) {
        _state.emit(UiState.Error("Gagal memuat data pengumuman"))
      }
    }
  }
}

sealed class DetailPengumumanPesertaEvent {
  object ReloadData : DetailPengumumanPesertaEvent()
}
data class PengumumanDetail(
  val id: String,
  val title: String,
  val imageUrl: Int,
  val likeCount: Int,
  val date: String,
  val description: String,
  val submitLink: String,
  val deadline: String
)

