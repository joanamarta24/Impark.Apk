package com.example.imparkapk.data.dao.remote.api.dto.avaliacao

// ✅ REQUEST DTOs
data class AvaliacaoCreateRequest(
    val usuarioId: String,
    val estacionamentoId: String,
    val reservaId: String,
    val nota: Int,
    val comentario: String? = null,
    val tags: List<String> = emptyList()
)

data class AvaliacaoUpdateRequest(
    val nota: Int? = null,
    val comentario: String? = null,
    val tags: List<String>? = null
)

data class AvaliacaoFilterRequest(
    val estacionamentoId: String? = null,
    val usuarioId: String? = null,
    val notaMinima: Int? = null,
    val notaMaxima: Int? = null,
    val comComentario: Boolean? = null,
    val page: Int = 1,
    val pageSize: Int = 20
)