package com.example.slicingbcf.implementation.mentor.jadwal.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.DetailJadwal
import com.example.slicingbcf.data.local.detailJadwal

@Composable
fun DetailJadwalMentorScreen(
    modifier: Modifier,
    id : String
) {
    val detail = detailJadwal.find { it.id == id }

    println("id di terima : $id")
    if (detail != null) {
        TopSection(detailJadwal = detail)
    } else {
        Text(
            text = "Data TIDACK ADA",
            style = StyledText.MobileBaseRegular,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}

@Composable
fun TopSection(detailJadwal: DetailJadwal) {
    val currentContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        Text(
            text = "Detail Jadwal Kegiatan",
            style = StyledText.MobileLargeSemibold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailItem(title = "Judul Lembar Kerja", value = detailJadwal.title)
        DetailItem(title = "Tipe Kegiatan", value = detailJadwal.type)
        DetailItem(title = "Tanggal Kegiatan", value = detailJadwal.date.toString())
        DetailItem(
            title = "Waktu Kegiatan",
            value = "${detailJadwal.beginTime} - ${detailJadwal.endTime} WIB"
        )
        DetailItem(title = "Nama Pemateri Kegiatan", value = detailJadwal.speaker)
        DetailItem(title = "Deskripsi Agenda", value = detailJadwal.description)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tautan Lembar Kerja",
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = detailJadwal.link,
            style = StyledText.MobileSmallRegular,
            color = ColorPalette.PrimaryColor400,
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailJadwal.link))
                    currentContext.startActivity(intent)
                }
                .padding(vertical = 4.dp),
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(56.dp))
    }
}

@Composable
fun DetailItem(title: String, value: String) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Justify,
            color = ColorPalette.PrimaryColor700,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Text(
            text = value,
            style = StyledText.MobileSmallRegular,
            textAlign = TextAlign.Justify,
        )
    }
}