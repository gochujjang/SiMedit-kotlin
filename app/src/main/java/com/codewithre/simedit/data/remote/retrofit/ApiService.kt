package com.codewithre.simedit.data.remote.retrofit

import com.codewithre.simedit.data.remote.response.AddSavingResponse
import com.codewithre.simedit.data.remote.response.AddTransacResponse
import com.codewithre.simedit.data.remote.response.AddTransacSavingResponse
import com.codewithre.simedit.data.remote.response.BalanceResponse
import com.codewithre.simedit.data.remote.response.DetailSavingResponse
import com.codewithre.simedit.data.remote.response.DropdownSavingResponse
import com.codewithre.simedit.data.remote.response.HistoryResponse
import com.codewithre.simedit.data.remote.response.InviteResponse
import com.codewithre.simedit.data.remote.response.LoginResponse
import com.codewithre.simedit.data.remote.response.LogoutResponse
import com.codewithre.simedit.data.remote.response.RegisterData
import com.codewithre.simedit.data.remote.response.RegisterResponse
import com.codewithre.simedit.data.remote.response.ResetPassResponse
import com.codewithre.simedit.data.remote.response.SavingLatestResponse
import com.codewithre.simedit.data.remote.response.SavingResponse
import com.codewithre.simedit.data.remote.response.TotalTargetResponse
import com.codewithre.simedit.data.remote.response.TotalTerkumpulResponse
import com.codewithre.simedit.data.remote.response.UpdateProfileResponse
import com.codewithre.simedit.data.remote.response.UserResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("update-profile")
    suspend fun updateProfile(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("email") email: String,
    ) : UpdateProfileResponse

    @FormUrlEncoded
    @POST("reset-password")
    suspend fun resetPass(
        @Field("current_password") currentPass: String,
        @Field("password") password: String,
        @Field("password_confirmation") passConfirm: String,
    ) : ResetPassResponse

    @GET("logout")
    suspend fun logout() : LogoutResponse

    @GET("transaction")
    suspend fun getHistory() : HistoryResponse

    @GET("total-money")
    suspend fun getTotalBalance() : BalanceResponse

    @GET("income")
    suspend fun getTotalIncome() : BalanceResponse

    @GET("total-terkumpul")
    suspend fun getTotalSave() : TotalTerkumpulResponse

    @GET("total-target")
    suspend fun getTotalSaveTarget() : TotalTargetResponse

    @GET("expense")
    suspend fun getTotalExpense() : BalanceResponse

    @GET("user")
    suspend fun getUser() : UserResponse

    @GET("portofolio")
    suspend fun getSaving() : SavingResponse

    @GET("get-portofolio")
    suspend fun getDropdownSaving() : DropdownSavingResponse

    @FormUrlEncoded
    @POST("transaction")
    suspend fun addTransaction(
        @Field("status") status: String,
        @Field("nominal") nominal: String,
        @Field("tgl") tgl: String,
        @Field("keterangan") keterangan: String
    ) : AddTransacResponse

    @FormUrlEncoded
    @POST("portofolio")
    suspend fun addSaving(
        @Field("title") title: String,
        @Field("target") target: Int,
    ) : AddSavingResponse

    @GET("portofolio/{id}")
    suspend fun getDetailSaving(
        @Path("id") id: Int
    ) : DetailSavingResponse

    @FormUrlEncoded
    @POST("portofolio-transaction")
    suspend fun addTransactionSaving(
        @Field("status") status: String,
        @Field("nominal") nominal: Int,
        @Field("keterangan") keterangan: RequestBody,
        @Field("portomember_id") portomember_id: Int
    ) : AddTransacSavingResponse

    @FormUrlEncoded
    @POST("portofolio-invite")
    suspend fun inviteFriend(
        @Field("email") email: String,
        @Field("porto_id") portoId: Int,
    ) : InviteResponse

}