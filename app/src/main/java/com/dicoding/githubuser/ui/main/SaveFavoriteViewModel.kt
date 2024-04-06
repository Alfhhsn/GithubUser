package com.dicoding.githubuser.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.database.UserEntity
import com.dicoding.githubuser.repository.UserRepository
import kotlinx.coroutines.launch

class SaveFavoriteViewModel (application: Application): AndroidViewModel(application) {
    private val mNoteRepository: UserRepository = UserRepository(application)

    private val _saveFavorite = MutableLiveData<List<UserEntity>?>()
    val saveFavorite: LiveData<List<UserEntity>?> = _saveFavorite

    fun getFavoriteUser() {
        viewModelScope.launch {
            val data = mNoteRepository.getAllUser()
            _saveFavorite.postValue(data)
        }

    }
}