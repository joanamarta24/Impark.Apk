package com.example.imparkapk.data.dao.remote.api.dto.usuario

import java.time.LocalDate
import java.time.LocalDateTime

data class ClienteDTO(
    val id: Long? = null,
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String? = null,
    val dataNascimento: String? = null, // ou LocalDate
    val endereco: String? = null,
    val cep: String? = null,
    val cidade: String? = null,
    val estado: String? = null,
    val isAtivo: Boolean = true,
    val tipoUsuario: String = "CLIENTE",
    val dataCadastro: LocalDateTime? = null,
    val ultimoAcesso: LocalDateTime? = null,
    val preferencias: List<String>? = null, // ["COBERTURA", "SEGURANCA", etc.]
    val fotoPerfil: String? = null,
    val notificacoesAtivas: Boolean = true,
    val termosAceitos: Boolean = true,
    val dataTermosAceitos: LocalDateTime? = null,
    val totalReservas: Int? = 0,
    val avaliacaoMedia: Double? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
