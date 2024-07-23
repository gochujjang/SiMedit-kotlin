package com.codewithre.simedit.ui.savings.detail

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.codewithre.simedit.R
import com.codewithre.simedit.adapter.DetailSavingAdapter
import com.codewithre.simedit.databinding.ActivityTransacProofBinding

class TransacProofActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransacProofBinding
    private var currentImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransacProofBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentImageUri = intent.getStringExtra(DetailSavingAdapter.EXTRA_PIC)?.toUri()

        binding.btnBack.setOnClickListener { finish() }
        showImage()
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            val localmode = uri.toString().replace("localhost", "10.0.2.2:8000").toUri()
            Glide.with(this)
                .load(localmode)
                .into(binding.ivPreview)
        }
    }
}