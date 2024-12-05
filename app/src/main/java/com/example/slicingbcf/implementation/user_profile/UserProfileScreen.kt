package com.example.slicingbcf.implementation.user_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.slicingbcf.ui.shared.state.LoadingCircularProgressIndicator

@Composable
fun UserProfileScreen(
  modifier : Modifier = Modifier,
  viewModel : UserProfileViewModel = hiltViewModel()
) {
  val state by viewModel.uiState.collectAsState()

  when {
    state.isLoading -> {
      LoadingCircularProgressIndicator()
    }
    state.error != null -> {
      Text(
        text = state.error!!,
        modifier = modifier.padding(
          horizontal = 16.dp,
          vertical = 24.dp
        )
      )
    }
    else -> {
      Column(
        modifier = modifier.padding(
          horizontal = 16.dp,
          vertical = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(text = "Name: ${state.user?.username ?: "Unknown"}")
        Text(text = "Email: ${state.user?.email ?: "Unknown"}")
      }
    }
  }


}