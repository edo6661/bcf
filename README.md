# LEAD Indonesia Mobile Application

Aplikasi **LEAD Indonesia** mendukung program **Leadership Experience and Development (LEAD)** oleh **Bakrie Center Foundation (BCF)** dengan tujuan mengurangi ketergantungan pada aplikasi pihak ketiga, mengintegrasikan data BCF, dan menyederhanakan proses bisnis LEAD.

## Tujuan Aplikasi

- Mengurangi ketergantungan pada aplikasi pihak ketiga.
- Mengintegrasikan data program LEAD Indonesia.
- Menyederhanakan proses bisnis program LEAD.

## Fitur Utama

### Auth

- Registrasi Peserta
- Login
- Lupa Password

### Peserta

- Data Peserta & Lembaga
- Kelompok Mentoring & Mentor
- Worksheet
- Jadwal Kegiatan & Pengumuman
- Feedback Peserta & Mentor
- Profil Peserta & Lembaga
- Laporan Bulanan
- Pengaturan & Penilaian Peserta

### Mentor

- Profil & Laporan Mentor
- Forum Diskusi
- Worksheet & Jadwal Kegiatan
- Umpan Balik Mentor & Penilaian Peserta

## Branching Strategy

- **main**: Branch utama, berisi kode siap produksi.
- **dev**: Branch pengembangan fitur, bug fixes, sebelum dipindah ke `main`.
- **remote**: Branch untuk kolaborasi tim, push/pull perubahan.

## API dan Integrasi

Dokumentasi API dapat diakses di [API Docs](https://straight-dareen-happiness-overload-7d6989f4.koyeb.app/api/docs/#/). API ini menangani data pengguna, peserta, mentor, feedback, dan laporan.

## Room Database

Aplikasi menggunakan **Room** untuk penyimpanan lokal. Berikut adalah entitas `User` yang digunakan untuk menyimpan data peserta dan lembaga:

```kotlin
@Entity(tableName = "users")
data class User(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val namaLembaga: String?,
  val emailLembaga: String?,
  val alamatLembaga: String?,
  val provinsi: String?,
  val kota: String?,
  val tanggalBerdiri: String?,
  val jenisLembagaSosial: String?,
  val jenisClusterLembagaSosial: String?,
  val fokusIsu: String?,
  val profilSingkatLembaga: String?,
  val alasanMengikutiLead: String?,
  val dokumenProfilPerusahaan: String?,
  val jangkauanProgram: String?,
  val wilayahJangkauanProgram: String?,
  val jumlahAngkaPenerimaanManfaat: List<JangkauanPenerimaManfaat>,
  val targetUtamaProgram: String?,
  val proposalProgramMitra: String?,
  val namaPeserta: String?,
  val posisi: String?,
  val pendidikanTerakhir: String?,
  val jenisKelamin: String?,
  val nomorWhatsapp: String?,
  val emailPeserta: String?,
  val password: String?,
  val ktp: String?,
  val cv: String?,
  val adaPengurusLainYangAkanDiikutSertakanSebagaiPeserta: Boolean,
  val alasanMengikutiAgenda: String?,
  val pernahMengikutiPelatihan: Boolean,
  val darimanaMengetahuiLead: String?,
  val yangDiketahuiTerkaitDesainProgram: String?,
  val yangDiketahuiTerkaitKeberlanjutan: String?,
  val yangDiketahuiTerkaitLaporanSosial: String?,
  val laporanAkhirTahun: String?,
  val ekspetasiMengikutiLead: String?,
  val halYangInginDitanyakanKeLead: String?,
  val umpanBalik: String?,
  val pengalamanMendaftarLead: String?,
  val role: String
)

enum class Role { PESERTA, MENTOR, SUPERADMIN }

@Serializable
data class JangkauanPenerimaManfaat(val kota: String, val jumlah: Int)

```

## Error Handling

Aplikasi menggunakan metode `safeApiCall` untuk menangani error pada panggilan API. Berikut implementasinya:

```kotlin
abstract class BaseRepository {

  protected suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
  ): UiState<T> {
    return try {
      val response = apiCall()
      if (response.isSuccessful) {
        response.body()?.let {
          UiState.Success(it)
        } ?: UiState.Error("Empty response")
      } else {
        val errorBody = response.errorBody()?.string()
        val errorMessage = parseErrorMessage(errorBody)
        UiState.Error(errorMessage)
      }
    } catch (e: Exception) {
      UiState.Error(e.message ?: "Unknown error")
    }
  }

  private fun parseErrorMessage(errorBody: String?): String {
    return try {
      JSONObject(errorBody ?: "").getString("message")
    } catch (e: Exception) {
      Log.e("BaseRepository", "parseErrorMessage: ${e.message}")
      "Unknown error occurred"
    }
  }
}
```

## Penjelasan Error Handling

- **safeApiCall**: Menangani permintaan API dan mengembalikan hasil sebagai `UiState` (`Success`/`Error`).
- **parseErrorMessage**: Mengekstrak pesan error dari body error yang diterima server.

## Teknologi yang Digunakan

- **Jetpack Compose**: Framework UI deklaratif.
- **Kotlin**: Bahasa pemrograman utama.
- **Hilt**: Dependency Injection.
- **DataStore**: Penyimpanan preferensi.
- **MVVM**: Arsitektur aplikasi.
- **Room**: Database lokal.
- **Coil**: Memuat gambar.
- **Retrofit**: Library HTTP.
- **Lottie**: Animasi berbasis JSON.

<!-- ## Pengujian Aplikasi

- **JUnit**: Pengujian unit.
- **MockK**: Mocking dependensi.
- **Espresso**: Pengujian UI. -->

## Struktur File Project

```
    📁BakrieCenterFoundation
    └── 📁constant
    └── 📁data
        └── 📁common
        └── 📁dao
            └── 📁helper
            └── 📁model
            └── 📁user
        └── 📁local
            └── 📁preferences
        └── 📁remote
            └── 📁api
            └── 📁helper
            └── 📁request
            └── 📁response
        └── 📁repo
            └── 📁auth
            └── 📁user
        └── 📁viewmodel
    └── 📁di
    └── 📁domain
        └── 📁model
        └── 📁usecase
            └── 📁auth
            └── 📁user
        └── 📁validator
    └── 📁implementation
        └── 📁auth
            └── 📁forgot_password
            └── 📁login
            └── 📁registrasi
        └── 📁mentor
            └── 📁data_peserta
            └── 📁feedback_peserta
            └── 📁forum_diskusi
            └── 📁jadwal
                └── 📁bulan
                └── 📁detail
                └── 📁minggu
                └── 📁tambah_jadwal
            └── 📁kelompok_mentoring_mentor
            └── 📁laporan
            └── 📁pengaturan_mentor
            └── 📁pengumuman_mentor
            └── 📁penilaian_peserta
            └── 📁pitchdeck
            └── 📁profil
            └── 📁umpan_balik
            └── 📁worksheet_mentor
        └── 📁peserta
            └── 📁check_status_registrasi
            └── 📁data_peserta
            └── 📁feedback_peserta
            └── 📁form_feedback_mentor
            └── 📁form_feedback_mini_training
            └── 📁form_monthly_report
            └── 📁jadwal
                └── 📁bulanan
                └── 📁detail
                └── 📁mingguan
            └── 📁kelompok_mentoring
            └── 📁pengaturan
            └── 📁pengumuman_peserta
            └── 📁penilaian_peserta
            └── 📁pitch_deck
            └── 📁profil
                └── 📁profil_lembaga
                └── 📁profil_peserta
            └── 📁pusat_informasi
            └── 📁worksheet_peserta
        └── 📁user_profile
    └── 📁interceptor
    └── 📁ui
        └── 📁animations
        └── 📁navigation
        └── 📁scaffold
        └── 📁shared
            └── 📁dialog
            └── 📁dropdown
            └── 📁form_monthly_report
            └── 📁message
            └── 📁pengumuman
            └── 📁pitchdeck_worksheet
            └── 📁pusat_informasi
            └── 📁rating
            └── 📁state
            └── 📁tabs
            └── 📁textfield
        └── 📁sidenav
        └── 📁theme
        └── 📁upload
    └── 📁util
    └── folder-structure.txt
```

### Deskripsi Folder dan File:

- **constant/**: Nilai konstan (palet warna, gaya teks).
- **data/**: Kelas dan objek terkait data (DAO, sumber data lokal/remote, repositori, view models).
- **di/**: Modul dependency injection (konfigurasi jaringan, database).
- **domain/**: Model domain, use case, dan validator.
- **implementation/**: Implementasi composables dan screens (auth, mentor, peserta).
- **interceptor/**: AuthInterceptor untuk permintaan jaringan.
- **ui/**: Komponen UI, animasi, navigasi, scaffold, komponen bersama, dan upload.
- **util/**: Kelas utilitas dan ekstensi.

## Lisensi

Aplikasi ini dilisensikan di bawah **MIT License**.

---

Dokumentasi ini dapat diperbarui sesuai perkembangan aplikasi. Untuk pertanyaan, hubungi tim pengembang aplikasi LEAD Indonesia.
