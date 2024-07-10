package com.codewithre.simedit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithre.simedit.R
import com.codewithre.simedit.data.remote.response.SavingItem
import com.codewithre.simedit.databinding.ItemSavingsMiniBinding

class SavingMiniAdapter : ListAdapter<SavingItem, SavingMiniAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemSavingsMiniBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SavingItem) {
            binding.apply {
                tvTitleSaving.text = item.title
                if (item.persentase!! >= 100) {
                    tvProgressSaving.text = binding.root.context.getString(R.string.finished)
                } else {
                    tvProgressSaving.text = binding.root.context.getString(R.string.progress_savings, item.persentase)
                }
                progressIndicatorSaving.progress = item.persentase
            }
        }
    }


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SavingMiniAdapter.MyViewHolder {
            val binding = ItemSavingsMiniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SavingMiniAdapter.MyViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item)
        }

    companion object {
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