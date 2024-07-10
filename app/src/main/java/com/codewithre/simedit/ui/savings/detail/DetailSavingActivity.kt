package com.codewithre.simedit.ui.savings.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithre.simedit.R
import com.codewithre.simedit.adapter.DetailSavingAdapter
import com.codewithre.simedit.data.remote.response.TransaksiPortoItem
import com.codewithre.simedit.databinding.ActivityDetailSavingBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.ui.savings.SavingsFragment.Companion.EXTRA_ID
import com.codewithre.simedit.utils.formatCurrency

class DetailSavingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSavingBinding
    private val viewModel by viewModels<DetailSavingViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        viewModel.getDetailSaving(id)
        Log.d("COY DetailSavingActivity", "onCreate: $id")

//        viewModel.isLoading.observe(this) {
//            showLoading(it)
//        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvTransacsaving.layoutManager = layoutManager

        setDetailData()
        setTransactionData()
        setBackBtn()
    }

    private fun setBackBtn() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setTransactionData() {
        viewModel.transaksiSaving.observe(this) { listTransaction ->
            if (listTransaction != null) {
                setTransaction(listTransaction)
            }
        }

    }

    private fun setTransaction(listTransaction: List<TransaksiPortoItem?>) {
        val sortedList = listTransaction.sortedByDescending { it?.createdAt }

        val adapter = DetailSavingAdapter()
        adapter.submitList(sortedList)
        binding.rvTransacsaving.adapter = adapter
    }

    private fun setDetailData() {
        viewModel.apply {
            detailSaving.observe(this@DetailSavingActivity) {
                binding.apply {
                    tvTitleSaving.text = it?.title ?: "Null"
                    tvTotalSavingsBalance.text = formatCurrency(it?.terkumpul)
                    tvTotalTargetBalance.text = formatCurrency(it?.target)
                    val persentasi = it?.persentase?.toFloat()
                    tvProgressPercent.text = getString(R.string.progress_savings, it?.persentase)
                    circularProgressBar.apply {
                        progressMax = 100f
                        if (persentasi != null) {
                            setProgressWithAnimation(persentasi, 1000)
                        } else {
                            setProgressWithAnimation(0f, 1000)
                        }
                    }
                }
            }
        }
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
}