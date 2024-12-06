package com.example.slicingbcf.implementation.peserta.jadwal.mingguan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.common.UiState
import com.example.slicingbcf.data.local.detailJadwal
import com.example.slicingbcf.data.local.profilLembaga
import com.example.slicingbcf.implementation.mentor.jadwal.JadwalEvent
import com.example.slicingbcf.implementation.mentor.jadwal.JadwalViewModel
import com.example.slicingbcf.ui.shared.state.ErrorWithReload
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun JadwalMentoringMingguScreen(
    modifier: Modifier = Modifier,
    onNavigateMonthlyCalendar: (String) -> Unit = {},
    onNavigateDetailCalendar: (String) -> Unit = {},
    id : String,
    viewModel: JadwalViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val schedule = detailJadwal.groupBy { it.date }.mapValues { entry ->
        entry.value.map { (it.beginTime to it.endTime) to it.title }
    }

    when (state) {
        is UiState.Loading -> {
            LoadingCircularProgressIndicator()
        }
        is UiState.Success -> {
            TopSection(
                userName = profilLembaga.firstOrNull()?.name ?: "Pengguna",
                schedule = schedule,
                onNavigateMonthlyCalendar = onNavigateMonthlyCalendar,
                onNavigateDetailCalendar = onNavigateDetailCalendar
            )
        }
        is UiState.Error -> {
            val errorMessage = (state as UiState.Error).message
            ErrorWithReload(
                errorMessage = errorMessage,
                onRetry = {
                    viewModel.onEvent(JadwalEvent.ReloadData)
                }
            )
        }
    }
}

@Composable
fun TopSection(
    userName: String,
    schedule: Map<LocalDate, List<Pair<Pair<LocalTime, LocalTime>, String>>>,
    onNavigateMonthlyCalendar: (String) -> Unit,
    onNavigateDetailCalendar: (String) -> Unit
) {
    var selectedWeekStart by remember { mutableStateOf(LocalDate.now().withDayOfWeek(1)) }
    val today = LocalDate.now()
    val currentWeekDates = (0..6).map { selectedWeekStart.plusDays(it.toLong()) }
    val currentMonth = YearMonth.of(selectedWeekStart.year, selectedWeekStart.month)
    var expanded by remember { mutableStateOf(false) }
    var isWeeklyView by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Halo, $userName",
            style = StyledText.MobileLargeSemibold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Text(
            text = "Hari ini: ${today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id", "ID"))}, " +
                    "${today.dayOfMonth} ${today.month.getDisplayName(TextStyle.FULL, Locale("id", "ID"))} ${today.year}",
            style = StyledText.MobileBaseRegular,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            textAlign = TextAlign.Start,
            color = ColorPalette.Monochrome400
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { selectedWeekStart = today.withDayOfWeek(1) },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = ColorPalette.PrimaryColor700
                ),
                border = BorderStroke(1.dp, ColorPalette.PrimaryColor700),
                modifier = Modifier
                    .height(28.dp)
                    .width(72.dp),
                contentPadding = PaddingValues(horizontal = 1.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "Hari Ini",
                    style = StyledText.MobileXsRegular,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(-4.dp),
                modifier = Modifier.padding(0.dp)
            ) {
                IconButton(onClick = { selectedWeekStart = selectedWeekStart.minusWeeks(1) }) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Minggu Sebelumnya",
                        modifier = Modifier.size(10.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " ${currentMonth.year}",
                        style = StyledText.MobileSmallSemibold,
                    )
                    Text(
                        text = "${currentWeekDates.first().formatShort()} - ${currentWeekDates.last().formatShort()}",
                        style = StyledText.Mobile2xsRegular
                    )
                }

                IconButton(onClick = { selectedWeekStart = selectedWeekStart.plusWeeks(1) }) {
                    Icon(
                        Icons.Default.ArrowForwardIos,
                        contentDescription = "Minggu Berikutnya",
                        modifier = Modifier.size(10.dp)
                    )
                }
            }

            Box(
                Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
            ) {
                TextButton(onClick = { expanded = true }) {
                    Text(
                        text = if (isWeeklyView) "Hari" else "Bulan",
                        style = StyledText.MobileXsRegular,
                        color = ColorPalette.Black
                    )
                    Icon(
                        Icons.Default.ArrowRight,
                        contentDescription = "Dropdown for Month",
                        modifier = Modifier.size(24.dp),
                        tint = ColorPalette.Black
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(Color.White)
                        .border(BorderStroke(1.dp, ColorPalette.Monochrome200))
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Bulan",
                                style = StyledText.MobileSmallRegular
                            )
                        },
                        onClick = {
                            expanded = false
                            isWeeklyView = false
                            onNavigateMonthlyCalendar("1")
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Hari",
                                style = StyledText.MobileSmallRegular
                            )
                        },
                        onClick = {
                            expanded = false
                            isWeeklyView = true
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        WeeklyCalendarView(
            weekDates = currentWeekDates,
            schedule = schedule,
            onNavigateDetailCalendar = onNavigateDetailCalendar
        )
        Spacer(modifier = Modifier.height(56.dp))
    }
}

@Composable
fun WeeklyCalendarView(
    weekDates: List<LocalDate>,
    schedule: Map<LocalDate, List<Pair<Pair<LocalTime, LocalTime>, String>>>,
    onNavigateDetailCalendar: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(ColorPalette.Monochrome100)
        ) {
            Spacer(modifier = Modifier.width(48.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
            ) {
                weekDates.forEach { date ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(ColorPalette.Success100)
                            .border(1.dp, ColorPalette.Monochrome200),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("id", "ID"))}\n${date.dayOfMonth}",
                            textAlign = TextAlign.Center,
                            style = StyledText.MobileSmallRegular
                        )
                    }
                }
            }
        }

        (0..23).forEach { hour ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, ColorPalette.Monochrome200)
            ) {
                Text(
                    text = "${hour.toString().padStart(2, '0')}:00",
                    modifier = Modifier
                        .width(48.dp)
                        .padding(top = 16.dp),
                    style = StyledText.MobileXsRegular,
                    textAlign = TextAlign.Center
                )

                weekDates.forEach { date ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(Color.Transparent)
                            .border(1.dp, ColorPalette.Monochrome200)
                    ) {
                        val events = schedule[date]?.filter { event ->
                            val (start, end) = event.first
                            hour in start.hour until end.hour
                        }

                        events?.forEach { event ->
                            val (timeRange, title) = event
                            val (beginTime, endTime) = timeRange
                            val eventColor = detailJadwal.find { it.title == title && it.beginTime == beginTime && it.endTime == endTime }?.color?.let { Color(it) } ?: ColorPalette.Monochrome100
                            val eventTime = "${beginTime.formatTime()} - ${endTime.formatTime()} WIB"
                            val id = detailJadwal.find { it.title == title && it.beginTime == beginTime && it.endTime == endTime }?.id

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .padding(2.dp)
                                    .background(eventColor)
                                    .clickable {
                                        if (id != null) {
                                            onNavigateDetailCalendar(id)
                                        }
                                    }
                            ) {
                                Text(
                                    text = "$eventTime $title",
                                    style = StyledText.Mobile3xsRegular,
                                    color = Color.White,
                                    modifier = Modifier.padding(4.dp),
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun LocalDate.withDayOfWeek(day: Int): LocalDate {
    val adjustedDay = if (this.dayOfWeek.value == 7) 0 else this.dayOfWeek.value
    return this.minusDays((adjustedDay - (day % 7)).toLong())
}
fun LocalTime.formatTime(): String {
    return this.toString().substring(0, 5).replace(":", ".")
}
fun LocalDate.formatShort(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.monthValue.toString().padStart(2, '0')
    val year = this.year
    return "$day/$month/$year"
}