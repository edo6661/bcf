package com.example.slicingbcf.implementation.peserta.check_status_registrasi

class CheckStatusRegistrasiConstant {
  companion object {

    val headers = listOf(
      "No",
      "Nama Instansi",
      "Provinsi",
      "Nama Peserta"
    )

    val data = listOf(
      CheckStatusRegistrasiPeserta(
        "Bakrie Center Foundation",
        "DKI Jakarta",
        "Zakaria Rahman"
      ),

      )


    data class CheckStatusRegistrasiPeserta(
      val namaInstansi : String,
      val provinsi : String,
      val namaPeserta : String,
    )
  }
}