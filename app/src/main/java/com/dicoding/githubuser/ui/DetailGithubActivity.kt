package com.dicoding.githubuser.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityDetailGithubBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailGithubActivity : AppCompatActivity() {
    var isChecked = false

    companion object {
        const val EXTRA_LOGIN="login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }
    private lateinit var binding: ActivityDetailGithubBinding

    private val detailViewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(EXTRA_LOGIN)

        if(detailViewModel.detailUser.value==null) detailViewModel.detailUser(username.toString())

        detailViewModel.checkUser(username.toString())
        detailViewModel.isUserExist.observe(this) {isFavorite ->
            if (isFavorite == true){
                binding.fabAdd.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.fabAdd.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        binding.fabAdd.setOnClickListener {
            detailViewModel.addToFavorite()
        }

        detailViewModel.detailUser.observe(this) { user ->
            user?.let { detailUser ->
                with(binding) {
                    tvFollower.text = "${detailUser.followers.toString()} Follower"
                    tvFollowing.text = "${detailUser.following.toString()} Following"
                    tvName.text = detailUser.name ?: ""
                    tvUsername.text = detailUser.login.toString()
                    Glide.with(binding.root)
                        .load(detailUser.avatarUrl)
                        .into(binding.imageProfile)
                        .clearOnDetach()
                }
            }
        }

        detailViewModel.isLoading.observe(this) {
            binding.ProgressBar.isVisible = it
        }

        val sectionsPagerAdapter = SectionPagersAdapter(this)
        val viewPager = binding.ViewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.TabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}