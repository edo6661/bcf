package com.example.slicingbcf.data.local

data class EvaluasiMentoring(
    val no: Int,
    val aspek_penilaian: String,
    val penilaian: String
)

// TODO: REPLACE WITH REAL DATA
val evaluasiMentoring = listOf(
    EvaluasiMentoring(
        no = 1,
        aspek_penilaian = "Tim menyampaikan laporan perkembangan ide secara jelas dan komprehensif.",
        penilaian = "Tidak Baik"
    ),
    EvaluasiMentoring(
        no = 2,
        aspek_penilaian = "Sesi mentoring berlangsung sesuai dengan kebutuhan pembelajaran peserta.",
        penilaian = "Tidak Baik"
    ),
    EvaluasiMentoring(
        no = 3,
        aspek_penilaian = "Tim memahami instruksi dan pertanyaan dari mentor dengan jelas.",
        penilaian = "Sangat Baik"
    ),
    EvaluasiMentoring(
        no = 4,
        aspek_penilaian = "Masing-masing anggota tim memiliki inisiatif untuk berkontribusi memberikan ide dan pendapat.",
        penilaian = "Sangat Baik"
    ),
    EvaluasiMentoring(
        no = 5,
        aspek_penilaian = "Tim memanfaatkan sesi mentoring untuk bertanya dan mengklarifikasi pemahaman.",
        penilaian = "Tidak Baik"
    ),
    EvaluasiMentoring(
        no = 6,
        aspek_penilaian = "Tim menggunakan tools/alat bantu yang mendukung proses mentoring berjalan dengan efektif.",
        penilaian = "Tidak Baik"
    ),
    EvaluasiMentoring(
        no = 7,
        aspek_penilaian = "Tim menerima dan memahami feedback yang diberikan oleh mentor.",
        penilaian = "Sangat Baik"
    )
)

data class EvaluasiLembaga(
    val no: Int,
    val aspek_penilaian: String,
    val penilaian: String
)

val evaluasiLembaga = listOf(
    EvaluasiLembaga(
        no = 1,
        aspek_penilaian = "Apakah lembaga mentoring on the track untuk mencapai tujuannya?",
        penilaian = "Tidak Baik"
    )
)

data class KepuasanMentoring(
    val no: Int,
    val aspek_penilaian: String,
    val penilaian: String
)

val kepuasanMentoring = listOf(
    KepuasanMentoring(
        no = 1,
        aspek_penilaian = "Apakah anda puas dalam sesi mentoring yang telah dilakukan",
        penilaian = "Sangat Puas"
    )
)

data class JawabanPertanyaan(
    val aspek: String,
    val jawaban: String
)

val jawabanMentoring = listOf(
    JawabanPertanyaan("Hal-Hal yang Dibahas Selama Kegiatan Mentoring", "Selama mentoring kami banyak mendiskusikan tentang kegiatan yang dilakukan mulai dari setelah training sampai dengan saat ini."),
    JawabanPertanyaan("Tantangan Utama yang Dihadapi", "Terbatasnya waktu, dana, dan personel yang dapat dialokasikan untuk kegiatan mentoring.")
)

data class DokumentasiMentoring(
    val namaFile: String,
    val path: String
)

val dokumentasiMentoring = listOf(
    DokumentasiMentoring("Dokumentasi Mentoring", "path/to/mentoring_dokumentasi.png")
)