package com.codewithre.simedit.ui.add.transacsaving

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.ActivityAddTransacSavingBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.ui.savings.SavingsFragment
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale

class AddTransacSavingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransacSavingBinding
    private val viewModel: AddTransacSavingViewModel by viewModels<AddTransacSavingViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val sDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var typeChecked : String = "pemasukan"
    private var id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddTransacSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        id = intent.getIntExtra(SavingsFragment.EXTRA_ID, 0)
        Log.d("COY AddTransacSavingActivity", "onCreate: $id")

        setupToggleButtonGroup()
        setupBackButton()
        setAddTransaction()
    }

    private fun setAddTransaction() {
        binding.apply {
            btnAddTransaction.setOnClickListener {
                val typeTransac = typeChecked
                val balance = edBalance.value.toInt()
                val desc = edDesc.text
                val portoId = id

                if (balance == 0) {
                    edBalance.error = "Balance can't be 0, please enter balance amount"
                    return@setOnClickListener
                } else if (desc?.isEmpty() == true) {
                    edDesc.error = "Description can't be empty"
                    return@setOnClickListener
                } else {
                    Log.d("COY FIELD", "setAddTransaction: $typeTransac balance : $balance desc : $desc porto : $portoId")
                    viewModel.addSavingTransaction(
                        typeTransac,
                        balance,
                        portoId,
                        desc.toString(),
                    )
                    finish()
                }
            }
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupToggleButtonGroup() {
        //set default to income btn
        binding.toggleButtonGroup.check(R.id.btn_income)
        typeChecked = "pemasukan"

        setButtonStyle(binding.btnIncome, true)
        setButtonStyle(binding.btnExpenses, false)

        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btn_income -> {
                        showToast("Income")
                        typeChecked = "pemasukan"
                        setButtonStyle(binding.btnIncome, true)
                        setButtonStyle(binding.btnExpenses, false)
                    }
                    R.id.btn_expenses -> {
                        showToast("Expenses")
                        typeChecked = "pengeluaran"
                        setButtonStyle(binding.btnIncome, false)
                        setButtonStyle(binding.btnExpenses, true)
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setButtonStyle(button: MaterialButton, isActive: Boolean) {
        if (isActive) {
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            button.strokeWidth = 0
        } else {
            button.setTextColor(ContextCompat.getColor(this, R.color.blue))
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.bg)
            button.strokeColor = ContextCompat.getColorStateList(this, R.color.blue)
            button.strokeWidth = 5
        }
    }
}