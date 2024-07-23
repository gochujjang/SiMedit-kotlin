package com.codewithre.simedit.ui.profile.edit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.ActivityEditProfileBinding
import com.codewithre.simedit.ui.ViewModelFactory

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels<EditProfileViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
        }

        getUserData()
        updateProfileBtn()
        errorMsg()
    }

    private fun errorMsg() {
        viewModel.errorMessage.observe(this) {
            if (it != null) {
                showToast(it)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateProfileBtn() {
        binding.apply {
            btnUpdateProfile.setOnClickListener {
                val name = edFullname.text.toString()
                val username = edUsername.text.toString()
                val email = edEmail.text.toString()

                if (name.isEmpty()) {
                    edFullname.error = "Name can't be empty"
                    return@setOnClickListener
                } else if (username.isEmpty()) {
                    edUsername.error = "Username can't be empty"
                    return@setOnClickListener
                } else if (email.isEmpty()) {
                    edEmail.error = "Email can't be empty"
                    return@setOnClickListener
                } else {
                    edFullname.error = null
                    edUsername.error = null
                    edEmail.error = null
                }

                viewModel.updateProfile(name, username, email)
            }
        }
        }

    private fun getUserData() {
        viewModel.userData.observe(this) {
            if (it != null) {
                binding.apply {
                    edFullname.setText(it.name)
                    edUsername.setText(it.username)
                    edEmail.setText(it.email)
                }
            }
        }
        viewModel.getUserData()
    }
}