package com.example.slicingbcf.data.remote.api

import com.example.slicingbcf.data.remote.request.auth.RefreshTokenRequest
import com.example.slicingbcf.data.remote.response.auth.ResponseRefreshToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface RefreshTokenService {
  @PUT(ApiConfig.AUTH)
  suspend fun getNewAccessToken(
    @Header("Authorization") accessToken: String,
    @Body refreshTokenRequest: RefreshTokenRequest
  ): Response<ResponseRefreshToken>
}
