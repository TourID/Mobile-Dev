package com.bangkit2024.tourid.data

import com.google.gson.annotations.SerializedName

data class GithubSearchResponse(

	@field:SerializedName("items") val items: List<GithubUsersResponseItem>
)

data class GithubUsersResponseItem(

	@field:SerializedName("login") val login: String? = null,

	@field:SerializedName("avatar_url") val avatarUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)
