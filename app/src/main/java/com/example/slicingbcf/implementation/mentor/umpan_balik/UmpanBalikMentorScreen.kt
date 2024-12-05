package com.example.slicingbcf.implementation.mentor.umpan_balik

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.listOfLembaga
import com.example.slicingbcf.ui.shared.dropdown.DropdownText
import com.example.slicingbcf.ui.shared.textfield.SearchBarCustom

// TODO: SARI
@Composable
fun UmpanBalikMentorScreen(
    modifier: Modifier = Modifier,
    onNavigateDetailUmpanBalikMentor: (String) -> Unit
) {
    var capaianMentoring by remember { mutableStateOf("") }
    var expandedCapaianMentoring by remember { mutableStateOf(false) }

    val capaianMentoringOnValueChange = { newValue: String ->
        capaianMentoring = newValue
    }

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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Capaian Mentoring",
                style = StyledText.MobileMediumSemibold,
                color = ColorPalette.PrimaryColor700
            )
            CustomDropdownMenu(
                label = "",
                value = capaianMentoring,
                placeholder = "1 Februari 2023",
                onValueChange = capaianMentoringOnValueChange,
                expanded = expandedCapaianMentoring,
                onChangeExpanded = { expandedCapaianMentoring = it },
                dropdownItems = listOf(
                    "1 Februari 2023",
                    "1 Maret 2023",
                    "1 April 2023",
                    "1 Mei 2023"
                )
            )
        }
        SearchBarCustom(
            onSearch = { Log.d("search", it) },
            modifier = Modifier.fillMaxWidth(),
            color = ColorPalette.Monochrome500,
            textStyle = StyledText.MobileSmallMedium,
            title = "Cari Peserta",
            bgColor = ColorPalette.Monochrome150
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listOfLembaga.size) { index ->
                UmpanBalikMentorItem(
                    lembaga = listOfLembaga[index],
                    onClick = { onNavigateDetailUmpanBalikMentor(listOfLembaga[index].namaLembaga) }
                )
            }
        }
    }
}

@Composable
fun CustomDropdownMenu(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    dropdownItems: List<String>,
    expanded: Boolean,
    onChangeExpanded: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        OutlinedButton(
            onClick = { onChangeExpanded(!expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = ColorPalette.PrimaryColor700
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (value.isEmpty()) placeholder else value,
                    style = StyledText.MobileSmallRegular,
                    color = ColorPalette.PrimaryColor700
                )
                Icon(
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = null,
                    tint = ColorPalette.PrimaryColor700
                )
            }
        }
        DropdownText(
            expanded = expanded,
            onExpandedChange = { onChangeExpanded(it) },
            onItemSelected = { item ->
                onValueChange(item)
                onChangeExpanded(false)
            },
            items = dropdownItems,
            currentItem = value
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUmpanBalikMentorScreen() {
    UmpanBalikMentorScreen(
        onNavigateDetailUmpanBalikMentor = {
            Log.d("UmpanBalikMentorScreen", "Navigate to detail umpan balik mentor")
        }
    )
}