package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName

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
	val nominal: String? = null,

	@field:SerializedName("porto_id")
	val portoId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
