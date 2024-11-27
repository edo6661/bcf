package com.example.slicingbcf.implementation.peserta.form_feedback_mentor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.FeedbackMentor
import com.example.slicingbcf.ui.animations.AnimatedContentSlide
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown

@Composable
fun FeedbackMentorScreen(
    modifier : Modifier = Modifier,
) {
    var currentScreen by rememberSaveable { mutableStateOf(0) }
    val onChangeScreen : (Int) -> Unit = { screen ->
        currentScreen = screen
    }
    var initialState by remember { mutableStateOf(0) }

    val onNavigateNextForm: (Int) -> Unit = { screen ->
        onChangeScreen(screen)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "Umpan Balik Mentor",
            style = StyledText.MobileLargeSemibold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        AnimatedContentSlide(
            currentScreen = currentScreen,
            initialState = initialState,
            label = "Feedback Peserta Animation Content ",
        ) { targetScreen ->
            when (targetScreen) {
                0 -> FirstScreen(
                    onNavigateNextForm = onNavigateNextForm
                )
                1 -> SecondScreen(
                    onNavigateBackForm = { onChangeScreen(0) },
                    onNavigateNextForm = { onChangeScreen(2) },
                    id = "id",
                )
                2 -> ThirdScreen(
                    onNavigateBackForm = { onChangeScreen(1) },
                    onNavigateNextForm = { onChangeScreen(3) },
                    id = "id",
                )
                3 -> FourthScreen(
                    onNavigateBackForm = { onChangeScreen(2) },
                    id = "id",
                )
            }

            LaunchedEffect(currentScreen) {
                initialState = currentScreen
            }
        }
    }

}

@Composable
fun FirstScreen(
    modifier: Modifier = Modifier,
    onNavigateNextForm: (Int) -> Unit,
) {
    var selectedEvaluasi by remember { mutableStateOf("Pilih evaluasi capaian mentoring") }
    var namaMentor by remember { mutableStateOf(TextFieldValue("")) }
    var selectedPeriode by remember { mutableStateOf("Pilih periode capaian mentoring") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        TopSectionScreen1(
            selectedEvaluasi = selectedEvaluasi,
            onEvaluasiChange = { selectedEvaluasi = it },
            namaMentor = namaMentor,
            onNamaMentorChange = { namaMentor = it },
            selectedPeriode = selectedPeriode,
            onPeriodeChange = { selectedPeriode = it }
        )
        BottomSectionScreen1(
            onNavigateNextForm = onNavigateNextForm
        )
    }
}

@Composable
fun SecondScreen(
    modifier: Modifier = Modifier,
    onSaveFeedback: (FeedbackMentor) -> Unit = {},
    onNavigateNextForm: (Int) -> Unit,
    onNavigateBackForm: (Int) -> Unit,
    id: String,
) {
    var issueSharingRating by remember { mutableStateOf(0) }
    var stakeholderMappingRating by remember { mutableStateOf(0) }
    var fundingStrategyRating by remember { mutableStateOf(0) }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        TopSectionScreen2(
            issueSharingRating = issueSharingRating,
            onIssueSharingRatingChange = { issueSharingRating = it },
            stakeholderMappingRating = stakeholderMappingRating,
            onStakeholderMappingRatingChange = { stakeholderMappingRating = it },
            fundingStrategyRating = fundingStrategyRating,
            onFundingStrategyRatingChange = { fundingStrategyRating = it }
        )
        BottomSectionScreen2(
            onNavigateBackForm = {onNavigateBackForm(1)},
            onNavigateNextForm = {
                // TODO: logika "Berikutnya"
                onNavigateNextForm(1)
                val feedback = FeedbackMentor(
                    issueSharingRating,
                    stakeholderMappingRating,
                    fundingStrategyRating
                )
                onSaveFeedback(feedback)
            }
        )
    }
}

@Composable
fun ThirdScreen(
    modifier: Modifier = Modifier,
    onSaveFeedback: (FeedbackMentor) -> Unit = {},
    onNavigateNextForm: (Int) -> Unit,
    onNavigateBackForm: (Int) -> Unit,
    id: String,
) {
    var comprehensiveExplanation by remember { mutableStateOf(0) }
    var sessionSuitability by remember { mutableStateOf(0) }
    var clearInstructions by remember { mutableStateOf(0) }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Evaluasi Mentor",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        RatingSection(
            title = "Mentor memberikan penjelasan secara komprehensif selama mentoring",
            rating = comprehensiveExplanation,
            onRatingSelected = { comprehensiveExplanation = it }
        )

        RatingSection(
            title = "Sesi mentoring berlangsung sesuai dengan kebutuhan pembelajaran peserta",
            rating = sessionSuitability,
            onRatingSelected = { sessionSuitability = it }
        )

        RatingSection(
            title = "Mentor memberikan instruksi dan pertanyaan dengan jelas",
            rating = clearInstructions,
            onRatingSelected = { clearInstructions = it }
        )

        BottomSectionScreen2(
            onNavigateBackForm = { onNavigateBackForm(1) },
            onNavigateNextForm = {
                onNavigateNextForm(1)
                val feedback = FeedbackMentor(
                    comprehensiveExplanation,
                    sessionSuitability,
                    clearInstructions
                )
                onSaveFeedback(feedback)
            }
        )
        Spacer(modifier = Modifier.height(56.dp))
    }
}

@Composable
fun FourthScreen(
    modifier: Modifier = Modifier,
    onSaveFeedback: (String, String, Uri?) -> Unit = { _, _, _ -> },
    onNavigateBackForm: (Int) -> Unit,
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
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
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
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .height(40.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { onNavigateBackForm(0) },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.extraLarge,
                    border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = ColorPalette.PrimaryColor700
                    )
                ) {
                    Text(text = "Kembali", style = StyledText.MobileBaseSemibold)
                }

                Button(
                    onClick = {
                        onSaveFeedback(discussionText, suggestionsText, selectedFileUri)
                    },
                    modifier = Modifier.weight(1f),
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
}

@Composable
fun TopSectionScreen1(
    selectedEvaluasi: String,
    onEvaluasiChange: (String) -> Unit,
    namaMentor: TextFieldValue,
    onNamaMentorChange: (TextFieldValue) -> Unit,
    selectedPeriode: String,
    onPeriodeChange: (String) -> Unit
) {

    var expandedEvaluasiCapaian by remember { mutableStateOf(false) }
    var expandedPeriodeCapaianMentoring by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Dropdown Evaluasi Capaian Mentoring
        Text(
            text = "Evaluasi Capaian Mentoring",
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
        )

        CustomOutlinedTextFieldDropdown(
            value = selectedEvaluasi,
            onValueChange = onEvaluasiChange,
            expanded = expandedEvaluasiCapaian,
            onChangeExpanded = { expandedEvaluasiCapaian = it },
            label = "Nama Lembaga",
            placeholder = "Pilih Evaluasi Capaian Mentoring",
            dropdownItems = listOf("Cluster", "Desain Program")
        )

        // Input Field Nama Mentor
        Text(
            text = "Nama Mentor",
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
        )
        OutlinedTextField(
            value = namaMentor,
            onValueChange = onNamaMentorChange,
            placeholder = {
                Text(
                    text = "Ketik nama mentor anda disini...",
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
                unfocusedIndicatorColor = ColorPalette.Monochrome900,
                focusedIndicatorColor = ColorPalette.Monochrome900
            ),
            singleLine = true
        )

        // Dropdown Periode Capaian Mentoring
        Text(
            text = "Periode Capaian Mentoring",
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
        )
//        DropdownButton(
//            text = selectedPeriode,
//            onClick = { /* TODO: logic on click dropdown */ }
//        )
        CustomOutlinedTextFieldDropdown(
            value = selectedPeriode,
            onValueChange = onPeriodeChange,
            expanded = expandedPeriodeCapaianMentoring,
            onChangeExpanded = { expandedPeriodeCapaianMentoring = it },
            label = "Periode Capaian Mentoring",
            placeholder = "Pilih Periode Capaian Mentoring",
            dropdownItems = listOf("Capaian Mentoring 1", "Capaian Mentoring 2", "Capaian Mentoring 3")
        )
    }
}

@Composable
fun BottomSectionScreen1(
    onNavigateNextForm : (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            modifier = Modifier
                .width(150.dp)
                .height(40.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            OutlinedButton(
//                onClick = {
//                    onNavigateBackForm(1)
//                },
//                modifier = Modifier
//                    .weight(1f)
//                    .height(40.dp),
//                shape = MaterialTheme.shapes.extraLarge,
//                border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
//                colors = ButtonDefaults.outlinedButtonColors(
//                    containerColor = Color.Transparent,
//                    contentColor = ColorPalette.PrimaryColor700
//                )
//            ) {
//                Text(text = "Kembali", style = StyledText.MobileBaseSemibold)
//            }

            Button(
                onClick = {
                    onNavigateNextForm(1)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.PrimaryColor700,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Berikutnya", style = StyledText.MobileBaseSemibold)
            }
        }
    }
}

@Composable
fun TopSectionScreen2(
    issueSharingRating: Int,
    onIssueSharingRatingChange: (Int) -> Unit,
    stakeholderMappingRating: Int,
    onStakeholderMappingRatingChange: (Int) -> Unit,
    fundingStrategyRating: Int,
    onFundingStrategyRatingChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Evaluasi Capaian Mentoring Cluster 1",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        RatingSection(
            title = "Sharing isu yang digeluti, membahas 3P (Progress, Problem, Plan) yang dilalui peserta dalam kegiatan LEAD maupun yang dihadapi di lapangan",
            rating = issueSharingRating,
            onRatingSelected = onIssueSharingRatingChange
        )

        RatingSection(
            title = "Pemetaan potensial stakeholder...",
            rating = stakeholderMappingRating,
            onRatingSelected = onStakeholderMappingRatingChange
        )

        RatingSection(
            title = "Menyusun strategi capaian pendanaan...",
            rating = fundingStrategyRating,
            onRatingSelected = onFundingStrategyRatingChange
        )
    }
}

@Composable
fun BottomSectionScreen2(
    onNavigateBackForm: (Int) -> Unit,
    onNavigateNextForm: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            modifier = Modifier
                .width(300.dp)
                .height(40.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {onNavigateBackForm(0)},
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.extraLarge,
                border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = ColorPalette.PrimaryColor700
                )
            ) {
                Text(text = "Kembali", style = StyledText.MobileBaseSemibold)
            }

            Button(
                onClick = {onNavigateNextForm(2)},
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.PrimaryColor700,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Berikutnya", style = StyledText.MobileBaseSemibold)
            }
        }
    }
}

@Composable
fun RatingSection(
    title: String,
    rating: Int,
    onRatingSelected: (Int) -> Unit
) {
    Column {
        Text(
            text = title,
            style = StyledText.MobileSmallRegular,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            (1..4).forEach { value ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    OutlinedButton(
                        onClick = { onRatingSelected(value) },
                        modifier = Modifier.size(78.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = if (rating == value)
                            BorderStroke(2.dp, ColorPalette.PrimaryColor700)
                        else
                            BorderStroke(1.dp, ColorPalette.Monochrome500),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = ColorPalette.Monochrome900
                        )
                    ) {
                        Text(
                            text = value.toString(),
                            style = StyledText.MobileBaseSemibold,
                            color = if (rating == value) ColorPalette.PrimaryColor700
                            else ColorPalette.Monochrome700
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        ) {
            Text(
                text = "Sangat Tidak Baik",
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.Monochrome500,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "Sangat Baik",
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.Monochrome500,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(end = 8.dp)
            )
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
