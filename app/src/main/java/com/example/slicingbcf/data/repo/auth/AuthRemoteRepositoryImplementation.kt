package com.example.slicingbcf.data.repo.auth

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.preferences.UserRemotePreferences
import com.example.slicingbcf.data.remote.api.ApiService
import com.example.slicingbcf.data.remote.helper.toUser
import com.example.slicingbcf.data.remote.request.LoginRequest
import com.example.slicingbcf.data.remote.response.ResponseLogin
import com.example.slicingbcf.data.repo.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRemoteRepositoryImplementation @Inject constructor(
  private val apiService : ApiService,
  private val userRemotePreferences : UserRemotePreferences
) : AuthRemoteRepository, BaseRepository() {

  override suspend fun login(
    loginRequest : LoginRequest
  ) : Flow<UiState<ResponseLogin>> =
    flow {
      emit(UiState.Loading)
      val result = safeApiCall {
        apiService.signIn(
          loginRequest
        )
      }
      if (result is UiState.Success) {
        userRemotePreferences.saveUserSession(result.data.toUser())
        userRemotePreferences.saveUserAccessToken(result.data.data.accessToken)
        userRemotePreferences.saveUserRefreshToken(result.data.data.refreshToken)
      }
      emit(result)
    }

}