package com.example.slicingbcf.data.local

import java.time.LocalDate
import java.time.LocalTime

data class DetailJadwal(
    val id: String,
    val title: String,
    val type: String,
    val date: LocalDate,
    val beginTime: LocalTime,
    val endTime: LocalTime,
    val speaker: String,
    val description: String,
    val link: String,
    val color: Int,
)

val detailJadwal = listOf(
    DetailJadwal(
        id = "1234",
        title = "Melangkah Bersama: Membimbing Menuju Masa Depan Pendidikan yang Lebih Baik",
        type = "Mentoring",
        date = LocalDate.of(2024, 11, 26),
        beginTime = LocalTime.of(9, 0),
        endTime = LocalTime.of(12, 0),
        speaker = "Dodi Supriyadi",
        description = "Agenda bertujuan untuk memberikan bimbingan kepada para pendidik, orangtua, dan pemangku kepentingan lainnya dalam menghadapi tantangan pendidikan.",
        link = "https://tel.meet/wvc-qcnf-jtb?pin=3575142989705",
        color = 0xFF8E24AA.toInt()
    ),
    DetailJadwal(
        id = "1235",
        title = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
        type = "Mentoring",
        date = LocalDate.of(2024, 11, 29),
        beginTime = LocalTime.of(13, 0),
        endTime = LocalTime.of(15, 0),
        speaker = "Dodi Supriyadi",
        description = "Agenda bertujuan untuk memberikan bimbingan kepada para pendidik, orangtua, dan pemangku kepentingan lainnya dalam menghadapi tantangan pendidikan.",
        link = "https://tel.meet/wvc-qcnf-jtb?pin=3575142989705",
        color = 0xFF40E0D0.toInt()
    )
)
