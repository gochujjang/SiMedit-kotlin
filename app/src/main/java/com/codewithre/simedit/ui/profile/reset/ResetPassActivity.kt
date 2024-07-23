package com.codewithre.simedit.ui.profile.reset

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.ActivityEditProfileBinding
import com.codewithre.simedit.databinding.ActivityResetPassBinding
import com.codewithre.simedit.ui.ViewModelFactory

class ResetPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPassBinding
    private val viewModel : ResetPassViewModel by viewModels<ResetPassViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            btnUpdatePass.setOnClickListener {
                val currentPass = edOldpass.text.toString()
                val newPass = edNewpass.text.toString()
                val confirmPass = edConfirmpass.text.toString()

                if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                    showToast("Password fields must not be empty")
                    return@setOnClickListener
                }

                viewModel.resetPass(
                    currentPass,
                    newPass,
                    confirmPass
                )
            }
        }

        viewModel.message.observe(this) {
            if (it != null) {
                showToast(it.msg.toString())
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}