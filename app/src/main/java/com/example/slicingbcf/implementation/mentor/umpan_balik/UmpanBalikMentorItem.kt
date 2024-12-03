package com.example.slicingbcf.implementation.mentor.umpan_balik

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.data.local.Lembaga
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.listOfLembaga

@Composable
fun UmpanBalikMentorItem(
    lembaga: Lembaga,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ColorPalette.PrimaryColor100)
            .clickable { onClick(lembaga.namaLembaga) }
            .padding(
                end = 16.dp,
                bottom = 8.dp
            )
    ){
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            headlineContent = {
                Text(
                    text = "Evaluasi Capaian Mentoring:",
                    style = StyledText.MobileSmallMedium,
                    color = ColorPalette.PrimaryColor400
                )
            },
            supportingContent = {
                Text(
                    text = lembaga.namaLembaga,
                    style = StyledText.MobileBaseSemibold,
                    color = ColorPalette.PrimaryColor700
                )
            },
            trailingContent = {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Arrow Forward",
                    modifier = Modifier.size(24.dp),
                    tint = ColorPalette.PrimaryColor700
                )
            },
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewUmpanBalikMentorItem() {
    UmpanBalikMentorItem(
        lembaga = listOfLembaga[1],
        onClick = {},
    )
}