package com.example.slicingbcf.data.repo

import com.example.slicingbcf.data.common.UiState
import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepository {


  protected suspend fun <T> safeApiCall(
    apiCall : suspend () -> Response<T>
  ) : UiState<T> {
    return try {
      val response = apiCall()
      if (response.isSuccessful) {
        response.body()?.let {
          UiState.Success(it)
        } ?: UiState.Error("Empty response")
      } else {
        val errorBody = response.errorBody()?.string()
        val errorMessage = parseErrorMessage(errorBody)
        UiState.Error(errorMessage)
      }
    } catch (e : Exception) {
      UiState.Error(e.message ?: "Unknown error")
    }
  }


  private fun parseErrorMessage(errorBody : String?) : String {
    return try {
      JSONObject(errorBody ?: "").getString("message")
    } catch (e : Exception) {

      "Unknown error occurred"
    }
  }
}
