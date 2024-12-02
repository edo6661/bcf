package com.example.slicingbcf.data.local

import java.util.Date

data class Pengumuman(
  val title : String,
  val date : Date,
  val content : String,
  val category : String
)

val pengumumans = listOf(
  Pengumuman(
    title = "Jangan lupa untuk mengumpulkan MISI 2 terkait Momen Onboarding sebelum Sabtu, 2 Mei 2023 pukul 19.00 WIB. Tetap semangat, ya!",
    date = Date(),
    content = "Content 1",
    category = "Berita"
  ),
  Pengumuman(
    title = "Pengumuman 2",
    date = Date(),
    content = "Content 2",
    category = "LEAD"
  ),
  Pengumuman(
    title = "Pengumuman 3",
    date = Date(),
    content = "Content 3",
    category = "BCF"
  ),
  Pengumuman(
    title = "Pengumuman 4",
    date = Date(),
    content = "Content 4",
    category = "Berita"
  ),
  Pengumuman(
    title = "Pengumuman 5",
    date = Date(),
    content = "Content 5",
    category = "LEAD"
  ),
)
