package com.example.slicingbcf.data.remote.api

import com.example.slicingbcf.data.remote.request.LoginRequest
import com.example.slicingbcf.data.remote.response.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

  @POST(ApiConfig.SIGN_IN)
  suspend fun signIn(
    @Body loginRequest : LoginRequest
  ) : Response<ResponseLogin>

}