package com.codewithre.simedit.ui.profile.reset

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.ActivityEditProfileBinding
import com.codewithre.simedit.databinding.ActivityResetPassBinding

class ResetPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}