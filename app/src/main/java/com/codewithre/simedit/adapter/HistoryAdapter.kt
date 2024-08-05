package com.codewithre.simedit.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithre.simedit.R
import com.codewithre.simedit.data.remote.response.HistoryItem
import com.codewithre.simedit.databinding.ItemTransactionBinding
import com.codewithre.simedit.ui.history.HistoryViewModel
import com.codewithre.simedit.utils.formatCurrency
import com.codewithre.simedit.utils.formatDate
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HistoryAdapter(private val viewModel: HistoryViewModel, private val context: Context) : ListAdapter<HistoryItem, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemTransactionBinding, private val viewModel: HistoryViewModel, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryItem) {
            binding.apply {
                tvTitleTransac.text = item.keterangan
                tvValueTransac.text = formatCurrency(item.nominal?.toLong())
                val formattedDate = formatDate(item.tgl.toString())
                tvDateTransac.text = formattedDate

                if (item.status == "pemasukan") {
                    binding.apply {
                        tvValueTransac.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green_miskin))
                        ivArrow.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_arrow_up))
                        ivArrow.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.green_miskin))
                    }
                } else {
                    binding.apply {
                        tvValueTransac.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
                        ivArrow.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_arrow_down))
                        ivArrow.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.red))
                    }
                }

                root.setOnClickListener {
                    val id = item.id
                    MaterialAlertDialogBuilder(context)
                        .setTitle("Delete this transaction")
                        .setMessage("Are you sure you want to delete this transaction?")
                        .setPositiveButton("Yes") { _, _ ->
                            if (id != null) {
                                viewModel.deleteTransaction(id)
                            }

                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.MyViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, viewModel, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryItem>(){
            override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}