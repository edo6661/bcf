package com.example.slicingbcf.implementation.peserta.form_feedback_mentor

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.formEvaluasiCapaianMentoring
import com.example.slicingbcf.data.local.formEvaluasiMentor
import com.example.slicingbcf.data.local.formMentor
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.ConstantFormFeedbackMentor.Companion.evaluasiCapaians
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.ConstantFormFeedbackMentor.Companion.namaMentors
import com.example.slicingbcf.implementation.peserta.form_feedback_mentor.ConstantFormFeedbackMentor.Companion.periodeCapaians
import com.example.slicingbcf.ui.animations.AnimatedContentSlide
import com.example.slicingbcf.ui.animations.SubmitLoadingIndicatorDouble
import com.example.slicingbcf.ui.shared.dialog.CustomAlertDialog
import com.example.slicingbcf.ui.shared.rating.RatingField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown
import com.example.slicingbcf.ui.upload.FileUploadSection


@Composable
fun FeedbackMentorScreen(
    modifier : Modifier = Modifier,
    viewModel: FormFeedbackMentorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        AnimatedContentSlide(
            currentScreen = currentScreen,
            initialState = initialState,
            label = "Feedback Peserta Animation Content ",
        ) { targetScreen ->
            when (targetScreen) {
                0 -> FirstScreen(
                    state = uiState,
                    onEvent = {event -> viewModel.onEvent(event)},
                    onNavigateNextForm = onNavigateNextForm,
                )
                1 -> SecondScreen(
                    state = uiState,
                    onEvent = {event -> viewModel.onEvent(event)},
                    onNavigateBackForm = { onChangeScreen(0) },
                    onNavigateNextForm = { onChangeScreen(2) },
                    id = "id",
                )
                2 -> ThirdScreen(
                    state = uiState,
                    onEvent = {event -> viewModel.onEvent(event)},
                    onNavigateBackForm = { onChangeScreen(1) },
                    onNavigateNextForm = { onChangeScreen(3) },
                    id = "id",
                )
                3 -> FourthScreen(
                    state = uiState,
                    onEvent = {event -> viewModel.onEvent(event)},
                    onNavigateBackForm = { onChangeScreen(2) },
                    isLoading = isLoading
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
    state : FormFeedbackMentorState,
    onEvent: (FormFeedbackMentorEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        TopSectionScreen1(
            state = state,
            onEvent = onEvent
        )
        BottomSectionScreen1(
            onNavigateNextForm = onNavigateNextForm
        )
    }
}

@Composable
fun SecondScreen(
    modifier: Modifier = Modifier,
    onNavigateNextForm: (Int) -> Unit,
    onNavigateBackForm: (Int) -> Unit,
    id: String,
    state: FormFeedbackMentorState,
    onEvent: (FormFeedbackMentorEvent) -> Unit
){

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        TopSectionScreen2(
            state = state,
            onEvent = onEvent,
        )
        BottomSectionScreen2(
            onNavigateBackForm = {onNavigateBackForm(1)},
            onNavigateNextForm = {
                onNavigateNextForm(1)
            }
        )
    }
}

@Composable
fun ThirdScreen(
    modifier: Modifier = Modifier,
    state : FormFeedbackMentorState,
    onEvent: (FormFeedbackMentorEvent) -> Unit,
    onNavigateNextForm: (Int) -> Unit,
    onNavigateBackForm: (Int) -> Unit,
    id: String
) {
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
        state.evaluasiMentor.forEachIndexed { i, _ ->
            RatingField(
                description = formEvaluasiMentor[i].title,
                rating = state.evaluasiMentor[i],
                onRatingChange = {
                    onEvent(
                        FormFeedbackMentorEvent.EvaluasiMentorChanged(
                            index = i,
                            evaluasiMentor = it
                        )
                    )
                }
            )
        }
        BottomSectionScreen2(
            onNavigateBackForm = { onNavigateBackForm(1) },
            onNavigateNextForm = {
                onNavigateNextForm(1)
            }
        )
        Spacer(modifier = Modifier.height(56.dp))
    }
}

@Composable
fun FourthScreen(
    modifier: Modifier = Modifier,
    state : FormFeedbackMentorState,
    onEvent: (FormFeedbackMentorEvent) -> Unit,
    isLoading: Boolean,
    onNavigateBackForm: (Int) -> Unit,
) {
   var showDialog by remember { mutableStateOf(false) }
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            onEvent(
                FormFeedbackMentorEvent.SelectedFileUriChanged(
                    uri
                )
            )
        }
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        CustomOutlinedTextField(
            label = formMentor[5].title,
            value = state.discussionText,
            onValueChange = {
                onEvent(FormFeedbackMentorEvent.DiscussionTextChanged(it))
            },
            asteriskAtEnd = true,
            error = state.discussionTextError,
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
            value = state.suggestionText,
            onValueChange = {
                onEvent(FormFeedbackMentorEvent.SuggestionTextChanged(it))
            },
            asteriskAtEnd = true,
            error = state.suggestionTextError,
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
            selectedFileUri = state.selectedFileUri,
            deleteFile = { onEvent(FormFeedbackMentorEvent.SelectedFileUriChanged(null)) },
            error = state.selectedFileUriError
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
                onEvent(FormFeedbackMentorEvent.Submit)
                showDialog = false
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
    state : FormFeedbackMentorState,
    onEvent: (FormFeedbackMentorEvent) -> Unit,
) {
    var expandedEvaluasiCapaian by remember { mutableStateOf(false) }
    var expandedPeriodeCapaianMentoring by remember { mutableStateOf(false) }
    var expandedNamaMentor by remember { mutableStateOf(false) }
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
            value = state.selectedEvaluasi,
            asteriskAtEnd = true,
            onValueChange = {
                onEvent(FormFeedbackMentorEvent.SelectedEvaluasiChanged(it))
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
            error = state.selectedEvaluasiError
        )

        CustomOutlinedTextFieldDropdown(
            label = formMentor[1].title,
            value = state.namaMentor,
            asteriskAtEnd = true,
            onValueChange = {
                onEvent(FormFeedbackMentorEvent.NamaMentorChanged(it))
            },
            placeholder = "Pilih nama mentor disini...",
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            dropdownItems = namaMentors,
            expanded = expandedNamaMentor,
            onChangeExpanded = {
                expandedNamaMentor = it
            },
            error = state.namaMentorError
        )

        Text(
            text = formMentor[2].title,
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
        )

        CustomOutlinedTextFieldDropdown(
            label = "Periode Capaian Mentoring",
            value = state.selectedPeriode,
            asteriskAtEnd = true,
            onValueChange = {
                onEvent(FormFeedbackMentorEvent.SelectedPeriodeChanged(it))
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
            error = state.selectedPeriodeError
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
    state : FormFeedbackMentorState,
    onEvent: (FormFeedbackMentorEvent) -> Unit,
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

        state.evaluasiCapaian.forEachIndexed { i, _ ->
            RatingField(
                description = formEvaluasiCapaianMentoring[i].title,
                rating = state.evaluasiCapaian[i],
                onRatingChange = {
                    onEvent(
                        FormFeedbackMentorEvent.IssueSharingRatingChanged(
                            index = i,
                            evaluasiCapaian = it
                        )
                    )
                }
            )
        }
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