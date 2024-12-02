package com.example.slicingbcf.implementation.peserta.form_monthly_report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.laporan


@Preview(showSystemUi = true)
@Composable
fun AdaMonthlyReportScreen(
    onNavigateChangeReport: (Int) -> Unit = {},
    onNavigateRingkasan: (Int) -> Unit = {},
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        val judul = laporan[0].title
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            Text(
                text = "$judul Sudah Tersedia",
                style = StyledText.MobileLargeSemibold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start= 52.dp, end=52.dp, bottom = 16.dp),
                color = ColorPalette.PrimaryColor700
            )
            Image(
                painter = painterResource(id = R.drawable.laporan_tidak_ada),
                contentDescription = "",
                modifier = Modifier.size(320.dp)
            )

            Spacer(modifier = Modifier.height(56.dp))

            Button(
                onClick = { onNavigateRingkasan(1) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.PrimaryColor700),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Text(
                    "Lihat Ringkasan",
                    color = ColorPalette.OnPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onNavigateChangeReport(1) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.PrimaryColor100),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Text(
                    "Ubah Laporan",
                    color = ColorPalette.PrimaryColor700)
            }
        }
    }
}