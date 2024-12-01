package com.example.slicingbcf.data.local

import com.example.slicingbcf.R
import com.example.slicingbcf.util.parseDate


data class DataPusatInformasi(
  val username : String?,
  val profilePicture : Int?,
  val timestamp : Long,
  val question : String,
)


val mockDataPusatInformasi = listOf(
  DataPusatInformasi(
    username = "John Doe",
    profilePicture = null,
    timestamp = parseDate("2 days ago"),
    question = "Bagaimana cara membuat pitch deck yang menarik?"
  ),
  DataPusatInformasi(
    username = "Jane Doe",
    profilePicture = R.drawable.avatar,
    timestamp = parseDate("3 days ago"),
    question = "Apa saja yang harus dipersiapkan sebelum membuat pitch deck?"
  ),
)

