package com.example.slicingbcf.interceptor.helper

import com.example.slicingbcf.data.local.preferences.UserRemotePreferences
import com.example.slicingbcf.data.remote.api.RefreshTokenService
import com.example.slicingbcf.data.remote.request.auth.RefreshTokenRequest
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
  private val userRemotePreferences: UserRemotePreferences,
  private val refreshTokenService: RefreshTokenService
) {

  suspend fun refreshToken(): String? {
    val refreshToken = userRemotePreferences.getRefreshToken().firstOrNull()
    val accessToken = userRemotePreferences.getAccessToken().firstOrNull()

    if (!refreshToken.isNullOrEmpty() && !accessToken.isNullOrEmpty()) {
      try {
        val response = refreshTokenService.getNewAccessToken(
          "Bearer $accessToken",
          RefreshTokenRequest(refreshToken)
        )
        if (response.isSuccessful) {
          val newAccessToken = response.body()?.data?.accessToken
          if (!newAccessToken.isNullOrEmpty()) {
            userRemotePreferences.saveUserAccessToken(newAccessToken)
            return newAccessToken
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
    return null
  }
}
