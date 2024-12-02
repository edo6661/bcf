package com.example.slicingbcf.implementation.peserta.form_feedback_mentor

import android.net.Uri
import com.example.slicingbcf.implementation.auth.registrasi.RegisterEvent

data class FormFeedbackMentorState(
    val selectedFileUriDokumentasiFormFeedbackMentor: Uri? = null,
    val selectedFileUriDokumentasiFormFeedbackMentorError : String? = null,
)

sealed class FormEvent {
    data class selectedFileUriDokumentasiFormFeedbackMentorChange(val uri: Uri?) : FormEvent()
}
