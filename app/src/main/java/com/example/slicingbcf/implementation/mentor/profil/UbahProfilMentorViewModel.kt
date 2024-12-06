package com.example.slicingbcf.implementation.mentor.profil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slicingbcf.data.local.Batch
import com.example.slicingbcf.data.repo.user.UserRepository
import com.example.slicingbcf.di.IODispatcher
import com.example.slicingbcf.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UbahProfilMentorViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _state = MutableStateFlow(UbahProfilMentorState())
    val state = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    fun onEvent(event: UbahProfilMentorEvent) {
        when (event) {
            is UbahProfilMentorEvent.NamaLengkapChanged -> {
                _state.update { it.copy(namaLengkap = event.namaLengkap) }
            }
            is UbahProfilMentorEvent.EmailChanged -> {
                _state.update { it.copy(email = event.email) }
            }
            is UbahProfilMentorEvent.InstansiChanged -> {
                _state.update { it.copy(instansi = event.instansi) }
            }
            is UbahProfilMentorEvent.JenisKelaminChanged -> {
                _state.update { it.copy(jenisKelamin = event.jenisKelamin) }
            }
            is UbahProfilMentorEvent.JurusanChanged -> {
                _state.update { it.copy(jurusan = event.jurusan) }
            }
            is UbahProfilMentorEvent.KategoriMentorChanged -> {
                _state.update { it.copy(kategoriMentor = event.kategoriMentor) }
            }
            is UbahProfilMentorEvent.NomorHPChanged -> {
                _state.update { it.copy(nomorHP = event.nomorHP) }
            }
            is UbahProfilMentorEvent.PekerjaanChanged -> {
                _state.update { it.copy(pekerjaan = event.pekerjaan) }
            }
            is UbahProfilMentorEvent.PendidikanTerakhirChanged -> {
                _state.update { it.copy(pendidikanTerakhir = event.pendidikanTerakhir) }
            }
            is UbahProfilMentorEvent.TanggalLahirChanged -> {
                _state.update { it.copy(tanggalLahir = event.tanggalLahir) }
            }
            is UbahProfilMentorEvent.ClusterChanged -> {
                _state.update { it.copy(cluster = event.cluster) }
            }
            is UbahProfilMentorEvent.FokusIsuChanged -> {
                _state.update { it.copy(fokusIsu = event.fokusIsu) }
            }

            UbahProfilMentorEvent.ClearError -> {
                _state.update {
                    it.copy(
                        namaLengkapError = null,
                        tanggalLahirError = null,
                        jenisKelaminError = null,
                        emailError = null,
                        nomorHPError = null,
                        pendidikanTerakhirError = null,
                        jurusanError = null,
                        pekerjaanError = null,
                        instansiError = null,
                        kategoriMentorError = null,
                        clusterError = null,
                        fokusIsuError = null
                    )
                }
            }
            UbahProfilMentorEvent.ClearState -> {
                _state.value = UbahProfilMentorState()
            }
            UbahProfilMentorEvent.Reload -> {
                viewModelScope.launch {
                    _isLoading.value = true
                    delay(1000)
                    _isLoading.value = false
                }
            }
            UbahProfilMentorEvent.Submit -> {
                onSubmit() }
            }

        }

    private fun onSubmit() {
        if (! validate()) {
            _state.update {
                it.copy(
                    error = "Mohon isi semua field yang wajib diisi dengan format yang benar"
                )
            }
            return
        }

        viewModelScope.launch(ioDispatcher) {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                Log.d("UbahProfilMentorViewModel", "submitProfil: ${state.value}")
                _isLoading.value = true
            } catch (e: Exception) {
                withContext(mainDispatcher) {
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            }
        }
    }

    @Suppress("t")
    private fun validate(): Boolean {
        val state = _state.value

        val validationsString = listOf(
            state.namaLengkap to "Nama Lengkap",
            state.tanggalLahir to "Tanggal Lahir",
            state.jenisKelamin to "Jenis Kelamin",
            state.email to "Email",
            state.nomorHP to "Nomor HP",
            state.pendidikanTerakhir to "Pendidikan Terakhir",
            state.jurusan to "Jurusan",
            state.pekerjaan to "Pekerjaan",
            state.instansi to "Instansi",
            state.kategoriMentor to "Kategori Mentor",
            state.cluster to "Cluster",
            state.fokusIsu to "Fokus Isu"
        )
        var isValid = true
        val errorMap = validationsString.mapNotNull { (field, errorMessage) ->
            if (field.isBlank()) {
                isValid = false
                errorMessage + " tidak boleh kosong"
            } else null
        }
        _state.update { currentState ->
            currentState.copy(
                namaLengkapError = errorMap.find { it.contains("Nama Lengkap") },
                tanggalLahirError = errorMap.find { it.contains("Tanggal Lahir") },
                jenisKelaminError = errorMap.find { it.contains("Jenis Kelamin") },
                emailError = errorMap.find { it.contains("Email") },
                nomorHPError = errorMap.find { it.contains("Nomor HP") },
                pendidikanTerakhirError = errorMap.find { it.contains("Pendidikan Terakhir") },
                jurusanError = errorMap.find { it.contains("Jurusan") },
                pekerjaanError = errorMap.find { it.contains("Pekerjaan") },
                instansiError = errorMap.find { it.contains("Instansi") },
                kategoriMentorError = errorMap.find { it.contains("Kategori Mentor") },
                clusterError = errorMap.find { it.contains("Cluster") },
                fokusIsuError = errorMap.find { it.contains("Fokus Isu") }
            )
        }
        return isValid
    }
}

sealed class UbahProfilMentorEvent {
    object Reload : UbahProfilMentorEvent()
    object Submit: UbahProfilMentorEvent()
    object ClearState: UbahProfilMentorEvent()
    object ClearError: UbahProfilMentorEvent()

    data class NamaLengkapChanged(val namaLengkap: String): UbahProfilMentorEvent()
    data class TanggalLahirChanged(val tanggalLahir: String): UbahProfilMentorEvent()
    data class JenisKelaminChanged(val jenisKelamin: String): UbahProfilMentorEvent()
    data class EmailChanged(val email: String): UbahProfilMentorEvent()
    data class NomorHPChanged(val nomorHP: String): UbahProfilMentorEvent()
    data class PendidikanTerakhirChanged(val pendidikanTerakhir: String): UbahProfilMentorEvent()
    data class JurusanChanged(val jurusan: String): UbahProfilMentorEvent()
    data class PekerjaanChanged(val pekerjaan: String): UbahProfilMentorEvent()
    data class InstansiChanged(val instansi: String): UbahProfilMentorEvent()
    data class KategoriMentorChanged(val kategoriMentor: String): UbahProfilMentorEvent()
    data class ClusterChanged(val cluster: String): UbahProfilMentorEvent()
    data class FokusIsuChanged(val fokusIsu: String): UbahProfilMentorEvent()
}

data class UbahProfilMentorState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val message: String? = null,

    val namaLengkap: String = "",
    val namaLengkapError: String? = null,
    val tanggalLahir: String = "",
    val tanggalLahirError: String? = null,
    val jenisKelamin: String = "",
    val jenisKelaminError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val nomorHP: String = "",
    val nomorHPError: String? = null,
    val pendidikanTerakhir: String = "",
    val pendidikanTerakhirError: String? = null,
    val jurusan: String = "",
    val jurusanError: String? = null,
    val pekerjaan: String = "",
    val pekerjaanError: String? = null,
    val instansi: String = "",
    val instansiError: String? = null,
    val kategoriMentor: String = "",
    val kategoriMentorError: String? = null,

    val batches: List<Batch> = emptyList(),
    val cluster: String = "",
    val clusterError: String? = null,
    val fokusIsu: String = "",
    val fokusIsuError: String? = null,
)