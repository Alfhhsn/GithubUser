package com.dicoding.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

    private lateinit var binding: FragmentFollowerBinding
    private val followerViewModel by viewModels<FollowerViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = requireActivity().intent.getStringExtra(DetailGithubActivity.EXTRA_LOGIN)

        val layoutManager = LinearLayoutManager(context)
        binding.recycleView.layoutManager = layoutManager

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.ProgressBar.isVisible = it
        }

        followerViewModel.isEmpty.observe(viewLifecycleOwner) {
            binding.EmptyData.isVisible = it
        }

        if(index==1) {
            if(followerViewModel.listFollowers.value==null) followerViewModel.getFollowers(username.toString())
            followerViewModel.listFollowers.observe(viewLifecycleOwner) {
                it?.let{ setAdapter(it) }
            }
        } else {
            if(followerViewModel.listFollowings.value==null) followerViewModel.getFollowings(username.toString())
            followerViewModel.listFollowings.observe(viewLifecycleOwner) {
                it?.let{ setAdapter(it) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setAdapter(user: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.recycleView.adapter =adapter
    }
}