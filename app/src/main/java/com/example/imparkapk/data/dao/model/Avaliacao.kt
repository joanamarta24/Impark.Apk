package com.example.imparkapk.data.dao.model

import java.util.Date

data class Avaliacao(
    val id: Long ,
    val usuarioId: String = "",
    val estacionamentoId: String = "",
    val nota: Int = 5, // 1-5
    val comentario: String = "",
    val dataAvaliacao: Date = Date(),

    val respostaEstabelecimento: String? = null,
    val dataResposta: Date? = null,
    val foiUtil: Boolean = false,
    val totalUtil: Int = 0
) {
    val notaEmEstrelas: String get() = "★".repeat(nota) + "☆".repeat(5 - nota)
    val temComentario: Boolean get() = comentario.isNotBlank()
}
