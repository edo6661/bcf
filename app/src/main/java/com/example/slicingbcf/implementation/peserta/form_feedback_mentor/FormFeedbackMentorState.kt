package com.example.slicingbcf.implementation.peserta.form_feedback_mentor

import android.net.Uri
import com.example.slicingbcf.implementation.peserta.form_feedback_mini_training.FormMiniTrainingEvent

data class FormFeedbackMentorState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val isSuccess : Boolean = false,
    val selectedEvaluasi : String = "",
    val selectedEvaluasiError : String? = null,
    val namaMentor : String = "",
    val namaMentorError : String? = null,
    val selectedPeriode : String = "",
    val selectedPeriodeError : String? = null,

    val evaluasiCapaian : List<String> = listOf(
        "",
        "",
        ""
    ),
    val evaluasiCapaianError : String? = null,
    val evaluasiMentor : List<String> = listOf(
        "",
        "",
        ""
    ),
    val evaluasiMentorError : String? = null,
    val discussionText : String = "",
    val discussionTextError : String? = null,
    val suggestionText : String = "",
    val suggestionTextError : String? = null,
    val selectedFileUri : Uri? = null,
    val selectedFileUriError : String? = null,
)

sealed class FormFeedbackMentorEvent {

    data class SelectedEvaluasiChanged(val selectedEvaluasi: String) : FormFeedbackMentorEvent()
    data class NamaMentorChanged(val namaMentor: String) : FormFeedbackMentorEvent()
    data class IssueSharingRatingChanged(val index : Int, val evaluasiCapaian : String) :
        FormFeedbackMentorEvent()
    data class SelectedPeriodeChanged(val selectedPeriode: String) : FormFeedbackMentorEvent()
    data class EvaluasiMentorChanged(val index : Int, val evaluasiMentor : String) :
        FormFeedbackMentorEvent()
    data class DiscussionTextChanged(val discussionText: String) : FormFeedbackMentorEvent()
    data class SuggestionTextChanged(val suggestionText: String) : FormFeedbackMentorEvent()
    data class SelectedFileUriChanged(val selectedFileUri : Uri?) : FormFeedbackMentorEvent()
    object Submit : FormFeedbackMentorEvent()
}
