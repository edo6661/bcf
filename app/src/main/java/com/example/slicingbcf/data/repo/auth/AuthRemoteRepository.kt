package com.example.slicingbcf.data.repo.auth

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.request.LoginRequest
import com.example.slicingbcf.data.remote.response.ResponseLogin
import kotlinx.coroutines.flow.Flow

interface AuthRemoteRepository{
  suspend fun login(
    loginRequest : LoginRequest
  ) : Flow<UiState<ResponseLogin>>
}