package com.example.slicingbcf.data.dao.helper

import androidx.room.TypeConverter
import com.example.slicingbcf.data.dao.model.JangkauanPenerimaManfaat
import com.example.slicingbcf.data.dao.model.Role
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {

  @TypeConverter
  fun fromList(value : List<JangkauanPenerimaManfaat>?) : String? {
    return Gson().toJson(value)
  }

  @TypeConverter
  fun toList(value : String?) : List<JangkauanPenerimaManfaat>? {
    val type = object : TypeToken<List<JangkauanPenerimaManfaat>>() {}.type
    return Gson().fromJson(value, type)
  }

  @TypeConverter
  fun fromRole(value : Role) : String {
    return value.name
  }

  @TypeConverter
  fun toRole(value : String) : Role {
    return Role.valueOf(value)
  }

  @TypeConverter
  fun fromDate(value : Date?) : Long? {
    return value?.time
  }

  @TypeConverter
  fun toDate(value : Long?) : Date? {
    return value?.let { Date(it) }
  }
}