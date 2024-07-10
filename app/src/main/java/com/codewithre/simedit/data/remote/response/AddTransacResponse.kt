package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddTransacResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: TransactionData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TransactionData(

	@field:SerializedName("nominal")
	val nominal: String? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("tgl")
	val tgl: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
