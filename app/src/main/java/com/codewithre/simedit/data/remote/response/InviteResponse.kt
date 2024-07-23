package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName

data class InviteResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: InviteData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class InviteData(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("portofolio_id")
	val portofolioId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
