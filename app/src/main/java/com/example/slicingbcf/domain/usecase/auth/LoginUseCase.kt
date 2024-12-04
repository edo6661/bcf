package com.example.slicingbcf.domain.usecase.auth

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.request.LoginRequest
import com.example.slicingbcf.data.remote.response.ResponseLogin
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    suspend operator fun invoke(
        loginRequest : LoginRequest
    ) : Flow<UiState<ResponseLogin>>
}