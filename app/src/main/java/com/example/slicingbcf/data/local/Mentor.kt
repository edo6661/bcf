package com.example.slicingbcf.data.local

import androidx.room.Entity

data class Mentor(
    val namaLengkap: String,
    val tanggalLahir: String,
    val jenisKelamin: String,
    val email: String,
    val nomorHP: String,
    val pendidikanTerakhir: String,
    val jurusan: String,
    val pekerjaan: String,
    val instansi: String,
    val kategoriMentor: String,
    val batch: List<Batch>
)

data class Batch(
    val namaBatch: String,
    val kategoriMentor: String,
    val cluster: String,
    val fokusIsu: String,
    val listLembaga: List<Lembaga>,
    val tipeMentoring: String? = null,
    val capaianProgram: String? = null,
)

data class Lembaga(
    val namaLembaga: String,
    val fokusIsu: String,
    val provinsi: String,
    val namaPeserta: String? = null,
)

val listOfLembaga = listOf(
    Lembaga(
        "Bakrie Center Foundation",
        "Tuberculosis (TBC)",
        "DKI Jakarta",
        "Sri Maharani Arfiani"

    ),
    Lembaga(
        "Inisiatif Lampung",
        "Stunting",
        "Lampung"
    ),
    Lembaga(
        "YAMALI",
        "Stunting",
        "Sumatera Selatan"
    ),
    Lembaga(
        "STPI Penabulu Banten",
        "Stunting",
        "Banten"
    ),
    Lembaga(
        "STPI Penabulu DKI Jakarta",
        "Tuberculosis (TBC)",
        "DKI Jakarta"
    )
)

data class FokusIsu(
    val fokusIsu: String,
    val cluster: String,
    val batch: String
)

val listFokusIsu = listOf(
    FokusIsu(
        "Tuberculosis (TBC)",
        "Kesehatan",
        "3"
    ),
    FokusIsu(
        "Pendidikan Anak Prasejahtera",
        "Pendidikan",
        "3"
    ),
    FokusIsu(
        "Gerakan Lanjut Kuliah",
        "Pendidikan",
        "4"
    ),
    FokusIsu(
        "Ekosistem Lanjutan",
        "Lingkungan",
        "4"
    )
)

val listBatch = listOf(
    Batch(
        "Batch 4",
        "Desain Program",
        "Kesehatan",
        "Tuberculosis (TBC), Stunting, HIV/AIDS",
        listLembaga = listOfLembaga,
        "Cluster",
        "Mentoring 1 (Februari 2023)"
    ),
    Batch(
        "Batch 3",
        "Desain Program",
        "Kesehatan",
        "Tuberculosis (TBC), Stunting, HIV/AIDS",
        listLembaga = listOfLembaga
    ),
)

val mentor = Mentor(
    namaLengkap = "Dody Supriadi",
    tanggalLahir = "21-10-1990",
    jenisKelamin = "Pria",
    email = "dodysupriadi@gmail.com",
    nomorHP = "08123749297",
    pendidikanTerakhir = "S2",
    jurusan = "Sastra Mesin",
    pekerjaan = "IT Manager",
    instansi = "Bakrie Center Foundation",
    kategoriMentor = "Desain Program",
    batch = listBatch
)