package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName

data class EditSavingResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: EditedData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class EditedData(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("terkumpul")
	val terkumpul: Int? = null,

	@field:SerializedName("target")
	val target: String? = null,

	@field:SerializedName("persentase")
	val persentase: Int? = null
)
