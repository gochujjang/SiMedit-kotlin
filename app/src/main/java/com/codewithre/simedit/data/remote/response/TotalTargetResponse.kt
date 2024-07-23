package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName

data class TotalTargetResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: TargetData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TargetData(

	@field:SerializedName("total_target")
	val totalTarget: String? = null
)
