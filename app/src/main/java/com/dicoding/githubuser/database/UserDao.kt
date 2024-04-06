package com.dicoding.githubuser.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: UserEntity)
    @Update
    fun update(note: UserEntity)
    @Delete
    fun delete(note: UserEntity)
    @Query("SELECT * from UserEntity ORDER BY id ASC")
    suspend fun getAllFavoriteUser(): List<UserEntity>
    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE login = :username)")
    suspend fun isUserIsExist(username : String) : Boolean
}