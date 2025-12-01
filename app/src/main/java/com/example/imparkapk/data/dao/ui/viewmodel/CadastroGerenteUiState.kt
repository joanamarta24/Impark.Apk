package com.example.imparkapk.data.dao.ui.viewmodel

import com.example.imparkapk.data.dao.model.Gerente

data class CadastroGerenteUiState(
    // Campos do formulário
    val nome: String = "",
    val email: String = "",
    val cpf: String = "",
    val telefone: String = "",
    val nivelAcesso: Int = Gerente.NIVEL_FUNCIONARIO,
    val usuarioId: String = "",
    val estacionamentoId: String = "",

    // Estados da UI
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,

    // Validações
    val nomeError: String? = null,
    val emailError: String? = null,
    val cpfError: String? = null,
    val telefoneError: String? = null,
    val nivelAcessoError: String? = null,

    // Navegação
    val shouldNavigateBack: Boolean = false,
    val shouldShowSuccessDialog: Boolean = false,

    // Dados auxiliares
    val niveisAcesso: List<NivelAcessoItem> = listOf(
        NivelAcessoItem(
            id = Gerente.NIVEL_GERENTE,
            nome = "Gerente Principal",
            descricao = "Acesso total ao estacionamento"
        ),
        NivelAcessoItem(
            id = Gerente.NIVEL_SUPERVISOR,
            nome = "Supervisor",
            descricao = "Acesso parcial, pode gerenciar funcionários"
        ),
        NivelAcessoItem(
            id = Gerente.NIVEL_FUNCIONARIO,
            nome = "Funcionário",
            descricao = "Acesso básico às operações"
        )
    ),

    // Busca de usuário
    val isSearchingUser: Boolean = false,
    val usuarioEncontrado: UsuarioEncontrado? = null,
    val usuarioSearchError: String? = null,
    val showUsuarioSearchDialog: Boolean = false
)

data class NivelAcessoItem(
    val id: Int,
    val nome: String,
    val descricao: String
)

data class UsuarioEncontrado(
    val id: String,
    val nome: String,
    val email: String
)
