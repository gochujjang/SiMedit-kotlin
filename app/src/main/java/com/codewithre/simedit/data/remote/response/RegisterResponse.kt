package com.codewithre.simedit.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: RegisterData? = null,

	@field:SerializedName("email")
	var email: List<String>?,

	@field:SerializedName("username")
	var username: List<String>?,
)

data class RegisterData(

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

//data class RegErrorResponse(
//	@field:SerializedName("email")
//	var email: List<String>?,
//
//	@field:SerializedName("username")
//	var username: List<String>?,
//)