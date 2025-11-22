package com.example.imparkapk.data.dao.remote.api.dto


import java.time.LocalDateTime

data class GerenteDto(
    val id: Long? = null,
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val dataNascimento: LocalDateTime? = null,
    val dataCriacao: LocalDateTime? = LocalDateTime.now(),
    val dataAtualizacao: LocalDateTime? = LocalDateTime.now(),
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
    val matricula: String,
    val salario: Double? = null,
    val departamento: String? = null,
    val estacionamentoId: Long? = null
)

// Versão para resposta (sem dados sensíveis)
data class GerenteResponseDto(
    val id: Long,
    val nome: String,
    val email: String,
    val telefone: String,
    val matricula: String,
    val departamento: String?,
    val dataAdmissao: LocalDateTime?,
    val ativo: Boolean,
    val estacionamento: EstacionamentoDto? = null
)