package com.example.slicingbcf.data.repo.user

import com.example.slicingbcf.data.dao.model.User
import com.example.slicingbcf.data.dao.user.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
  private val userDao : UserDao
) : UserRepository {


  override suspend fun insertUser(user : User) {
    userDao.insertUser(user)
  }

  override suspend fun updateUser(user : User) {
    userDao.updateUser(user)
  }

  override fun getAllUsers() : Flow<List<User>> {
    return userDao.getAllUsers()
  }

  override fun getUserById(id : String) : Flow<User?> {
    return userDao.getUserById(
      id.toLong()
    )
  }

  override fun isUserExist(email : String, password : String) : Flow<Boolean> {
    return userDao.isUserExist(email, password)
  }

  override fun getUserIfExist(email : String, password : String) : Flow<User?> {
    return userDao.getUserIfExist(email, password)
  }

  override fun isEmailExist(email : String) : Flow<Boolean> {
    return userDao.isEmailExist(email)
  }
}
