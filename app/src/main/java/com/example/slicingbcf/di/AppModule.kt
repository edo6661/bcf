package com.example.slicingbcf.di

import android.content.Context
import com.example.slicingbcf.data.local.preferences.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


  @Provides
  @Singleton
  fun provideUserPreferences(@ApplicationContext context : Context) : UserPreferences {
    return UserPreferences(context)
  }


}