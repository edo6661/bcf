package com.example.slicingbcf.domain.usecase.auth

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.request.auth.LoginRequest
import com.example.slicingbcf.data.remote.response.auth.ResponseLogin
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    suspend operator fun invoke(
        loginRequest : LoginRequest
    ) : Flow<UiState<ResponseLogin>>
}