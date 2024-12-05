package com.example.slicingbcf.data.remote.response

import com.google.gson.annotations.SerializedName

data class Meta(

  @field:SerializedName("version")
  val version: String,

  @field:SerializedName("timestamp")
  val timestamp: String
)
