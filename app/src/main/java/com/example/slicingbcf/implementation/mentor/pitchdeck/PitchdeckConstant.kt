package com.example.slicingbcf.implementation.mentor.pitchdeck

import com.example.slicingbcf.data.local.LembarKerjaPeserta
import com.example.slicingbcf.data.local.worksheetsPeserta

class PitchdeckConstant {
  companion object {
     val mockUpPitchdeckMentor = worksheetsPeserta

    val mockUpLembarKerjaPeserta = listOf(
      LembarKerjaPeserta(
        namaPeserta = "Asep",
        waktuSubmisi = "Senin, 1 April 2024 13.55 WIB",
      ),
      LembarKerjaPeserta(
        namaPeserta = "Budi",
        waktuSubmisi = "Selasa, 2 April 2024 13.55 WIB",
      ),
      LembarKerjaPeserta(
        namaPeserta = "Cecep",
        waktuSubmisi = "Rabu, 3 April 2024 13.55 WIB",
      ),
    )

  }
}