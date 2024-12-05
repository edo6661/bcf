package com.example.slicingbcf.data.remote.api

import com.example.slicingbcf.data.remote.request.LoginRequest
import com.example.slicingbcf.data.remote.response.ResponseLogin
import com.example.slicingbcf.data.remote.response.ResponseProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

  // AUTH
  @POST(ApiConfig.SIGN_IN)
  suspend fun signIn(
    @Body loginRequest : LoginRequest
  ) : Response<ResponseLogin>

  // USER
  @GET(ApiConfig.CURRENT_USER_PROFILE)
  suspend fun getCurrentUserProfile() : Response<ResponseProfile>

}