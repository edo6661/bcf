package com.example.slicingbcf.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.dao.model.JangkauanPenerimaManfaat
import com.example.slicingbcf.data.dao.model.Role
import com.example.slicingbcf.data.dao.model.User
import com.example.slicingbcf.data.local.preferences.UserPreferences
import com.example.slicingbcf.data.repo.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
  private val userRepository : UserRepository,
  private val userPreferences : UserPreferences
) : ViewModel() {

  private val _currentUser = MutableStateFlow<User?>(null)
  val currentUser : StateFlow<User?> = _currentUser

  init {
    viewModelScope.launch {
      userPreferences.getUserData().collect { user ->
        _currentUser.value = user
      }

    }
  }


  private fun insertUser(user : User) {
    viewModelScope.launch {
      try {
        userRepository.insertUser(user)
      } catch (e : Exception) {
        e.printStackTrace()
        Log.e("UserViewModel", "insertUser: ${e.message}")
      }
    }
  }

  suspend fun clearUserSession() {
    userPreferences.clearUserSession()
  }

  fun insertDummyData() {
    viewModelScope.launch {
      try {

        val dataList = listOf(
          JangkauanPenerimaManfaat("Jawa Barat", 100),
          JangkauanPenerimaManfaat("Jawa Timur", 200)
        )
        val dummyUser = User(
          namaLembaga = "Lembaga Contoh",
          emailLembaga = "example@lembaga.com",
          alamatLembaga = "Jl. Contoh No. 123",
          provinsi = "Jawa Barat",
          kota = "Bandung",
          tanggalBerdiri = "2024-01-01",
          jenisLembagaSosial = "Non-Profit",
          jenisClusterLembagaSosial = "Pendidikan",
          fokusIsu = "Kesejahteraan Anak",
          profilSingkatLembaga = "Lembaga yang fokus pada pendidikan anak",
          alasanMengikutiLead = "Pengembangan Lembaga",
          dokumenProfilPerusahaan = "Profil.pdf",
          jangkauanProgram = "Nasional",
          wilayahJangkauanProgram = "Perkotaan",
          jumlahAngkaPenerimaanManfaat = dataList,
          targetUtamaProgram = "Anak-anak",
          proposalProgramMitra = "Proposal.pdf",
          namaPeserta = "mentor",
          posisi = "Manajer",
          pendidikanTerakhir = "S1",
          jenisKelamin = "Laki-laki",
          nomorWhatsapp = "081234567890",
          emailPeserta = "mentor@gmail.com",
          password = "mentor123",
          ktp = "1234567890123456",
          cv = "CV_JohnDoe.pdf",
          adaPengurusLainYangAkanDiikutSertakanSebagaiPeserta = false,
          alasanMengikutiAgenda = "Belajar dan Networking",
          pernahMengikutiPelatihan = true,
          darimanaMengetahuiLead = "Media Sosial",
          yangDiketahuiTerkaitDesainProgram = "Peningkatan Dampak",
          yangDiketahuiTerkaitKeberlanjutan = "Keberlanjutan Program",
          yangDiketahuiTerkaitLaporanSosial = "Pelaporan",
          laporanAkhirTahun = "Laporan2024.pdf",
          ekspetasiMengikutiLead = "Jaringan dan Ilmu Baru",
          halYangInginDitanyakanKeLead = "Peluang Kolaborasi",
          umpanBalik = "Program bermanfaat",
          pengalamanMendaftarLead = "Pernah",
          role = Role.MENTOR.name
        )
        val dummyUser2 = User(
          namaLembaga = "Lembaga Contoh",
          emailLembaga = "example@lembaga.com",
          alamatLembaga = "Jl. Contoh No. 123",
          provinsi = "Jawa Barat",
          kota = "Bandung",
          tanggalBerdiri = "2024-01-01",
          jenisLembagaSosial = "Non-Profit",
          jenisClusterLembagaSosial = "Pendidikan",
          fokusIsu = "Kesejahteraan Anak",
          profilSingkatLembaga = "Lembaga yang fokus pada pendidikan anak",
          alasanMengikutiLead = "Pengembangan Lembaga",
          dokumenProfilPerusahaan = "Profil.pdf",
          jangkauanProgram = "Nasional",
          wilayahJangkauanProgram = "Perkotaan",
          jumlahAngkaPenerimaanManfaat = dataList,
          targetUtamaProgram = "Anak-anak",
          proposalProgramMitra = "Proposal.pdf",
          namaPeserta = "peserta",
          posisi = "Manajer",
          pendidikanTerakhir = "S1",
          jenisKelamin = "Laki-laki",
          nomorWhatsapp = "081234567890",
          emailPeserta = "peserta@gmail.com",
          password = "peserta123",
          ktp = "1234567890123456",
          cv = "CV_JohnDoe.pdf",
          adaPengurusLainYangAkanDiikutSertakanSebagaiPeserta = false,
          alasanMengikutiAgenda = "Belajar dan Networking",
          pernahMengikutiPelatihan = true,
          darimanaMengetahuiLead = "Media Sosial",
          yangDiketahuiTerkaitDesainProgram = "Peningkatan Dampak",
          yangDiketahuiTerkaitKeberlanjutan = "Keberlanjutan Program",
          yangDiketahuiTerkaitLaporanSosial = "Pelaporan",
          laporanAkhirTahun = "Laporan2024.pdf",
          ekspetasiMengikutiLead = "Jaringan dan Ilmu Baru",
          halYangInginDitanyakanKeLead = "Peluang Kolaborasi",
          umpanBalik = "Program bermanfaat",
          pengalamanMendaftarLead = "Pernah",
          role = Role.PESERTA .name
        )
        insertUser(dummyUser)
        insertUser(dummyUser2)
        Log.d("UserViewModel", "Dummy user inserted")
      } catch (e : Exception) {
        Log.e("UserViewModel", "Error inserting dummy data: ${e.message}")
      }
    }
  }


}
