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
fun FeedbackMentorScreen2(
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
        TopSection(
            issueSharingRating = issueSharingRating,
            onIssueSharingRatingChange = { issueSharingRating = it },
            stakeholderMappingRating = stakeholderMappingRating,
            onStakeholderMappingRatingChange = { stakeholderMappingRating = it },
            fundingStrategyRating = fundingStrategyRating,
            onFundingStrategyRatingChange = { fundingStrategyRating = it }
        )
        BottomSection2(
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
fun TopSection(
    issueSharingRating: Int,
    onIssueSharingRatingChange: (Int) -> Unit,
    stakeholderMappingRating: Int,
    onStakeholderMappingRatingChange: (Int) -> Unit,
    fundingStrategyRating: Int,
    onFundingStrategyRatingChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Umpan Balik Mentor",
            style = StyledText.MobileLargeSemibold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),

        )
        Text(
            text = "Evaluasi Capaian Mentoring Cluster 1",
            style = StyledText.MobileBaseSemibold,
            color = ColorPalette.PrimaryColor700,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        RatingSections(
            title = "Sharing isu yang digeluti, membahas 3P (Progress, Problem, Plan) yang dilalui peserta dalam kegiatan LEAD maupun yang dihadapi di lapangan",
            rating = issueSharingRating,
            onRatingSelected = onIssueSharingRatingChange
        )

        RatingSections(
            title = "Pemetaan potensial stakeholder...",
            rating = stakeholderMappingRating,
            onRatingSelected = onStakeholderMappingRatingChange
        )

        RatingSections(
            title = "Menyusun strategi capaian pendanaan...",
            rating = fundingStrategyRating,
            onRatingSelected = onFundingStrategyRatingChange
        )
    }
}
@Composable
fun RatingSections(
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
fun BottomSection2(
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
                onClick = {onNavigateBackForm(1)},
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
                onClick = {onNavigateNextForm(1)},
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