package com.example.slicingbcf.implementation.auth.registrasi

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.dao.model.JangkauanPenerimaManfaat
import com.example.slicingbcf.data.dao.model.Role
import com.example.slicingbcf.data.dao.model.User
import com.example.slicingbcf.data.repo.user.UserRepository
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import com.example.slicingbcf.domain.validator.ValidationResult
import com.example.slicingbcf.domain.validator.isBlankOrEmpty
import com.example.slicingbcf.domain.validator.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrasiViewModel @Inject constructor(
  private val userRepository : UserRepository,
  @IODispatcher private val ioDispatcher : CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher : CoroutineDispatcher
) : ViewModel() {

  private val _uiState = MutableStateFlow(RegistrasiState())
  val uiState = _uiState.asStateFlow()


  private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    _uiState.update {
      it.copy(isLoading = false, error = exception.message ?: "Terjadi kesalahan")
    }
  }


  private fun onSubmit() {

    if (! validate()) {

      _uiState.update {
        it.copy(
          error = "Mohon isi semua field yang wajib diisi dan dengan format yang benar",
        )
      }
      return
    }
    _uiState.update { it.copy(isLoading = true) }

    viewModelScope.launch(ioDispatcher + exceptionHandler) {
      try {
        val user = User(
          namaLembaga = _uiState.value.namaLembaga,
          emailLembaga = _uiState.value.emailLembaga,
          alamatLembaga = _uiState.value.alamatLembaga,
          provinsi = _uiState.value.provinsi,
          kota = _uiState.value.kota,
          tanggalBerdiri = _uiState.value.selectedDate,
          jenisLembagaSosial = _uiState.value.jenisLembagaSosial,
          jenisClusterLembagaSosial = _uiState.value.jenisLembagaSosial,
          fokusIsu = _uiState.value.fokusIsu,
          profilSingkatLembaga = _uiState.value.profilLembaga,
          alasanMengikutiLead = _uiState.value.alasanKeikutsertaan,
          dokumenProfilPerusahaan = _uiState.value.selectedFileUriLaporanAkhirTahun.toString(),
          jangkauanProgram = _uiState.value.jangkauanProgram,
          wilayahJangkauanProgram = _uiState.value.wilayahJangkauanProgram,
          jumlahAngkaPenerimaanManfaat = _uiState.value.jumlahAngkaPenerimaanManfaat,
          targetUtamaProgram = _uiState.value.targetUtamaProgram,
          proposalProgramMitra = _uiState.value.selectedFileUriProposalProgram.toString(),
          namaPeserta = _uiState.value.namaLengkapPeserta,
          posisi = _uiState.value.posisiPeserta,
          pendidikanTerakhir = _uiState.value.pendidikanTerakhir,
          jenisKelamin = _uiState.value.jenisKelamin,
          nomorWhatsapp = _uiState.value.nomorWhatsappPeserta,
          emailPeserta = _uiState.value.emailPeserta,
          password = _uiState.value.namaLengkapPeserta + "123",
          ktp = _uiState.value.selectedFileUriKTP.toString(),
          cv = _uiState.value.selectedFileUriCV.toString(),
          adaPengurusLainYangAkanDiikutSertakanSebagaiPeserta = _uiState.value.adaPengurusLain,
          alasanMengikutiAgenda = _uiState.value.alasanTidakMengikutiAgenda,
          pernahMengikutiPelatihan = _uiState.value.pernahMengikutiPelatihanDesainProgram == "Sudah",
          darimanaMengetahuiLead = _uiState.value.sumberInformasiLEAD.toString(),
          yangDiketahuiTerkaitDesainProgram = _uiState.value.pengetahuanDesainProgram,
          yangDiketahuiTerkaitKeberlanjutan = _uiState.value.pengetahuanSustainability,
          yangDiketahuiTerkaitLaporanSosial = _uiState.value.pengetahuanSocialReport,
          laporanAkhirTahun = _uiState.value.selectedFileUriLaporanAkhirTahun.toString(),
          ekspetasiMengikutiLead = _uiState.value.ekspetasiSetelahLEAD,
          halYangInginDitanyakanKeLead = _uiState.value.halLainYangInginDisampaikan,
          umpanBalik = _uiState.value.halLainYangInginDisampaikan,
          pengalamanMendaftarLead = _uiState.value.halLainYangInginDisampaikan,
          role = Role.PESERTA.name
        )
        val isEmailExist = isEmailAlreadyExist(user.emailPeserta !!)
        if (isEmailExist) {
          withContext(mainDispatcher) {
            _uiState.update {
              it.copy(isLoading = false, error = "Email sudah terdaftar")
            }
          }
          return@launch

        }

        userRepository.insertUser(user)
        delay(3000)

        withContext(mainDispatcher) {
          _uiState.update {
            it.copy(isSuccess = true, isLoading = false, message = "Registrasi berhasil")
          }
        }


      } catch (e : Exception) {
        withContext(mainDispatcher) {
          _uiState.update {
            it.copy(isLoading = false, error = e.message ?: "Terjadi kesalahan saat registrasi")
          }
        }

      }
    }
  }

  private suspend fun isEmailAlreadyExist(email : String) : Boolean {
    return userRepository.isEmailExist(email).first()
  }


  fun onEvent(event : RegisterEvent) {
    when (event) {
      is RegisterEvent.NamaLembagaChanged                                    -> onChangeNamaLembaga(
        event.namaLembaga
      )

      is RegisterEvent.ClearError                                            -> {
        _uiState.update { it.copy(error = null) }
      }


      is RegisterEvent.ReadAndUnderstandCheckedChanged                       -> onReadAndUnderstandCheckedChanged(
        event.isChecked
      )

      is RegisterEvent.ConfirmInformationCheckedChanged                      -> onConfirmInformationCheckedChanged(
        event.isChecked
      )

      is RegisterEvent.MiniTrainingCheckedChanged                            -> onMiniTrainingCheckedChanged(
        event.isChecked
      )

      is RegisterEvent.InitialMentoringCheckedChanged                        -> onInitialMentoringCheckedChanged(
        event.isChecked
      )

      is RegisterEvent.PendampinganIntensifCheckedChanged                    -> onPendampinganIntensifCheckedChanged(
        event.isChecked
      )

      is RegisterEvent.SelectedDateChanged                                   -> onChangeSelectedDate(
        event.selectedDate
      )

      is RegisterEvent.EmailLembagaChanged                                   -> onChangeEmailLembaga(
        event.emailLembaga
      )

      is RegisterEvent.AlamatLembagaChanged                                  -> onChangeAlamatLembaga(
        event.alamatLembaga
      )

      is RegisterEvent.ProvinsiChanged                                       -> onChangeProvinsi(
        event.provinsi
      )

      is RegisterEvent.KotaChanged                                           -> onChangeKota(event.kota)
      is RegisterEvent.JenisLembagaSosialChanged                             -> onChangeJenisLembagaSosial(
        event.jenisLembagaSosial
      )

      is RegisterEvent.JenisClusterLembagaSosialChanged                      -> onChangeClusterJenisLembagaSosial(
        event.jenisLembagaSosial
      )

      is RegisterEvent.FokusIsuChanged                                       -> onChangeFokusIsu(
        event.fokusIsu
      )

      is RegisterEvent.ProfilLembagaChanged                                  -> onChangeProfilLembaga(
        event.profilLembaga
      )

      is RegisterEvent.AlasanKeikutsertaanChanged                            -> onChangeAlasanKeikutsertaan(
        event.alasanKeikutsertaan
      )

      is RegisterEvent.SelectedFileUriDokumentasiSesiMentoringClusterChanged -> onChangeSelectedFileUriDokumentasiSesiMentoringCluster(
        event.uri
      )

      is RegisterEvent.JangkauanProgramChanged                               -> onChangeJangkauanProgram(
        event.jangkauanProgram
      )

      is RegisterEvent.WilayahJangkauanProgramChanged                        -> onChangeWilayahJangkauanProgram(
        event.wilayahJangkauanProgram
      )


      is RegisterEvent.UpdateJumlahAngkaPenerimaanManfaat                    -> {
        onChangeJumlahAngkaPenerimaanManfaat(event.index, event.field)
      }

      is RegisterEvent.RemoveLastJumlahAngkaPenerimaanManfaat                -> {
        onRemoveLastJumlahAngkaPenerimaanManfaat()
      }

      is RegisterEvent.AddJumlahAngkaPenerimaanManfaat                       -> {
        onAddJumlahAngkaPenerimaanManfaat(event.newField)
      }


      is RegisterEvent.TargetUtamaProgramChanged                             -> onChangeTargetUtamaProgram(
        event.targetUtamaProgram
      )

      is RegisterEvent.SelectedFileUriProposalProgramChanged                 -> onChangeSelectedFileUriProposalProgram(
        event.uri
      )

      is RegisterEvent.NamaLengkapPesertaChanged                             -> onChangeNamaLengkapPeserta(
        event.namaLengkap
      )

      is RegisterEvent.PosisiPesertaChanged                                  -> onChangePosisiPeserta(
        event.posisiPeserta
      )

      is RegisterEvent.PendidikanTerakhirChanged                             -> onChangePendidikanTerakhir(
        event.pendidikanTerakhir
      )

      is RegisterEvent.JurusanPendidikanTerakhirChanged                      -> onChangeJurusanPendidikanTerakhir(
        event.jurusanPendidikan
      )

      is RegisterEvent.JenisKelaminChanged                                   -> onChangeJenisKelamin(
        event.jenisKelamin
      )

      is RegisterEvent.NomorWhatsappPesertaChanged                           -> onChangeNomorWhatsappPeserta(
        event.nomorWhatsapp
      )

      is RegisterEvent.EmailPesertaChanged                                   -> onChangeEmailPeserta(
        event.email
      )

      is RegisterEvent.SelectedFileUriKTPChanged                             -> onChangeSelectedFileUriKTP(
        event.uri
      )

      is RegisterEvent.SelectedFileUriCVChanged                              -> onChangeSelectedFileUriCV(
        event.uri
      )

      is RegisterEvent.AdaPengurusLainChanged                                -> onChangeAdaPengurusLain(
        event.adaPengurusLain
      )

      is RegisterEvent.AlasanTidakMengikutiAgendaChanged                     -> onChangeAlasanTidakMengikutiAgenda(
        event.alasan
      )

      is RegisterEvent.BersediaMengikutiAgendaChanged                        -> onChangeBersediaMengikutiAgenda(
        event.bersedia
      )

      is RegisterEvent.PernahMengikutiPelatihanDesainProgramChanged          -> onChangePernahMengikutiPelatihanDesainProgram(
        event.pelatihan
      )

      is RegisterEvent.SumberInformasiLEADChanged                            -> onChangeSumberInformasiLEAD(
        event.sumber,
        event.isChecked
      )

      is RegisterEvent.PengetahuanLeadChanged                                -> onChangePengetahuanLead(
        event.pengetahuan
      )

      is RegisterEvent.PengetahuanDesainProgramChanged                       -> onChangePengetahuanDesainProgram(
        event.pengetahuan
      )

      is RegisterEvent.PengetahuanSustainabilityChanged                      -> onChangePengetahuanSustainability(
        event.pengetahuan
      )

      is RegisterEvent.PengetahuanSocialReportChanged                        -> onChangePengetahuanSocialReport(
        event.pengetahuan
      )

      is RegisterEvent.SelectedFileUriLaporanAkhirTahunChanged               -> onChangeSelectedFileUriLaporanAkhirTahun(
        event.uri
      )

      is RegisterEvent.EkspetasiSetelahLEADChanged                           -> onChangeEkspetasiSetelahLEAD(
        event.ekspetasi
      )

      is RegisterEvent.HalLainYangInginDisampaikanChanged                    -> onChangeHalLainYangInginDisampaikan(
        event.halLain
      )

      is RegisterEvent.Submit                                                -> onSubmit()
      is RegisterEvent.ClearState                                            -> onClear()
    }
  }


  private fun onClear() {
    _uiState.value = RegistrasiState()
  }

  @Suppress("t")
  private fun validate() : Boolean {
    val state = _uiState.value

    val validationsString = listOf(
      state.namaLembaga to "Nama lembaga tidak boleh kosong",
      state.emailLembaga to "Email lembaga tidak boleh kosong",
      state.alamatLembaga to "Alamat lembaga tidak boleh kosong",
      state.provinsi to "Provinsi tidak boleh kosong",
      state.kota to "Kota tidak boleh kosong",
      state.jenisLembagaSosial to "Jenis lembaga sosial tidak boleh kosong",
      state.fokusIsu to "Fokus isu tidak boleh kosong",
      state.profilLembaga to "Profil lembaga tidak boleh kosong",
      state.alasanKeikutsertaan to "Alasan keikutsertaan tidak boleh kosong",
      state.jangkauanProgram to "Jangkauan program tidak boleh kosong",
      state.wilayahJangkauanProgram to "Wilayah jangkauan program tidak boleh kosong",
      state.targetUtamaProgram to "Target utama program tidak boleh kosong",
      state.namaLengkapPeserta to "Nama lengkap peserta tidak boleh kosong",
      state.posisiPeserta to "Posisi peserta tidak boleh kosong",
      state.pendidikanTerakhir to "Pendidikan terakhir tidak boleh kosong",
      state.jurusanPendidikanTerakhir to "Jurusan pendidikan terakhir tidak boleh kosong",
      state.jenisKelamin to "Jenis kelamin tidak boleh kosong",
      state.nomorWhatsappPeserta to "Nomor WhatsApp peserta tidak boleh kosong",
      state.emailPeserta to "Email peserta tidak boleh kosong",
      state.alasanTidakMengikutiAgenda to "Alasan tidak mengikuti agenda tidak boleh kosong",
      state.ekspetasiSetelahLEAD to "Ekspetasi setelah LEAD tidak boleh kosong",
      state.selectedDate to "Tanggal berdiri tidak boleh kosong",
      state.pernahMengikutiPelatihanDesainProgram to "Pernah mengikuti pelatihan desain program tidak boleh kosong",
      state.sumberInformasiLEADAsString to "Sumber informasi LEAD tidak boleh kosong",
      state.pengetahuanSustainability to "Pengetahuan sustainability tidak boleh kosong",
      state.pengetahuanSocialReport to "Pengetahuan social report tidak boleh kosong",
      state.ekspetasiSetelahLEAD to "Ekspetasi setelah LEAD tidak boleh kosong",
      state.halLainYangInginDisampaikan to "Hal lain yang ingin disampaikan tidak boleh kosong",
      state.jenisClusterLembagaSosial to "Jenis cluster lembaga sosial tidak boleh kosong",
      state.pengetahuanDesainProgram to "Pengetahuan desain program tidak boleh kosong",


      )
    val validationsBoolean = listOf(
      state.readAndUnderstandChecked to "Mohon centang bahwa anda telah membaca dan memahami informasi",
      state.confirmInformationChecked to "Mohon centang bahwa informasi yang anda berikan adalah benar",
      state.miniTrainingCheckedError to "Mohon centang bahwa anda telah membaca dan memahami informasi",
      state.initialMentoringCheckedError to "Mohon centang bahwa anda telah membaca dan memahami informasi",
      state.pendampinganIntensifCheckedError to "Mohon centang bahwa anda telah membaca dan memahami informasi",

      )
    val validationsUri = listOf(
      state.selectedFileUriDokumentasiSesiMentoringCluster to "Dokumentasi sesi mentoring cluster tidak boleh kosong",
      state.selectedFileUriProposalProgram to "Proposal program mitra tidak boleh kosong",
      state.selectedFileUriKTP to "KTP tidak boleh kosong",
      state.selectedFileUriCV to "CV tidak boleh kosong",
      state.selectedFileUriLaporanAkhirTahun to "Laporan akhir tahun tidak boleh kosong",
    )

    val errorMapString = validationsString.mapNotNull { (field, errorMessage) ->
      if (field.isBlankOrEmpty()) errorMessage else null
    }
    val errorMapBoolean = validationsBoolean.mapNotNull { (field, errorMessage) ->
      if (field == false) errorMessage else null
    }

    val errorMapUri = validationsUri.mapNotNull { (field, errorMessage) ->
      if (field == null) errorMessage else null
    }

    val allErrorsMap = errorMapString + errorMapBoolean + errorMapUri


    _uiState.update { it ->
      it.copy(
        namaLembagaError = errorMapString.find { it == "Nama lembaga tidak boleh kosong" },
        emailLembagaError = errorMapString.find { it == "Email lembaga tidak boleh kosong" },
        alamatLembagaError = errorMapString.find { it == "Alamat lembaga tidak boleh kosong" },
        provinsiError = errorMapString.find { it == "Provinsi tidak boleh kosong" },
        kotaError = errorMapString.find { it == "Kota tidak boleh kosong" },
        jenisLembagaSosialError = errorMapString.find { it == "Jenis lembaga sosial tidak boleh kosong" },
        fokusIsuError = errorMapString.find { it == "Fokus isu tidak boleh kosong" },
        profilLembagaError = errorMapString.find { it == "Profil lembaga tidak boleh kosong" },
        alasanKeikutsertaanError = errorMapString.find { it == "Alasan keikutsertaan tidak boleh kosong" },
        jangkauanProgramError = errorMapString.find { it == "Jangkauan program tidak boleh kosong" },
        wilayahJangkauanProgramError = errorMapString.find { it == "Wilayah jangkauan program tidak boleh kosong" },
        targetUtamaProgramError = errorMapString.find { it == "Target utama program tidak boleh kosong" },
        namaLengkapPesertaError = errorMapString.find { it == "Nama lengkap peserta tidak boleh kosong" },
        posisiPesertaError = errorMapString.find { it == "Posisi peserta tidak boleh kosong" },
        pendidikanTerakhirError = errorMapString.find { it == "Pendidikan terakhir tidak boleh kosong" },
        jurusanPendidikanTerakhirError = errorMapString.find { it == "Jurusan pendidikan terakhir tidak boleh kosong" },
        jenisKelaminError = errorMapString.find { it == "Jenis kelamin tidak boleh kosong" },
        nomorWhatsappPesertaError = errorMapString.find { it == "Nomor WhatsApp peserta tidak boleh kosong" },
        emailPesertaError = errorMapString.find { it == "Email peserta tidak boleh kosong" },
        alasanTidakMengikutiAgendaError = errorMapString.find { it == "Alasan tidak mengikuti agenda tidak boleh kosong" },
        ekspetasiSetelahLEADError = errorMapString.find { it == "Ekspetasi setelah LEAD tidak boleh kosong" },
        selectedDateError = errorMapString.find { it == "Tanggal berdiri tidak boleh kosong" },
        pernahMengikutiPelatihanDesainProgramError = errorMapString.find { it == "Pernah mengikuti pelatihan desain program tidak boleh kosong" },
        errorSumberInformasiLEAD = errorMapString.find { it == "Sumber informasi LEAD tidak boleh kosong" },
        pengetahuanSustainabilityError = errorMapString.find { it == "Pengetahuan sustainability tidak boleh kosong" },
        pengetahuanSocialReportError = errorMapString.find { it == "Pengetahuan social report tidak boleh kosong" },
        halLainYangInginDisampaikanError = errorMapString.find { it == "Hal lain yang ingin disampaikan tidak boleh kosong" },
        jenisClusterLembagaSosialError = errorMapString.find { it == "Jenis cluster lembaga sosial tidak boleh kosong" },
        readAndUnderstandError = errorMapBoolean.find { it == "Mohon centang bahwa anda telah membaca dan memahami informasi" },
        confirmInformationError = errorMapBoolean.find { it == "Mohon centang bahwa informasi yang anda berikan adalah benar" },
        miniTrainingCheckedError = errorMapBoolean.find { it == "Mohon centang bahwa anda telah membaca dan memahami informasi" },
        initialMentoringCheckedError = errorMapBoolean.find { it == "Mohon centang bahwa anda telah membaca dan memahami informasi" },
        pendampinganIntensifCheckedError = errorMapBoolean.find { it == "Mohon centang bahwa anda telah membaca dan memahami informasi" },
        pengetahuanDesainProgramError = errorMapString.find { it == "Pengetahuan desain program tidak boleh kosong" },
        selectedFileUriDokumentasiSesiMentoringClusterError = errorMapUri.find { it == "Dokumentasi sesi mentoring cluster tidak boleh kosong" },
        selectedFileUriProposalProgramError = errorMapUri.find { it == "Proposal program mitra tidak boleh kosong" },
        selectedFileUriKTPError = errorMapUri.find { it == "KTP tidak boleh kosong" },
        selectedFileUriCVError = errorMapUri.find { it == "CV tidak boleh kosong" },
        selectedFileUriLaporanAkhirTahunError = errorMapUri.find { it == "Laporan akhir tahun tidak boleh kosong" },

        )
    }

    val emailPesertaValidationResult = state.emailPeserta.validateEmail()
    val emailLembagaValidationResult = state.emailLembaga.validateEmail()
    _uiState.update {
      it.copy(
        emailPesertaError = if (emailPesertaValidationResult is ValidationResult.Invalid) emailPesertaValidationResult.message else null,
        emailLembagaError = if (emailLembagaValidationResult is ValidationResult.Invalid) emailLembagaValidationResult.message else null
      )
    }



    return emailPesertaValidationResult is ValidationResult.Valid && emailLembagaValidationResult is ValidationResult.Valid && allErrorsMap.isEmpty()

  }


  private fun onChangeNamaLembaga(namaLembaga : String) {
    _uiState.update { it.copy(namaLembaga = namaLembaga) }
  }

  private fun onReadAndUnderstandCheckedChanged(isChecked : Boolean) {
    _uiState.update { it.copy(readAndUnderstandChecked = isChecked) }
  }

  private fun onConfirmInformationCheckedChanged(isChecked : Boolean) {
    _uiState.update { it.copy(confirmInformationChecked = isChecked) }
  }

  private fun onMiniTrainingCheckedChanged(isChecked : Boolean) {
    _uiState.update { it.copy(miniTrainingChecked = isChecked) }
  }

  private fun onInitialMentoringCheckedChanged(isChecked : Boolean) {
    _uiState.update { it.copy(initialMentoringChecked = isChecked) }
  }

  private fun onPendampinganIntensifCheckedChanged(isChecked : Boolean) {
    _uiState.update { it.copy(pendampinganIntensifChecked = isChecked) }
  }

  private fun onChangeSelectedDate(selectedDate : String) {
    _uiState.update { it.copy(selectedDate = selectedDate) }
  }

  private fun onChangeEmailLembaga(emailLembaga : String) {
    _uiState.update { it.copy(emailLembaga = emailLembaga) }
  }

  private fun onChangeAlamatLembaga(alamatLembaga : String) {
    _uiState.update { it.copy(alamatLembaga = alamatLembaga) }
  }

  private fun onChangeProvinsi(provinsi : String) {
    _uiState.update { it.copy(provinsi = provinsi) }
  }

  private fun onChangeKota(kota : String) {
    _uiState.update { it.copy(kota = kota) }
  }

  private fun onChangeJenisLembagaSosial(jenisLembagaSosial : String) {
    _uiState.update { it.copy(jenisLembagaSosial = jenisLembagaSosial) }
  }

  private fun onChangeClusterJenisLembagaSosial(jenisClusterLembagaSosial : String) {
    _uiState.update { it.copy(jenisClusterLembagaSosial = jenisClusterLembagaSosial) }
  }

  private fun onChangeFokusIsu(fokusIsu : String) {
    _uiState.update { it.copy(fokusIsu = fokusIsu) }
  }

  private fun onChangeProfilLembaga(profilLembaga : String) {
    _uiState.update { it.copy(profilLembaga = profilLembaga) }
  }

  private fun onChangeAlasanKeikutsertaan(alasanKeikutsertaan : String) {
    _uiState.update { it.copy(alasanKeikutsertaan = alasanKeikutsertaan) }
  }

  private fun onChangeSelectedFileUriDokumentasiSesiMentoringCluster(uri : Uri?) {
    _uiState.update { it.copy(selectedFileUriDokumentasiSesiMentoringCluster = uri) }
  }

  private fun onChangeJangkauanProgram(jangkauanProgram : String) {
    _uiState.update { it.copy(jangkauanProgram = jangkauanProgram) }
  }

  private fun onChangeWilayahJangkauanProgram(wilayahJangkauanProgram : String) {
    _uiState.update { it.copy(wilayahJangkauanProgram = wilayahJangkauanProgram) }
  }

  private fun onChangeJumlahAngkaPenerimaanManfaat(
    index : Int,
    updatedField : JangkauanPenerimaManfaat
  ) {
    _uiState.update { currentState ->
      currentState.copy(
        jumlahAngkaPenerimaanManfaat = currentState.jumlahAngkaPenerimaanManfaat.mapIndexed { i, field ->
          if (i == index) updatedField else field
        }
      )
    }
  }


  private fun onRemoveLastJumlahAngkaPenerimaanManfaat() {
    _uiState.update { currentState ->
      currentState.copy(
        jumlahAngkaPenerimaanManfaat = currentState.jumlahAngkaPenerimaanManfaat.dropLast(1)
      )
    }
  }

  private fun onAddJumlahAngkaPenerimaanManfaat(
    newField : JangkauanPenerimaManfaat
  ) {
    _uiState.update { currentState ->
      currentState.copy(
        jumlahAngkaPenerimaanManfaat = currentState.jumlahAngkaPenerimaanManfaat + newField
      )
    }

  }


  private fun onChangeTargetUtamaProgram(targetUtamaProgram : String) {
    _uiState.update { it.copy(targetUtamaProgram = targetUtamaProgram) }
  }

  private fun onChangeSelectedFileUriProposalProgram(uri : Uri?) {
    _uiState.update { it.copy(selectedFileUriProposalProgram = uri) }
  }

  private fun onChangeNamaLengkapPeserta(namaLengkap : String) {
    _uiState.update { it.copy(namaLengkapPeserta = namaLengkap) }
  }

  private fun onChangePosisiPeserta(posisiPeserta : String) {
    _uiState.update { it.copy(posisiPeserta = posisiPeserta) }
  }

  private fun onChangePendidikanTerakhir(pendidikanTerakhir : String) {
    _uiState.update { it.copy(pendidikanTerakhir = pendidikanTerakhir) }
  }

  private fun onChangeJurusanPendidikanTerakhir(jurusanPendidikan : String) {
    _uiState.update { it.copy(jurusanPendidikanTerakhir = jurusanPendidikan) }
  }

  private fun onChangeJenisKelamin(jenisKelamin : String) {
    _uiState.update { it.copy(jenisKelamin = jenisKelamin) }
  }

  private fun onChangeNomorWhatsappPeserta(nomorWhatsapp : String) {
    _uiState.update { it.copy(nomorWhatsappPeserta = nomorWhatsapp) }
  }

  private fun onChangeEmailPeserta(email : String) {
    _uiState.update { it.copy(emailPeserta = email) }
  }

  private fun onChangeSelectedFileUriKTP(uri : Uri?) {
    _uiState.update { it.copy(selectedFileUriKTP = uri) }
  }

  private fun onChangeSelectedFileUriCV(uri : Uri?) {
    _uiState.update { it.copy(selectedFileUriCV = uri) }
  }

  private fun onChangeAdaPengurusLain(adaPengurusLain : Boolean) {
    _uiState.update { it.copy(adaPengurusLain = adaPengurusLain) }
  }

  private fun onChangeAlasanTidakMengikutiAgenda(alasan : String) {
    _uiState.update { it.copy(alasanTidakMengikutiAgenda = alasan) }
  }

  private fun onChangeBersediaMengikutiAgenda(bersedia : Boolean) {
    _uiState.update { it.copy(bersediaMengikutiAgenda = bersedia) }
  }

  private fun onChangePernahMengikutiPelatihanDesainProgram(pelatihan : String) {
    _uiState.update { it.copy(pernahMengikutiPelatihanDesainProgram = pelatihan) }
  }

  private fun onChangeSumberInformasiLEAD(sumber : String, isChecked : Boolean) {
    val updatedSumberInformasiLEAD = if (isChecked) {
      _uiState.value.sumberInformasiLEAD + sumber
    } else {
      _uiState.value.sumberInformasiLEAD - sumber
    }
    _uiState.value = _uiState.value.copy(sumberInformasiLEAD = updatedSumberInformasiLEAD)

  }

  private fun onChangePengetahuanDesainProgram(pengetahuan : String) {
    _uiState.update { it.copy(pengetahuanDesainProgram = pengetahuan) }
  }

  private fun onChangePengetahuanLead(pengetahuan : String) {
    _uiState.update { it.copy(pengetahuanLead = pengetahuan) }
  }

  private fun onChangePengetahuanSustainability(pengetahuan : String) {
    _uiState.update { it.copy(pengetahuanSustainability = pengetahuan) }
  }

  private fun onChangePengetahuanSocialReport(pengetahuan : String) {
    _uiState.update { it.copy(pengetahuanSocialReport = pengetahuan) }
  }

  private fun onChangeSelectedFileUriLaporanAkhirTahun(uri : Uri?) {
    _uiState.update { it.copy(selectedFileUriLaporanAkhirTahun = uri) }
  }

  private fun onChangeEkspetasiSetelahLEAD(ekspetasi : String) {
    _uiState.update { it.copy(ekspetasiSetelahLEAD = ekspetasi) }
  }

  private fun onChangeHalLainYangInginDisampaikan(halLain : String) {
    _uiState.update { it.copy(halLainYangInginDisampaikan = halLain) }
  }


}
