package com.example.imparkapk.data.dao.remote.api.dto.avaliacao

import com.example.imparkapk.data.dao.remote.api.dto.EstacionamentoDto
import com.example.imparkapk.data.dao.remote.api.dto.PaginationDto
import java.util.Date

data class AvaliacaoDto(
    val id: String,
    val usuarioId: String,
    val estacionamentoId: String,
    val reservaId: String,
    val nota: Int,
    val comentario: String?,
    val tags: List<String>,
    val dataAvaliacao: Date,
    val usuarioNome: String? = null,
    val estacionamentoNome: String? = null,
    val likeCount: Int = 0,
    val usuarioCurtiu: Boolean = false
)
data class EstatisticasAvaliacaoDTO(
    val mediaGeral: Double,
    val totalAvaliacoes: Int,
    val distribuicaoNotas: Map<Int, Int>,
    val totalComentarios: Int,
    val tagsMaisUsadas: List<TagCountDTO>
)

data class TagCountDTO(
    val tag: String,
    val count: Int
)

data class AvaliaxaoDetailResponse(
    val avaliacao: AvaliacaoDto,
    val estacionamento: EstacionamentoDto,
    val pagination: PaginationDto

)
data class AvaliacaoCreateResponse(
    val avaliacao: AvaliacaoDto,
    val mensagem: String = "Avaliação criada com sucesso"
)
data class AvaliacaoSummaryResponse(
    val estacionamentoId: String,
    val mediaNotas: Double,
    val totalAvaliacoes: Int,
    val distribuicao: Map<Int, Int>,
    val ultimasAvaliacoes: List<AvaliacaoDto>
)

data class AvaliacaoLikeRequest(
    val avaliacaoId: String,
    val usuarioId: String
)

data class AvaliacaoLikeResponse(
    val likeCount: Int,
    val usuarioCurtiu: Boolean
)
