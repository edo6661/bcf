package com.example.slicingbcf.implementation.peserta.profil.profil_lembaga

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.ProfilLembaga
import com.example.slicingbcf.data.local.WilayahJangkauan
import com.example.slicingbcf.data.local.profilLembaga
import com.example.slicingbcf.R.drawable
import androidx.compose.runtime.setValue

@Preview(showSystemUi = true)
@Composable
fun ProfilLembagaScreen(
//    profile: ProfilLembaga,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        TopSection(profile = profilLembaga[0])
        BottomSection(profile = profilLembaga[0])
    }
}

@Composable
fun TopSection(profile: ProfilLembaga) {
    var currentPage by remember { mutableStateOf(1) }
    val totalPages = 1

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        NavigationHeader(
            currentPage = currentPage,
            totalPages = totalPages,
            onPreviousClick = { if (currentPage > 1) currentPage-- },
            onNextClick = { if (currentPage < totalPages) currentPage++ }
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = drawable.sampul),
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 40.dp)
            ) {
                Image(
                    painter = painterResource(id = drawable.avatar_sampul),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(ColorPalette.Monochrome300)
                )
            }
        }

        Spacer(modifier = Modifier
            .height(60.dp)
        )

        ProfileInfoRow("Nama Lembaga", profile.name)
        ProfileInfoRow("Email Lembaga", profile.email)
        ProfileInfoRow("Alamat", profile.address)
        ProfileInfoRow("Jenis Lembaga", profile.type)
        ProfileInfoRow("Jenis Cluster", profile.cluster)
        ProfileInfoRow("Fokus Isu", profile.focusIssue)

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileInfoRow("Alasan Mengikuti Program", profile.programReason)
        ProfileInfoRow("Cakupan/Jangkauan Program", profile.programCoverage)

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PdfLinkRow("Profil Perusahaan", profile.companyProfilePdf)
        PdfLinkRow("Proposal Program", profile.programProposalPdf)
    }
}

@Composable
fun BottomSection(profile: ProfilLembaga) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Text(
            text = "Wilayah Jangkauan Program",
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        HeaderRow(wilayah_headers)

        profile.wilayahJangkauan.forEach { wilayah ->
            WilayahRow(item = wilayah)
        }
    }
}

@Composable
fun NavigationHeader(
    currentPage: Int,
    totalPages: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Profil Lembaga",
            style = StyledText.MobileLargeSemibold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Previous Page",
                tint = if (currentPage > 1) Color.Black else Color.Gray,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(enabled = currentPage > 1) { onPreviousClick() }
            )
            Text(
                text = "$currentPage dari $totalPages",
                style = StyledText.MobileSmallRegular,
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Next Page",
                tint = if (currentPage < totalPages) Color.Black else Color.Gray,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(enabled = currentPage < totalPages) { onNextClick() }
            )
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label",
            style = StyledText.MobileSmallSemibold,
            modifier = Modifier.weight(1.3f)
        )
        Text(
            text = ":",
            style = StyledText.MobileSmallSemibold,
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = value,
            style = StyledText.MobileSmallRegular,
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun PdfLinkRow(label: String, pdfUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label",
            style = StyledText.MobileSmallSemibold,
            modifier = Modifier.weight(1.3f)
        )
        Text(
            text = ":",
            style = StyledText.MobileSmallSemibold,
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = pdfUrl.substringAfterLast('/'),
            style = StyledText.MobileSmallRegular.copy(color = ColorPalette.PrimaryColor700),
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun HeaderRow(headers: List<Header>) {
    Row(
        modifier = Modifier
            .background(
                color = ColorPalette.Success100,
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
            )
            .border(
                BorderStroke(1.dp, ColorPalette.Monochrome300),
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
            )
    ) {
        headers.forEach { header ->
            TableCell(
                text = header.name,
                isHeader = true,
                weight = header.weight,
            )
        }
    }
}

@Composable
fun TableCell(
    text: String,
    isHeader: Boolean = false,
    weight: Float,
    color: Color = ColorPalette.Monochrome900,
    content: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .width(120.dp * weight)
            .padding(vertical = 12.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (content != null) {
            content()
        } else {
            Text(
                text = text,
                style = if (isHeader) StyledText.MobileXsBold else StyledText.MobileXsRegular,
                color = color
            )
        }
    }
}

@Composable
fun WilayahRow(item: WilayahJangkauan) {
    Row(
        modifier = Modifier
            .background(ColorPalette.SurfaceContainerLowest)
            .border(BorderStroke(1.dp, ColorPalette.Monochrome300))
    ) {
        TableCell(text = item.no.toString(), weight = 0.5f)
        TableCell(text = item.provinsi, weight = 0.7f)
        TableCell(text = item.penerimaManfaat.toString(), weight = 1.3f)
        TableCell(
            text = "",
            weight = 0.5f
        ) {
            Icon(
                imageVector = Icons.Default.OpenInNew,
                contentDescription = "Open Link",
                tint = ColorPalette.PrimaryColor700,
                modifier = Modifier
                    .size(16.dp)
                    .padding(start = 4.dp)
                    .clickable { /* TODO: LOGIC URL */ }
            )
        }
    }
}

data class Header(
    val name: String,
    val weight: Float
)

val wilayah_headers = listOf(
    Header("No.", 0.5f),
    Header("Provinsi", 0.7f),
    Header("Penerima Manfaat", 1.3f),
    Header("Rincian", 0.5f)
)

