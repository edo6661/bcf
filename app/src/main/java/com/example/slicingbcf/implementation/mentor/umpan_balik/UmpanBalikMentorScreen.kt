package com.example.slicingbcf.implementation.mentor.umpan_balik

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.listOfLembaga
import com.example.slicingbcf.ui.shared.textfield.SearchBarCustom

@Composable
fun UmpanBalikMentorScreen(
    modifier: Modifier = Modifier,
    onNavigateDetailUmpanBalikMentor: (String) -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier.padding(
            horizontal = 16.dp,
            vertical = 24.dp
        )
    ) {
        Text(
            text = "Umpan Balik Peserta",
            style = StyledText.MobileMediumMedium,
            color = ColorPalette.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Capaian Mentoring",
                style = StyledText.MobileMediumSemibold,
                color = ColorPalette.PrimaryColor700
            )
            OutlinedButton(
                onClick = { /* Handle date navigation */ },
                modifier = Modifier
                    .height(40.dp),
                border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = ColorPalette.PrimaryColor700
                ),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                Text(
                    text = "1 Februari 2023",
                    style = StyledText.MobileSmallRegular,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = null,
                    tint = ColorPalette.PrimaryColor700,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        SearchBarCustom(
            onSearch = { Log.d("search", it) },
            modifier = Modifier
                .fillMaxWidth(),
            color = ColorPalette.Monochrome500,
            textStyle = StyledText.MobileSmallMedium,
            title = "Cari Peserta",
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(listOfLembaga.size){ index ->
                UmpanBalikMentorItem(
                    lembaga = listOfLembaga[index],
                    onClick = { onNavigateDetailUmpanBalikMentor(listOfLembaga[index].namaLembaga) }
                )
            }
        }
    }
}

