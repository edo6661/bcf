package com.example.slicingbcf.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseProfile(

	@field:SerializedName("data")
	val data: ResponseDataProfile,

	@field:SerializedName("meta")
	val meta: Meta,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean,

	@field:SerializedName("statusCode")
	val statusCode: Int
)


data class ResponseDataProfile(

	@field:SerializedName("profilePicture")
	val profilePicture: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("isVerified")
	val isVerified: Boolean,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("isEmailVerified")
	val isEmailVerified: Boolean,

	@field:SerializedName("userProfile")
	val userProfile: ResponseUserProfile,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
