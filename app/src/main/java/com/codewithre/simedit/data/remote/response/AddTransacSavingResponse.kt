package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class AddTransacSavingResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: TransacSavingData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TransacSavingData(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("nominal")
	val nominal: BigInteger? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("user_Data")
	val userData: UserData? = null,

	@field:SerializedName("portomember_id")
	val portomemberId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class UserData(

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
