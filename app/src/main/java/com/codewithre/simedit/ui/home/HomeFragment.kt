package com.codewithre.simedit.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithre.simedit.R
import com.codewithre.simedit.adapter.HistoryAdapter
import com.codewithre.simedit.adapter.SavingMiniAdapter
import com.codewithre.simedit.data.remote.response.HistoryItem
import com.codewithre.simedit.data.remote.response.SavingItem
import com.codewithre.simedit.databinding.FragmentHomeBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.ui.history.HistoryViewModel
import com.codewithre.simedit.ui.savings.SavingsViewModel
import com.codewithre.simedit.utils.formatCurrency
import com.codewithre.simedit.utils.formatShortCurrency

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val historyViewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val savingViewModel: SavingsViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private var isShortFormat: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        historyViewModel.getHistory()
        savingViewModel.getSaving()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.listHistory.observe(viewLifecycleOwner) {listHistory ->
            if (listHistory != null) {
                if (listHistory.isEmpty()) {
                    showNotFound(true)
                } else {
                    showNotFound(false)
                    setLatestHistory(listHistory)
                }
            }
        }

        savingViewModel.listSaving.observe(viewLifecycleOwner) {listSaving ->
            if (listSaving != null) {
                if (listSaving.isEmpty()) {
                    showNotFoundSaving(true)
                } else {
                    showNotFoundSaving(false)
                    setLatestSaving(listSaving)
                }
            }
        }
        savingViewModel.getSaving()

        historyViewModel.transactionChangedEvent.observe(viewLifecycleOwner) {
            getRefreshData()
        }

        val historyLayoutManager = LinearLayoutManager(requireContext())
        val savingLayoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            rvHistory.layoutManager = historyLayoutManager
            rvSavings.layoutManager = savingLayoutManager
            rvHistory.setHasFixedSize(true)
            rvSavings.setHasFixedSize(true)
        }
        avatarBtn()
        setLoading()
        setBalance()
        setUserData()
        refreshData()
    }

    private fun getRefreshData() {
        viewModel.getTotalBalance()
        viewModel.getUserData()
        historyViewModel.getHistory()
        savingViewModel.getSaving()
    }

    private fun showNotFound(isEmptyTransac: Boolean = false) {
        binding.tvNotFoundTransac.visibility = if (isEmptyTransac) View.VISIBLE else View.GONE
        binding.rvHistory.visibility = if (isEmptyTransac) View.GONE else View.VISIBLE
    }

    private fun showNotFoundSaving(isEmptySaving: Boolean = false) {
        binding.tvNotFoundSaving.visibility = if (isEmptySaving) View.VISIBLE else View.GONE
        binding.rvSavings.visibility = if (isEmptySaving) View.GONE else View.VISIBLE
    }

    private fun refreshData() {
        binding.swipeRefresh.setOnRefreshListener {
            getRefreshData()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun avatarBtn() {
        binding.ivAvatar.setOnClickListener {
            val navOption = NavOptions.Builder()
                .setPopUpTo(R.id.navigation_home, true)
                .build()
            findNavController().navigate(R.id.navigation_profile, null, navOption)
        }
    }

    override fun onResume() {
        super.onResume()

        getRefreshData()
    }

    private fun setLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData() {
        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            if (userData != null) {
                binding.tvFullname.text = userData.name
            }
        }
        viewModel.getUserData()
    }

    private fun setBalance() {
        viewModel.totalBalance.observe(viewLifecycleOwner) { totalBalance ->
            if (totalBalance != null) {
                if (totalBalance.data!! > limit_balance.toBigInteger()) {
                    isShortFormat = true
                    binding.tvTotalAmount.text = formatShortCurrency(totalBalance.data.toLong())
                } else {
                    isShortFormat = false
                    binding.tvTotalAmount.text = formatCurrency(totalBalance.data.toLong())
                }
            } else {
                binding.tvTotalAmount.text = formatCurrency(0)
            }

            binding.tvTotalAmount.setOnClickListener {
                if (totalBalance != null) {
                    isShortFormat = !isShortFormat
                    if (isShortFormat == true) {
                        binding.tvTotalAmount.text = formatShortCurrency(totalBalance.data!!.toLong())
                    } else {
                        binding.tvTotalAmount.text = formatCurrency(totalBalance.data!!.toLong())
                    }
                } else {
                    isShortFormat = false
                    binding.tvTotalAmount.text = formatCurrency(0)
                }
            }
        }

        viewModel.getTotalBalance()


    }

    private fun setLatestHistory(listHistory: List<HistoryItem?>) {
        val latestHistory = listHistory.takeLast(5)
        val adapter = HistoryAdapter(historyViewModel, requireContext())
        adapter.submitList(latestHistory)
        binding.rvHistory.adapter = adapter
        showLoading(false)
    }

    private fun setLatestSaving(listSaving: List<SavingItem?>) {
        val latestSaving = listSaving.takeLast(5)
        val adapter = SavingMiniAdapter()
        adapter.submitList(latestSaving)
        binding.rvSavings.adapter = adapter
        showLoading(false)
    }

    companion object {
        val limit_balance = 999_999_999
    }
}