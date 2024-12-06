package com.example.slicingbcf.interceptor

import com.example.slicingbcf.data.local.preferences.UserRemotePreferences
import com.example.slicingbcf.interceptor.helper.TokenManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
  private val userRemotePreferences: UserRemotePreferences,
  private val tokenManager: TokenManager
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()

    val accessToken = runBlocking { userRemotePreferences.getAccessToken().firstOrNull() }

    val requestWithAccessToken = if (!accessToken.isNullOrEmpty()) {
      originalRequest.newBuilder()
        .header("Authorization", "Bearer $accessToken")
        .build()
    } else originalRequest

    val response = chain.proceed(requestWithAccessToken)

    if (response.code == 401) {
      synchronized(this) {
        val newAccessToken = runBlocking { tokenManager.refreshToken() }
        if (!newAccessToken.isNullOrEmpty()) {
          val retryRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
          return chain.proceed(retryRequest)
        }
      }
    }

    return response
  }
}
