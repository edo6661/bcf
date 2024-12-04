package com.example.slicingbcf.domain.usecase.auth

import com.example.slicingbcf.data.remote.request.LoginRequest
import com.example.slicingbcf.data.repo.auth.AuthRemoteRepository
import javax.inject.Inject

class LoginUseCaseImplementation @Inject constructor(private val authRemoteRepository : AuthRemoteRepository) : LoginUseCase {
  override suspend operator fun invoke(
    loginRequest : LoginRequest
  ) = authRemoteRepository.login(
    loginRequest
  )

}