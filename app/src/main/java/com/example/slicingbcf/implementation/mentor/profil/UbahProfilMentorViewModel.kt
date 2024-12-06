package com.example.slicingbcf.implementation.mentor.profil

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

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { it.copy(isLoading = false, error = exception.localizedMessage?: "Terjadi kesalahan") }
    }

    private fun onSubmit(){
    }

    init {
        viewModelScope.launch(exceptionHandler + ioDispatcher) {
            // TODO: Load data
        }
    }

    fun onEvent(event: UbahProfilMentorEvent) {
        when (event) {
            is UbahProfilMentorEvent.Reload -> {
            }
            is UbahProfilMentorEvent.Submit -> {
                onSubmit()
            }
            is UbahProfilMentorEvent.ClearState -> {
                _state.update { UbahProfilMentorState() }
            }
            is UbahProfilMentorEvent.ClearError -> TODO()
            is UbahProfilMentorEvent.NamaLengkapChanged -> TODO()
            is UbahProfilMentorEvent.EmailChanged -> TODO()
            is UbahProfilMentorEvent.InstansiChanged -> TODO()
            is UbahProfilMentorEvent.JenisKelaminChanged -> TODO()
            is UbahProfilMentorEvent.JurusanChanged -> TODO()
            is UbahProfilMentorEvent.KategoriMentorChanged -> TODO()
            is UbahProfilMentorEvent.NomorHPChanged -> TODO()
            is UbahProfilMentorEvent.PekerjaanChanged -> TODO()
            is UbahProfilMentorEvent.PendidikanTerakhirChanged -> TODO()
            is UbahProfilMentorEvent.TanggalLahirChanged -> TODO()
        }
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
)