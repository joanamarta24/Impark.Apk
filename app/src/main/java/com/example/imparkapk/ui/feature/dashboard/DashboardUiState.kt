package com.example.imparkapk.ui.feature.dashboard

import com.example.imparkapk.domain.model.Estacionamento
import com.example.imparkapk.domain.model.enuns.TipoDeUsuario
import kotlinx.coroutines.flow.Flow
import java.util.Date

data class DashboardUiState(
    // Informações do usuário logado

    var nome: String = "",
    var email: String = "",
    var senha: String = "",
    var confirmarSenha: String = "",
    var tipoDeUsuario: TipoDeUsuario = TipoDeUsuario.CLIENTE,
    var nascimento: Date = Date(),

    var estacionamentos: List<Estacionamento> = emptyList(),
    var pesquisa: String = "",

    // Informações inatas da tela

    var isLoading: Boolean = false,
    var errorMessage: String? = null,
)
