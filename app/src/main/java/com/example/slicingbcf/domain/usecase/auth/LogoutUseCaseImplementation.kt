package com.example.slicingbcf.domain.usecase.auth

import com.example.slicingbcf.data.repo.auth.AuthRemoteRepository
import javax.inject.Inject

class LogoutUseCaseImplementation @Inject constructor(private val authRemoteRepository : AuthRemoteRepository) : LogoutUseCase {
  override suspend operator fun invoke(
    refreshToken: String
  ) = authRemoteRepository.logout(
    refreshToken
  )

}