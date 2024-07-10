package com.codewithre.simedit.ui.savings

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithre.simedit.R
import com.codewithre.simedit.adapter.SavingAdapter
import com.codewithre.simedit.data.remote.response.SavingItem
import com.codewithre.simedit.databinding.FragmentHistoryBinding
import com.codewithre.simedit.databinding.FragmentSavingsBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.ui.add.saving.AddSavingActivity
import com.codewithre.simedit.utils.formatCurrency

class SavingsFragment : Fragment() {

    private lateinit var binding: FragmentSavingsBinding
    private val viewModel: SavingsViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //rv
        viewModel.listSaving.observe(viewLifecycleOwner) {listSaving ->
            if (listSaving != null) {
                setSaving(listSaving)
            }
        }
        viewModel.getSaving()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSavings.layoutManager = layoutManager

        setBalance()
        addSavingGoal()
    }

    private fun addSavingGoal() {
        binding.btnAddSaving.setOnClickListener {
            val intent = Intent(requireContext(), AddSavingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setBalance() {
        viewModel.apply {
            totalSaving.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.tvTotalSavingsBalance.text = formatCurrency(it.data)
                }
            }
            totalSaveTarget.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.tvTotalTargetBalance.text = formatCurrency(it.data)
                }
            }
        }
    }

    private fun setSaving(listSaving: List<SavingItem?>) {
        val adapter = SavingAdapter()
        adapter.submitList(listSaving)
        binding.rvSavings.adapter = adapter
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}