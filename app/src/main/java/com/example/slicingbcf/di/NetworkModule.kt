package com.example.slicingbcf.di

import com.example.slicingbcf.BuildConfig
import com.example.slicingbcf.data.remote.api.ApiService
import com.example.slicingbcf.data.remote.api.RefreshTokenService
import com.example.slicingbcf.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(
        if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
      )
      .addInterceptor(authInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(okHttpClient: OkHttpClient): ApiService {
    val retrofit = Retrofit.Builder()
      .baseUrl(BuildConfig.API_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
    return retrofit.create(ApiService::class.java)
  }
  // ! SENGAJA DIPISAH, MASALAH INJECT
  @Provides
  @Singleton
  fun provideRefreshTokenService(): RefreshTokenService {
    val retrofit = Retrofit.Builder()
      .baseUrl(BuildConfig.API_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(OkHttpClient.Builder().build())
      .build()
    return retrofit.create(RefreshTokenService::class.java)
  }
}
