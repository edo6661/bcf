package com.example.slicingbcf.implementation.peserta.feedback_peserta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.EvaluasiMentoring
import com.example.slicingbcf.data.local.evaluasiMentoring
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
class FeedbackPesertaViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher : CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher : CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<EvaluasiMentoring>>>(UiState.Loading)
    val state: StateFlow<UiState<List<EvaluasiMentoring>>> = _state.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event : FeedbackPesertaEvent) {
        when (event) {
            is FeedbackPesertaEvent.ReloadData -> {
                loadData()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch(ioDispatcher) {
            try {
                delay(1500)
                val data = evaluasiMentoring
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

sealed class FeedbackPesertaEvent {
    object ReloadData : FeedbackPesertaEvent()
}