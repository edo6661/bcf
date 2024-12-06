package com.example.slicingbcf.domain.model

data class User(
  val id: String,
  val username: String,
  val email: String,
  val role: String,
  val profilePicture: String = "https://i.pinimg.com/736x/1e/f6/42/1ef642c4c5864a930b260941dff37711.jpg",
)
