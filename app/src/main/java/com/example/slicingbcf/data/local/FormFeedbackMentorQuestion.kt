package com.example.slicingbcf.data.local

data class FormEvaluasiCapaianMentoring(
    val title: String
)

val formEvaluasiCapaianMentoring = listOf(
    FormEvaluasiCapaianMentoring(
        "Sharing isu yang digeluti, membahas 3P (Progress, Problem, Plan) yang dilalui peserta dalam kegiatan LEAD maupun yang dihadapi di lapangan"
    ),
    FormEvaluasiCapaianMentoring(
        "Pemetaan potensial stakeholder..."
    ),
    FormEvaluasiCapaianMentoring(
        "Menyusun strategi capaian pendanaan..."
    ),
)

data class FormEvaluasiMentor(
    val title: String
)

val formEvaluasiMentor = listOf(
    FormEvaluasiMentor(
        title = "Mentor memberikan penjelasan secara komprehensif selama mentoring",
        ),
    FormEvaluasiMentor(
        title = "Sesi mentoring berlangsung sesuai dengan kebutuhan pembelajaran peserta",
    ),
    FormEvaluasiMentor(
        title = "Mentor memberikan instruksi dan pertanyaan dengan jelas",
    ),
)

data class FormMentor(
    val title: String
)

val formMentor= listOf (
    FormMentor(
        title = "Evaluasi Capaian Mentoring"
    ),
    FormMentor(
        title = "Nama Mentor"
    ),
    FormMentor(
        title = "Periode Capaian Mentoring"
    ),
    FormMentor(
        title = "Evaluasi Capaian Mentoring 1"
    ),
    FormMentor(
        title = "Evaluasi Mentor"
    ),
    FormMentor(
        title = "Pembahasan selama kegiatan mentoring"
    ),
    FormMentor(
        title = "Kritik dan saran kegiatan mentoring"
    ),
    FormMentor(
        title = "Dokumen sesi mentoring cluster"
    ),
)

