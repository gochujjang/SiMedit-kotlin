package com.codewithre.simedit.ui.add.transaction

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.ActivityAddTransactionBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private val viewModel: AddTransactionViewModel by viewModels<AddTransactionViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val sDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var typeChecked : String = "pemasukan"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupToggleButtonGroup()
        setupBackButton()
        setupDatePicker()
        addTransaction()

        viewModel.addTransactionResult.observe(this) { response ->
            if (response.message == "success") {
                showToast("Transaction added successfully")
                viewModel.notifyTransactionChanged()
                finish()
            } else {
                showToast("Failed to add transaction")
            }
        }
    }

    private fun addTransaction() {
        binding.apply {
            btnAddTransaction.setOnClickListener {
                val typeTransac = typeChecked
                val balance = edBalance.value
                val dateTransac = edDate.text.toString()
                val desc = edDesc.text

                if (balance.toInt() == 0) {
                    edBalance.error = "Balance can't be 0, please enter balance amount"
                    return@setOnClickListener
                } else if (desc?.isEmpty() == true) {
                    edDesc.error = "Description can't be empty"
                    return@setOnClickListener
                } else {
                    viewModel.addTransaction(
                        typeTransac,
                        balance.toString(),
                        dateTransac,
                        desc.toString())
                }
            }
        }
    }

    private fun setupDatePicker() {
        binding.apply {
            edDate.setText(sDF.format(System.currentTimeMillis()))

            edDate.setInputType(InputType.TYPE_NULL)
            edDate.setOnClickListener {
                val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())

                val datePicker = datePickerBuilder.build()
                datePicker.show(supportFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    val selectedDate = sDF.format(it)
                    edDate.setText(selectedDate)
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


