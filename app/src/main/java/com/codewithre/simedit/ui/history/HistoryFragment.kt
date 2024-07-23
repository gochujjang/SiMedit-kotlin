package com.codewithre.simedit.ui.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithre.simedit.R
import com.codewithre.simedit.adapter.HistoryAdapter
import com.codewithre.simedit.data.remote.response.HistoryItem
import com.codewithre.simedit.databinding.FragmentHistoryBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.utils.formatCurrency

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("Harian", "Bulanan")

        binding.autoComplete.setAdapter(
            ArrayAdapter(requireContext(), R.layout.item_timefilter, items)
        )

        binding.autoComplete.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            // Handle the selected item
            Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show()
        }

        //rv
        viewModel.listHistory.observe(viewLifecycleOwner) {listHistory ->
            if (listHistory != null) {
                setHistory(listHistory)
            }
        }
        viewModel.getHistory()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.layoutManager = layoutManager

        setBalance()
    }

    override fun onResume() {
        super.onResume()

        viewModel.apply {
            getHistory()
            getTotalIncome()
            getTotalExpense()
            getTotalBalance()
        }
    }

    private fun setBalance() {
        viewModel.apply {
            totalBalance.observe(viewLifecycleOwner) { totalBalance ->
                if (totalBalance != null) {
                    binding.tvTotalBalance.text = formatCurrency(totalBalance.data)
                }
            }
            totalIncome.observe(viewLifecycleOwner) { totalIncome ->
                if (totalIncome != null) {
                    binding.tvIncomeBalance.text = formatCurrency(totalIncome.data)
                }
            }
            totalExpense.observe(viewLifecycleOwner) { totalExpense ->
                if (totalExpense != null) {
                    binding.tvExpenseBalance.text = formatCurrency(totalExpense.data)
                }
            }

            getTotalBalance()
            getTotalIncome()
            getTotalExpense()
        }
    }

    private fun setHistory(historyItem: List<HistoryItem?>) {
        val adapter = HistoryAdapter()
        adapter.submitList(historyItem)
        binding.rvHistory.adapter = adapter

    }
}