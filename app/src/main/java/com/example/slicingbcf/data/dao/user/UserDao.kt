package com.example.slicingbcf.data.dao.user

import androidx.room.*
import com.example.slicingbcf.data.dao.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUser(user : User)

  @Update
  suspend fun updateUser(user : User)

  @Query("SELECT * FROM users")
  fun getAllUsers() : Flow<List<User>>

  @Query("SELECT * FROM users WHERE id = :id")
  fun getUserById(id : Long) : Flow<User?>

  @Query("SELECT COUNT(*) > 0 FROM users WHERE emailPeserta = :email AND password = :password")
  fun isUserExist(email : String, password : String) : Flow<Boolean>

  @Query("SELECT * FROM users WHERE emailPeserta = :email AND password = :password")
  fun getUserIfExist(email : String, password : String) : Flow<User?>

  @Query("SELECT COUNT(*) > 0 FROM users WHERE emailPeserta = :email")
  fun isEmailExist(email : String) : Flow<Boolean>


}
