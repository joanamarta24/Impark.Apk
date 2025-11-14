package com.example.imparkapk.data.dao.remote.api.dto

import androidx.privacysandbox.ads.adservices.adid.AdId
import java.util.Date

data class ClienteDto(
    val id: Long,
    val nome: String,
    val email: String,
    val tipo: String,
    val dataCliacao: Date,
    val  dataAtualizacao: Date,
    val ativo: Boolean,
    val ultimoAcesso: Date?
)

