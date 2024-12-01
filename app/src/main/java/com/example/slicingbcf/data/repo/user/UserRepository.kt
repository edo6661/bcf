package com.example.slicingbcf.data.repo.user

import com.example.slicingbcf.data.dao.model.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {

  suspend fun insertUser(user : User)
  suspend fun updateUser(user : User)
  fun getAllUsers() : Flow<List<User>>
  fun getUserById(id : String) : Flow<User?>
  fun isUserExist(email : String, password : String) : Flow<Boolean>
  fun getUserIfExist(email : String, password : String) : Flow<User?>
  fun isEmailExist(email : String) : Flow<Boolean>
}