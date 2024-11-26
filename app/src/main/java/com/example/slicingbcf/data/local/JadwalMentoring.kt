package com.example.slicingbcf.data.local

import java.time.LocalDate
import java.time.LocalTime


// TODO: REPLACE WITH REAL DATA
val jadwalMentoring = listOf(
  JadwalMentoring(
    no = 1,
    tanggal = LocalDate.of(2023, 2, 8),
    waktuMulai = LocalTime.of(10, 0),
    waktuSelesai = LocalTime.of(11, 0)
  ),
  JadwalMentoring(
    no = 2,
    tanggal = LocalDate.of(2023, 2, 10),
    waktuMulai = LocalTime.of(10, 0),
    waktuSelesai = LocalTime.of(11, 0)
  ),
  JadwalMentoring(
    no = 3,
    tanggal = LocalDate.of(2023, 2, 12),
    waktuMulai = LocalTime.of(10, 0),
    waktuSelesai = LocalTime.of(11, 0)
  ),
  JadwalMentoring(
    no = 4,
    tanggal = LocalDate.of(2023, 2, 14),
    waktuMulai = LocalTime.of(10, 0),
    waktuSelesai = LocalTime.of(11, 0)
  ),
  )

data class JadwalMentoring(
  val no : Int,
  val tanggal: LocalDate,
  val waktuMulai: LocalTime,
  val waktuSelesai: LocalTime
)


