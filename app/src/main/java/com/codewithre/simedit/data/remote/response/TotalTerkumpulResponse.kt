package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName

data class TotalTerkumpulResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: TerkumpulData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TerkumpulData(

	@field:SerializedName("total_terkumpul")
	val totalTerkumpul: String? = null
)
