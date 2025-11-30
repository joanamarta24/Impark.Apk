package com.example.imparkapk.data.dao.remote.api.dto

import com.example.imparkapk.data.dao.model.enus.NivelAcesso
import com.example.imparkapk.data.dao.remote.api.response.EstacionamentoInfo
import java.util.Date


data class GerenteDto(
    val id: String,
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val nivelAcesso: Int,
    val dataCriacao: Date,
    val dataAtualizacao: Date,
    val ativo: Boolean = true,

    // Relacionamentos (opcionais - podem ser IDs ou DTOs completos)
    val estacionamentoId: Long? = null,
    val estacionamento: EstacionamentoDto? = null

)
// Versão simplificada para criação/atualização
data class GerenteCreateDto(
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val estacionamentoId: Long? = null
)

// Versão para resposta (sem dados sensíveis)
data class GerenteResponseDto(
    val id: Long,
    val nome: String,
    val email: String,
    val telefone: String,
    val ativo: Boolean,
    val estacionamento: EstacionamentoDto? = null,
    val nivelAcesso: NivelAcesso
)

