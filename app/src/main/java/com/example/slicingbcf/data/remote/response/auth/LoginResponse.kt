package com.example.slicingbcf.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("data")
	val data: ResponseDataLogin,
	@field:	SerializedName("status")
	val status: String,
	// ! kalo fail
	@field:SerializedName("message")
	val message: String?

)

data class ResponseDataLogin(
	val accessToken: String,
	val refreshToken: String,
)
