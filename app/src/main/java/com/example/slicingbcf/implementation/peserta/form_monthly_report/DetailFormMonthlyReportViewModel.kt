package com.example.slicingbcf.implementation.peserta.form_monthly_report

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailFormMonthlyReportViewModel @Inject constructor() : ViewModel() {

  private val _state = MutableStateFlow(FormMonthlyReportState())
  val state = _state.asStateFlow()

  @Suppress("t")
  fun onEvent(event: FormMonthlyReportEvent) {
    when (event) {
      is FormMonthlyReportEvent.UpdatePeningkatanPenerimaManfaat -> {
        _state.update { it.copy(peningkatanPenerimaManfaatDariKegiatan = event.value) }
      }
      is FormMonthlyReportEvent.UpdateJumlahAngkaPenerimaanManfaat -> {
        _state.update {
          it.copy(
            jumlahAngkaPenerimaanManfaat = it.jumlahAngkaPenerimaanManfaat.mapIndexed { index, jumlahAngkaPenerimaanManfaat ->
              if (index == event.index) event.field else jumlahAngkaPenerimaanManfaat

            }
          )
        }
      }
      is FormMonthlyReportEvent.AddJumlahAngkaPenerimaanManfaat -> {
        _state.update { it.copy(jumlahAngkaPenerimaanManfaat = it.jumlahAngkaPenerimaanManfaat + event.newField) }
      }
      is FormMonthlyReportEvent.RemoveJumlahAngkaPenerimaanManfaat -> {
        _state.update { it.copy(jumlahAngkaPenerimaanManfaat =
          it.jumlahAngkaPenerimaanManfaat.dropLast(1)
        ) }
      }
      is FormMonthlyReportEvent.UpdateDonasi -> {
        _state.update {
          it.copy(pencapaianPencarianDanaDenganDonasi = it.pencapaianPencarianDanaDenganDonasi.mapIndexed { index, donasi ->
            if (index == event.index) event.donasi else donasi
          })
        }
      }
      is FormMonthlyReportEvent.UpdateDonasiSponsorship -> {
        _state.update {
          it.copy(pencapaianPencarianDanaDenganSponsorship = it.pencapaianPencarianDanaDenganSponsorship.mapIndexed { index, donasi ->
            if (index == event.index) event.donasi else donasi
          })
        }
      }
      is FormMonthlyReportEvent.UpdateDonasiMitra -> {
        _state.update {
          it.copy(
            pencapaianPencarianDanaDenganDonasiMitra = it.pencapaianPencarianDanaDenganDonasiMitra.mapIndexed { index, donasiMitra ->
              if (index == event.index) event.donasiMitra else donasiMitra
            }
          )
        }
      }
      is FormMonthlyReportEvent.UpdateProsesOpenRecruitmentRelawan -> {
        _state.update { it.copy(prosesOpenRecruitmentRelawan = event.proses) }
      }
      is FormMonthlyReportEvent.UpdateProsesRancanganDesainProgram -> {
        _state.update { it.copy(prosesRancanganDesainProgram = event.value) }
      }
      is FormMonthlyReportEvent.UpdateTantanganHambatan -> {
        _state.update { it.copy(tantanganHambatanMerancangDesainProgram = event.value) }
      }
      is FormMonthlyReportEvent.UpdateMappingStakeholder -> {

        _state.update {
          it.copy(
            mappingStakeholderLembagaYangDitargetkanUntukPerjalinanKerjasama = it.mappingStakeholderLembagaYangDitargetkanUntukPerjalinanKerjasama.mapIndexed { index, kerjaSama ->
              if (index == event.index) event.field else kerjaSama
            }
          )
        }
      }
      is FormMonthlyReportEvent.RemoveLastMappingStakeholder -> {
        _state.update { it.copy(mappingStakeholderLembagaYangDitargetkanUntukPerjalinanKerjasama = it.mappingStakeholderLembagaYangDitargetkanUntukPerjalinanKerjasama.dropLast(1)) }
      }

      is FormMonthlyReportEvent.AddMappingStakeholder -> {
        _state.update {
          it.copy(mappingStakeholderLembagaYangDitargetkanUntukPerjalinanKerjasama = it.mappingStakeholderLembagaYangDitargetkanUntukPerjalinanKerjasama + event.newField)
        }
      }

      is FormMonthlyReportEvent.UpdateRekapanPemasaranSosialMedia -> {
        _state.update { it.copy(rekapanPemasaranSosialMedia =
          it.rekapanPemasaranSosialMedia.mapIndexed { index, pemasaranSosialMedia ->
            if (index == event.index) event.socialMedia else pemasaranSosialMedia
          }
        ) }
      }
      is FormMonthlyReportEvent.RemoveLastRekapanPemasaranSosialMedia -> {
        _state.update { it.copy(rekapanPemasaranSosialMedia = it.rekapanPemasaranSosialMedia.dropLast(1)) }
      }
      is FormMonthlyReportEvent.AddRekapanPemasaranSosialMedia -> {
        _state.update { it.copy(rekapanPemasaranSosialMedia = it.rekapanPemasaranSosialMedia + event.newField) }
      }
      is FormMonthlyReportEvent.ClearForm -> {
        _state.update { FormMonthlyReportState() }
      }
      is FormMonthlyReportEvent.Submit -> {
        Log.d("FormMonthlyReportViewModel", _state.value.toString())
      }
    }
  }

}
