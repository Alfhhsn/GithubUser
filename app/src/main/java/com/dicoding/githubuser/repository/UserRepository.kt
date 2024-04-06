package com.dicoding.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.database.UserDao
import com.dicoding.githubuser.database.UserEntity
import com.dicoding.githubuser.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.noteDao()
    }
    suspend fun getAllUser(): List<UserEntity> = mUserDao.getAllFavoriteUser()
    fun insert(note: UserEntity) {
        executorService.execute { mUserDao.insert(note) }
    }
    fun delete(note: UserEntity) {
        executorService.execute { mUserDao.delete(note) }
    }
    fun update(note: UserEntity) {
        executorService.execute { mUserDao.update(note) }
    }
    suspend fun isUserExist(username: String) = mUserDao.isUserIsExist(username)

}