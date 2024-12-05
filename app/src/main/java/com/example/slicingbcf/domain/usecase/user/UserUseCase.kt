package com.example.slicingbcf.domain.usecase.user

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.response.ResponseProfile
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

  fun getCurrentUserProfile() : Flow<UiState<ResponseProfile>>
}