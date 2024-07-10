package com.codewithre.simedit.ui.add.saving

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.ActivityAddSavingBinding
import com.codewithre.simedit.ui.ViewModelFactory

class AddSavingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSavingBinding
    private val viewModel: AddSavingViewModel by viewModels<AddSavingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonBack()
        addSaving()
    }

    private fun addSaving() {
        binding.apply {
            btnAddSaving.setOnClickListener {
                val titleSaving = edDesc.text.toString()
                val totalTarget = edBalance.value.toInt()

                if (totalTarget == 0) {
                    edBalance.error = "Balance can't be 0, please enter balance amount"
                    return@setOnClickListener
                } else if (titleSaving.isEmpty()) {
                    edDesc.error = "Please enter saving title"
                    return@setOnClickListener
                } else {
                   viewModel.addSaving(titleSaving, totalTarget)
                   finish()
                }
            }
        }
    }

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}