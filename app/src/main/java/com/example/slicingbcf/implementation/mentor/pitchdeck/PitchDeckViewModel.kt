package com.example.slicingbcf.implementation.mentor.pitchdeck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.PitchDeck
import com.example.slicingbcf.data.local.pitchDeck
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
class PitchDeckViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<PitchDeck>>>(UiState.Loading)
    val state: StateFlow<UiState<List<PitchDeck>>> = _state.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: PitchDeckEvent) {
        when (event) {
            is PitchDeckEvent.ReloadData -> {
                loadData()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch(ioDispatcher) {
            _state.value = UiState.Loading
            try {
                delay(1500)
                val data = pitchDeck
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
sealed class PitchDeckEvent {
    object ReloadData : PitchDeckEvent()
}