package com.example.slicingbcf.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("data")
	val data: ResponseDataLogin,

	@field:SerializedName("meta")
	val meta: Meta,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class ResponseUserProfile(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("bio")
	val bio: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("age")
	val age: Any,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class 	ResponseDataLogin(

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("isVerified")
	val isVerified: Boolean,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("isEmailVerified")
	val isEmailVerified: Boolean,

	@field:SerializedName("userProfile")
	val userProfile: ResponseUserProfile,

	@field:SerializedName("profilePicture")
	val profilePicture: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)

