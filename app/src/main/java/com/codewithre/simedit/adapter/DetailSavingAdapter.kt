package com.codewithre.simedit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithre.simedit.R
import com.codewithre.simedit.data.remote.response.TransaksiPortoItem
import com.codewithre.simedit.databinding.ItemTransactionBinding
import com.codewithre.simedit.databinding.ItemTransactionsavingBinding
import com.codewithre.simedit.ui.savings.detail.TransacProofActivity
import com.codewithre.simedit.utils.formatCurrency
import com.codewithre.simedit.utils.formatDate

class DetailSavingAdapter : ListAdapter<TransaksiPortoItem, DetailSavingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemTransactionsavingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransaksiPortoItem) {
            binding.apply {
                tvTitleTransac.text = item.keterangan
                tvValueTransac.text = formatCurrency(item.nominal?.toLong())
                val formattedDate = formatDate(item.createdAt.toString())
                tvDateTransac.text = formattedDate
                tvUserName.text = item.user?.name ?: "Null"

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
                    val context = it.context
                    val intent = Intent(context, TransacProofActivity::class.java).apply {
                        putExtra(EXTRA_PIC, item.foto)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemTransactionsavingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        const val EXTRA_PIC = "extra_pic"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransaksiPortoItem>(){
            override fun areItemsTheSame(oldItem: TransaksiPortoItem, newItem: TransaksiPortoItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TransaksiPortoItem, newItem: TransaksiPortoItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}