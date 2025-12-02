package com.example.imparkapk.UiState


import com.example.imparkapk.data.dao.local.dao.entity.Estacionamento
import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparkapk.data.dao.model.enus.Cliente
import com.example.imparkapk.domain.model.Carro


data class ClienteUiState(

    // Dados do usuário
    val usuario: Cliente? = null,
    val usuarios:List<Cliente> = emptyList(),

    // Formulários
    val nome:String ="",
    val email:String ="",
    val senha: String ="",
    val confirmaSenha: String ="",
    val tipoUsuario: String ="CLIENTE",
    // Listas de dados
    val carros: List<Carro> = emptyList(),
    val carrosExibidos: List<Carro> = emptyList(),
    val estacionamentos: List<Estacionamento> = emptyList(),
    val reservas: List<Reserva> = emptyList(),

    // Carros especiais
    val carroSelecionado: Carro? = null,
    val carroPrincipal: Carro? = null,
    val estacionamentoSelecionado: Estacionamento? = null,

    // Estados de carregamento
    val isLoadingCarros: Boolean = false,
    val isLoadingEstacionamentos: Boolean = false,
    val isLoadingReservas: Boolean = false,
    val isSincronizandoCarros: Boolean = false,
    val isExportandoCarros: Boolean = false,
    val isImportandoCarros: Boolean = false,
    val isProcessandoLote: Boolean = false,


    // Navegação
    val telaAtual: String = "HOME",
    val estacionamentoDetalhesId: String? = null,
    val reservaDetalhesId: String? = null,
    val reservaCriada: Boolean = false,

    // Filtros e buscas
    val searchQueryEstacionamentos: String = "",
    val searchQueryCarros: String = "",
    val filtroPrecoMaximo: Double? = null,
    val filtroCarros: String? = null,
    val modoBuscaCarros: String = "", // "MODELO", "MARCA", "COR", "ANO", "TODOS"


    // Localização
    val localizacaoUsuario: Pair<Double, Double>? = null,

    // Dados de exportação/importação
    val dadosExportados: String? = null,

    // Estados de loadin
    val isLoading: Boolean = false,
    val isLogging: Boolean = false,
    val isRegistering: Boolean = false,
    val loginSucesso: Boolean = false,
    val cadastroSucesso: Boolean = false,
    val mensagemErro: String = "",
    val mensagemSucesso: String = "",
    val emailValido: Boolean = true,
    val senhaValida: Boolean = true,
    val searchQuery: String = "",
    val usuariosFiltrados: List<Cliente> = emptyList(),
    val usuarioLogado: Cliente? = null,
    val sessaoAtiva: Boolean = false
)
