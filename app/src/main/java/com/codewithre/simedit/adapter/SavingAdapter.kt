package com.codewithre.simedit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithre.simedit.R
import com.codewithre.simedit.data.remote.response.HistoryItem
import com.codewithre.simedit.data.remote.response.SavingItem
import com.codewithre.simedit.databinding.ItemSavingsBinding
import com.codewithre.simedit.ui.savings.detail.DetailSavingActivity
import com.codewithre.simedit.utils.formatCurrency

class SavingAdapter : ListAdapter<SavingItem, SavingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemSavingsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SavingItem) {
            binding.apply {
                tvTitleSaving.text = item.title
                tvMoneyCollected.text = formatCurrency(item.terkumpul?.toLong())
                tvTotalCollected.text = formatCurrency(item.target?.toLong())
                if (item.persentase!! >= 100) {
                    tvProgressSaving.text = binding.root.context.getString(R.string.finished)
                } else {
                    tvProgressSaving.text = binding.root.context.getString(R.string.progress_savings, item.persentase)
                }
                progressIndicatorSaving.progress = item.persentase

                root.setOnClickListener {
                    val context = it.context
                    val intent = Intent(context, DetailSavingActivity::class.java).apply {
                        putExtra(EXTRA_ID, item.portofolioId)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingAdapter.MyViewHolder {
        val binding = ItemSavingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavingAdapter.MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        const val EXTRA_ID = "extra_id"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SavingItem>(){
            override fun areItemsTheSame(oldItem: SavingItem, newItem: SavingItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SavingItem, newItem: SavingItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}