package com.example.slicingbcf.implementation.peserta.form_feedback_mentor

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.FeedbackMentor
import com.example.slicingbcf.data.local.formEvaluasiCapaianMentoring
import com.example.slicingbcf.data.local.formMentor
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.ConstantFormFeedbackMentor.Companion.evaluasiCapaians
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.ConstantFormFeedbackMentor.Companion.periodeCapaians
import com.example.slicingbcf.ui.animations.AnimatedContentSlide
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.dialog.CustomAlertDialog
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.upload.FileUploadSection


@Composable
fun FeedbackMentorScreen(
    modifier : Modifier = Modifier,
) {

    var currentScreen by rememberSaveable { mutableStateOf(0) }
    val onChangeScreen : (Int) -> Unit = { screen ->
        currentScreen = screen
    }
    var initialState by remember { mutableStateOf(0) }

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val deleteFile = {
        selectedFileUri = null
    }

    val onNavigateNextForm: (Int) -> Unit = { screen ->
        onChangeScreen(screen)
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            selectedFileUri = uri
        }
    )

    var namaMentor by remember { mutableStateOf("") }
    var selectedEvaluasi by remember { mutableStateOf("") }
    var selectedPeriode by remember { mutableStateOf("") }

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
                    onNavigateNextForm = onNavigateNextForm,
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
                    deleteFile = deleteFile,
                    selectedFileUri = selectedFileUri,
                    filePickerLauncher = filePickerLauncher
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

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        TopSectionScreen1(
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
    deleteFile : () -> Unit,
    selectedFileUri: Uri?,
    filePickerLauncher: ManagedActivityResultLauncher<Array<String>, Uri?>
) {
    var discussionText by remember { mutableStateOf("") }
    var suggestionsText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CustomOutlinedTextField(
            label = formMentor[5].title,
            value = discussionText,
            onValueChange = {
                discussionText = it
            },
            asteriskAtEnd = true,
            error = null,
            placeholder = "Tuliskan hal yang dibahas selama mentoring disini...",
            modifier = Modifier
                .fillMaxWidth()
                .height(148.dp),
            multiLine = true,
            maxLines = 5,
            labelDefaultColor = ColorPalette.Monochrome400,
            rounded = 20,
            borderColor = ColorPalette.Outline,
            )

        CustomOutlinedTextField(
            label = formMentor[6].title,
            value = suggestionsText,
            onValueChange = {
                suggestionsText = it
            },
            asteriskAtEnd = true,
            error = null,
            placeholder = "Berisi uraian penjelasan mengenai kritik dan saran dari peserta...",
            modifier = Modifier
                .fillMaxWidth()
                .height(148.dp),
            multiLine = true,
            maxLines = 5,
            labelDefaultColor = ColorPalette.Monochrome400,
            rounded = 20,
            borderColor = ColorPalette.Outline,
        )

        FileUploadSection(
            title = formMentor[7].title,
            asteriskAtEnd = true,
            buttonText = "Klik untuk unggah file",
            onFileSelect = { filePickerLauncher.launch(arrayOf("image/*", "application/pdf")) },
            selectedFileUri = selectedFileUri,
            deleteFile = deleteFile,
            error = null
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
                        showDialog = true
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
    if (showDialog) {
        CustomAlertDialog(
            title = "Apakah anda yakin ingin mengirimkan umpan balik ini?",
            confirmButtonText = "Kirim",
            dismissButtonText = "Batal",
            onConfirm = {
                showDialog = false
                isLoading = true
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
    if(isLoading){
        SubmitLoadingIndicatorDouble(
            isLoading = isLoading,
            title = "Memproses Umpan Balik Anda...",
            onAnimationFinished = {onNavigateBackForm(0)},
            titleBerhasil = "Umpan Balik Anda Berhasil Terkirim!",
        )
    }
}

@Composable
fun TopSectionScreen1(
) {

    var namaMentor by remember { mutableStateOf("") }
    var selectedEvaluasi by remember { mutableStateOf("") }
    var selectedPeriode by remember { mutableStateOf("") }
    var expandedEvaluasiCapaian by remember { mutableStateOf(false) }
    var expandedPeriodeCapaianMentoring by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = formMentor[0].title,
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
        )

        CustomOutlinedTextFieldDropdown(
            label = "Evaluasi Capaian",
            value = selectedEvaluasi,
            asteriskAtEnd = true,
            onValueChange = {
                selectedEvaluasi = it
            },
            placeholder = "Pilih Evaluasi Capaian Mentoring",
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            dropdownItems = evaluasiCapaians,
            expanded = expandedEvaluasiCapaian,
            onChangeExpanded = {
                expandedEvaluasiCapaian = it
            },
            error = null
        )

        CustomOutlinedTextField(
            label = formMentor[1].title,
            value = namaMentor,
            error = null,
            onValueChange = {namaMentor = it
            },
            placeholder = "Ketik nama mentor anda disini...",
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )

        Text(
            text = formMentor[2].title,
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
        )

        CustomOutlinedTextFieldDropdown(
            label = "Periode Capaian Mentoring",
            value = selectedPeriode,
            asteriskAtEnd = true,
            onValueChange = {
                selectedPeriode = it
            },
            placeholder = "Pilih Periode Capaian Mentoring",
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            dropdownItems = periodeCapaians,
            expanded = expandedPeriodeCapaianMentoring,
            onChangeExpanded = {
                expandedPeriodeCapaianMentoring = it
            },
            error = null
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
            text = formMentor[3].title,
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        RatingSection(
            title = formEvaluasiCapaianMentoring[0].title,
            rating = issueSharingRating,
            onRatingSelected = onIssueSharingRatingChange
        )

        RatingSection(
            title = formEvaluasiCapaianMentoring[1].title,
            rating = stakeholderMappingRating,
            onRatingSelected = onStakeholderMappingRatingChange
        )

        RatingSection(
            title = formEvaluasiCapaianMentoring[2].title,
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
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = title,
                style = StyledText.MobileSmallRegular,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .width(356.dp)
            )
            Text(
                text = "*",
                style = StyledText.MobileBaseSemibold,
                color = ColorPalette.Error,
                modifier = Modifier.fillMaxWidth()
            )
        }

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