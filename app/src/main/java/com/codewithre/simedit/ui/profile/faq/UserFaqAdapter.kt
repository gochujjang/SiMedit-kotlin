package com.codewithre.simedit.ui.profile.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithre.simedit.data.entity.Faq
import com.codewithre.simedit.databinding.ItemFaqBinding

class UserFaqAdapter : ListAdapter<Faq, UserFaqAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: Faq, listener: (Int) -> Unit) {
            binding.apply {
                tvQuestion.text = faq.question
                tvAnswer.text = faq.answer
                tvAnswer.visibility = if (faq.isExpanded) View.VISIBLE else View.GONE
            }

            binding.root.setOnClickListener {
                listener(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val faq = getItem(position)
        holder.bind(faq) {
            toggleItemExpansion(position)
        }
    }

    private fun toggleItemExpansion(position: Int) {
        val currentItem = getItem(position)
        currentItem.isExpanded = !currentItem.isExpanded
        notifyItemChanged(position)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Faq>() {
            override fun areItemsTheSame(oldItem: Faq, newItem: Faq): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Faq, newItem: Faq): Boolean {
                return oldItem == newItem
            }
        }
    }
}