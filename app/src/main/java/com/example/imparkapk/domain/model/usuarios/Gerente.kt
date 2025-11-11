package com.example.imparkapk.domain.model.usuarios

data class Gerente(
    val id: String = "",
    val usuarioId: String = "",
    val estacionamentoId: String = "",
    val nivelAcesso: Int = 1, // 1-Gerente, 2-Supervisor, 3-Funcionário
    val ativo: Boolean = true
) {
    val isGerente: Boolean get() = nivelAcesso == 1
    val isSupervisor: Boolean get() = nivelAcesso == 2
    val isFuncionario: Boolean get() = nivelAcesso == 3
}