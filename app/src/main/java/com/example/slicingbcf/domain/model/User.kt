package com.example.slicingbcf.domain.model

data class User(
  val id: String,
  val username: String,
  val email: String,
  val role: String,

  val profilePicture: String,
  val isEmailVerified: Boolean,
  val isVerified: Boolean,
  val createdAt: String,
  val updatedAt: String,
  val userProfile: UserProfile
)

data class UserProfile(
  val id: String,
  val userId: String,
  val createdAt: String,
  val updatedAt: String,
  val bio: String?,
  val age: Any?
)
