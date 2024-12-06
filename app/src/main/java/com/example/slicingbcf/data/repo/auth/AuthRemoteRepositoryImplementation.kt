package com.example.slicingbcf.data.repo.auth

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.preferences.UserRemotePreferences
import com.example.slicingbcf.data.remote.api.ApiService
import com.example.slicingbcf.data.remote.request.auth.LoginRequest
import com.example.slicingbcf.data.remote.request.auth.RefreshTokenRequest
import com.example.slicingbcf.data.remote.response.auth.ResponseLogin
import com.example.slicingbcf.data.remote.response.auth.ResponseLogout
import com.example.slicingbcf.data.remote.response.auth.ResponseRefreshToken
import com.example.slicingbcf.data.repo.BaseRepository
import com.example.slicingbcf.domain.model.User
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
      val result   = safeApiCall {
        apiService.signIn(
          loginRequest
        )
      }
      if (result is UiState.Success) {
        userRemotePreferences.saveUserSession(
          User(
            id = "MOCK_ID",
            username = loginRequest.email.split("@")[0],
            email = loginRequest.email,
            role = "USER",
          )
        )

        userRemotePreferences.saveUserAccessToken(result.data.data.accessToken)
        userRemotePreferences.saveUserRefreshToken(result.data.data.refreshToken)
      }
      emit(result)
    }

  override suspend fun logout(refreshToken : String) : Flow<UiState<ResponseLogout>> = flow {
    emit(UiState.Loading)
    // TODO: nanti benerin kalo endpoint logout dah diganti (soalnya request delete gabisa pake body di retrofit)
//    val result = safeApiCall {
//      apiService.logout(
//        refreshToken
//      )
//    }
//    if (result is UiState.Success) {
//
//    }
//    emit(result)
      userRemotePreferences.clearUserSession()
  }

  override suspend fun getNewAccessToken(refreshTokenRequest : RefreshTokenRequest) : Flow<UiState<ResponseRefreshToken>> = flow {
    emit(UiState.Loading)
    val result = safeApiCall {
      apiService.getNewAccessToken(
        refreshTokenRequest = refreshTokenRequest
      )
    }
    if (result is UiState.Success) {
      userRemotePreferences.saveUserAccessToken(result.data.data.accessToken)
    }
    emit(result)
  }

}