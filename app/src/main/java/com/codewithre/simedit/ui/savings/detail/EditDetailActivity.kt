package com.codewithre.simedit.ui.savings.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.ActivityEditDetailBinding
import com.codewithre.simedit.ui.ViewModelFactory

class EditDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDetailBinding
    private val viewModel by viewModels<DetailSavingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var id : Int = 0
    private var title: String = ""
    private var target: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(EXTRA_DETAIL_ID, 0)
        title = intent.getStringExtra(EXTRA_DETAIL_TITLE).toString()
        target = intent.getIntExtra(EXTRA_DETAIL_TARGET, 0)

        viewModel.editMessage.observe(this) {
            if (it == "Saving updated successfully") {
                showToast(it)
                finish()
            } else {
                showToast(it)
            }
        }

        binding.btnBack.setOnClickListener { finish() }
        binding.btnEditSaving.setOnClickListener { editSaving() }

        setEditText()
    }

    private fun editSaving() {
        val newTitle = binding.edDesc.text.toString()
        val newTarget = binding.edBalance.value.toInt()

        if (newTarget == 0) {
            binding.edBalance.error = "Balance can't be 0, please enter balance amount"
        } else if (newTitle.isEmpty()) {
            binding.edDesc.error = "Please enter saving title"
        } else {
            viewModel.editSaving(id, newTitle, newTarget)
        }
    }

    private fun setEditText() {
        binding.apply {
            edDesc.setText(title)
            edBalance.setText(target.toString())
        }
    }

    private fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_DETAIL_ID = "extra_detail_id"
        const val EXTRA_DETAIL_TITLE = "extra_detail_title"
        const val EXTRA_DETAIL_TARGET = "extra_detail_target"
    }
}