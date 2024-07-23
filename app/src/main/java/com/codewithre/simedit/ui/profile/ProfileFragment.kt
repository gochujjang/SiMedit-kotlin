package com.codewithre.simedit.ui.profile

import android.content.Context
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.FragmentProfileBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.ui.login.LoginActivity
import com.codewithre.simedit.ui.profile.edit.EditProfileActivity
import com.codewithre.simedit.ui.profile.faq.FaqActivity
import com.codewithre.simedit.ui.profile.reset.ResetPassActivity

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

            btnEditProfile.setOnClickListener {
                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                startActivity(intent)
            }

            btnResetPassword.setOnClickListener {
                val intent = Intent(requireContext(), ResetPassActivity::class.java)
                startActivity(intent)
            }

            btnHelpCenter.setOnClickListener {
                val intent = Intent(requireContext(), FaqActivity::class.java)
                startActivity(intent)
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

    override fun onResume() {
        super.onResume()

        viewModel.getUserData()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}