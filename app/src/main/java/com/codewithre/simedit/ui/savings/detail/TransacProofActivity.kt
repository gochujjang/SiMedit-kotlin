package com.codewithre.simedit.ui.savings.detail

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.codewithre.simedit.R
import com.codewithre.simedit.adapter.DetailSavingAdapter
import com.codewithre.simedit.databinding.ActivityTransacProofBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TransacProofActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransacProofBinding
    private var currentImageUri: Uri? = null
    private val viewModel by viewModels<DetailSavingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var id: Int = 0


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

        id = intent.getIntExtra(DetailSavingAdapter.EXTRA_ID, 0)

        currentImageUri = intent.getStringExtra(DetailSavingAdapter.EXTRA_PIC)?.toUri()

        viewModel.deleteMessageTransaction.observe(this) {
            if (it != null) {
                showToast(it)
                finish()
            }
        }

        binding.btnBack.setOnClickListener { finish() }
        binding.btnDelete.setOnClickListener { deleteSavingTrans() }
        showImage()
    }

    private fun deleteSavingTrans() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete this transaction")
            .setMessage("Are you sure you want to delete this transaction?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteSavingTrans(id)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            val url = uri
//            val localmode = uri.toString().replace("localhost", "10.0.2.2:8000").toUri()
            Glide.with(this)
                .load(uri)
                .into(binding.ivPreview)
        }
    }
}