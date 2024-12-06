package com.example.slicingbcf.implementation.mentor.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.Batch
import com.example.slicingbcf.data.local.Mentor
import com.example.slicingbcf.data.local.listBatch
import com.example.slicingbcf.data.local.mentor
import com.example.slicingbcf.implementation.mentor.laporan.GenericTable
import com.example.slicingbcf.implementation.mentor.laporan.LaporanPenilaianPesertaConstant.Companion.headerLembagaPenilaianPeserta
import com.example.slicingbcf.implementation.mentor.laporan.LembagaTableRow
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator

@Composable
fun ProfilMentorScreen(
  modifier : Modifier = Modifier,
  viewModel : ProfilMentorViewModel = hiltViewModel(),
  onEditIconClick : () -> Unit
) {
  val state by viewModel.state.collectAsState()
  when {
    state.isLoading -> {
      LoadingCircularProgressIndicator()
    }

    state.error != null -> {
      ErrorWithReload(
        modifier = modifier,
        errorMessage = state.error,
        onRetry = {
          viewModel.onEvent(ProfilMentorEvent.Reload)
        }
      )
    }

    else -> {
      LazyColumn(
        modifier = modifier
          .fillMaxSize()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        item {
          TopSection(
            mentor = mentor,
            onEditIconClick = { onEditIconClick() }
          )
        }
        item {
          HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
          )
        }
        items(state.batches) { batch ->
          Text(
            text = batch.namaBatch,
            style = StyledText.MobileMediumSemibold,
            color = ColorPalette.PrimaryColor700
          )
          Spacer(modifier = Modifier.height(8.dp))
          BatchInformation(batches = batch)
          Spacer(modifier = Modifier.height(8.dp))
          GenericTable(
            headers = headerLembagaPenilaianPeserta,
            items = batch.listLembaga,
            rowContent = { index, lembaga ->
              LembagaTableRow(
                index = index + 1, lembaga = lembaga
              )
            },
            showTotalAndPagination = false
          )

        }
      }

    }

  }

}


@Composable
fun TopSection(
  mentor : Mentor,
  onEditIconClick : () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    HeaderScreen(
      onEditIconClick = { onEditIconClick() }
    )
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
    ) {
      Image(
        painter = painterResource(id = R.drawable.sampul),
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
          painter = painterResource(id = R.drawable.avatar),
          contentDescription = "Profile Image",
          modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(4.dp, Color.White, CircleShape)
            .background(ColorPalette.Monochrome300)
        )
      }
    }
    Spacer(modifier = Modifier.height(60.dp))
    ProfileItem(
      label = "Nama Lengkap",
      value = mentor.namaLengkap
    )
    ProfileItem(
      label = "Tanggal Lahir",
      value = mentor.tanggalLahir
    )
    ProfileItem(
      label = "Jenis Kelamin",
      value = mentor.jenisKelamin
    )
    ProfileItem(
      label = "Email",
      value = mentor.email
    )
    ProfileItem(
      label = "Nomor HP",
      value = mentor.nomorHP
    )
    ProfileItem(
      label = "Pendidikan Terakhir",
      value = mentor.pendidikanTerakhir
    )
    ProfileItem(
      label = "Jurusan",
      value = mentor.jurusan
    )
    ProfileItem(
      label = "Pekerjaan",
      value = mentor.pekerjaan
    )
    ProfileItem(
      label = "Instansi",
      value = mentor.instansi
    )
    ProfileItem(
      label = "Kategori Mentor",
      value = mentor.kategoriMentor
    )
    Spacer(modifier = Modifier.height(16.dp))
  }
}

@Composable
fun BatchInformation(
  batches : Batch
) {
  Column() {
    ProfileItem(
      label = "Kategori Mentor",
      value = listBatch[0].kategoriMentor
    )
    ProfileItem(
      label = "Cluster",
      value = listBatch[0].cluster
    )
    ProfileItem(
      label = "Fokus Isu",
      value = listBatch[0].fokusIsu
    )
  }
}

@Composable
fun HeaderScreen(
  onEditIconClick : () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Profil Mentor",
      style = StyledText.MobileLargeSemibold
    )
    SmallFloatingActionButton(
      onClick = { onEditIconClick() },
      modifier = Modifier.size(56.dp),
      containerColor = ColorPalette.PrimaryColor100,
      elevation = FloatingActionButtonDefaults.elevation(0.dp)
    ) {
      Image(
        painter = painterResource(id = R.drawable.edit),
        contentDescription = "Edit",
        modifier = Modifier.size(24.dp)
      )
    }
  }
}

@Composable
fun ProfileItem(
  label : String,
  value : String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Text(
      text = label,
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


@Preview(showSystemUi = true)
@Composable
fun ProfilMentorScreenPreview() {
    ProfilMentorScreen(
        onEditIconClick = {}
    )
}