package com.example.slicingbcf.implementation.peserta.form_feedback_mentor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
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
import com.example.slicingbcf.data.local.FeedbackMentor

@Composable
//@Preview(showSystemUi = true)
fun FeedbackMentorScreen3(
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
        modifier = modifier.verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Umpan Balik Mentor",
            style = StyledText.MobileLargeSemibold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
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

        BottomSection2(
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
fun RatingSection(
    title: String,
    rating: Int,
    onRatingSelected: (Int) -> Unit
) {
    Column {
        Text(
            text = title,
            style = StyledText.MobileSmallRegular,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Justify,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            (1..4).forEach { value ->
                OutlinedButton(
                    onClick = { onRatingSelected(value) },
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        width = if (rating == value) 2.dp else 1.dp,
                        color = if (rating == value) ColorPalette.PrimaryColor700 else ColorPalette.Monochrome500
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = if (rating == value) ColorPalette.PrimaryColor700 else ColorPalette.Monochrome700
                    )
                ) {
                    Text(
                        text = value.toString(),
                        style = StyledText.MobileBaseSemibold
                    )
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
                color = ColorPalette.Monochrome500
            )
            Text(
                text = "Sangat Baik",
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.Monochrome500
            )
        }
    }
}
