package com.example.slicingbcf.ui.shared.pitchdeck_worksheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.data.local.PitchDeck
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.message.SecondaryButton

@Composable
fun PitchDeckItem(
    data: PitchDeck,
    onNavigateDetailPitchdeck : (String) -> Unit,
    bgColor: Color,
    id : String
) {
    val isExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isExpanded.value) 1.dp else 0.dp,
                color = if (isExpanded.value) ColorPalette.Monochrome200 else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = bgColor,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Card(
            onClick = {
                isExpanded.value = !isExpanded.value
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isExpanded.value) ColorPalette.PrimaryColor100 else bgColor,
            ),
            border = BorderStroke(1.dp, ColorPalette.Monochrome200)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.folder),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Column {
                        Text(
                            text = data.title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Dibuat ${getCurrentTime()} WIB",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black
                        )
                    }
                }
                Icon(
                    imageVector = if (isExpanded.value) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        if (isExpanded.value) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .background(
                        color = bgColor,
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable {
                        // Handle link click
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_link_45deg),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = data.link,
                        style = StyledText.MobileSmallRegular,
                        color = ColorPalette.PrimaryColor400
                    )
                }
                Text(
                    text = data.description,
                    style = StyledText.MobileSmallRegular
                )
                Text(
                    text = buildAnnotatedString {
                        append("Batas Submisi: ")
                        withStyle(
                            style = SpanStyle(
                                color = ColorPalette.SecondaryColor400
                            )
                        ) {
                            append(data.submissionDeadline)
                        }
                    },
                    style = StyledText.MobileSmallMedium
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    SecondaryButton(
                        text = "Lihat Detail",
                        onClick = {onNavigateDetailPitchdeck(id)},
                        style = StyledText.MobileSmallMedium
                    )
                }
            }
        }
    }
}

fun getCurrentTime(): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(Date())
}
