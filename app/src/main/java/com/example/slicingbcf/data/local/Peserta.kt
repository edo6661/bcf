package com.example.slicingbcf.data.local


data class Peserta(
  val namaLembaga : String,
  val emailLembaga : String,
  val alamatLembaga : String,
  val jenisLembaga : String,
  val jenisClusterLembaga : String,
  val fokusIsu : String,
  val alasanMengikutiProgram : String,
  val cakupanProgram : String,
  val targetUtamaProgram : String,
  val wilayahJangkauanProgram : List<WilayahJangkauan>,
  val namaLengkap : String,
  val posisi : String,
  val pendidikanTerakhir : String,
  val nomorWhatsapp : String,
  val email : String,
)

data class WilayahJangkauan(
  val provinsi : String,
  val jumlahPenerimaManfaat : Int,

  )