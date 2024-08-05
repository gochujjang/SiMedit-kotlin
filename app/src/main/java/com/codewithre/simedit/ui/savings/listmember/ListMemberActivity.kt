package com.codewithre.simedit.ui.savings.listmember

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithre.simedit.R
import com.codewithre.simedit.adapter.MemberAdapter
import com.codewithre.simedit.data.remote.response.MemberItem
import com.codewithre.simedit.databinding.ActivityListMemberBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.ui.savings.SavingsFragment

class ListMemberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMemberBinding
    private val viewModel by viewModels<ListMemberViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var id : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        id = intent.getIntExtra(SavingsFragment.EXTRA_ID, 0)
        viewModel.getListMember(id)
        viewModel.listMember.observe(this) {listMember ->
            if (listMember != null) {
                if (listMember.isEmpty()) {
                    showNotFound(true)
                } else {
                    showNotFound(false)
                    setListMember(listMember)
                }
            }
        }

        viewModel.deleteMember.observe(this) {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvListmember.layoutManager = layoutManager

        binding.btnBack.setOnClickListener { finish() }
        refreshData()

    }

    private fun refreshData() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListMember(id)

            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListMember(id)
    }

    private fun setListMember(listMember: List<MemberItem?>) {
        val adapter = MemberAdapter(viewModel, this)
        adapter.submitList(listMember)
        binding.rvListmember.adapter = adapter
    }

    private fun showNotFound(isEmptyTransac: Boolean = false) {
        binding.tvNotFoundTransac.visibility = if (isEmptyTransac) View.VISIBLE else View.GONE
    }
}