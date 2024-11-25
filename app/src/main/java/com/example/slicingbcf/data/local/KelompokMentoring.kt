package com.example.slicingbcf.data.local

data class KelompokMentoring(
  val namaLembaga : String,
  val fokusIsu : String,
  val jumlahPeserta : Int,
  val jumlahMentor : Int,
  val jumlahSesi : Int,
)

val kelompoksMentoring = listOf(
  KelompokMentoring(
    namaLembaga = "Lembaga A",
    fokusIsu = "Pendidikan",
    jumlahPeserta = 20,
    jumlahMentor = 5,
    jumlahSesi = 10,
  ),
  KelompokMentoring(
    namaLembaga = "Lembaga A",
    fokusIsu = "Pendidikan",
    jumlahPeserta = 20,
    jumlahMentor = 5,
    jumlahSesi = 10,
  ),
  KelompokMentoring(
    namaLembaga = "Lembaga A",
    fokusIsu = "Pendidikan",
    jumlahPeserta = 20,
    jumlahMentor = 5,
    jumlahSesi = 10,
  ),
  KelompokMentoring(
    namaLembaga = "Lembaga A",
    fokusIsu = "Pendidikan",
    jumlahPeserta = 20,
    jumlahMentor = 5,
    jumlahSesi = 10,
  ),
  KelompokMentoring(
    namaLembaga = "Lembaga A",
    fokusIsu = "Pendidikan",
    jumlahPeserta = 20,
    jumlahMentor = 5,
    jumlahSesi = 10,
  ),
  KelompokMentoring(
    namaLembaga = "Lembaga A",
    fokusIsu = "Pendidikan",
    jumlahPeserta = 20,
    jumlahMentor = 5,
    jumlahSesi = 10,
  ),
  KelompokMentoring(
    namaLembaga = "Lembaga A",
    fokusIsu = "Pendidikan",
    jumlahPeserta = 20,
    jumlahMentor = 5,
    jumlahSesi = 10,
  ),
)


data class HeaderKelompokMentoring(
  val name : String,
  val weight : Float
)

val headerKelompokMentorings = listOf(
  HeaderKelompokMentoring(
    name = "No",
    weight = 0.5f,
  ),
  HeaderKelompokMentoring(
    name = "Nama Lembaga",
    weight = 1.5f,
  ),
  HeaderKelompokMentoring(
    name = "Fokus Isu",
    weight = 1f,
  ),
  HeaderKelompokMentoring(
    name = "Jumlah Peserta",
    weight = 1.2f,
  ),
  HeaderKelompokMentoring(
    name = "Jumlah Mentor",
    weight = 1.2f,
  ),
  HeaderKelompokMentoring(
    name = "Jumlah Sesi",
    weight = 1f,
  ),
)
