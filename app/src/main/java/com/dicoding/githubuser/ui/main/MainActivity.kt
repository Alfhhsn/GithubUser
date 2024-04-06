package com.dicoding.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.ui.SaveFavoriteActivity
import com.dicoding.githubuser.ui.SettingPreferences
import com.dicoding.githubuser.ui.UserAdapter
import com.dicoding.githubuser.ui.ViewModelFactory
import com.dicoding.githubuser.ui.dataStore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel=
            ViewModelProvider(this, ViewModelFactory(SettingPreferences.getInstance(applicationContext.dataStore))).get(
            MainViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        mainViewModel.listUser.observe(this) { listUser->
            setAdapter(listUser)
        }

        mainViewModel.isLoading.observe(this) {
            binding.ProgressBar.isVisible = it
        }

        mainViewModel.isEmpty.observe(this) {
            binding.EmptyValue.isVisible = it
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    mainViewModel.searchUser(searchView.text.toString())
                    searchView.hide()
                    false
                }
        }

        binding.btnFavorite.setOnClickListener {
            val intent = Intent(this, SaveFavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter(listUser: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listUser)
        binding.rvReview.adapter =adapter
    }
}