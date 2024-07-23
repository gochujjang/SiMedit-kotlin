package com.codewithre.simedit.ui.savings.detail

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithre.simedit.R
import com.codewithre.simedit.adapter.DetailSavingAdapter
import com.codewithre.simedit.data.remote.response.TransaksiPortoItem
import com.codewithre.simedit.databinding.ActivityDetailSavingBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.ui.add.transacsaving.AddTransacSavingActivity
import com.codewithre.simedit.ui.savings.SavingsFragment.Companion.EXTRA_ID
import com.codewithre.simedit.ui.savings.listmember.ListMemberActivity
import com.codewithre.simedit.utils.formatCurrency
import com.codewithre.simedit.utils.formatShortCurrency
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailSavingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSavingBinding
    private val viewModel by viewModels<DetailSavingViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var id : Int = 0
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private var isShortFormat: Boolean = true


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
        viewModel.deleteMessage.observe(this) {
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
        refreshData()
        setFormatCurrency()
    }

    private fun setFormatCurrency() {
        binding.tvTotalSavingsBalance.setOnClickListener {
            toggleFormat()
        }
        binding.tvRemainingTargetBalance.setOnClickListener {
            toggleFormat()
        }
        binding.tvTotalTargetBalance.setOnClickListener {
            toggleFormat()
        }
    }

    private fun toggleFormat() {
        isShortFormat = !isShortFormat

        val totalSavings = viewModel.detailSaving.value?.terkumpul?.toLong() ?: 0L
        val totalTarget = viewModel.detailSaving.value?.target?.toLong() ?: 0L
        val remainingBalance = totalTarget - totalSavings

//        fun setMargins(view: View, marginDp: Int) {
//            val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
//            layoutParams.setMargins(marginDp, marginDp, marginDp, marginDp)
//            view.layoutParams = layoutParams
//        }


        if (isShortFormat) {
            binding.tvTotalSavingsBalance.textSize = 24f
            binding.tvTotalSavingsBalance.text = formatShortCurrency(totalSavings)
//            setMargins(binding.tvTotalSavingsBalance, 20)
        } else {
            binding.tvTotalSavingsBalance.textSize = 16f
//            setMargins(binding.tvTotalSavingsBalance, 10)
            formatCurrency(totalSavings)
        }

        binding.tvTotalTargetBalance.text = if (isShortFormat) formatShortCurrency(totalTarget) else formatCurrency(totalTarget)
        binding.tvRemainingTargetBalance.text = if (isShortFormat) formatShortCurrency(remainingBalance) else formatCurrency(remainingBalance)
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

        binding.tvListfriend.setOnClickListener {
            val intent = Intent(this, ListMemberActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            startActivity(intent)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.tvDeleteSaving.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Delete Saving Plan")
                .setMessage("Are you sure you want to delete this saving plan?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteSaving(id)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    finish()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                .show()
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

    private fun showNotFound(isEmptyTransac: Boolean = false) {
        binding.tvNotFoundTransac.visibility = if (isEmptyTransac) View.VISIBLE else View.GONE
        binding.rvTransacsaving.visibility = if (isEmptyTransac) View.GONE else View.VISIBLE
    }

    private fun refreshData() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getDetailSaving(id)
            binding.swipeRefresh.isRefreshing = false
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
                if (listTransaction.isEmpty()) {
                    showNotFound(true)
                } else {
                    showNotFound(false)
                    setTransaction(listTransaction)
                }
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

                    val totalSavings = it?.terkumpul?.toLong() ?: 0L
                    val totalTarget = it?.target?.toLong() ?: 0L
                    val remainingBalance = totalTarget - totalSavings

                    tvTotalSavingsBalance.text = formatShortCurrency(totalSavings)
                    tvTotalTargetBalance.text = formatShortCurrency(totalTarget)
                    tvRemainingTargetBalance.text = formatShortCurrency(remainingBalance)
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