package com.example.slicingbcf.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.slicingbcf.data.dao.helper.Converters
import com.example.slicingbcf.data.dao.model.User
import com.example.slicingbcf.data.dao.user.UserDao


@TypeConverters(
  value = [
    Converters::class
  ]
)
@Database(
  entities = [
    User::class
  ],
  version = 2,
  exportSchema = false
)

abstract class Database : RoomDatabase() {

  object Constants {

    const val DATABASE_NAME = "bcf_db"
  }

  abstract val userDao : UserDao
}