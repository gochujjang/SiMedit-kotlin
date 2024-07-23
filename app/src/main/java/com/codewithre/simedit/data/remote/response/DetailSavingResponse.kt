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

data class User(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class TransaksiPortoItem(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("nominal")
	val nominal: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("portomember_id")
	val portomemberId: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SavingDetailItem(

	@field:SerializedName("transaksi_porto")
	val transaksiPorto: List<TransaksiPortoItem?>? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("porto_member_id")
	val portoMemberId: Int? = null,

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
