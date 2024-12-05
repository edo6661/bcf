package com.example.slicingbcf.implementation.mentor.jadwal.tambah_jadwal

data class AddJadwalMentorState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val isSuccess : Boolean = false,
    val judulKegiatan : String = "",
    val judulKegiatanError : String? = null,
    val tipeKegiatan : String = "",
    val tipeKegiatanError : String? = null,
    val selectedDate : String = "",
    val selectedDateError : String? = null,
    val waktuMulai : String = "",
    val waktuMulaiError : String? = null,
    val waktuSelesai : String = "",
    val waktuSelesaiError : String? = null,
    val namaPemateri : String = "",
    val namaPemateriError : String? = null,
    val namaLembaga : String = "",
    val namaLembagaError : String? = null,
    val deskripsiAgenda : String = "",
    val deskripsiAgendaError : String? = null,
    val tautanKegiatan : String = "",
    val tautanKegiatanError : String? = null,
)

sealed class AddJadwalMentorEvent{
    data class JudulKegiatanChanged(val judulKegiatan: String) : AddJadwalMentorEvent()
    data class TipeKegiatanChanged(val tipeKegiatan: String) : AddJadwalMentorEvent()
    data class SelectedDateChanged(val selectedDate : String) : AddJadwalMentorEvent()
    data class WaktuMulaiChanged(val waktuMulai: String) : AddJadwalMentorEvent()
    data class WaktuSelesaiChanged(val waktuSelesai: String) : AddJadwalMentorEvent()
    data class NamaPemateriChanged(val namaPemateri: String) : AddJadwalMentorEvent()
    data class NamaLembagaChanged(val namaLembaga: String) : AddJadwalMentorEvent()
    data class DeskripsiAgendaChanged(val deskripsiAgenda: String) : AddJadwalMentorEvent()
    data class TautanKegiatanChanged(val tautanKegiatan: String) : AddJadwalMentorEvent()
    object ClearState : AddJadwalMentorEvent()
    object Submit : AddJadwalMentorEvent()
}