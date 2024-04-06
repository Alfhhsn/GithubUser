package com.dicoding.githubuser.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.database.UserEntity
import com.dicoding.githubuser.repository.UserRepository

class UserAddUpdateViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: UserRepository = UserRepository(application)
    fun insert(note: UserEntity) {
        mNoteRepository.insert(note)
    }
    fun update(note: UserEntity) {
        mNoteRepository.update(note)
    }
    fun delete(note: UserEntity) {
        mNoteRepository.delete(note)
    }
}