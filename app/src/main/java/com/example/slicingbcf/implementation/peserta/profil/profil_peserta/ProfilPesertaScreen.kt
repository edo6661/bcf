package com.example.slicingbcf.implementation.peserta.profil.profil_peserta

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.ProfilPeserta
import com.example.slicingbcf.data.local.profilPeserta

@Preview(showSystemUi = true)
@Composable
fun PreviewProfilPesertaScreen() {
    ProfilPesertaScreen(profile = profilPeserta[0])
}

@Composable
fun ProfilPesertaScreen(
    profile: ProfilPeserta,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        TopSection(profile = profile)
        BottomSection(profile = profile)
    }
}

@Composable
fun TopSection(profile: ProfilPeserta) {
    var currentPage by remember { mutableStateOf(1) }
    val totalPages = 2

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        NavigationHeader(
            currentPage = currentPage,
            totalPages = totalPages,
            onPreviousClick = { if (currentPage > 1) currentPage-- },
            onNextClick = { if (currentPage < totalPages) currentPage++ }
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sampul),
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 40.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_sampul),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(ColorPalette.Monochrome300)
                )
            }
        }

        Spacer(modifier = Modifier
            .height(60.dp)
        )

        ProfileInfoRow("Nama Lengkap", profile.namaLengkap)
        ProfileInfoRow("Email Posisi", profile.email)
        ProfileInfoRow("Pendidikan Terakhir", profile.pendidikanTerakhir)
        ProfileInfoRow("Nomor WhatsApp", profile.nomorWhatsApp)
        ProfileInfoRow("Email", profile.email)
        PdfLinkRow("KTP Peserta", profile.ktpPesertaUrl)
        PdfLinkRow("CV Peserta", profile.cvPesertaUrl)

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun BottomSection(profile: ProfilPeserta) {
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri -> selectedFileUri = uri }
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Pertanyaan Umum",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            modifier = Modifier.fillMaxWidth()
        )

//        InputField(
//            label = "Apakah Anda pernah mengikuti pelatihan atau memiliki pengetahuan terkait desain program sebelum mendaftar LEAD Indonesia 2024?",
//            value = profile.pertanyaanUmum.pernahMempelajari,
//            onValueChange = { profile.pertanyaanUmum.pernahMempelajari = it },
//            placeholder = "isi jawaban disini"
//        )
//
//        InputField(
//            label = "Dari mana Anda mengetahui LEAD Indonesia?",
//            value = profile.pertanyaanUmum.mengetahuiLEAD,
//            onValueChange = { profile.pertanyaanUmum.mengetahuiLEAD = it },
//            placeholder = "isi jawaban disini"
//        )

        DropdownField(
            label = "Apakah Anda pernah mengikuti pelatihan atau memiliki pengetahuan terkait desain program sebelum mendaftar LEAD Indonesia 2024?",
            options = listOf("Pernah", "Belum Pernah"),
            selectedOption = profile.pertanyaanUmum.pernahMempelajari,
            onOptionSelected = { profile.pertanyaanUmum.pernahMempelajari = it }
        )

        DropdownField(
            label = "Dari mana Anda mengetahui LEAD Indonesia?",
            options = listOf("Instagram", "Facebook", "LinkedIn", "Teman/Alumni", "Lainnya"),
            selectedOption = profile.pertanyaanUmum.mengetahuiLEAD,
            onOptionSelected = { profile.pertanyaanUmum.mengetahuiLEAD = it }
        )

        InputField(
            label = "Apa yang Anda ketahui terkait desain program?",
            value = profile.pertanyaanUmum.desainProgram,
            onValueChange = { profile.pertanyaanUmum.desainProgram = it },
            placeholder = "isi jawaban disini"
        )

        InputField(
            label = "Apa yang Anda ketahui terkait sustainability atau keberlanjutan?",
            value = profile.pertanyaanUmum.sustainability,
            onValueChange = { profile.pertanyaanUmum.sustainability = it },
            placeholder = "isi jawaban disini"
        )

        InputField(
            label = "Apa yang Anda ketahui terkait social report atau laporan sosial?",
            value = profile.pertanyaanUmum.socialReport,
            onValueChange = { profile.pertanyaanUmum.socialReport = it },
            placeholder = "isi jawaban disini"
        )

        FileUploadSection(
            title = "Unggah Laporan Akhir Tahun atau Laporan Pertanggungjawaban Pelaksanaan Program Instansi",
            buttonText = "Unggah File Dokumentasi",
            onFileSelect = { filePickerLauncher.launch(arrayOf("image/*", "application/pdf")) },
            selectedFileUri = selectedFileUri
        )

        InputField(
            label = "Jelaskan ekspektasi Anda setelah mengikuti kegiatan LEAD Indonesia 2023",
            value = profile.pertanyaanUmum.ekspektasi,
            onValueChange = { profile.pertanyaanUmum.ekspektasi = it },
            placeholder = "isi jawaban disini"
        )

        InputField(
            label = "Apakah ada hal lain yang ingin Anda tanyakan atau sampaikan terkait LEAD Indonesia 2023?",
            value = profile.pertanyaanUmum.pertanyaanLainnya,
            onValueChange = { profile.pertanyaanUmum.pertanyaanLainnya = it },
            placeholder = "isi jawaban disini"
        )

        InputField(
            label = "Ceritakan pengalaman kamu saat mengisi sesi ini registrasi!",
            value = profile.pertanyaanUmum.pengalamanRegistrasi,
            onValueChange = { profile.pertanyaanUmum.pengalamanRegistrasi = it },
            placeholder = "isi jawaban disini"
        )
    }
}

@Composable
fun NavigationHeader(
    currentPage: Int,
    totalPages: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Profil Peserta",
            style = StyledText.MobileLargeSemibold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Previous Page",
                tint = if (currentPage > 1) Color.Black else Color.Gray,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(enabled = currentPage > 1) { onPreviousClick() }
            )
            Text(
                text = "$currentPage dari $totalPages",
                style = StyledText.MobileSmallRegular,
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Next Page",
                tint = if (currentPage < totalPages) Color.Black else Color.Gray,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(enabled = currentPage < totalPages) { onNextClick() }
            )
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label",
            style = StyledText.MobileSmallSemibold,
            modifier = Modifier.weight(1.3f)
        )
        Text(
            text = ":",
            style = StyledText.MobileSmallSemibold,
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = value,
            style = StyledText.MobileSmallRegular,
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun PdfLinkRow(label: String, pdfUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label",
            style = StyledText.MobileSmallSemibold,
            modifier = Modifier.weight(1.3f)
        )
        Text(
            text = ":",
            style = StyledText.MobileSmallSemibold,
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = pdfUrl.substringAfterLast('/'),
            style = StyledText.MobileSmallRegular.copy(color = ColorPalette.PrimaryColor700),
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = ""
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = StyledText.MobileBaseRegular,
            color = ColorPalette.PrimaryColor700,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = StyledText.MobileBaseRegular,
                    color = ColorPalette.Monochrome400
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 50.dp),
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = ColorPalette.Monochrome400,
                focusedIndicatorColor = ColorPalette.Monochrome400,
                focusedTextColor = ColorPalette.Monochrome400,
                unfocusedTextColor = ColorPalette.Monochrome400
            ),
            singleLine = false,
            minLines = 1,
            maxLines = Int.MAX_VALUE
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = StyledText.MobileBaseRegular,
            color = ColorPalette.PrimaryColor700,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = "Pilih...",
                        style = StyledText.MobileBaseRegular,
                        color = ColorPalette.Monochrome400
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = MaterialTheme.shapes.extraLarge,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = ColorPalette.Monochrome400,
                    focusedIndicatorColor = ColorPalette.Monochrome400,
                    focusedTextColor = ColorPalette.Monochrome400,
                    unfocusedTextColor = ColorPalette.Monochrome400
                ),
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option, style = StyledText.MobileSmallRegular) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
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
            style = StyledText.MobileSmallRegular,
            color = ColorPalette.PrimaryColor700,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = onFileSelect,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val dashWidth = 10.dp.toPx()
                    val dashGap = 6.dp.toPx()
                    val cornerRadius = 12.dp.toPx()
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)

                    drawRoundRect(
                        color = ColorPalette.PrimaryColor400,
                        topLeft = Offset(0f, 0f),
                        size = size,
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(width = strokeWidth, pathEffect = pathEffect)
                    )
                },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = ColorPalette.PrimaryColor400
            ),
            border = null
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