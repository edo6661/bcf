package com.example.slicingbcf.domain.usecase.auth

import com.example.slicingbcf.data.remote.request.auth.RefreshTokenRequest
import com.example.slicingbcf.data.repo.auth.AuthRemoteRepository
import javax.inject.Inject

class RefreshTokenUseCaseImplementation @Inject constructor(private val authRemoteRepository : AuthRemoteRepository) : RefreshTokenUseCase {
  override suspend operator fun invoke(
    refreshTokenRequest: RefreshTokenRequest
  ) = authRemoteRepository.getNewAccessToken(
    refreshTokenRequest
  )

}