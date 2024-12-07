package com.example.slicingbcf.implementation.mentor.profil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.listBatch
import com.example.slicingbcf.data.local.mentor
import com.example.slicingbcf.ui.shared.dropdown.DropdownText
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextField
import com.example.slicingbcf.ui.shared.textfield.CustomOutlinedTextFieldDropdown

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun UbahProfilMentorScreenPreview() {
    UbahProfilMentorScreen(
        id = "1"
    )
}

@Composable
fun UbahProfilMentorScreen(
    modifier: Modifier = Modifier,
    id: String,
    viewModel: UbahProfilMentorViewModel = hiltViewModel()
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
                    viewModel.onEvent(UbahProfilMentorEvent.Reload)
                }
            )
        }
        else -> {
            Column(
                modifier = modifier.padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                Text(
                    text = "Ubah Data Mentor",
                    style = StyledText.MobileLargeSemibold
                )
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                EditImageProfil()
                EditDataDiriMentor(
                    state = state,
                    onEvent = { viewModel.onEvent(it) }
                )
                EditLatarBelakangMentor(
                    state = state,
                    onEvent = { viewModel.onEvent(it) }
                )
                EditBatchInformation(
                    state = state,
                    onEvent = { viewModel.onEvent(it) }
                )
                SaveCancelButton()
            }
        }
    }
}

@Composable
private fun EditImageProfil() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
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
            FloatingActionButton(
                onClick = { },
                modifier = Modifier.run {
                    align(Alignment.TopEnd)
                                .size(40.dp)
                                .offset(x = (-8).dp, y = 8.dp)
                },
                containerColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit Icon",
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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

                OutlinedButton(
                    onClick = { /* Handle Change Photo */ },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(40.dp),
                    border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = ColorPalette.PrimaryColor700
                    )
                ) {
                    Text(
                        text = "Ubah Foto",
                        style = StyledText.MobileBaseSemibold
                    )
                }
            }
        }
    }
}


@Composable
fun EditDataDiriMentor(
    state: UbahProfilMentorState,
    onEvent: (UbahProfilMentorEvent) -> Unit
) {
    var expandedJenisKelamin by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Data Diri",
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
        )
        CustomOutlinedTextField(
            label = "Nama Lengkap",
            value = state.namaLengkap,
            onValueChange = {
                onEvent(UbahProfilMentorEvent.NamaLengkapChanged(it))
            },
            error = state.namaLengkapError,
            placeholder = state.namaLengkap,
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )
        CustomOutlinedTextField(
            label = "Tanggal Lahir",
            value = state.tanggalLahir,
            onValueChange = {
                onEvent(UbahProfilMentorEvent.TanggalLahirChanged(it))
            },
            error = state.tanggalLahirError,
            placeholder = state.tanggalLahir,
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )
        CustomOutlinedTextFieldDropdown(
            value = state.jenisKelamin,
            onValueChange = {
                onEvent(UbahProfilMentorEvent.JenisKelaminChanged(it))
            },
            expanded = expandedJenisKelamin,
            error = state.jenisKelaminError,
            onChangeExpanded = { expandedJenisKelamin = it },
            label = "Jenis Kelamin",
            dropdownItems = listOf("Pria", "Wanita"),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
        )
        CustomOutlinedTextField(
            label = "Email",
            value = state.email,
            onValueChange = {
                onEvent(UbahProfilMentorEvent.EmailChanged(it))
            },
            error = state.emailError,
            placeholder = state.email,
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )
        CustomOutlinedTextField(
            label = "Nomor HP",
            value = state.nomorHP,
            onValueChange = {
                onEvent(UbahProfilMentorEvent.NomorHPChanged(it))
            },
            error = state.nomorHPError,
            placeholder = state.nomorHP,
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun EditLatarBelakangMentor(
    state: UbahProfilMentorState,
    onEvent: (UbahProfilMentorEvent) -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Latar Belakang",
            style = StyledText.MobileBaseSemibold,
            textAlign = TextAlign.Left,
            color = ColorPalette.PrimaryColor700,
        )
        CustomOutlinedTextField(
            label = "Pendidikan Terakhir",
            value = state.pendidikanTerakhir,
            onValueChange = {
                onEvent(UbahProfilMentorEvent.PendidikanTerakhirChanged(it))
            },
            error = state.pendidikanTerakhirError,
            placeholder = state.pendidikanTerakhir,
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )
        CustomOutlinedTextField(
            label = "Jurusan",
            value = state.jurusan,
            onValueChange = {
                onEvent(UbahProfilMentorEvent.JurusanChanged(it))
            },
            error = state.jurusanError,
            placeholder = mentor.jurusan,
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )
        CustomOutlinedTextField(
            label = "Pekerjaan",
            value = state.pekerjaan,
            onValueChange = {
                onEvent(UbahProfilMentorEvent.PekerjaanChanged(it))
            },
            error = state.pekerjaanError,
            placeholder = state.pekerjaan,
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )
        CustomOutlinedTextField(
            label = "Instansi",
            value = state.instansi,
            onValueChange = {
                onEvent(UbahProfilMentorEvent.InstansiChanged(it))
            },
            error = state.instansiError,
            placeholder = state.instansi,
            modifier = Modifier.fillMaxWidth(),
            labelDefaultColor = ColorPalette.Monochrome400,
            labelFocusedColor = ColorPalette.PrimaryColor700,
            borderColor = ColorPalette.Outline,
            rounded = 40,
        )

        var expanded by remember { mutableStateOf(false) }
        var selectedItems by remember { mutableStateOf(listOf("Batch 3", "Batch 4")) }
        val availableItems = listOf("Batch 1", "Batch 2", "Batch 5", "Batch 3", "Batch 4")

        DropdownWithChips(
            selectedItems = selectedItems,
            onItemRemoved = { item -> selectedItems = selectedItems - item },
            availableItems = availableItems.filterNot { it in selectedItems },
            onItemSelected = { item -> selectedItems = selectedItems + item },
            expanded = expanded,
            onChangeExpanded = { expanded = it },
            label = "Batch Mentor"
        )

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        )

    }
}

@Composable
fun EditBatchInformation(
    state: UbahProfilMentorState,
    onEvent: (UbahProfilMentorEvent) -> Unit
){

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        listBatch.forEach{ batch ->
            var kategoriMentor by remember { mutableStateOf(batch.kategoriMentor) }
            var expandedKategoriMentor by remember { mutableStateOf(false) }

            Text(
                text = batch.namaBatch,
                style = StyledText.MobileBaseSemibold,
                textAlign = TextAlign.Left,
                color = ColorPalette.PrimaryColor700,
            )
            CustomOutlinedTextFieldDropdown(
                value = state.kategoriMentor,
                onValueChange = {
                    kategoriMentor = it
                },
                expanded = expandedKategoriMentor,
                onChangeExpanded = { expandedKategoriMentor = it },
                error = state.kategoriMentorError,
                label = "Kategori Mentor",
                dropdownItems = listOf("Desain Program", "Lainnya"),
                labelDefaultColor = ColorPalette.Monochrome400,
                labelFocusedColor = ColorPalette.PrimaryColor700,
            )
            CustomOutlinedTextField(
                label = "Cluster Mentor",
                value = state.cluster,
                onValueChange = {
                    onEvent(UbahProfilMentorEvent.ClusterChanged(it))
                },
                error = state.clusterError,
                placeholder = batch.cluster,
                modifier = Modifier.fillMaxWidth(),
                labelDefaultColor = ColorPalette.Monochrome400,
                labelFocusedColor = ColorPalette.PrimaryColor700,
                borderColor = ColorPalette.Outline,
                rounded = 40,
            )
//            CustomOutlinedTextField(
//                label = "Fokus Isu",
//                value = batch.fokusIsu,
//                onValueChange = {
//                    onEvent(UbahProfilMentorEvent.FokusIsuChanged(it))
//                },
//                error = state.fokusIsuError,
//                placeholder = batch.fokusIsu,
//                modifier = Modifier.fillMaxWidth(),
//                labelDefaultColor = ColorPalette.Monochrome400,
//                labelFocusedColor = ColorPalette.PrimaryColor700,
//                borderColor = ColorPalette.Outline,
//                rounded = 40,
//            )
            FokusIsuChips(
                initialText = state.fokusIsu
            )
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun SaveCancelButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = { TODO() },
            modifier = Modifier
                .height(40.dp),
            border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = ColorPalette.PrimaryColor700,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Simpan",
                style = StyledText.MobileBaseRegular
            )
        }
        OutlinedButton(
            onClick = { TODO() },
            modifier = Modifier
                .height(40.dp),
            border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = ColorPalette.PrimaryColor700
            )
        ) {
            Text(
                text = "Batal",
                style = StyledText.MobileBaseRegular
            )
        }
    }
}

@Composable
fun DropdownWithChips(
    selectedItems: List<String>,
    onItemRemoved: (String) -> Unit,
    availableItems: List<String>,
    onItemSelected: (String) -> Unit,
    expanded: Boolean,
    onChangeExpanded: (Boolean) -> Unit,
    label: String
) {
    var isFocused by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = {
                Text(label)
            },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Drop Down",
                    tint = ColorPalette.PrimaryColor700,
                    modifier = Modifier.clickable {
                        onChangeExpanded(!expanded)
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .onFocusChanged { isFocused = it.isFocused },
            shape = MaterialTheme.shapes.extraLarge,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = ColorPalette.Outline,
                focusedIndicatorColor = ColorPalette.Outline
            ),
            textStyle = TextStyle(
                color = if (isFocused) ColorPalette.Monochrome800 else ColorPalette.Monochrome300
            ),
            singleLine = true,
            readOnly = false,

            leadingIcon = {
                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    selectedItems.forEach { item ->
                        Chip(
                            text = item,
                            onRemove = { onItemRemoved(item) }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        )

        if (expanded) {
            DropdownText(
                expanded = expanded,
                onExpandedChange = {
                    onChangeExpanded(it)
                },
                onItemSelected = { item ->
                    onItemSelected(item)
                    onChangeExpanded(false)
                    text = ""
                },
                items = availableItems,
                currentItem = text
            )
        }
    }
}

@Composable
fun FokusIsuChips(
    initialText: String,
) {
    var selectedItems by remember { mutableStateOf(initialText.split(", ").filter { it.isNotBlank() }) }

    Column {
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Fokus Isu") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
            readOnly = true,
            leadingIcon = {
                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    selectedItems.forEach { item ->
                        Chip(
                            text = item,
                            onRemove = { selectedItems = selectedItems - item }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        )
    }
}

@Composable
fun Chip(
    text: String,
    onRemove: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = ColorPalette.PrimaryColor100,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = text,
                style = StyledText.MobileSmallRegular,
                color = ColorPalette.PrimaryColor600
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = ColorPalette.PrimaryColor700,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemove() }
            )
        }
    }
}
