package com.example.slicingbcf.data.remote.api

import com.example.slicingbcf.data.remote.request.auth.LoginRequest
import com.example.slicingbcf.data.remote.request.auth.RefreshTokenRequest
import com.example.slicingbcf.data.remote.response.auth.ResponseLogin
import com.example.slicingbcf.data.remote.response.auth.ResponseLogout
import com.example.slicingbcf.data.remote.response.auth.ResponseRefreshToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT


interface ApiService {

  // AUTH
  @POST(ApiConfig.AUTH)
  suspend fun signIn(
    @Body loginRequest : LoginRequest
  ) : Response<ResponseLogin>
  @DELETE(ApiConfig.AUTH)
  suspend fun logout(
    @Body refreshToken: String
  ) : Response<ResponseLogout>

  @PUT(ApiConfig.AUTH)
  suspend fun getNewAccessToken(
    @Body refreshTokenRequest: RefreshTokenRequest
  ) : Response<ResponseRefreshToken>

}