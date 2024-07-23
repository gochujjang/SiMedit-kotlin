package com.codewithre.simedit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithre.simedit.data.remote.response.MemberItem
import com.codewithre.simedit.data.remote.response.SavingItem
import com.codewithre.simedit.databinding.ItemMemberBinding

class MemberAdapter : ListAdapter<MemberItem, MemberAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MemberItem) {
            binding.apply {
                tvNameMember.text = item.userData?.name ?: "Null"
                tvEmailMember.text = item.userData?.email ?: "Null"
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.MyViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberAdapter.MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        const val EXTRA_ID = "extra_id"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MemberItem>(){
            override fun areItemsTheSame(oldItem: MemberItem, newItem: MemberItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MemberItem, newItem: MemberItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}