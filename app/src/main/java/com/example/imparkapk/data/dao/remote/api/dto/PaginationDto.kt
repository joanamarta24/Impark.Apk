package com.example.imparkapk.data.dao.remote.api.dto

import com.example.imparkapk.data.dao.remote.api.response.PaginationInfo
import com.google.gson.annotations.SerializedName


data class PaginationDto<T>(
    @SerializedName("data")
    val data: List<T>,

    @SerializedName("pagination")
    val pagination: PaginationInfo

) {
    //METODOS DE UTILIDADE
    fun hasNextPage(): Boolean = pagination.currentPage < pagination.totalPage

    fun hasPreviousPage(): Boolean = pagination.currentPage > 1
    fun getNextPage(): Int? = if (hasNextPage()) pagination.currentPage + 1 else null
    fun getPreviousPage(): Int? = if (hasPreviousPage()) pagination.currentPage - 1 else null
    fun isEmpty(): Boolean = data.isEmpty()
    fun isNotEmpty(): Boolean = data.isNotEmpty()
    fun size(): Int = data.size
    fun getTotaItems(): Int = pagination.totalIntems

    //OPERADOR PARA ACESSAR ELEMENTOS
    operator fun get(index: Int): T = data[index]

    //CONVERTER PARA LISTA SIMPLES
    fun toList(): List<T> = data

    //MAPEAR OS DADOS
    fun <R> map(transfor: (T) -> R): PaginationDto<R> {
        return PaginationDto(
            data = data.map(transfor),
            pagination = pagination
        )
    }

    //FILTAR OS DADOS MATENDO A PAGINA
    fun filter(predicate: (T) -> Boolean): PaginationDto<T> {
        return PaginationDto(
            data = data.filter(predicate),
            pagination = pagination.copy(totalIntems = data.count(predicate))
        )
    }
    //ADICIONAR MAIS DADOS(U´LTIMO PARA PAGINA INFINITA)
    fun append(newData: PaginationDto<T>):PaginationDto<T> {
        return PaginationDto(
            data = this.data + newData.data,
            pagination = newData.pagination.copy(
                totalIntems = this.pagination.totalIntems
            )
        )
    }
}
data class PaginatioInfo(
    @SerializedName("current_page")
    val currentPage: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_items")
    val totalItems: Int,

    @SerializedName("items_per_page")
    val itemsPerPage: Int,

    @SerializedName("has_next")
    val hasNext: Boolean,

    @SerializedName("has_previous")
    val hasPrevious: Boolean
) {
    //MÉTODOS DE UTILIDADE
    fun isFirsrPage(): Boolean = currentPage == 1
    fun isLastPage(): Boolean = currentPage == totalPages
    fun getPageRange(): IntRange = 1..totalPages

    fun getDisplayRange(maxPages: Int = 5): IntRange {
        val half = maxPages / 2
        var start = currentPage - half
        var end = currentPage + half
        if (start < 1) {
            end += 1 - start
            start = 1
            if (end > totalPages) {
                start -= end - totalPages
                end = totalPages
            }

            start = start.coerceAtLeast(1)
            end = end.coerceAtMost(totalPages)

            return start..end
        }
    }
}
data class PaginationRequest(
    val page: Int = 1,
    val limit: Int = 20,
    val sortBy: String? = null,
    val sortOrder: String = "asc", // "asc" or "desc"
    val search: String? = null
) {
    fun toQueryMap(): Map<String, String> {
        val map = mutableMapOf(
            "page" to page.toString(),
            "limit" to limit.toString()
        )

        sortBy?.let {
            map["sort_by"] = it
            map["sort_order"] = sortOrder
        }

        search?.let {
            map["search"] = it
        }

        return map
    }

    fun nextPage(): PaginationRequest {
        return copy(page = page + 1)
    }

    fun previousPage(): PaginationRequest {
        return copy(page = (page - 1).coerceAtLeast(1))
    }

    fun withPage(newPage: Int): PaginationRequest {
        return copy(page = newPage.coerceAtLeast(1))
    }

    fun withLimit(newLimit: Int): PaginationRequest {
        return copy(limit = newLimit.coerceAtLeast(1))
    }
}