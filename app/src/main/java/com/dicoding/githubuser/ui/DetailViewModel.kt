package com.dicoding.githubuser.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.response.DetailGithubResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.database.UserEntity
import com.dicoding.githubuser.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private val mNoteRepository: UserRepository = UserRepository(application)

    fun insert(note: UserEntity) {
        mNoteRepository.insert(note)
    }
    fun delete(note: UserEntity) {
        mNoteRepository.delete(note)
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _detailUser = MutableLiveData<DetailGithubResponse?>()
    val detailUser: LiveData<DetailGithubResponse?> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isUserExist = MutableLiveData<Boolean>()
    val isUserExist: LiveData<Boolean> = _isUserExist

    fun checkUser(username: String) {
        viewModelScope.launch {
            val data = mNoteRepository.isUserExist(username)
            _isUserExist.postValue(data)
        }
    }

    fun addToFavorite(){
        val userEntity = UserEntity(
            detailUser.value?.id?:0,
            detailUser.value?.login?:"",
            detailUser.value?.avatarUrl?:""
        )
        if (isUserExist.value == true) {
            delete(userEntity)
        } else {
            insert(userEntity)
        }
        _isUserExist.postValue(!isUserExist.value!!)

    }

    fun detailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailGithubResponse> {
            override fun onResponse(
                call: Call<DetailGithubResponse>,
                response: Response<DetailGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}