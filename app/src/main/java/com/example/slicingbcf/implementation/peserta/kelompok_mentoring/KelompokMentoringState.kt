package com.example.slicingbcf.implementation.peserta.kelompok_mentoring

import com.example.slicingbcf.data.local.KelompokMentoring

data class KelompokMentoringState(
  val isLoading: Boolean = false,
  val kelompokMentoringList: List<KelompokMentoring> = emptyList(),
  val currentTabIndex: Int = 0,
  val error: String? = null
)

sealed class KelompokMentoringEvent {
  data class TabChanged(val tabIndex: Int) : KelompokMentoringEvent()
  object ReloadData : KelompokMentoringEvent()
}
