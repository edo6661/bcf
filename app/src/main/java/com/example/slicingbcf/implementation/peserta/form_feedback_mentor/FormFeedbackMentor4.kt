package com.example.slicingbcf.implementation.peserta.form_feedback_mentor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText

@Composable
//@Preview(showSystemUi = true)
fun FeedbackMentorScreen4(
    modifier: Modifier = Modifier,
    onSaveFeedback: (String, String, Uri?) -> Unit = { _, _, _ -> },
    id: String,
) {
    var discussionText by remember { mutableStateOf("") }
    var suggestionsText by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri -> selectedFileUri = uri }
    )
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Umpan Balik Mentor",
            style = StyledText.MobileLargeSemibold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        TextSection(
            title = "Pembahasan selama kegiatan mentoring",
            hint = "Tuliskan hal yang dibahas selama mentoring disini...",
            textValue = discussionText,
            onTextChange = { discussionText = it }
        )

        TextSection(
            title = "Kritik dan saran kegiatan mentoring",
            hint = "Berisi uraian penjelasan mengenai kritik dan saran dari peserta...",
            textValue = suggestionsText,
            onTextChange = { suggestionsText = it }
        )

        FileUploadSection(
            title = "Dokumentasi sesi mentoring cluster",
            buttonText = "Klik untuk unggah file dokumentasi",
            onFileSelect = { filePickerLauncher.launch(arrayOf("image/*", "application/pdf")) },
            selectedFileUri = selectedFileUri
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(
                onClick = {
                    onSaveFeedback(discussionText, suggestionsText, selectedFileUri)
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.PrimaryColor700,
                    contentColor = Color.White
                )
            ) {
                Text("Simpan", style = StyledText.MobileBaseSemibold)
            }
        }
    }
}

@Composable
fun TextSection(
    title: String,
    hint: String,
    textValue: String,
    onTextChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = textValue,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = hint,
                    style = StyledText.MobileSmallRegular,
                    color = ColorPalette.Monochrome400,
                    textAlign = TextAlign.Justify,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = ColorPalette.Monochrome400,
                focusedIndicatorColor = ColorPalette.Monochrome700
            )
        )
    }
}

@Composable
fun FileUploadSection(
    title: String,
    buttonText: String,
    onFileSelect: () -> Unit,
    selectedFileUri: Uri?
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = onFileSelect,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = ColorPalette.PrimaryColor700
            )
        ) {
            Text(
                text = buttonText,
                style = StyledText.MobileSmallRegular,
                textAlign = TextAlign.Center
            )
        }

        selectedFileUri?.let {
            Text(
                text = "File terpilih: ${it.lastPathSegment}",
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.Monochrome700,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
