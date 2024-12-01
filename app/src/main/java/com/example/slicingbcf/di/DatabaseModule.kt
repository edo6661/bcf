package com.example.slicingbcf.di

import android.content.Context
import androidx.room.Room
import com.example.slicingbcf.data.dao.Database
import com.example.slicingbcf.data.dao.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Provides
  @Singleton
  fun provideUserDao(database : Database) : UserDao = database.userDao

  @Provides
  @Singleton
  fun provideDatabase(
    @ApplicationContext context : Context
  ) : Database = Room.databaseBuilder(
    context,
    Database::class.java,
    Database.Constants.DATABASE_NAME
  ).build()
}