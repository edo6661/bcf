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
        title = "Pitch Deck A",
        batch = 6,
        description = """
            Deskripsi Pitch Deck A 
        """.trimIndent(),
        link = "https://bit.ly/pitchdeckcapaianTPT",
        submissionDeadline = "Jumat, 15 November 2024 13:55"),
    PitchDeck(
        title = "Pitch Deck B",
        batch = 6,
        description = """
            Deskripsi Pitch Deck B
        """.trimIndent(),
        link = "https://bit.ly/pitchdeckcapaianTPT",
        submissionDeadline = "Jumat, 15 November 2024 13:55"),
    PitchDeck(
        title = "Pitch Deck C",
        batch = 6,
        description = """
            Deskripsi Pitch Deck C
        """.trimIndent(),
        link = "https://bit.ly/pitchdeckcapaianTPT",
        submissionDeadline = "Jumat, 15 November 2024 13:55"),PitchDeck(
        title = "Pitch Deck D",
        batch = 6,
        description = """
            Deskripsi Pitch Deck D
        """.trimIndent(),
        link = "https://bit.ly/pitchdeckcapaianTPT",
        submissionDeadline = "Jumat, 15 November 2024 13:55"),
)