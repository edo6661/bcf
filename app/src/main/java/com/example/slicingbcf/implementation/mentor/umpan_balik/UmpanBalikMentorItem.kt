package com.example.slicingbcf.implementation.mentor.umpan_balik

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.data.local.Lembaga
import com.example.slicingbcf.constant.StyledText

@Composable
fun UmpanBalikMentorItem(
    lembaga: Lembaga,
    onClick: () -> Unit = {},
    isClickable: Boolean = true,
    bgColor: Color = ColorPalette.PrimaryColor100,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .then(
                if (isClickable) Modifier.clickable(onClick = onClick)
                else Modifier
            )
            .background(
                color = bgColor,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(bottom = 16.dp, top = 8.dp, start = 8.dp, end = 16.dp).
            fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "Evaluasi Capaian Mentoring:",
                style = StyledText.MobileSmallMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = ColorPalette.PrimaryColor400
            )
            Text(
                text = lembaga.namaLembaga,
                style = StyledText.MobileMediumSemibold,
                modifier = Modifier.padding(8.dp),
                color = ColorPalette.PrimaryColor700
            )
        }
        IconButton(
            onClick = { /*todo*/ }
        ) {
            Icon(
                Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = "Lihat Detail",
                tint = ColorPalette.PrimaryColor700,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}