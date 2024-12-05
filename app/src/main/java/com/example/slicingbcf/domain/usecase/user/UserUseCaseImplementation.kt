package com.example.slicingbcf.domain.usecase.user

import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.remote.response.ResponseProfile
import com.example.slicingbcf.data.repo.user.UserRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCaseImplementation @Inject constructor(
  private val userRemoteRepository : UserRemoteRepository
) : UserUseCase {

  override fun getCurrentUserProfile() : Flow<UiState<ResponseProfile>> {
    return userRemoteRepository.getCurrentUserProfile()
  }

}