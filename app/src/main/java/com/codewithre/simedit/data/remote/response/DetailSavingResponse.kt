package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailSavingResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: SavingDetailItem? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class SavingDetailItem(

	@field:SerializedName("transaksi_porto")
	val transaksiPorto: List<TransaksiPortoItem?>? = null,

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
	val terkumpul: Int? = null,

	@field:SerializedName("target")
	val target: Int? = null,

	@field:SerializedName("persentase")
	val persentase: Int? = null
)

data class TransaksiPortoItem(

	@field:SerializedName("nominal")
	val nominal: Int? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("porto_id")
	val portoId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)