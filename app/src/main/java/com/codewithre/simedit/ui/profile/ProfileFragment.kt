package com.codewithre.simedit.ui.profile

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.FragmentProfileBinding
import com.codewithre.simedit.ui.ViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogout.setOnClickListener {
                viewModel.logout()
            }
        }

        getUserData()
    }

    private fun getUserData() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            if (userData != null) {
                binding.tvUserName.text = userData.name
            }
        }
        viewModel.getUserData()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}