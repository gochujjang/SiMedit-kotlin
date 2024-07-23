package com.codewithre.simedit.ui.savings.detail

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithre.simedit.R
import com.codewithre.simedit.adapter.DetailSavingAdapter
import com.codewithre.simedit.data.remote.response.TransaksiPortoItem
import com.codewithre.simedit.databinding.ActivityDetailSavingBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.ui.add.transacsaving.AddTransacSavingActivity
import com.codewithre.simedit.ui.savings.SavingsFragment.Companion.EXTRA_ID
import com.codewithre.simedit.utils.formatShortCurrency
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DetailSavingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSavingBinding
    private val viewModel by viewModels<DetailSavingViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var id : Int = 0
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(EXTRA_ID, 0)
        viewModel.getDetailSaving(id)
        Log.d("COY DetailSavingActivity", "onCreate: $id")

//        viewModel.isLoading.observe(this) {
//            showLoading(it)
//        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvTransacsaving.layoutManager = layoutManager
        // Initialize BottomSheetBehavior
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        viewModel.inviteMessage.observe(this) {
            if (it != null) {
                showToast(it)
            }
        }

        // Set initial state to hidden
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        setupBottomSheet()
        setDetailData()
        setTransactionData()
        setBackBtn()
        setTransactionBtn()
        setMenuBtn()
    }

    private fun setupBottomSheet() {
        binding.tvInvite.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater =  layoutInflater
            val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.ed_invite)
            with(builder) {
                setPositiveButton("OK") { _, _ ->
                    val invite = editText.text.toString()
                    if (invite.isEmpty()) {
                        showToast("Field can't be empty")
                        return@setPositiveButton
                    } else {
                        val portoId = id
                        viewModel.inviteFriend(invite, portoId)
                        Log.d("COY DetailSavingActivity", "setupBottomSheet: $invite")
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                }
                setNegativeButton("Cancel") { _, _ ->

                }
                setView(dialogLayout)
            }
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            dialog.show()
        }

        val bottomSheet = binding.bottomSheet
        BottomSheetBehavior.from(bottomSheet).apply {
            isDraggable = true
            isHideable = true
            this.state = BottomSheetBehavior.STATE_HIDDEN
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // Handle state changes if needed
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        binding.dimView.visibility = View.GONE
                    } else {
                        binding.dimView.visibility = View.VISIBLE
                        binding.dimView.alpha = 0.5f
                    }

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // Change the alpha value of the dimView based on slide offset
                    binding.dimView.visibility = View.VISIBLE
//                    binding.dimView.alpha = slideOffset
                }
            })
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun animateDimViewAlpha(targetAlpha: Float) {
        val animator = ObjectAnimator.ofFloat(binding.dimView, View.ALPHA, targetAlpha)
        animator.duration = 300 // Adjust duration as needed
        animator.start()
    }

    private fun setMenuBtn() {
        binding.btnMenu.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                binding.dimView.visibility = View.VISIBLE
            } else {
                binding.dimView.visibility = View.GONE
            }
        }
    }


    private fun setTransactionBtn() {
        binding.btnAddTransaction.setOnClickListener {
            val intent = Intent(this, AddTransacSavingActivity::class.java)
            intent.putExtra(EXTRA_ID, viewModel.detailSaving.value?.id)
            startActivity(intent)
        }
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
                    tvTotalSavingsBalance.text = formatShortCurrency(it?.terkumpul)
                    tvTotalTargetBalance.text = formatShortCurrency(it?.target)
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

    override fun onResume() {
        super.onResume()
        viewModel.getDetailSaving(id)
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
}