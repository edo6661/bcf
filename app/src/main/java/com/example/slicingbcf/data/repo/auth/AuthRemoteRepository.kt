package com.example.slicingbcf.data.repo.auth

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.request.auth.LoginRequest
import com.example.slicingbcf.data.remote.request.auth.RefreshTokenRequest
import com.example.slicingbcf.data.remote.response.auth.ResponseLogin
import com.example.slicingbcf.data.remote.response.auth.ResponseLogout
import com.example.slicingbcf.data.remote.response.auth.ResponseRefreshToken
import kotlinx.coroutines.flow.Flow

interface AuthRemoteRepository{
  suspend fun login(
    loginRequest : LoginRequest
  ) : Flow<UiState<ResponseLogin>>
  suspend fun logout(
    refreshToken: String
  ) : Flow<UiState<ResponseLogout>>
  suspend fun getNewAccessToken(
    refreshTokenRequest: RefreshTokenRequest
  ) : Flow<UiState<ResponseRefreshToken>>
}