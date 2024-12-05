package com.example.slicingbcf.implementation.peserta.form_feedback_mini_training

data class FormMiniTrainingState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val isSuccess : Boolean = false,
    val selectedDate : String = "",
    val selectedDateError : String? = null,
    val hariKegiatan : String = "",
    val hariKegiatanError : String? = null,
    val speaker1Name : String = "",
    val speaker1NameError : String? = null,
    val speaker2Name : String = "",
    val speaker2NameError : String? = null,
    val ratingMateri : List<String> = listOf(
        "",
        "",
    ),
    val ratingMateriError : String? = null,
    val ratingWaktu : List<String> = listOf(
        "",
        "",
    ),
    val ratingWaktuError : String? = null,
    val ratingJawaban : List<String> = listOf(
        "",
        "",
    ),
    val ratingJawabanError : String? = null,
    val ratingMetode : List<String> = listOf(
        "",
        "",
    ),
    val ratingMetodeError : String? = null,
    val kritikSaran : String = "",
    val kritikSaranError : String? = null,
)

sealed class FormMiniTrainingEvent{
    data class HariKegiatanChanged(val hariKegiatan: String) : FormMiniTrainingEvent()
    data class Speaker1NameChanged(val speaker1Name: String) : FormMiniTrainingEvent()
    data class Speaker2NameChanged(val speaker2Name: String) : FormMiniTrainingEvent()
    data class SelectedDateChanged(val selectedDate : String) : FormMiniTrainingEvent()
    data class ratingMateriChanged(val index : Int, val evaluasiMateri : String) :
        FormMiniTrainingEvent()
    data class ratingWaktuChanged(val index : Int, val evaluasiWaktu : String) :
        FormMiniTrainingEvent()
    data class ratingJawabanChanged(val index : Int, val evaluasiJawaban : String) :
        FormMiniTrainingEvent()
    data class ratingMetodeChanged(val index : Int, val evaluasiMetode : String) :
        FormMiniTrainingEvent()
    data class KritikSaranChanged(val kritikSaran: String) : FormMiniTrainingEvent()
    object ClearState : FormMiniTrainingEvent()
    object Submit : FormMiniTrainingEvent()
}

