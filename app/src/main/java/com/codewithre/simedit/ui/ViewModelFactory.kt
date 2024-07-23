package com.codewithre.simedit.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.di.Injection
import com.codewithre.simedit.ui.add.saving.AddSavingViewModel
import com.codewithre.simedit.ui.add.transacsaving.AddTransacSavingViewModel
import com.codewithre.simedit.ui.add.transaction.AddTransactionViewModel
import com.codewithre.simedit.ui.history.HistoryViewModel
import com.codewithre.simedit.ui.home.HomeViewModel
import com.codewithre.simedit.ui.main.MainViewModel
import com.codewithre.simedit.ui.profile.ProfileViewModel
import com.codewithre.simedit.ui.profile.edit.EditProfileViewModel
import com.codewithre.simedit.ui.profile.reset.ResetPassViewModel
import com.codewithre.simedit.ui.savings.SavingsViewModel
import com.codewithre.simedit.ui.savings.detail.DetailSavingViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SavingsViewModel::class.java) -> {
                SavingsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddTransactionViewModel::class.java) -> {
                AddTransactionViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddSavingViewModel::class.java) -> {
                AddSavingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailSavingViewModel::class.java) -> {
                DetailSavingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddTransacSavingViewModel::class.java) -> {
                AddTransacSavingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ResetPassViewModel::class.java) -> {
                ResetPassViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context)= ViewModelFactory(Injection.provideRepository(context))
    }
}