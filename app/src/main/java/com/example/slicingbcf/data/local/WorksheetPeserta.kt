package com.example.slicingbcf.data.local

data class WorksheetPeserta(
  val title : String,
  val subTitle : String,
  val description : String,
  val link : String,
  val submissionDeadline : String
)

data class DetailWorksheetPeserta(
  val judulLembarKerja : String,
  val tautanLembarKerja : String,
  val deskripsiLembarKerja : String,
  val batasSubmisi : String,
)

data class LembarKerjaPeserta(
  val namaPeserta : String,
  val waktuSubmisi : String,
)


val worksheetsPeserta = listOf(
  WorksheetPeserta(
    title = "[Capacity Building] Hari ke-4 Lembar Kerja - Topik:",
    subTitle = "Sustainability and Sustainable Development",
    description = "Lembar kerja ini akan membahas seputar media sosial, sasaran, persona, dan strategi yang dapat diaplikasikan dalam melakukan pemasaran sosial program",
    link = "https://bit.ly/pitchdeckcapaianTPT",
    submissionDeadline = "Jumat, 15 November 2024 13:55"
  ),
  WorksheetPeserta(
    title = "[Capacity Building] Hari ke-3 Lembar Kerja - Topik:",
    subTitle = "Sustainability and Sustainable Development",
    description = "Deskripsi worksheet 3",
    link = "https://bit.ly/pitchdeckcapaianTPT",
    submissionDeadline = "Jumat, 15 November 2024 13:55"
  ),
  WorksheetPeserta(
    title = "[Capacity Building] Hari ke-2 Lembar Kerja - Topik:",
    subTitle = "Sustainability and Sustainable Development",
    description = "Deskripsi worksheet 2",
    link = "https://bit.ly/pitchdeckcapaianTPT",
    submissionDeadline = "Jumat, 15 November 2024 13:55"
  ),
  WorksheetPeserta(
    title = "[Capacity Building] Hari ke-1 Lembar Kerja - Topik:",
    subTitle = "Sustainability and Sustainable Development",
    description = "Deskripsi worksheet 1",
    link = "https://bit.ly/pitchdeckcapaianTPT",
    submissionDeadline = "Jumat, 15 November 2024 13:55"
  ),
)
