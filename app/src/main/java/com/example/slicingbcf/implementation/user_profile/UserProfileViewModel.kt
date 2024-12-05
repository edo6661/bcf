package com.example.slicingbcf.implementation.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.helper.toUser
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import com.example.slicingbcf.domain.model.User
import com.example.slicingbcf.domain.usecase.user.UserUseCaseImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
  @IODispatcher private val ioDispatcher : CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher : CoroutineDispatcher,
  private val userUseCase : UserUseCaseImplementation
) :ViewModel() {

  private val _uiState = MutableStateFlow(UserProfileState())
  val uiState get() = _uiState.asStateFlow()

  init {
    fetchUserProfile()
  }

  private fun fetchUserProfile() {
    viewModelScope.launch(ioDispatcher) {
      _uiState.value = _uiState.value.copy(isLoading = true)
      userUseCase.getCurrentUserProfile()
        .onStart {
          loadingChanged(true)
        }
        .collect { result ->
          when (result) {
            is UiState.Success -> {
              userChanged(result.data.toUser())
              loadingChanged(false)
            }
            is UiState.Error -> {
              errorChanged(result.message)
              loadingChanged(false)
            }
            is UiState.Loading -> {
              loadingChanged(true)
            }
          }
        }
    }
  }


  private suspend fun loadingChanged(isLoading : Boolean) {
    withContext(mainDispatcher) {
      _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
  }
  private suspend fun userChanged(user : User) {
    withContext(mainDispatcher) {
      _uiState.value = _uiState.value.copy(user = user)
    }
  }
  private suspend fun errorChanged(error : String?) {
    withContext(mainDispatcher) {
      _uiState.value = _uiState.value.copy(error = error)
    }
  }



}

data class UserProfileState(
  val isLoading : Boolean = false,
  val user : User? = null,
  val error : String? = null
)

