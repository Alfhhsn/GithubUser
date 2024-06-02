package com.dicoding.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.database.UserEntity
import com.dicoding.githubuser.databinding.ActivitySaveFavoriteBinding
import com.dicoding.githubuser.ui.main.SaveFavoriteViewModel

class SaveFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaveFavoriteBinding
    private val saveFavoriteViewModel by viewModels<SaveFavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySaveFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saveFavoriteViewModel.getFavoriteUser()

        saveFavoriteViewModel.saveFavorite.observe(this) { user ->
            Log.d("String", "data = $user")
            if (user != null) {
                setAdapter(user)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        saveFavoriteViewModel.getFavoriteUser()
    }

    private fun setAdapter(listUser: List<UserEntity>) {
        val favoriteAdapter = UserAdapter()
        favoriteAdapter.submitList(mapUser(listUser))
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter =favoriteAdapter
    }

    private fun mapUser(listUser: List<UserEntity>): List<ItemsItem>{
        val userArray = ArrayList<ItemsItem>()
        for (user in listUser) {
            if (user.login?.isEmpty() != false)break
            userArray.add(
                ItemsItem(
                    login = user.login,
                    avatarUrl = user.avatarUrl,
                    id = user.id
                )
            )
        }
        return userArray
    }
}