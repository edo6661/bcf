package com.example.slicingbcf.implementation.peserta.data_peserta

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.shared.PrimaryButton
import com.example.slicingbcf.ui.shared.textfield.SearchBarCustom

@Composable
fun DataPesertaScreen(
  modifier : Modifier = Modifier,
  onNavigateDetailDataPeserta: (String) -> Unit
) {
  Column(
    modifier = modifier
      .background(ColorPalette.Monochrome100)
      .padding(
        horizontal = 16.dp,
      )
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(28.dp),
  ) {

    TopSection(
      onSearch = { q ->
      }
    )
    BottomSection(
      onNavigateDetailDataPeserta = onNavigateDetailDataPeserta
    )

  }
}

@Composable
fun TopSection(
  onSearch : (String) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text(
      text = "Data Peserta",
      style = StyledText.MobileLargeSemibold,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally),
      verticalAlignment = Alignment.CenterVertically,

      ) {
      SearchBarCustom(
        onSearch = onSearch,
        title = "Cari Peserta",
        bgColor = ColorPalette.OnPrimary,
      )

      ContainerImageCenteredShadow() {
        Image(
          painter = painterResource(id = R.drawable.filter),
          contentDescription = "Filter",
          modifier = Modifier.size(24.dp)
        )
      }


      ContainerImageCenteredShadow(
        onClick = {
          Log.d("DataPesertaScreen", "Download")
        }
      ) {
        Icon(
          imageVector = Icons.Default.Download,
          contentDescription = "Download",
          modifier = Modifier.size(24.dp)
        )
      }
    }
  }
}


@Composable
private fun ContainerImageCenteredShadow(
  onClick : () -> Unit = {},
  content : @Composable () -> Unit
) {
  Box(
    modifier = Modifier
      .shadow(
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        clip = false
      )
      .clip(RoundedCornerShape(12.dp))
      .background(ColorPalette.OnPrimary)
      .size(40.dp)
      .clickable { onClick() },
    contentAlignment = Alignment.Center
  ) {
    content()
  }
}

@Composable
private fun BottomSection(
  onNavigateDetailDataPeserta: (String) -> Unit
) {
  val columnWeights = listOf(0.1f, 0.6f, 0.3f)
  val rows = mockDataPesertaData.mapIndexed { i, data ->
    listOf(
      (i + 1).toString(),
      data,
      "Lihat"
    )
  }
  Table(
    columnWeights = columnWeights,
    rows = rows,
    headers = headersPesertaData,
    onNavigateDetailDataPeserta = onNavigateDetailDataPeserta
  )
}

@Composable
private fun Table(
  columnWeights : List<Float>,
  rows : List<List<String>>,
  headers : List<String>,
  onNavigateDetailDataPeserta: (String) -> Unit
) {
  Column(
    modifier = Modifier

  ) {
    TableRow(isHeader = true) {
      headers.forEachIndexed { i, cell ->
        TableCell(
          modifier = Modifier
            .weight(columnWeights.getOrElse(i) { 1f }),
          isHeader = true,
          value = cell,
        )
      }
    }

    Column(
      modifier = Modifier
        .shadow(
          elevation = 4.dp,
        )
        .background(ColorPalette.Monochrome100)
        .padding(
          vertical = 16.dp,
          horizontal = 8.dp
        ),
      verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
      rows.forEach {
        Column(
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          TableRow {
            it.forEachIndexed { i, cell ->
              TableCell(
                modifier = Modifier.weight(columnWeights.getOrElse(i) { 1f }),
                value = cell,
                isButton = cell == "Lihat",
                onClick = {onNavigateDetailDataPeserta(it)}
              )
            }
          }
        }
      }

    }

  }
}

@Composable
private fun TableCell(
  modifier : Modifier,
  isHeader : Boolean = false,
  value : String,
  isButton : Boolean = false,
  onClick : (String) -> Unit = {}
) {
  val color = if (isHeader) ColorPalette.Monochrome500 else ColorPalette.Black
  val style = if (isHeader) StyledText.MobileXsRegular else StyledText.MobileSmallMedium

  Box(
    modifier = modifier.fillMaxHeight(),
    contentAlignment = Alignment.Center
  ) {
    if (isButton) {
      PrimaryButton(
        style = StyledText.MobileSmallMedium,
        color = ColorPalette.PrimaryColor700,
        onClick = { onClick(value) },
        text = value,
      )
    } else {
      Text(
        text = value,
        style = style,
        color = color,
        modifier = Modifier
          .fillMaxWidth(),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
      )
    }
  }
}

@Composable
private fun TableRow(
  isHeader : Boolean = false,
  content : @Composable () -> Unit
) {
  val background = if (isHeader) ColorPalette.OnPrimary else ColorPalette.PrimaryColor100
  val shadowElevation = if (isHeader) 4.dp else 0.dp
  val shape =
    if (isHeader)
      RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    else
      RoundedCornerShape(54.dp)

  val padding : PaddingValues = if (isHeader) PaddingValues(
    vertical = 20.dp,
    horizontal = 26.dp
  ) else PaddingValues(
    horizontal = 26.dp,
    vertical = 12.dp
  )


  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .shadow(
        elevation = shadowElevation,
        shape = shape,
        clip = false
      )
      .background(background, shape = shape)
      .clip(shape)
      .fillMaxWidth()
      .padding(padding)
  ) {
    content()
  }
}


private val headersPesertaData = listOf(
  "No",
  "Nama Lembaga",
  "Lihat"
)

private val mockDataPesertaData = listOf(
  "Bakrie Center Foundation",
  "YAMALI",
  "Inisiatif Lampung Sehat",
  "STPI Penabulu Banten",
  "Perkumpulan Masyarakat Sriwijaya Sehat Sumatera Selatan",
  "STPI Penabulu DKI Jakarta",
  "Yayasan Rekat Peduli Indonesia",
  "Yayasan Bekantan",
  "Yayasan Kesehatan Masyarakat Indonesia",
  "Yayasan Kesehatan Masyarakat Indonesia",
  "Yayasan Kesehatan Masyarakat Indonesia"
)


