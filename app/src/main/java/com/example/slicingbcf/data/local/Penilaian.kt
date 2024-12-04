package com.example.slicingbcf.data.local


data class Penilaian(
  val namaLembaga : String,
  val batch : Int,
  val totalPenilaian : Int,
)

val headerTable = listOf(
  "No",
  "Aspek Penilaian",
  "Penilaian",
)

data class PenilaianUmum(
  val aspekPenilaian : String,
  val penilaian : Int
)


val penilaianUmums = listOf(
  PenilaianUmum("Kehadiran", 50),
  PenilaianUmum("Keaktifan", 40),
  PenilaianUmum("Kemandirian / Inisiatif", 30),
  PenilaianUmum("Pitching Day", 20),
  PenilaianUmum("Capaian pendanaan yang didapat", 10),
  PenilaianUmum("Kerjasama dengan instansi lain", 5),
  PenilaianUmum("Keaktifan Sosial Media", 5),
  PenilaianUmum("Pengurangan Nilai", 5),
)
val nilaiCapaianClusters = listOf(
  PenilaianUmum("Capaian Pendanaan", 10),
  PenilaianUmum("Kerjasama dengan Instansi Lain", 5),
  PenilaianUmum("Keaktifan Sosial Media", 5),
  PenilaianUmum("Pengurangan Nilai", 5),
)