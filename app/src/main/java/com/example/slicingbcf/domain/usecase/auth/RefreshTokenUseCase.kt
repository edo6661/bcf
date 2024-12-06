package com.example.slicingbcf.domain.usecase.auth

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.request.auth.RefreshTokenRequest
import com.example.slicingbcf.data.remote.response.auth.ResponseRefreshToken
import kotlinx.coroutines.flow.Flow

interface RefreshTokenUseCase {
  suspend operator fun invoke(
    refreshTokenRequest: RefreshTokenRequest
  ) : Flow<UiState<ResponseRefreshToken>>
}