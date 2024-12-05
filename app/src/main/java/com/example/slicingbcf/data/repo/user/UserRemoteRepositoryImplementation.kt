package com.example.slicingbcf.data.repo.user

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.api.ApiService
import com.example.slicingbcf.data.remote.response.ResponseProfile
import com.example.slicingbcf.data.repo.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRemoteRepositoryImplementation @Inject constructor(
  private val apiService : ApiService
) : UserRemoteRepository ,BaseRepository(){

  override fun getCurrentUserProfile() : Flow<UiState<ResponseProfile>> = flow {
    emit(UiState.Loading)
    emit(safeApiCall { apiService.getCurrentUserProfile() })


  }
}