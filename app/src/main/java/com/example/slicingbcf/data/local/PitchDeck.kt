package com.example.slicingbcf.data.local

data class PitchDeck(
    val title: String,
    val batch: Int,
    val description: String,
    val link: String,
    val submissionDeadline: String
)

val pitchDeck = listOf(
    PitchDeck(
        title = "Pitch Deck Program Peserta",
        batch = 5,
        description = """
        Buatlah sebuah presentasi singkat yang memuat terkait “Program Kolaborasi Peningkatan Capaian Terapi Pencegahan Tuberkulosis (TPT)” pada Balita dan anak usia 5–14 tahun dengan detail: Profil, Latar Belakang, Logical Framework Analysis, Indikator Program, Anggaran Pendanaan, dan lainnya.
    """.trimIndent(),
        link = "https://bit.ly/pitchdeckcapaianTPT",
        submissionDeadline = "Selasa, 2 April 2024 13:55"),
    PitchDeck(
        title = "Pitch Deck Program Peserta",
        batch = 6,
        description = """
        Buatlah sebuah presentasi singkat yang memuat terkait “Program Kolaborasi Peningkatan Capaian Terapi Pencegahan Tuberkulosis (TPT)” pada Balita dan anak usia 5–14 tahun dengan detail: Profil, Latar Belakang, Logical Framework Analysis, Indikator Program, Anggaran Pendanaan, dan lainnya.
    """.trimIndent(),
        link = "https://bit.ly/pitchdeckcapaianTPT",
        submissionDeadline = "Jumat, 15 November 2024 13:55"),
)