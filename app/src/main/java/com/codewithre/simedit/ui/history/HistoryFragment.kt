package com.codewithre.simedit.ui.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
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
import com.codewithre.simedit.ui.add.transaction.AddTransactionViewModel
import com.codewithre.simedit.ui.home.HomeFragment
import com.codewithre.simedit.utils.formatCurrency
import com.codewithre.simedit.utils.formatShortCurrency
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val transactionViewModel: AddTransactionViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }


    private var fullHistoryList: List<HistoryItem?> = listOf()

    private var isShortFormat: Boolean = false
    private var isIncomeShortFormat: Boolean = true
    private var isExpenseShortFormat: Boolean = true



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

        viewModel.deleteMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.resetDeleteMessage() // Reset the delete message after showing the toast
            }
        }

        viewModel.transactionChangedEvent.observe(viewLifecycleOwner) {
            getRefreshData()
        }

        transactionViewModel.transactionChangedEvent.observe(viewLifecycleOwner) {
            Log.d("COY HistoryFragment", "Transaction changed event received.")
            getRefreshData()
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.layoutManager = layoutManager

        setBalance()
        refreshData()
    }

    private fun getRefreshData() {
        viewModel.apply {
            getHistory()
            getTotalIncome()
            getTotalExpense()
            getTotalBalance()
        }
    }

    private fun showNotFound(isEmptyTransac: Boolean = false) {
        binding.tvNotFoundTransac.visibility = if (isEmptyTransac) View.VISIBLE else View.GONE
        binding.rvHistory.visibility = if (isEmptyTransac) View.GONE else View.VISIBLE
    }

    private fun refreshData() {
        binding.swipeRefresh.setOnRefreshListener {
            getRefreshData()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        getRefreshData()
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
                    updateIncomeBalance(totalIncome.data!!.toLong(), isIncomeShortFormat)
                } else {
                    binding.tvIncomeBalance.text = formatCurrency(0)
                }
            }

            totalExpense.observe(viewLifecycleOwner) { totalExpense ->
                if (totalExpense != null) {
                    updateExpenseBalance(totalExpense.data!!.toLong(), isExpenseShortFormat)
                } else {
                    binding.tvExpenseBalance.text = formatCurrency(0)
                }
            }

            binding.tvIncomeBalance.setOnClickListener {
                toggleIncomeBalanceFormat()
            }

            binding.tvExpenseBalance.setOnClickListener {
                toggleExpenseBalanceFormat()
            }

            getTotalBalance()
            getTotalIncome()
            getTotalExpense()
        }
    }

    private fun toggleIncomeBalanceFormat() {
        isIncomeShortFormat = !isIncomeShortFormat

        val totalIncomeValue = viewModel.totalIncome.value?.data?.toLong() ?: 0L
        updateIncomeBalance(totalIncomeValue, isIncomeShortFormat)

        // Ensure the expense balance is in short format
        val totalExpenseValue = viewModel.totalExpense.value?.data?.toLong() ?: 0L
        updateExpenseBalance(totalExpenseValue, true)
        isExpenseShortFormat = true
    }

    private fun toggleExpenseBalanceFormat() {
        isExpenseShortFormat = !isExpenseShortFormat

        val totalExpenseValue = viewModel.totalExpense.value?.data?.toLong() ?: 0L
        updateExpenseBalance(totalExpenseValue, isExpenseShortFormat)

        // Ensure the income balance is in short format
        val totalIncomeValue = viewModel.totalIncome.value?.data?.toLong() ?: 0L
        updateIncomeBalance(totalIncomeValue, true)
        isIncomeShortFormat = true
    }

    private fun updateIncomeBalance(amount: Long, isShort: Boolean) {
        binding.tvIncomeBalance.text = if (isShort) formatShortCurrency(amount) else formatCurrency(amount)
    }

    private fun updateExpenseBalance(amount: Long, isShort: Boolean) {
        binding.tvExpenseBalance.text = if (isShort) formatShortCurrency(amount) else formatCurrency(amount)
    }

    private fun setHistory(historyItem: List<HistoryItem?>) {
        val adapter = HistoryAdapter(viewModel, requireContext())
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

    companion object {
        val limit_balance = 999_999_999
    }
}