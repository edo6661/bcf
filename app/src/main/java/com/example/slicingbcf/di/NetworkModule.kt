package com.example.slicingbcf.di

import com.example.slicingbcf.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
//  TODO: add interceptor
  fun provideOkHttpClient() : OkHttpClient {
    return OkHttpClient
      .Builder()
      .addInterceptor(
        if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
      )
      .build()
  }
//  TODO: add api service
}