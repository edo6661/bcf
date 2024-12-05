package com.example.slicingbcf.data.repo.user

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.response.ResponseProfile
import kotlinx.coroutines.flow.Flow

interface UserRemoteRepository {
  fun getCurrentUserProfile():Flow<UiState<ResponseProfile>>
}