package com.example.slicingbcf.implementation.mentor.laporan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.FokusIsu
import com.example.slicingbcf.data.local.Lembaga
import com.example.slicingbcf.data.local.listFokusIsu
import com.example.slicingbcf.data.local.listOfLembaga
import com.example.slicingbcf.ui.shared.textfield.SearchBarCustom

@Preview(showSystemUi = true)
@Composable
fun LaporanDataPesertaScreen(
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(40.dp),
        modifier = modifier.padding(
            horizontal = 16.dp,
            vertical = 44.dp
        )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Penilaian Peserta",
            style = StyledText.MobileMediumMedium,
            color = ColorPalette.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        TabNavigation()
    }
}

@Composable
fun TabNavigation(){
    var currentTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Lembaga", "Fokus Isu")

    TabRow(
        selectedTabIndex = currentTabIndex,
        containerColor = ColorPalette.Monochrome100,
        contentColor = ColorPalette.PrimaryColor700,
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                color = ColorPalette.PrimaryColor700,
                width = 46.dp,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                ),
                modifier = Modifier.tabIndicatorOffset(tabPositions[currentTabIndex])

            )
        }
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = currentTabIndex == index,
                onClick = { currentTabIndex = index },
                text = {
                    Text(
                        text = title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = if (currentTabIndex == index)
                            ColorPalette.PrimaryColor700
                        else
                            ColorPalette.Monochrome500
                    )
                }
            )
        }
    }
    TabContent(currentTabIndex)
}

@Composable
fun TabContent(
    currentTabIndex: Int
) {
    SearchBarSortSection()
    when (currentTabIndex) {
        0 -> GenericTable(
            headers = headerLembagaPenilaianPeserta,
            items = listOfLembaga,
            rowContent = { index, item -> LembagaTableRow(index + 1, item) },
            totalLabel = "Total Lembaga"
        )
        1 -> GenericTable(
            headers = headerFokusIsuPenilaianPeserta,
            items = listFokusIsu,
            rowContent = { index, item -> FokusIsuTableRow(index + 1, item) },
            totalLabel = "Total Fokus Isu"
        )
    }
}

@Composable
private fun SearchBarSortSection(){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBarCustom(
                onSearch = {  },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                color = ColorPalette.Monochrome500,
                textStyle = StyledText.MobileSmallMedium,
                title = "Cari Pertanyaan",
            )
            SmallFloatingActionButton(
                onClick = {  },
                modifier = Modifier.size(40.dp),
                containerColor = ColorPalette.PrimaryColor100
            ) {
                Image(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Filter",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun <T> GenericTable(
    headers: List<String>,
    items: List<T>,
    rowContent: @Composable (Int, T) -> Unit,
    totalLabel: String = "Total",
    rowsPerPage: Int = 5,
    showTotalAndPagination: Boolean = true
) {
    var currentPage by remember { mutableIntStateOf(0) }
    val totalPages = (items.size + rowsPerPage - 1) / rowsPerPage
    val startIndex = currentPage * rowsPerPage
    val endIndex = minOf(startIndex + rowsPerPage, items.size)
    val pageItems = if (showTotalAndPagination) items.subList(startIndex, endIndex) else items

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TableHeader(headers = headers)
        pageItems.forEachIndexed { index, item ->
            rowContent(index + (if (showTotalAndPagination) startIndex else 0), item)
        }
        if (showTotalAndPagination) {
            TotalRow(
                totalCount = items.size,
                totalLabel = totalLabel,
                backgroundColor = ColorPalette.Monochrome100
            )
            Spacer(modifier = Modifier.height(16.dp))
            PaginationControls(
                currentPage = currentPage,
                totalPages = totalPages,
                onPageChange = { newPage -> currentPage = newPage }
            )
        }
    }
}


@Composable
fun PaginationControls(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { if (currentPage > 0) onPageChange(currentPage - 1) },
            enabled = currentPage > 0
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
        }
        Text(
            text = "${currentPage + 1} dari $totalPages",
            style = StyledText.MobileSmallMedium,
            color = ColorPalette.Black
        )
        IconButton(
            onClick = { if (currentPage < totalPages - 1) onPageChange(currentPage + 1) },
            enabled = currentPage < totalPages - 1
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
        }
    }
}

@Composable
private fun TableHeader(headers: List<String>) {
    Row(
        modifier = Modifier
            .drawTableBorder()
            .fillMaxWidth()
            .background(ColorPalette.PrimaryColor100)
            .padding(16.dp)
    ) {
        headers.forEach {
            TableCell(
                modifier = Modifier.weight(1f),
                isHeader = true,
                value = it,
            )
        }
    }
}

@Composable
private fun TableCell(
    modifier : Modifier,
    isHeader : Boolean = false,
    value : String,
    color : Color = ColorPalette.PrimaryBorder
) {
    val style = if (isHeader) StyledText.MobileSmallSemibold else StyledText.MobileSmallRegular
    Text(
        text = value,
        style = style,
        color = color,
        modifier = modifier

    )
}

@Composable
private fun TotalRow(
    totalCount: Int,
    totalLabel: String,
    backgroundColor: Color
) {
    Row(
        modifier = Modifier
            .drawTableBorder()
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = totalLabel,
            style = StyledText.MobileSmallSemibold,
            color = ColorPalette.Black,
            modifier = Modifier.weight(3f)
        )
        Text(
            text = totalCount.toString(),
            style = StyledText.MobileSmallSemibold,
            color = ColorPalette.Black,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LembagaTableRow(
    index: Int,
    lembaga: Lembaga
) {
    Row(
        modifier = Modifier
            .drawTableBorder()
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TableCell(value = index.toString(), modifier = Modifier.weight(1f))
        TableCell(value = lembaga.namaLembaga, modifier = Modifier.weight(1f))
        TableCell(value = lembaga.fokusIsu, modifier = Modifier.weight(1f))
        TableCell(value = lembaga.provinsi, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun FokusIsuTableRow(
    index: Int,
    fokusIsu: FokusIsu
) {
    Row(
        modifier = Modifier
            .drawTableBorder()
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TableCell(value = index.toString(), modifier = Modifier.weight(1f))
        TableCell(value = fokusIsu.fokusIsu, modifier = Modifier.weight(1f))
        TableCell(value = fokusIsu.cluster, modifier = Modifier.weight(1f))
        TableCell(value = fokusIsu.batch, modifier = Modifier.weight(1f))
    }
}

fun Modifier.drawTableBorder() = this.drawBehind {
    drawLine(
        color = ColorPalette.OutlineVariant,
        strokeWidth = 1.dp.toPx(),
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f)
    )
    drawLine(
        color = ColorPalette.OutlineVariant,
        strokeWidth = 1.dp.toPx(),
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height)
    )
}

val headerLembagaPenilaianPeserta = listOf(
    "No.",
    "Nama Lembaga",
    "Fokus Isu",
    "Provinsi"
)

val headerFokusIsuPenilaianPeserta = listOf(
    "No.",
    "Fokus Isu",
    "Cluster",
    "Batch"
)