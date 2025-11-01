package com.example.imparkapk.data.dao.remote.api.response

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("succes")
    val success: Boolean,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("status_code")
    val statusCode: Int = 200

)
data class PaginatedResponse<T>(
    @SerializedName("data")
    val data: List<T>,
    @SerializedName("pagination")
    val pagination: PaginationInfo
)
data class PaginationInfo(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("per_page")
    val totalPage: Int,
    @SerializedName("total")
    val totalIntems: Int,
    @SerializedName("last_page")
    val itemsPerPage: Int,

)