package com.example.imparkapk.data.dao.remote.api.repository.gerente

import com.example.imparkapk.data.dao.remote.api.response.GerenteResponse

class GerenciadorPermissoes {
    companion object{
        fun podeExecutarAcao(gerente: GerenteResponse,acao: String): Boolean{
            return when (acao) {
                "CRIAR_FUNCIONARIO" -> gerente.podeGerenciarFuncionarios()
                "EDITAR_CONFIGURACOES" -> gerente.podeAlterarConfiguracoes()
                "VISUALIZAR_FINANCEIRO" -> gerente.temAcessoFinanceiro()
                "GERENCIAR_VAGAS" -> gerente.podeGerenciarEstacionamento()
                "VISUALIZAR_RELATORIO" -> gerente.podeVisualizarRelatorios()
                else -> false
            }
        }
        // Filtra lista de ações baseado nas permissões
        fun getAcoesPermitidas(gerente: GerenteResponse): List<String> {
            val todasAcoes = listOf(
                "CRIAR_FUNCIONARIO",
                "EDITAR_CONFIGURACOES",
                "VISUALIZAR_FINANCEIRO",
                "GERENCIAR_VAGAS",
                "VISUALIZAR_RELATORIO"
            )

            return todasAcoes.filter { acao ->
                podeExecutarAcao(gerente, acao)
            }
        }
    }
}