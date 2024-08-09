package com.codewithre.simedit.adapter

import android.content.Intent
import android.util.TypedValue
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
import com.codewithre.simedit.utils.formatShortCurrency

class DetailSavingAdapter : ListAdapter<TransaksiPortoItem, DetailSavingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemTransactionsavingBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isShortFormat: Boolean = true

        fun bind(item: TransaksiPortoItem) {
            binding.apply {
                tvTitleTransac.text = item.keterangan
                tvValueTransac.text = (if (item.nominal!! > limit_balance.toBigInteger()) {
                    formatShortCurrency(item.nominal.toLong())
                } else {
                    formatCurrency(item.nominal.toLong())
                }
                        ).toString()
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

                tvValueTransac.setOnClickListener {
                    isShortFormat = !isShortFormat
                    if(isShortFormat && item.nominal > limit_balance.toBigInteger()) {
                        tvValueTransac.text = formatShortCurrency(item.nominal.toLong())
                        tvValueTransac.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                    } else {
                        tvValueTransac.text = formatCurrency(item.nominal.toLong())
                        if (item.nominal > limit_balance.toBigInteger()) {
                            tvValueTransac.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                        } else {
                            tvValueTransac.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                        }
                    }
                }

                root.setOnClickListener {
                    val context = it.context
                    val intent = Intent(context, TransacProofActivity::class.java).apply {
                        putExtra(EXTRA_PIC, item.foto)
                        putExtra(EXTRA_ID, item.id)
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
        const val EXTRA_ID = "extra_id"
        const val limit_balance = 999_999_999

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