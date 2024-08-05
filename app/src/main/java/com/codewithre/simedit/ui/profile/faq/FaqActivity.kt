package com.codewithre.simedit.ui.profile.faq

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithre.simedit.R
import com.codewithre.simedit.data.entity.Faq
import com.codewithre.simedit.databinding.ActivityFaqBinding

class FaqActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqBinding
    private lateinit var adapter: UserFaqAdapter
    private var faqList = ArrayList<Faq>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addDataToList()
        setupRecyclerView()
        setBackBtn()
    }

    private fun setBackBtn() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = UserFaqAdapter()
        binding.rvFaq.setHasFixedSize(true)
        binding.rvFaq.layoutManager = LinearLayoutManager(this)
        binding.rvFaq.adapter = adapter
        adapter.submitList(faqList)
    }


    private fun addDataToList() {
        faqList.add(
            Faq(
                getString(R.string.faq_q1),
                getString(R.string.faq_a1),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q2),
                getString(R.string.faq_a2),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q3),
                getString(R.string.faq_a3),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q4),
                getString(R.string.faq_a4),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q5),
                getString(R.string.faq_a5),
            )
        )
    }
}