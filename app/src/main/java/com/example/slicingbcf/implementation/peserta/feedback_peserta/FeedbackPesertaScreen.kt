package com.example.slicingbcf.implementation.peserta.feedback_peserta

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.EvaluasiLembaga
import com.example.slicingbcf.data.local.EvaluasiMentoring
import com.example.slicingbcf.data.local.JadwalMentoring
import com.example.slicingbcf.data.local.JawabanPertanyaan
import com.example.slicingbcf.data.local.KepuasanMentoring
import com.example.slicingbcf.data.local.dokumentasiMentoring
import com.example.slicingbcf.data.local.evaluasiLembaga
import com.example.slicingbcf.data.local.evaluasiMentoring
import com.example.slicingbcf.data.local.jadwalMentoring
import com.example.slicingbcf.data.local.jawabanMentoring
import com.example.slicingbcf.data.local.kepuasanMentoring
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
@Preview(showSystemUi = true)
fun FeedbackPesertaScreen(
    modifier : Modifier = Modifier
){
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(40.dp),

        ) {
        TopSection()
        BottomSection()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSection() {
    var currentTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Umpan Balik Peserta",
            style = StyledText.MobileLargeSemibold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // tab layout Cluster - Desain Program
        PrimaryTabRow(
            selectedTabIndex = currentTabIndex,
            containerColor= Color.Transparent,
            contentColor = ColorPalette.PrimaryColor700) {
            Tab(
                selected = true,
                onClick = {
                    if (currentTabIndex != 0) currentTabIndex = 0

                },
                text = { Text(text = "Cluster", maxLines = 2, overflow = TextOverflow.Ellipsis) }
            )
            Tab(
                selected = false,
                onClick = {
                    if (currentTabIndex != 1) currentTabIndex = 1

                },
                text = {
                    Text(
                        text = "Desain Program",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }

        // Capaian Mentoring + tanggal
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Capaian Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
                color = ColorPalette.PrimaryColor700,
            )
            DateButtonCustom()
        }

        // Data peserta
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Bakrie Center Foundation",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                Column(
                    modifier = Modifier.size(132.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Nama Mentor",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Tipe Mentoring",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Batch",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Capaian Program",
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = ": " /* TODO bind database entity nama mentor*/,
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = ": " /* TODO bind database entity tipe mentoring*/,
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = ": " /* TODO bind database entity Batch*/,
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = ": " /* TODO bind database entity Capaian Program*/,
                        style = StyledText.MobileSmallRegular,
                        textAlign = TextAlign.Left,
                    )
                }
            }
        }

        // Tabel Jadwal Mentoring
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Jadwal Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(jadwal_headers)
                    jadwalMentoring.forEachIndexed { index, jadwalMentoring ->
                        JadwalMentoringRow(jadwalMentoring, index)
                    }
                }
            }
        }

         // Tabel Evaluasi Capaian Mentoring
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Evaluasi Capaian Mentoring",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(evaluasi_headers)
                    evaluasiMentoring.forEachIndexed { index, evaluasiMentoring ->
                        EvaluasiCapaianRow(evaluasiMentoring, index)
                    }
                }
            }
        }

        // Tabel Evaluasi Lembaga
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Evaluasi Lembaga",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(evaluasi_headers)
                    evaluasiLembaga.forEachIndexed { index, evaluasiLembaga ->
                        EvaluasiLembagaRow(evaluasiLembaga, index)
                    }
                }
            }
        }

        // Tabel Kepuasan Mentoring
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Evaluasi Lembaga",
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(evaluasi_headers)
                    kepuasanMentoring.forEachIndexed { index, kepuasanMentoring ->
                        KepuasanMentoringRow(kepuasanMentoring, index)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            jawabanMentoring.forEach { pertanyaan ->
                JawabanPertanyaanRow(pertanyaan)
            }

            DokumentasiMentoringSection()
        }
    }
}

@Composable
fun DateButtonCustom() {
    OutlinedButton(
        onClick = { /*TODO logika klik*/ },
        modifier = Modifier
            .height(35.dp),
        shape = MaterialTheme.shapes.extraLarge,
        border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "1 Februari 2023", /*TODO masukin tanggal yang sesuai*/
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.PrimaryColor700
            )
            Icon(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = "Next",
                tint = ColorPalette.PrimaryColor700,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun BottomSection() {
}

@Composable
fun HeaderRow(headers : List<Header>) {
    Row(
        modifier = Modifier
            .background(
                color = ColorPalette.Monochrome150,
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
            )
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
            )
    ){
        headers.forEach { header ->
            TableCell(
                text = header.name,
                isHeader = true,
                weight = header.weight
            )
        }
    }
}


@Composable
fun JadwalMentoringRow(jadwalMentoring : JadwalMentoring, index : Int) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH.mm")
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale("id", "ID"))

    Row(
        modifier = Modifier
            .background(ColorPalette.SurfaceContainerLowest)
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RectangleShape
            )
    ) {
        TableCell(
            text = jadwalMentoring.no.toString(),
            weight = 0.5f
        )
        TableCell(
            text = jadwalMentoring.tanggal.format(dateFormatter),
            weight = 1.5f
        )
        TableCell(
            text = "${jadwalMentoring.waktuMulai.format(timeFormatter)} - ${jadwalMentoring.waktuSelesai.format(timeFormatter)}",
            weight = 1f
        )
    }
}

@Composable
fun EvaluasiCapaianRow(evaluasiMentoring: EvaluasiMentoring, index : Int) {
    Row(
        modifier = Modifier
            .background(ColorPalette.SurfaceContainerLowest)
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RectangleShape
            )
    ) {
        TableCell(
            text = evaluasiMentoring.no.toString(),
            weight = 0.5f
        )
        TableCell(
            text = evaluasiMentoring.aspek_penilaian,
            weight = 1.7f
        )
        TableCell(
            text = evaluasiMentoring.penilaian,
            weight = 0.8f
        )
    }
}

@Composable
fun EvaluasiLembagaRow(evaluasiLembaga: EvaluasiLembaga, index : Int) {
    Row(
        modifier = Modifier
            .background(ColorPalette.SurfaceContainerLowest)
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RectangleShape
            )
    ) {
        TableCell(
            text = evaluasiLembaga.no.toString(),
            weight = 0.5f
        )
        TableCell(
            text = evaluasiLembaga.aspek_penilaian,
            weight = 1.7f
        )
        TableCell(
            text = evaluasiLembaga.penilaian,
            weight = 0.8f
        )
    }
}

@Composable
fun KepuasanMentoringRow(kepuasanMentoring: KepuasanMentoring, index : Int) {
    Row(
        modifier = Modifier
            .background(ColorPalette.SurfaceContainerLowest)
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RectangleShape
            )
    ) {
        TableCell(
            text = kepuasanMentoring.no.toString(),
            weight = 0.5f
        )
        TableCell(
            text = kepuasanMentoring.aspek_penilaian,
            weight = 1.7f
        )
        TableCell(
            text = kepuasanMentoring.penilaian,
            weight = 0.8f
        )
    }
}

@Composable
fun TableCell(
    text : String,
    isHeader : Boolean = false,
    weight : Float,
    color : Color = ColorPalette.Monochrome900
) {
    Text(
        text = text,
        style = if (isHeader) StyledText.MobileXsBold else StyledText.MobileXsRegular,
        color = color,
//        textAlign = TextAlign.Center,
        modifier = Modifier
            .width(120.dp * weight)
            .padding(8.dp)
    )
}

@Composable
fun JawabanPertanyaanRow(pertanyaan: JawabanPertanyaan) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = pertanyaan.aspek,
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Justify
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, ColorPalette.Monochrome300, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = pertanyaan.jawaban,
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.Black,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun DokumentasiMentoringSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 56.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Dokumentasi Mentoring",
            style = StyledText.MobileBaseSemibold,
        )

        dokumentasiMentoring.forEach { dokumen ->
            OutlinedButton(
                onClick = { /* TODO: LOGIC ON CLICK  gaada ga si, kan ga di klik*/ },
                modifier = Modifier
                    .width(250.dp)
                    .height(45.dp),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, ColorPalette.PrimaryColor400),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.png_filetype),
                        contentDescription = "File PNG Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = dokumen.namaFile,
                        style = StyledText.MobileSmallRegular,
                        color = ColorPalette.Black,
                    )
                }
            }
        }
    }
}



data class Header(
    val name : String,
    val weight : Float
)

val jadwal_headers = listOf(
    Header("No.", 0.5f),
    Header("Tanggal", 1.5f),
    Header("Waktu", 1f)
)

val evaluasi_headers = listOf(
    Header("No.", 0.5f),
    Header("Aspek Penilaian", 1.7f),
    Header("Penilaian", 0.8f)
)