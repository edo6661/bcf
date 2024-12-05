package com.example.slicingbcf.implementation.peserta.pitch_deck

data class PitchDeckState (
    val isLoading : Boolean = false,
    val error : String? = null,
    val isSuccess : Boolean = false,
    val tautanKegiatan : String = "",
    val tautanKegiatanError : String? = null,
)


sealed class PitchDeckEvent{
    data class TautanKegiatanChanged(val tautanKegiatan: String) : PitchDeckEvent()
    object ClearState : PitchDeckEvent()
    object Submit : PitchDeckEvent()
    object ReloadData : PitchDeckEvent()
}