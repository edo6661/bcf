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

- **constant/**  
  Berisi nilai-nilai konstan yang digunakan di seluruh aplikasi (warna, gaya teks, dll).

- **data/**  
  - **common/**: Kelas dan fungsi yang digunakan secara umum di aplikasi.  
  - **dao/**: Data access objects untuk akses ke database atau data lokal.  
    - **helper/**: Kelas atau fungsi pembantu untuk pengelolaan data (mapper).  
    - **model/**: Model data yang digunakan untuk menyimpan dan mengirim data.  
    - **user/**: Data terkait pengguna.  
  - **local/**: Penyimpanan data lokal.  
    - **preferences/**: Menyimpan preferensi pengguna.  
  - **remote/**: Akses data dari server atau API eksternal.  
    - **api/**: Untuk komunikasi dengan server.  
    - **helper/**: Kelas atau fungsi pembantu untuk pengelolaan data (mapper).  
    - **request/**: Mengelola permintaan data ke API.  
    - **response/**: Menangani respons API.  
  - **repo/**: Repositori untuk akses data lokal atau remote.  
    - **auth/**: Repositori autentikasi pengguna.  
    - **user/**: Repositori data pengguna.  
  - **viewmodel/**: `ViewModel` untuk mengelola state UI dan logika untuk layar tertentu.

- **di/**  
  Konfigurasi dan setup dependency injection (DI), termasuk dependensi jaringan dan database.

- **domain/**  
  - **model/**: Model yang digunakan dalam domain aplikasi.  
  - **usecase/**: Logika aplikasi yang berfokus pada tindakan atau proses bisnis.  
    - **auth/**: Use case autentikasi pengguna.  
    - **user/**: Use case pengelolaan data pengguna.  
  - **validator/**: Kelas untuk validasi input atau data.

- **implementation/**  
  - **auth/**: Implementasi terkait autentikasi (login, registrasi, reset password).  
  - **mentor/**: Fitur untuk mentor, termasuk data peserta, feedback, forum diskusi, dan laporan.  
  - **peserta/**: Fitur untuk peserta, termasuk data peserta, feedback, jadwal kegiatan, dan laporan.  
  - **user_profile/**: Profil pengguna untuk menampilkan dan mengelola informasi pengguna.

- **interceptor/**  
  Kelas untuk intercept permintaan dan respons API untuk log atau pemrosesan tambahan (konfigurasi headers).

- **ui/**  
  - **animations/**: Animasi yang digunakan di berbagai bagian UI.  
  - **navigation/**: Menangani navigasi antar layar.  
  - **scaffold/**: Struktur dasar halaman aplikasi.  
  - **shared/**: Komponen UI bersama, seperti dialog, dropdown, pesan, rating, tabs, dan textfield.  
  - **sidenav/**: Sidebar navigasi.  
  - **theme/**: Tema aplikasi, termasuk gaya dan warna.  
  - **upload/**: Mengelola upload file.

- **util/**  
  Folder untuk utilitas atau helper function yang digunakan di berbagai bagian aplikasi.

## Lisensi

Aplikasi ini dilisensikan di bawah **MIT License**.

---

Dokumentasi ini dapat diperbarui sesuai perkembangan aplikasi. Untuk pertanyaan, hubungi tim pengembang aplikasi LEAD Indonesia.

# Dokumentasi Data Aplikasi

## Color

```kotlin

object ColorPalette {


  val OnPrimary = Color(0xFFFFFFFF)
  val OnSurface = Color(0xFF1D1B20)
  val OnSurfaceVariant = Color(0xFF49454F)
  val Outline = Color(0xFF79747E)
  val OutlineVariant = Color(0xFFCAC4D0)

  val Surface = Color(0xFFFEF7FF)
  val SurfaceContainerLowest = Color(0xFFFFFFFF)
  val PrimaryColor700 = Color(0xFF0D4690)
  val PrimaryColor200 = Color(0xFFA6C9F7)

  val Monochrome10 = Color(0xFFFFFFFF)
  val Monochrome100 = Color(0xFFFAFAFA)
  val Monochrome150 = Color(0xFFE3E3E3)
  val Monochrome200 = Color(0xFFD1D1D1)
  val Monochrome300 = Color(0xFFB8B8B8)
  val Monochrome400 = Color(0xFF9E9E9E)
  val Monochrome500 = Color(0xFF858585)
  val Monochrome600 = Color(0xFF6B6B6B)
  val Monochrome700 = Color(0xFF525252)
  val Monochrome800 = Color(0xFF383838)
  val Monochrome900 = Color(0xFF1F1F1F)
  val F5F9FE = Color(0xFFF5F9FE)
  val Black = Color(0xFF000000)
  val Indigo300 = Color(0xFFA370F7)
  val PrimaryColor400 = Color(0xFF4991EE)
  val PrimaryColor10 = Color(0xFFFAFCFF)
  val PrimaryColor100 = Color(0xFFECF3FD)
  val Error = Color(0xFFB3261E)
  val OnError = Color(0xFFFFFFFF)
  val SecondaryColor400 = Color(0xFFFFA73C)
  val Blue300 = Color(0xFF6EA8FE)
  val StatusSuccess = Color(0xFF28A745)
  val Success100 = Color(0xFFF2F8F5)
  val Success200 = Color(0xFFA3CFBB)
  val Success500 = Color(0xFF198754)
  val Success600 = Color(0xFF146C43)
  val Success = Color(0xCCB3F0D5)
  val SuccessText = Color(0xFF28A745)
  val StatusSuccessBg = Color(0xFFB3F0D5)
  val Warning100 = Color(0xFFFFFAEB)
  val Warning200 = Color(0xFFFFE69C)
  val Warning700 = Color(0xFF997404)
  val PrimaryColor600 = Color(0xFF115DC0)
  val Danger100 = Color(0xFFFBE9EB)
  val Danger200 = Color(0xFFF1AEB5)
  val Danger500 = Color(0xFFDC3545)
  val Shade1 = Color(0xFFFCFCFC)
  val Shade2 = Color(0xFFD1D1D1)
  val Yellow = Color(0xFFE89229)
  val Blue = Color(0xFF3B82F6)
  val Muted = Color(0xFF6B6B6B)
  val Shadow = Color(0x13000000)
  val PrimaryBorder = Color(0xFF333333)
  val PrimaryContainer = Color(0xFFEADDFF)
  val BgStatusSuccess2 = Color(0xFFD4F8D3)
  val SysOnPrimaryContainer = Color(0xFF4F378A)
}
```

## Style Text

```kotlin

object StyledText {

  val MobileBaseSemibold = TextStyle(
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )
  val MobileBaseRegular = TextStyle(
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val MobileXsRegular = TextStyle(
    fontSize = 12.sp,
    lineHeight = 16.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val MobileXsMedium = TextStyle(
    fontSize = 12.sp,
    lineHeight = 16.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )

  val MobileSmallMedium = TextStyle(
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )

  val MobileLargeMedium = TextStyle(
    fontSize = 20.sp,
    lineHeight = 28.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )
  val MobileLargeSemibold = TextStyle(
    fontSize = 20.sp,
    lineHeight = 28.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )
  val MobileLargeBold = TextStyle(
    fontSize = 20.sp,
    lineHeight = 28.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Bold,
  )

  val MobileSmallRegular = TextStyle(
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val Mobile3xsMedium = TextStyle(
    fontSize = 10.sp,
    lineHeight = 14.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )

  val Mobile2xlBold = TextStyle(
    fontSize = 28.sp,
    lineHeight = 33.6.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Bold,
  )

  val MobileXsBold = TextStyle(
    fontSize = 12.sp,
    lineHeight = 16.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Bold,
  )

  val MobileSmallSemibold = TextStyle(
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )

  val Mobile3xsRegular = TextStyle(
    fontSize = 10.sp,
    lineHeight = 14.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val MobileMediumMedium = TextStyle(
    fontSize = 18.sp,
    lineHeight = 25.2.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )
  val MobileMediumSemibold = TextStyle(
    fontSize = 18.sp,
    lineHeight = 25.2.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )
  val MobileMediumRegular = TextStyle(
    fontSize = 18.sp,
    lineHeight = 25.2.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val Mobile2xsRegular = TextStyle(
    fontSize = 11.sp,
    lineHeight = 15.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Normal,
  )

  val Mobile2xsSemibold = TextStyle(
    fontSize = 11.sp,
    lineHeight = 15.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.SemiBold,
  )

  val MobileBaseMedium = TextStyle(
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )

  val MobileXlMedium = TextStyle(
    fontSize = 24.sp,
    lineHeight = 28.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Medium,
  )
  val MobileXlBold = TextStyle(
    fontSize = 24.sp,
    lineHeight = 28.8.sp,
    fontFamily = Poppins,
    fontWeight = FontWeight.Bold,
  )
}

val Poppins = FontFamily(
  Font(R.font.poppins_regular, FontWeight.W400),
  Font(R.font.poppins_medium, FontWeight.W500),
  Font(R.font.poppins_semibold, FontWeight.W600),
  Font(R.font.poppins_bold, FontWeight.W700)
)

```

## Local Database

### User Entity

```kotlin

@Entity(tableName = "users")
data class User(
  @PrimaryKey(autoGenerate = true)
  val id : Long = 0,
  val namaLembaga : String?,

  val emailLembaga : String?,
  val alamatLembaga : String?,
  val provinsi : String?,
  val kota : String?,
  val tanggalBerdiri : String?,
  val jenisLembagaSosial : String?,
  val jenisClusterLembagaSosial : String?,
  val fokusIsu : String?,
  val profilSingkatLembaga : String?,
  val alasanMengikutiLead : String?,
  val dokumenProfilPerusahaan : String?,
  val jangkauanProgram : String?,
  val wilayahJangkauanProgram : String?,
  val jumlahAngkaPenerimaanManfaat : List<JangkauanPenerimaManfaat>,
  val targetUtamaProgram : String?,
  val proposalProgramMitra : String?,
  val namaPeserta : String?,
  val posisi : String?,
  val pendidikanTerakhir : String?,
  val jenisKelamin : String?,
  val nomorWhatsapp : String?,
  val emailPeserta : String?,
  val password : String?,
  val ktp : String?,
  val cv : String?,
  val adaPengurusLainYangAkanDiikutSertakanSebagaiPeserta : Boolean,
  val alasanMengikutiAgenda : String?,
  val pernahMengikutiPelatihan : Boolean,
  val darimanaMengetahuiLead : String?,
  val yangDiketahuiTerkaitDesainProgram : String?,
  val yangDiketahuiTerkaitKeberlanjutan : String?,
  val yangDiketahuiTerkaitLaporanSosial : String?,
  val laporanAkhirTahun : String?,
  val ekspetasiMengikutiLead : String?,
  val halYangInginDitanyakanKeLead : String?,
  val umpanBalik : String?,
  val pengalamanMendaftarLead : String?,
  val role : String
)

enum class Role {
  PESERTA,
  MENTOR,
  SUPERADMIN
}

@Serializable
data class JangkauanPenerimaManfaat(
  val kota : String,
  val jumlah : Int
)
```

## Data Class

### Status Registrasi

#### Check Status Registrasi

```kotlin
data class CheckStatusRegistrasiPeserta(
      val namaInstansi : String,
      val provinsi : String,
      val namaPeserta : String,
    )
```

### Evaluasi Capaian Mentoring

#### Evaluasi Mentoring

```kotlin
data class EvaluasiMentoring(
    val no: Int,
    val aspek_penilaian: String,
    val penilaian: String
)
```

#### Evaluasi Lembaga

```kotlin
data class EvaluasiLembaga(
    val no: Int,
    val aspek_penilaian: String,
    val penilaian: String
)
```

#### Kepuasan Mentoring

```kotlin

data class KepuasanMentoring(
    val no: Int,
    val aspek_penilaian: String,
    val penilaian: String
)

```

#### Jawaban Pertanyaan

```kotlin
data class JawabanPertanyaan(
    val aspek: String,
    val jawaban: String
)
```

#### Dokumentasi Mentoring

```kotlin
data class DokumentasiMentoring(
    val namaFile: String,
    val uri: String
)
```

#### Form Monthly Report

#### Progress Kegiatan

```kotlin
data class ProgressKegiatan(
  val kegiatan : String,
  val wilayah : String,
)
```

#### Desain Program

```kotlin
data class DesainProgram(
  val aspekCapaian : String,
  val keterangan : String,
)
```

#### Pendanaan

```kotlin
data class Pendanaan(
  val tipeDonasi : String,
  val nominal : String,
  val platform : String,
)
```

#### Relawan

```kotlin
data class Relawan(
  val jumlah : String,
  val kendalaDanPenyesalan : String,
)
```

#### Status Media Massa

```kotlin

sealed class StatusMediaMassa {
  object Disetujui : StatusMediaMassa() {

    override fun toString() = "Disetujui"
  }

  object PengajuanProposal : StatusMediaMassa() {

    override fun toString() = "Pengajuan Proposal"
  }

  object TahapPersetujuan : StatusMediaMassa() {

    override fun toString() = "Tahap Persetujuan"
  }

  object Ditolak : StatusMediaMassa() {

    override fun toString() = "Ditolak"
  }
}
```

#### Media Massa

```kotlin
data class MediaMassa(
val lembaga : String,
val status : StatusMediaMassa,
val keterangan : String,
)

```

#### Jumlah Angka Penerimaan Manfaat

```kotlin
data class JumlahAngkaPenerimaanManfaat(
  val wilayahPenerimaManfaat: String,
  val jumlah: String,
)
```

#### Wilayah Penerimaan Manfaat

```kotlin
data class WilayahPenerimaManfaat (
  val wilayah : String,
  val jumlah: String,
)
```

#### Donasi

```kotlin
data class Donasi(
  val jumlah: String,
  val platform: String,
)

```

#### Proses Open Recruitment Relawan

```kotlin
data class ProsesOpenRecruitmentRelawan(
  val jumlah: String,
  val prosesYangDilakukan: String
)

```

#### Donasi Mitra

```kotlin
data class DonasiMitra(
  val jumlah: String,
  val keterangan: String,
)
```

#### Kerja Sama

```kotlin
data class KerjaSama(
  val jenisLembaga:String,
  val status: String,
  val namaLembaga: String,
  val keterangan: String,
)
```

#### Pemasaran Sosial Media

```kotlin

data class  PemasaranSosialMedia(
  val mediaSosial: String,
  val waktu: String,
  val konten: String,
  val tautan: String,
)

```

### Jadwal

#### Jadwal Mentoring

```kotlin
data class JadwalMentoring(
  val no : Int,
  val tanggal: LocalDate,
  val waktuMulai: LocalTime,
  val waktuSelesai: LocalTime
)

```

#### Detail Jadwal

```kotlin
data class DetailJadwal(
    val id: String,
    val title: String,
    val type: String,
    val date: LocalDate,
    val beginTime: LocalTime,
    val endTime: LocalTime,
    val speaker: String,
    val description: String,
    val link: String,
    val color: Int,
)
```

### Kelompok Mentoring

#### Kelompok Mentoring

```kotlin
data class KelompokMentoring(
  val namaLembaga : String,
  val fokusIsu : String,
  val jumlahPeserta : Int,
  val jumlahMentor : Int,
  val jumlahSesi : Int,
)

```

### Ubah Profil Mentor

#### Mentor

```kotlin
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
```

#### Batch

```kotlin
data class Batch(
    val namaBatch: String,
    val kategoriMentor: String,
    val cluster: String,
    val fokusIsu: String,
    val listLembaga: List<Lembaga>
)
```

#### Lembaga

```kotlin
data class Lembaga(
    val namaLembaga: String,
    val fokusIsu: String,
    val provinsi: String
)
```

#### Fokus Isu

```kotlin
data class FokusIsu(
    val fokusIsu: String,
    val cluster: String,
    val batch: String
)

```

### Profil Peserta

#### Profil Peserta

```kotlin
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

```

#### Profil Lembaga

```kotlin
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
    val wilayahJangkauan: List<WilayahJangkauan>,
    val keteranganLolos: String
)
```

#### Wilayah Jangkauan

```kotlin

data class WilayahJangkauan(
    val no: Int,
    val provinsi: String,
    val penerimaManfaat: Int,
    val rincianUrl: String
)
```

#### Pertanyaan Umum

```kotlin
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
```

#### Mentoring Peserta

```kotlin
data class MentoringPeserta(
    val mentorName: String,
    val mentoringType: String,
    val batch: Int,
    val capaianProgram: String
)
```

#### Profil Mentor

```kotlin
data class ProfilMentor(
    val namaLengkap: String,
    val type: String,
    val cluster: String,
    val focusIssue: String
)
```

### Participant

```kotlin
data class Participant(
  val namaLembaga : String,
  val batch : Int,
  val provinsi : String,
  val status : Status,
)
```

#### Status Participant

```kotlin
enum class Status {
  ACTIVE,
  INACTIVE,
  PENDING,
}


```

### Pengumuman

#### Pengumuman

```kotlin

data class Pengumuman(
  val title : String,
  val date : Date,
  val content : String,
  val category : String
)

```

### Penilaian

#### Penilaian

```kotlin
data class Penilaian(
  val namaLembaga : String,
  val batch : Int,
  val totalPenilaian : Int,
)

```

#### Penilaian Umum

```kotlin
data class PenilaianUmum(
  val aspekPenilaian : String,
  val penilaian : Int
)

```

#### Penilaian Peserta

```kotlin
data class PenilaianPeserta(
  val title : String,
  val description : String,
)
```

#### Penilaian Form Data

```kotlin
data class PenilaianFormData(
  val kegiatan: String,
  val umpanBalik: String
)
```

### Pitch Deck

#### Pitch Deck

```kotlin
data class PitchDeck(
    val title: String,
    val batch: Int,
    val description: String,
    val link: String,
    val submissionDeadline: String
)
```

### Pusat Informasi

#### Pusat Informasi

```kotlin
data class DataPusatInformasi(
  val username : String?,
  val profilePicture : Int?,
  val timestamp : Long,
  val question : String,
)

```

### Worksheet

#### Worksheet Peserta

```kotlin
data class WorksheetPeserta(
  val title : String,
  val subTitle: String,
  val description : String,
  val link: String,
  val submissionDeadline: String
)
```

#### Lembar Kerja Peserta

```kotlin
data class LembarKerjaPeserta(
  val namaPeserta : String,
  val waktuSubmisi : String,
)
```

## Dependency Injection

### App Module

```kotlin
@Module
@InstallIn(SingletonComponent::class)
class AppModule {


  @Provides
  @Singleton
  fun provideUserPreferences(@ApplicationContext context : Context) : UserPreferences {
    return UserPreferences(context)
  }
  @Provides
  @Singleton
  fun provideRemoteUserPreferences (@ApplicationContext context : Context) : UserRemotePreferences {
    return UserRemotePreferences(context)
  }


}
```

### Coroutine Module

```kotlin

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

  @Provides
  @MainDispatcher
  fun provideMainDispatcher() : CoroutineDispatcher = Dispatchers.Main

  @Provides
  @IODispatcher
  fun provideIODispatcher() : CoroutineDispatcher = Dispatchers.IO

  @Provides
  @DefaultDispatcher
  fun provideDefaultDispatcher() : CoroutineDispatcher = Dispatchers.Default
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

```

### Local Database Module

```kotlin

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Provides
  @Singleton
  fun provideUserDao(database : Database) : UserDao = database.userDao

  @Provides
  @Singleton
  fun provideDatabase(
    @ApplicationContext context : Context
  ) : Database = Room.databaseBuilder(
    context,
    Database::class.java,
    Database.Constants.DATABASE_NAME
  ).build()
}
```

## Constant Strings

```kotlin
class ConstantFeedbackPeserta {
  companion object {

    val lembagas = listOf(
      "BCF",
      "Gajah Tunggal",
      "Kompas",
      "Kompas Gramedia",
      "Kompas TV",
      "Kompas Gramedia Digital",
    )
    val evaluasiCapaianMentorings = listOf(
      "Tim menyampaikan laporan perkembangan ide secara jelas dan komprehensif.",
      "Sesi mentoring berlangsung sesuai dengan kebutuhan pembelajaran peserta.",
      "Tim memahami instruksi dan pertanyaan dari mentor dengan jelas.",
      "Masing-masing anggota tim memiliki inisiatif untuk berkontribusi memberikan ide dan pendapat.",
      "Tim memanfaatkan sesi mentoring untuk bertanya dan mengklarifikasi pemahaman.",
      "Tim menggunakan tools/alat bantu yang mendukung proses mentoring berjalan dengan efektif.",
      "Tim menerima dan memahami feedback yang diberikan oleh mentor."
    )
  }
}
class ConstantAddJadwalMentor {
    companion object {
        val tipeKegiatans = listOf(
            "Cluster",
            "Desain Program",
        )
        val namaPemateris = listOf(
            "Lisa Blekpink",
            "Bruno Maret",
            "Jukowaw"
        )
        val namaLembagas = listOf(
            "Bakrie CenterFoundation",
            "The Next Gen",
            "Indonesia Jaya")
    }
}
class LaporanPenilaianPesertaConstant {
  companion object {

    val headerLembagaPenilaianPeserta = listOf(
      "No.",
      "Nama Lembaga",
      "Fokus Isu",
      "Provinsi"
    )

    val headerFokusIsuPenilaianPeserta = listOf(
      "No.",
      "Fokus Isu",
      "Cluster",
      "Batch"
    )
    val tabTitles = listOf("Lembaga", "Fokus Isu")

  }
}
class ConstantFormFeedbackMentor {
    companion object {

        val evaluasiCapaians = listOf(
            "Cluster",
            "Desain Program"
        )
        val periodeCapaians = listOf(
            "Capaian Mentoring 1",
            "Capaian Mentoring 2",
            "Capaian Mentoring 3",
        )
        val namaMentors = listOf(
            "Lisa Blekpink",
            "Bruno Maret",
            "Jukowaw"
        )
    }
}

class ConstantFormMiniTraining {
    companion object {

        val hariKegiatans = listOf(
            "Mini Training hari ke-1",
            "Mini Training hari ke-2",
            "Mini Training hari ke-3",
            "Mini Training hari ke-4",
        )
        val miniTrainingQuestion = listOf(
            "Apakah materi yang disampaikan pembicara mudah dipahami?",
            "Apakah pembicara mampu mengatur waktu dengan baik?",
            "Apakah pembicara memberikan jawaban yang memuaskan atas pertanyaan peserta?",
            "Apakah pembicara menggunakan metode yang interaktif?",
        )
    }
}
```

