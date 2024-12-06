package com.example.slicingbcf.domain.usecase.auth

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.response.auth.ResponseLogout
import kotlinx.coroutines.flow.Flow

interface LogoutUseCase {
  suspend operator fun invoke(
    refreshToken: String
  ) : Flow<UiState<ResponseLogout>>
}