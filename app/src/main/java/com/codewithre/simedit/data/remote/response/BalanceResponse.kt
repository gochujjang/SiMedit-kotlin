package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName

data class BalanceResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Int? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
