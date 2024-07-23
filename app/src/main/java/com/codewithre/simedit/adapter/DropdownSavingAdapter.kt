package com.codewithre.simedit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithre.simedit.data.remote.response.DropdownItem
import com.codewithre.simedit.databinding.ItemTimefilterBinding

class DropdownSavingAdapter : ListAdapter<DropdownItem, DropdownSavingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemTimefilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DropdownItem) {
            binding.tvSavingName.text = item.title
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DropdownSavingAdapter.MyViewHolder {
        val binding = ItemTimefilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DropdownSavingAdapter.MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DropdownItem>(){
            override fun areItemsTheSame(oldItem: DropdownItem, newItem: DropdownItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DropdownItem, newItem: DropdownItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}