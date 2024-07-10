package com.codewithre.simedit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithre.simedit.R
import com.codewithre.simedit.data.remote.response.HistoryItem
import com.codewithre.simedit.databinding.ItemTransactionBinding
import com.codewithre.simedit.utils.formatCurrency
import com.codewithre.simedit.utils.formatDate
import java.text.NumberFormat
import java.util.Locale

class HistoryAdapter : ListAdapter<HistoryItem, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryItem) {
            binding.apply {
                tvTitleTransac.text = item.keterangan
                tvValueTransac.text = formatCurrency(item.nominal)
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
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.MyViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
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