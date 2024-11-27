package com.example.slicingbcf.data.local

data class WilayahJangkauan(
    val no: Int,
    val provinsi: String,
    val penerimaManfaat: Int,
    val rincianUrl: String
)

data class ProfilLembaga(
    val name: String,
    val email: String,
    val address: String,
    val type: String,
    val cluster: String,
    val focusIssue: String,
    val programReason: String,
    val programCoverage: String,
    val companyProfilePdf: String,
    val programProposalPdf: String,
    val backgroundImageUrl: String,
    val profileImageUrl: String,
    val wilayahJangkauan: List<WilayahJangkauan>
)

val profilLembaga = listOf(
    ProfilLembaga(
        name = "Bakrie Center Foundation",
        email = "bcf@gmail.com",
        address = "Wisma Bakrie 2nd Floor, Jalan H.R. Rasuna Said Kav B-1",
        type = "Gerakan",
        cluster = "Pendidikan",
        focusIssue = "Pendidikan Anak Prasejahtera",
        programReason = "Karena ingin mengembangkan komunitas",
        programCoverage = "Provinsi",
        companyProfilePdf = "https://example.com/Profil_BCF.pdf",
        programProposalPdf = "https://example.com/Proposal_Program.pdf",
        backgroundImageUrl = "https://s3-alpha-sig.figma.com/img/bdd8/8a6c/88c67a5b1a7f2e590c5891844cb4fc22?Expires=1732492800&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Jzxhi8~g0MDK5hdpItTvCjbA0mbIxvZyXMKFYYcRF-TKL6srsvhCNvEnC07q0nkgmUB-hbcYCylBrETbw3F~-qJC08tosX1vYDVPjdhyNP6sRWlA3sUsHGTY1SWY5OJ5NGdzoxx7NGQ0GnN7sicplo1s7JDwBiqAmSE~2ztE4bfsLxEPhimC~iCMoYOJPLvXZPDrksagG3rPOqflHPv9e5Y1R3lTfzHto4VPcHAhb9AODiZn4y3c63Bx~M5LTPxL0T3AZ~RWOGLciobCptC8hgLEBls5HJnPzrb9acOLwotKTydSyySy01vNwudKH5R6s3wLYxYokDggQUZJJdtorg__",
        profileImageUrl = "https://via.placeholder.com/100",
        wilayahJangkauan = listOf(
            WilayahJangkauan(1, "DKI Jakarta", 5000, "https://example.com/rincian1.pdf"),
            WilayahJangkauan(2, "Jawa Barat", 2700, "https://example.com/rincian2.pdf")
        )
    )
)

data class ProfilPeserta(
    val namaLengkap: String,
    val posisi: String,
    val pendidikanTerakhir: String,
    val nomorWhatsApp: String,
    val email: String,
    val ktpPesertaUrl: String,
    val cvPesertaUrl: String,
    val backgroundImageUrl: String,
    val profileImageUrl: String,
    var pertanyaanUmum: PertanyaanUmum
)

data class PertanyaanUmum(
    var pernahMempelajari: String = "",
    var mengetahuiLEAD: String = "",
    var desainProgram: String = "",
    var sustainability: String = "",
    var socialReport: String = "",
    var ekspektasi: String = "",
    var pertanyaanLainnya: String = "",
    var pengalamanRegistrasi: String = ""
)

val profilPeserta = listOf(
    ProfilPeserta(
        namaLengkap = "Aurelia Hawa",
        posisi = "Ketua Lembaga",
        pendidikanTerakhir = "S1 - Sistem Informasi",
        nomorWhatsApp = "081234567890",
        email = "aurel@gmail.com",
        ktpPesertaUrl = "https://example.com/KTP_Peserta1.pdf",
        cvPesertaUrl = "https://example.com/CV_Peserta1.pdf",
        backgroundImageUrl = "https://via.placeholder.com/300x150",
        profileImageUrl = "https://via.placeholder.com/100",
        pertanyaanUmum = PertanyaanUmum(
            pernahMempelajari = "Pernah mempelajari",
            mengetahuiLEAD = "Instagram @bakriecenter, Alumni LEAD Indonesia",
            desainProgram = "Program LEAD Indonesia 2023 bertujuan mengembangkan kepemimpinan.",
            sustainability = "Keberlanjutan adalah penggunaan metode yang berdampak positif.",
            socialReport = "Social report menggambarkan data performa media sosial.",
            ekspektasi = "Menambah wawasan dan relasi.",
            pertanyaanLainnya = "Apa saja keuntungan mengikuti program ini?",
            pengalamanRegistrasi = "Pengisian data sederhana dan to the point."
        )
    )
)

data class MentoringPeserta(
    val mentorName: String,
    val mentoringType: String,
    val batch: Int,
    val capaianProgram: String
)

val mentoringPeserta = listOf(
    MentoringPeserta(
        mentorName = "Dodit Saputra",
        mentoringType = "Cluster",
        batch = 5,
        capaianProgram = "Februari 2023"
    ),
    MentoringPeserta(
        mentorName = "Dodit Saputra",
        mentoringType = "Desain Program",
        batch = 5,
        capaianProgram = "Februari 2023"
    ),
)