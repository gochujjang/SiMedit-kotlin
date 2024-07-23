package com.codewithre.simedit.ui.register

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.ActivityRegisterBinding
import com.codewithre.simedit.ui.AuthViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.registerResult.observe(this) { registerResult ->
            if (registerResult.data != null) {
                showToast("Register Successfully")
                finish()
            } else {
                showToast("Register failed")
            }
        }

        viewModel.errorResponse.observe(this) { errorResponse ->
            if (errorResponse != null) {
                showToast(errorResponse.toString())
                Log.d("RegisterActivity", "Error: $errorResponse")
            }
        }

        binding.apply {
            btnRegister.setOnClickListener {
                val name = binding.edName.text.toString()
                val username = binding.edUsername.text.toString()
                val email = binding.edEmail.text.toString()
                val password = binding.edPassword.text.toString()

                if (name.isBlank()) {
                    binding.edName.error = "Name cannot be empty"
                    return@setOnClickListener
                }

                if (username.isBlank()) {
                    binding.edUsername.error = "Username cannot be empty"
                    return@setOnClickListener
                }

                if (email.isBlank()) {
                    binding.edEmail.error = "Email cannot be empty"
                    return@setOnClickListener
                }

                if (password.isBlank()) {
                    binding.edPassword.error = "Password cannot be empty"
                    return@setOnClickListener
                }

                viewModel.register(
                    name,
                    username,
                    email,
                    password
                )

            }
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}