package com.example.slicingbcf.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.preferences.UserRemotePreferences
import com.example.slicingbcf.data.remote.request.auth.RefreshTokenRequest
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import com.example.slicingbcf.domain.usecase.auth.LogoutUseCaseImplementation
import com.example.slicingbcf.domain.usecase.auth.RefreshTokenUseCaseImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.slicingbcf.domain.model.User as UserRemote

@HiltViewModel
class UserViewModel @Inject constructor(
  private val refreshTokenUseCase : RefreshTokenUseCaseImplementation,
  private val logoutUseCase : LogoutUseCaseImplementation,
  private val userRemotePreferences : UserRemotePreferences,
  @IODispatcher private val ioDispatcher : CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher : CoroutineDispatcher
) : ViewModel() {

  //
  private val _state = MutableStateFlow(UserState())
  val state : StateFlow<UserState> = _state.asStateFlow()

  init {
    viewModelScope.launch {
      userRemotePreferences.getUserData().collect { user ->
        _state.value = UserState(
          user = user
        )
      }
    }
    viewModelScope.launch {
      userRemotePreferences.getAccessToken().collect { accessToken ->
        Log.d("UserViewModel", "Access Token: $accessToken")
      }
    }
  }

  fun onEvent(event : UserEvent) {
    when (event) {
      is UserEvent.Logout -> logout()
      is UserEvent.GetNewAccessToken -> getNewAccessToken()
    }
  }
  private fun logout() {
    viewModelScope.launch(ioDispatcher) {
      val refreshToken = userRemotePreferences.getRefreshToken().first()

      logoutUseCase(refreshToken ?: "Empty Refresh Token")
        .onStart {
          updateLoadingState(true)
        }
        .collect{
          when (it) {
            is UiState.Success -> {
              updateSuccessState("Logout Success")
            }
            is UiState.Error -> {
              updateErrorState(it.message)
            }
            is UiState.Loading -> {
              updateLoadingState(true)
            }
          }
        }
    }
  }
  private fun getNewAccessToken() {
    viewModelScope.launch(ioDispatcher) {
      val refreshToken = userRemotePreferences.getRefreshToken().first()

      refreshTokenUseCase(
        RefreshTokenRequest(
          refreshToken = refreshToken ?: "Empty Refresh Token"
        )
      )
        .onStart {
          updateLoadingState(true)
        }
        .collect {
          when (it) {
            is UiState.Success -> {
              updateSuccessState("Get New Access Token Success")
            }

            is UiState.Error   -> {
              updateErrorState(it.message)
            }

            is UiState.Loading -> {
              updateLoadingState(true)
            }
          }
        }
    }
  }
  private suspend fun updateLoadingState(isLoading : Boolean) {
    withContext(mainDispatcher) {
      _state.update { it.copy(isLoading = isLoading) }
    }
  }

  private suspend fun updateSuccessState(message : String) {
    withContext(mainDispatcher) {
      _state.update {
        it.copy(
          isLoading = false,
          error = null,
          message = message
        )
      }
    }
  }

  private suspend fun updateErrorState(error : String?) {
    withContext(mainDispatcher) {
      _state.update {
        it.copy(
          isLoading = false,
          error = error
        )
      }
    }
    delay(5000)
    withContext(mainDispatcher) {
      _state.update {
        it.copy(
          error = null
        )
      }
    }
  }
}
sealed class UserEvent {
  object Logout : UserEvent()
  object GetNewAccessToken : UserEvent()
}
data class UserState(
  val user : UserRemote? = null,
  val message : String? = null,
  val isLoading : Boolean = false,
  val error : String? = null
)