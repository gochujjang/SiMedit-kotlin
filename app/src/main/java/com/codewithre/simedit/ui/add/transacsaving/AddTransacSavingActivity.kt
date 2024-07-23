package com.codewithre.simedit.ui.add.transacsaving

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codewithre.simedit.R
import com.codewithre.simedit.databinding.ActivityAddTransacSavingBinding
import com.codewithre.simedit.ui.ViewModelFactory
import com.codewithre.simedit.ui.add.transacsaving.CameraActivity.Companion.CAMERAX_RESULT
import com.codewithre.simedit.ui.savings.SavingsFragment
import com.codewithre.simedit.ui.savings.detail.DetailSavingViewModel
import com.codewithre.simedit.utils.reduceFileImage
import com.codewithre.simedit.utils.uriToFile
import com.google.android.material.button.MaterialButton
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Locale

class AddTransacSavingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransacSavingBinding
    private val viewModel: AddTransacSavingViewModel by viewModels<AddTransacSavingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModelDetail by viewModels<DetailSavingViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val sDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var typeChecked : String = "pemasukan"
    private var id : Int = 0
    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
            }
        }

    private fun allPermissionGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddTransacSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (!allPermissionGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        id = intent.getIntExtra(SavingsFragment.EXTRA_ID, 0)
        Log.d("COY AddTransacSavingActivity", "onCreate: $id")

        setupToggleButtonGroup()
        setupBackButton()
        binding.btnAddPhoto.setOnClickListener { startCamera() }
        binding.btnAddTransaction.setOnClickListener { uploadTransaction() }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelDetail.getDetailSaving(id)
    }

    private fun uploadTransaction() {
        val typeTransac = typeChecked
        val balance = binding.edBalance.value.toInt()
        val desc = binding.edDesc.text
        val portoId = id

        if (balance == 0) {
            binding.edBalance.error = "Balance can't be 0, please enter balance amount"
        } else if (desc?.isEmpty() == true) {
            binding.edDesc.error = "Description can't be empty"
        } else {
            Log.d("COY FIELD", "setAddTransaction: $typeTransac balance : $balance desc : $desc porto : $portoId")
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, this).reduceFileImage()
                Log.d("Image File", "showImage: ${imageFile.path}")
                val description = "Ini adalah deksripsi gambar"

                showLoading(true)
                val requestBody = description.toRequestBody("text/plain".toMediaType())
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "foto",
                    imageFile.name,
                    requestImageFile
                )
                val status = typeTransac.toRequestBody("text/plain".toMediaType())
                val nominal = balance.toString().toRequestBody("text/plain".toMediaType())
                val keterangan = desc.toString().toRequestBody("text/plain".toMediaType())
                val portomember_id = portoId.toString().toRequestBody("text/plain".toMediaType())
                viewModel.addSavingTransaction(
                    status,
                    nominal,
                    portomember_id,
                    keterangan,
                    requestBody,
                    multipartBody,
                    )
                showLoading(false)
                finish()
            }
        }
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startCameraLauncher.launch(intent)
    }

    private val startCameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == CAMERAX_RESULT) {
                currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
                Log.d("Image URI", "startCameraLauncher: $currentImageUri")
                showImage()
            }
        }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivPreview.setImageURI(it)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}