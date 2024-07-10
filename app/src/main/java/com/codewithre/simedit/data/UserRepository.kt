package com.codewithre.simedit.data

import com.codewithre.simedit.data.database.models.User
import com.codewithre.simedit.data.pref.UserPreference
import com.codewithre.simedit.data.remote.response.AddSavingResponse
import com.codewithre.simedit.data.remote.response.AddTransacResponse
import com.codewithre.simedit.data.remote.response.AddTransacSavingResponse
import com.codewithre.simedit.data.remote.response.BalanceResponse
import com.codewithre.simedit.data.remote.response.DetailSavingResponse
import com.codewithre.simedit.data.remote.response.HistoryResponse
import com.codewithre.simedit.data.remote.response.SavingLatestResponse
import com.codewithre.simedit.data.remote.response.SavingResponse
import com.codewithre.simedit.data.remote.response.UserResponse
import com.codewithre.simedit.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getHistory() : HistoryResponse {
        return withContext(Dispatchers.IO) {
            apiService.getHistory()
        }
    }

    suspend fun getTotalBalance() : BalanceResponse {
        return withContext(Dispatchers.IO) {
            apiService.getTotalBalance()
        }
    }

    suspend fun getTotalIncome() : BalanceResponse {
        return withContext(Dispatchers.IO) {
            apiService.getTotalIncome()
        }
    }

    suspend fun getTotalExpense() : BalanceResponse {
        return withContext(Dispatchers.IO) {
            apiService.getTotalExpense()
        }
    }

    suspend fun getTotalSave() : BalanceResponse {
        return withContext(Dispatchers.IO) {
            apiService.getTotalSave()
        }
    }

    suspend fun getTotalSaveTarget() : BalanceResponse {
        return withContext(Dispatchers.IO) {
            apiService.getTotalSaveTarget()
        }
    }

    suspend fun getUser() : UserResponse {
        return withContext(Dispatchers.IO) {
            apiService.getUser()
        }
    }

    suspend fun getSaving() : SavingResponse {
        return withContext(Dispatchers.IO) {
            apiService.getSaving()
        }
    }

    suspend fun getSavingLatest() : SavingLatestResponse {
        return withContext(Dispatchers.IO) {
            apiService.getSavingLatest()
        }
    }

    suspend fun getDetailSaving(id : Int) : DetailSavingResponse {
        return withContext(Dispatchers.IO) {
            apiService.getDetailSaving(id)
        }
    }
    suspend fun addTransactionSaving(
        status : String,
        nominal : String,
        porto_id : Int,
        keterangan : String
    ) : AddTransacSavingResponse {
        return withContext(Dispatchers.IO) {
            apiService.addTransactionSaving(
                status,
                nominal,
                keterangan,
                porto_id
            )
        }
    }

    suspend fun addTransaction(
        status : String,
        nominal : String,
        tgl : String,
        keterangan : String
        ) : AddTransacResponse {
        return withContext(Dispatchers.IO) {
            apiService.addTransaction(
                status,
                nominal,
                tgl,
                keterangan
            )
        }
    }

    suspend fun addSaving(
        title : String,
        target : Int
    ) : AddSavingResponse {
        return withContext(Dispatchers.IO) {
            apiService.addSaving(
                title,
                target
            )
        }
    }

    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) = UserRepository(apiService, userPreference)
    }
}