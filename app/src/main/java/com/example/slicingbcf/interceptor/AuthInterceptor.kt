package com.example.slicingbcf.interceptor

import com.example.slicingbcf.data.local.preferences.UserRemotePreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
  private val userRemotePreferences : UserRemotePreferences
) : Interceptor {

  override fun intercept(chain : Interceptor.Chain) : Response {
    val originalRequest = chain.request()
    val accessToken = runBlocking {
      userRemotePreferences.getAccessToken().firstOrNull()
    }
    return if (! accessToken.isNullOrEmpty()) {
      val newRequest = originalRequest.newBuilder()
        .header("Authorization", "Bearer $accessToken")
        .build()
      chain.proceed(newRequest)
    } else {
      chain.proceed(originalRequest)
    }
  }
}
