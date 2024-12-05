package com.example.slicingbcf.data.remote.helper

import com.example.slicingbcf.data.remote.response.ResponseDataProfile
import com.example.slicingbcf.data.remote.response.ResponseProfile
import com.example.slicingbcf.domain.model.User
import com.example.slicingbcf.domain.model.UserProfile


fun ResponseDataProfile.toUserProfile(): UserProfile {
  return UserProfile(
    id = this.userProfile.id,
    userId = this.userProfile.userId,
    createdAt = this.userProfile.createdAt,
    updatedAt = this.userProfile.updatedAt,
    bio = this.userProfile.bio,
    age = this.userProfile.age
  )
}

fun ResponseProfile.toUser(): User {
  return User(
    id = this.data.id,
    username = this.data.username,
    email = this.data.email,
    role = this.data.role,
    profilePicture = this.data.profilePicture,
    isEmailVerified = this.data.isEmailVerified,
    isVerified = this.data.isVerified,
    createdAt = this.data.createdAt,
    updatedAt = this.data.updatedAt,
    userProfile = this.data.toUserProfile()
  )
}
