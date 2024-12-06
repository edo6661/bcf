package com.example.slicingbcf.data.remote.response.auth

import com.google.gson.annotations.SerializedName


data class ResponseRefreshToken(


  @field:SerializedName("data")
  val data: ResponseDataRefreshToken,
  @field:  SerializedName("statusCode")
  val statusCode : Int,
  @field:  SerializedName("status")
  val status : String?,
  // ! kalo fail
  @field:SerializedName("message")
  val message : String?,
  val error : String?

)

data class ResponseDataRefreshToken(
  val accessToken: String,
)
