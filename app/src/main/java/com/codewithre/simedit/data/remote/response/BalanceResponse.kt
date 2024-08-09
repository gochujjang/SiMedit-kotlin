package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class BalanceResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: BigInteger? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
