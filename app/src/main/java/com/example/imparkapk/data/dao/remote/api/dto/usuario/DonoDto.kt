package com.example.imparkapk.data.dao.remote.api.dto.usuario

import java.time.LocalDateTime

data class DonoDto(
    val id: Long? = null,
    val nome: String,
    val email: String,
    val cpfCnpj: String,
    val telefone: String? = null,
    val tipoPessoa: String, // "FISICA" ou "JURIDICA"
    val razaoSocial: String? = null,
    val nomeFantasia: String? = null,
    val endereco: String? = null,
    val cep: String? = null,
    val cidade: String? = null,
    val estado: String? = null,
    val telefoneComercial: String? = null,
    val isAtivo: Boolean = true,
    val tipoUsuario: String = "DONO",
    val dataCadastro: LocalDateTime? = null,
    val ultimoAcesso: LocalDateTime? = null,
    val fotoPerfil: String? = null,
    val notificacoesAtivas: Boolean = true,
    val termosAceitos: Boolean = true,
    val dataTermosAceitos: LocalDateTime? = null,
    val estacionamentos: List<Long>? = null, // IDs dos estacionamentos
    val totalEstacionamentos: Int? = 0,
    val receitaTotal: Double? = 0.0,
    val ratingComoGestor: Double? = null,
    val bancoId: Long? = null, // Para pagamentos
    val contaBancaria: String? = null,
    val agenciaBancaria: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
data class DonoUpdateDto(
    val nome: String? = null,
    val telefone: String? = null,
    val telefoneComercial: String? = null,
    val endereco: String? = null,
    val cep: String? = null,
    val cidade: String? = null,
    val estado: String? = null,
    val fotoPerfil: String? = null,
    val notificacoesAtivas: Boolean? = null,
    val contaBancaria: String? = null,
    val agenciaBancaria: String? = null
)


