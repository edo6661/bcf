package com.example.slicingbcf.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.slicingbcf.domain.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user_remote_preferences")

class UserRemotePreferences @Inject constructor(private val context : Context) {

  private val gson = Gson()
  private val dataStore = context.dataStore

  suspend fun saveUserSession(user : User) = dataStore.edit { preferences ->
    preferences[USER_DATA_KEY] = gson.toJson(user)
  }

  fun getUserData() : Flow<User?> = dataStore.data.map { preferences ->
    preferences[USER_DATA_KEY]?.let { userDataString ->
      gson.fromJson(userDataString, User::class.java)
    }
  }

  suspend fun clearUserSession() = dataStore.edit { preferences ->
    preferences.clear()
  }

  fun isLoggedIn() : Flow<Boolean> = dataStore.data.map { preferences ->
    preferences[USER_DATA_KEY] != null
  }
  fun getAccessToken() : Flow<String?> = context.dataStore.data.map { preferences ->
    preferences[ACCESS_TOKEN]
  }
  suspend fun saveUserAccessToken(token : String) {
    context.dataStore.edit { preferences ->
      preferences[ACCESS_TOKEN] = token
    }
  }
  fun getRefreshToken() : Flow<String?> = context.dataStore.data.map { preferences ->
    preferences[REFRESH_TOKEN]
  }
  suspend fun saveUserRefreshToken(token : String) {
    context.dataStore.edit { preferences ->
      preferences[REFRESH_TOKEN] = token
    }
  }

  companion object {

    private val USER_DATA_KEY = stringPreferencesKey("user_remote_data")
    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
  }
}
