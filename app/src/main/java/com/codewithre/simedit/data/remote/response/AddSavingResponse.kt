package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class AddSavingResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: SavingData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class SavingData(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("terkumpul")
	val terkumpul: BigInteger? = null,

	@field:SerializedName("target")
	val target: BigInteger? = null
)
