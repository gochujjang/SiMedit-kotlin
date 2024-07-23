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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var fullHistoryList: List<HistoryItem?> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("All Time","This Day", "This Month")

        binding.autoComplete.setAdapter(
            ArrayAdapter(requireContext(), R.layout.item_timefilter, items)
        )

        binding.autoComplete.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            // Handle the selected item
            Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show()
            filterHistory(selectedItem)
        }

        //rv
        viewModel.listHistory.observe(viewLifecycleOwner) {listHistory ->
            if (listHistory != null) {
                if (listHistory.isEmpty()) {
                    showNotFound(true)
                } else {
                    showNotFound(false)
                    fullHistoryList = listHistory
                    setHistory(listHistory)
                }
            }
        }
        viewModel.getHistory()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.layoutManager = layoutManager

        setBalance()
        refreshData()
    }

    private fun showNotFound(isEmptyTransac: Boolean = false) {
        binding.tvNotFoundTransac.visibility = if (isEmptyTransac) View.VISIBLE else View.GONE
    }

    private fun refreshData() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.apply {
                getHistory()
                getTotalIncome()
                getTotalExpense()
                getTotalBalance()
            }
            binding.swipeRefresh.isRefreshing = false
        }
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
                    binding.tvTotalBalance.text = formatCurrency(totalBalance.data?.toLong())
                }
            }
            totalIncome.observe(viewLifecycleOwner) { totalIncome ->
                if (totalIncome != null) {
                    binding.tvIncomeBalance.text = formatCurrency(totalIncome.data?.toLong())
                }
            }
            totalExpense.observe(viewLifecycleOwner) { totalExpense ->
                if (totalExpense != null) {
                    binding.tvExpenseBalance.text = formatCurrency(totalExpense.data?.toLong())
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

    private fun filterHistory(filter: String) {
        val filteredList = when (filter) {
            "This Day" -> fullHistoryList.filter { it?.tgl?.startsWith(getTodayDate()) == true }
            "This Month" -> fullHistoryList.filter { it?.tgl?.startsWith(getCurrentMonth()) == true }
            else -> fullHistoryList
        }
        setHistory(filteredList)
    }

    private fun getTodayDate(): String {
        // Return today's date in the same format as your data's date format
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getCurrentMonth(): String {
        // Return current month in the same format as your data's date format
        val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return sdf.format(Date())
    }
}