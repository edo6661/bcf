package com.example.slicingbcf.interceptor.helper

import com.example.slicingbcf.data.local.preferences.UserRemotePreferences
import com.example.slicingbcf.data.remote.api.ApiService
import com.example.slicingbcf.data.remote.request.auth.RefreshTokenRequest
import javax.inject.Inject

class TokenRefresher @Inject constructor(
  private val apiService: ApiService,
  private val userRemotePreferences: UserRemotePreferences
) {

  suspend fun refreshAccessToken(refreshToken: String): String? {
    val result = apiService.getNewAccessToken(RefreshTokenRequest(refreshToken))
    return if (result.isSuccessful) {
      val newAccessToken = result.body()?.data?.accessToken
      newAccessToken?.let {
        userRemotePreferences.saveUserAccessToken(it)
      }
      newAccessToken
    } else null
  }
}
