package com.codewithre.simedit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithre.simedit.R
import com.codewithre.simedit.data.remote.response.MemberItem
import com.codewithre.simedit.data.remote.response.SavingItem
import com.codewithre.simedit.databinding.ItemMemberBinding
import com.codewithre.simedit.ui.savings.listmember.ListMemberViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MemberAdapter(private val viewModel: ListMemberViewModel, private val context: Context) : ListAdapter<MemberItem, MemberAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemMemberBinding, private val viewModel: ListMemberViewModel, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MemberItem) {
            binding.apply {
                tvNameMember.text = item.userData?.name ?: "Null"
                tvEmailMember.text = item.userData?.email ?: "Null"

                if (item.status == "owner") {
                    tvStatusMember.setTextColor(context.getColor(R.color.red))
                    tvStatusMember.text = "Owner"
                } else {
                    tvStatusMember.setTextColor(context.getColor(R.color.black))
                    tvStatusMember.text = "Member"
                }

                root.setOnClickListener {
                    val portoId = item.portofolioId
                    val memberId = item.userId

                    MaterialAlertDialogBuilder(context)
                        .setTitle("Delete user from saving plan")
                        .setMessage("Are you sure you want to delete this user member?")
                        .setPositiveButton("Yes") { _, _ ->
                            viewModel.deleteMember(portoId!!, memberId!!)
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.MyViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, viewModel, context)
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