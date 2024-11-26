package com.example.slicingbcf.implementation.peserta.pitch_deck

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.PitchDeck
import com.example.slicingbcf.data.local.pitchDeck
import java.text.SimpleDateFormat
import java.util.*

@Composable
@Preview(showSystemUi = true)
fun PitchDeckDetailScreen(
    modifier: Modifier = Modifier,
    id: String = "1"
) {
    var tautanPeserta by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val currentPitchDeck = pitchDeck.first()

        TopSection(
            pitchDeck = currentPitchDeck,
            tautanPeserta = tautanPeserta,
            onTautanChange = { tautanPeserta = it },)
    }
}

@Composable
fun TopSection(
    tautanPeserta: TextFieldValue,
    pitchDeck: PitchDeck,
    onTautanChange: (TextFieldValue) -> Unit,
) {
    var lastModified by remember { mutableStateOf(getCurrentTimePitchDeck()) }
    val currentContext = LocalContext.current
    val isLate = checkIfLate(pitchDeck.submissionDeadline)

    Text(
        text = "Submisi Pitch Deck",
        style = StyledText.MobileLargeSemibold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    )

    InfoRow(label = "Judul Lembar Kerja", value = pitchDeck.title)
    InfoRow(label = "Batch", value = pitchDeck.batch.toString())
    InfoRow(label = "Deskripsi Lembar Kerja", value = pitchDeck.description)

    Column {
        Text(
            text = "Tautan Lembar Kerja",
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
        )
        Text(
            text = "Tautan Lembar Kerja",
            style = MaterialTheme.typography.bodyMedium,
            color = ColorPalette.PrimaryColor400,
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pitchDeck.link))
                    currentContext.startActivity(intent)
                }
                .padding(vertical = 4.dp),
            textDecoration = TextDecoration.Underline
        )
    }

    InfoRow(
        label = "Batas Submisi Lembar Kerja",
        value = pitchDeck.submissionDeadline,
        valueColor = ColorPalette.SecondaryColor400
    )

    InfoRow(
        label = "Terakhir Diubah",
        value = lastModified,
        valueColor = if (isLate) Color.Red else ColorPalette.Monochrome300
    )

    Text(
        text = "Tautan Pitch Deck Peserta",
        style = StyledText.MobileBaseSemibold,
        textAlign = TextAlign.Left,
        color = ColorPalette.PrimaryColor700,
    )
    OutlinedTextField(
        value = tautanPeserta,
        onValueChange = onTautanChange,
        placeholder = {
            Text(
                text = "Lampirkan link pith deck anda disini...",
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.Monochrome400
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = ColorPalette.Monochrome400,
            focusedIndicatorColor = ColorPalette.Monochrome400
        ),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(16.dp))

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(
            onClick = { },/*TODO logic on click*/
//        modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorPalette.PrimaryColor700,
                contentColor = Color.White
            )
        ) {
            Text(text = "Submit Tugas", style = StyledText.MobileBaseSemibold)
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    valueColor: Color = Color.Black
) {
    Column {
        Text(
            text = label,
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Justify,
            color = ColorPalette.PrimaryColor700,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor,
            textAlign = TextAlign.Justify
        )
    }
}

fun getCurrentTimePitchDeck(): String {
    val timeFormat = SimpleDateFormat("EEEE, d MMM yyyy HH:mm", Locale("id", "ID"))
    return timeFormat.format(Date())
}

fun checkIfLate(deadline: String): Boolean {
    val dateFormat = SimpleDateFormat("EEEE, d MMM yyyy HH:mm", Locale("id", "ID"))
    val deadlineDate = dateFormat.parse(deadline) ?: return false
    val currentTime = Date()
    return currentTime.after(deadlineDate)
}
