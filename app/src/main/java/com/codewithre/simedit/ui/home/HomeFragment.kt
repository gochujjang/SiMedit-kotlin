package com.codewithre.simedit.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                setLatestHistory(listHistory)
            } else {
                showNotFound(true)
            }
        }

        savingViewModel.listSaving.observe(viewLifecycleOwner) {listSaving ->
            if (listSaving != null) {
                setLatestSaving(listSaving)
            } else {
                showNotFound(true)
            }
        }
        savingViewModel.getSaving()

        val historyLayoutManager = LinearLayoutManager(requireContext())
        val savingLayoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            rvHistory.layoutManager = historyLayoutManager
            rvSavings.layoutManager = savingLayoutManager
            rvHistory.setHasFixedSize(true)
            rvSavings.setHasFixedSize(true)
        }

        setLoading()
        setBalance()
        setUserData()
    }

    private fun setLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNotFound(isEmpty: Boolean) {
        binding.tvNotFound.visibility = if (isEmpty) View.VISIBLE else View.GONE
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
                binding.tvTotalAmount.text = formatCurrency(totalBalance.data)
            }
        }
        viewModel.getTotalBalance()
    }

    private fun setLatestHistory(listHistory: List<HistoryItem?>) {
        val latestHistory = listHistory.takeLast(5)
        val adapter = HistoryAdapter()
        adapter.submitList(latestHistory)
        binding.rvHistory.adapter = adapter
        showLoading(false)
    }

    private fun setLatestSaving(listSaving: List<SavingItem?>) {
        val latestSaving = listSaving.takeLast(5)
        val adapter = SavingMiniAdapter()
        adapter.submitList(latestSaving)
        binding.rvSavings.adapter = adapter
    }
}