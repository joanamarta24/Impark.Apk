package com.example.imparkapk.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.UiState.ClienteUiState
import com.example.imparkapk.data.dao.model.Estacionamento
import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparkapk.data.dao.remote.api.repository.carro.CarroRepository
import com.example.imparkapk.data.dao.remote.api.repository.estacionamento.EstacionamentoRepository
import com.example.imparkapk.data.dao.remote.api.repository.reserva.ReservaRepository
import com.example.imparkapk.data.dao.remote.api.repository.usuario.ClienteRepository
import com.example.imparkapk.domain.model.Carro
import com.example.imparkapk.model.Cliente
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onFailure

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository,
    private val carroRepository: CarroRepository,
    private val estacionamentoRepository: EstacionamentoRepository,
    private val reservaRepository: ReservaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClienteUiState())
    val uiState: StateFlow<ClienteUiState> = _uiState.asStateFlow()

    // INICIALIZAÇÃO
    init {
        carregarDadosIniciais()
    }

    private fun carregarDadosIniciais() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // Carrega dados do usuário logado
                carregarUsuarioLogado()

                // Carrega carros do usuário
                carregarCarrosDoUsuario()

                // Carrega reservas ativas
                carregarReservasAtivas()

                // Carrega estacionamentos próximos
                carregarEstacionamentosProximos()

                // Obtém carro principal
                obterCarroPrincipal()

                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro ao carregar dados: ${e.message}"
                    )
                }
            }
        }
    }

    // MÉTODOS BASE (que estavam faltando):

    // 1. Carregar usuário logado
    private suspend fun carregarUsuarioLogado() {
        try {
            // Em produção, pegaria do tokenStore
            // Por enquanto, simulação com ID fixo
            val resultado = clienteRepository.getClientePorId("usuario_teste_id")
            resultado.onSuccess { cliente ->
                _uiState.update { it.copy(usuarioLogado = cliente) }
            }.onFailure { erro ->

                println("Erro ao carregar usuário: ${erro.message}")
            }
        } catch (e: Exception) {
            println("Erro ao carregar usuário: ${e.message}")
        }
    }

    // 2. Carregar carros do usuário
    private fun carregarCarrosDoUsuario() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCarros = true) }

            try {
                val usuarioId = _uiState.value.usuarioLogado?.id ?: return@launch
                // Método alternativo se não houver obterCarrosPorUsuario
                val resultado = carroRepository.obterTodosCarros()

                resultado.onSuccess { todosCarros ->
                    // Filtrar carros do usuário atual
                    val carrosUsuario = todosCarros.filter { it.usuarioId == usuarioId }

                    _uiState.update {
                        it.copy(
                            isLoadingCarros = false,
                            carros = carrosUsuario,
                            carrosExibidos = carrosUsuario,
                            carroSelecionado = carrosUsuario.firstOrNull()
                        )
                    }
                }.onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoadingCarros = false,
                            mensagemErro = "Erro ao carregar carros: ${erro.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingCarros = false,
                        mensagemErro = "Erro ao carregar carros: ${e.message}"
                    )
                }
            }
        }
    }

    // 3. Carregar reservas ativas
    private fun carregarReservasAtivas() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingReservas = true) }

            try {
                val usuarioId = _uiState.value.usuarioLogado?.id ?: return@launch

                // Opção 1: Usar método que retorna todas as reservas
                val resultado = reservaRepository.obterTodasReservas()

                resultado.onSuccess { todasReservas ->
                    // Filtrar reservas do usuário atual
                    val reservasUsuario = todasReservas.filter { reserva ->
                        reserva.clienteId == usuarioId || reserva.usarioId == usuarioId
                    }

                    // Filtrar reservas ativas
                    val reservasAtivas = reservasUsuario.filter { reserva ->
                        reserva.status in listOf("CONFIRMADA", "PENDENTE", "EM_ANDAMENTO")
                    }

                    _uiState.update {
                        it.copy(
                            isLoadingReservas = false,
                            reservas = reservasAtivas
                        )
                    }
                }.onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoadingReservas = false,
                            mensagemErro = "Erro ao carregar reservas: ${erro.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingReservas = false,
                        mensagemErro = "Erro ao carregar reservas: ${e.message}"
                    )
                }
            }
        }
    }

    // 4. Carregar estacionamentos próximos
    private fun carregarEstacionamentosProximos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingEstacionamentos = true) }

            try {
                // Coordenadas de exemplo (São Paulo)
                val latitude = -23.5505
                val longitude = -46.6333
                val raioKm = 5.0

                val resultado = estacionamentoRepository.getEstacionamentosProximos(
                    latitude = latitude,
                    longitude = longitude,
                    raioKm = raioKm
                )

                resultado.onSuccess { estacionamentos ->
                    _uiState.update {
                        it.copy(
                            isLoadingEstacionamentos = false,
                            estacionamentos = estacionamentos,
                            estacionamentoSelecionado = estacionamentos.firstOrNull(),
                            localizacaoUsuario = Pair(latitude, longitude)
                        )
                    }
                }.onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoadingEstacionamentos = false,
                            mensagemErro = "Erro ao carregar estacionamentos: ${erro.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingEstacionamentos = false,
                        mensagemErro = "Erro ao carregar estacionamentos: ${e.message}"
                    )
                }
            }
        }
    }

    // Método corrigido na linha 372:
    fun limparBuscaCarros() {
        _uiState.update {
            it.copy(
                searchQueryCarros = "",
                modoBuscaCarros = "",
                filtroCarros = null
            )
        }
        carregarCarrosDoUsuario() // Parênteses adicionados
    }

    // Métodos auxiliares adicionais:

    fun atualizarPerfilUsuario(cliente: Cliente) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val resultado = clienteRepository.atualizarUsuario(cliente.id, cliente)
                resultado.onSuccess { clienteAtualizado ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            usuarioLogado = clienteAtualizado,
                            mensagemSucesso = "Perfil atualizado com sucesso!"
                        )
                    }
                }.onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemErro = "Erro ao atualizar perfil: ${erro.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro ao atualizar perfil: ${e.message}"
                    )
                }
            }
        }
    }

    fun adicionarCarro(carro: Carro) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCarros = true) }

            try {
                val resultado = carroRepository.salvarCarro(carro)
                resultado.onSuccess { carroSalvo ->
                    // Atualiza lista local
                    val novaLista = _uiState.value.carros + carroSalvo
                    _uiState.update {
                        it.copy(
                            isLoadingCarros = false,
                            carros = novaLista,
                            carrosExibidos = novaLista,
                            mensagemSucesso = "Carro adicionado com sucesso!"
                        )
                    }
                }.onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoadingCarros = false,
                            mensagemErro = "Erro ao adicionar carro: ${erro.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingCarros = false,
                        mensagemErro = "Erro ao adicionar carro: ${e.message}"
                    )
                }
            }
        }
    }

    fun removerCarro(carroId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCarros = true) }

            try {
                val resultado = carroRepository.deletarCarro(carroId)
                resultado.onSuccess { sucesso ->
                    if (sucesso) {
                        // Remove da lista local
                        val novaLista = _uiState.value.carros.filter { it.id != carroId }
                        _uiState.update {
                            it.copy(
                                isLoadingCarros = false,
                                carros = novaLista,
                                carrosExibidos = novaLista,
                                carroSelecionado = if (carroId == _uiState.value.carroSelecionado?.id) {
                                    novaLista.firstOrNull()
                                } else {
                                    _uiState.value.carroSelecionado
                                },
                                mensagemSucesso = "Carro removido com sucesso!"
                            )
                        }
                    }
                }.onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoadingCarros = false,
                            mensagemErro = "Erro ao remover carro: ${erro.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingCarros = false,
                        mensagemErro = "Erro ao remover carro: ${e.message}"
                    )
                }
            }
        }
    }

    fun selecionarCarro(carro: Carro?) {
        _uiState.update { it.copy(carroSelecionado = carro) }
    }

    fun selecionarEstacionamento(estacionamento: Estacionamento?) {
        _uiState.update { it.copy(estacionamentoSelecionado = estacionamento) }
    }
    fun criarReserva(reserva: Reserva) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingReservas = true) }

            try {
                val resultado = reservaRepository.criarReserva(reserva)
                resultado.onSuccess { reservaCriada ->
                    // Adiciona à lista local usando o operador + com uma lista
                    val novaLista = _uiState.value.reservas + listOf(reservaCriada)

                    _uiState.update {
                        it.copy(
                            isLoadingReservas = false,
                            reservas = novaLista,
                            mensagemSucesso = "Reserva criada com sucesso!",
                            reservaCriada = true
                        )
                    }
                }.onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoadingReservas = false,
                            mensagemErro = "Erro ao criar reserva: ${erro.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingReservas = false,
                        mensagemErro = "Erro ao criar reserva: ${e.message}"
                    )
                }
            }
        }
    }
    fun cancelarReserva(reservaId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingReservas = true) }

            try {
                val resultado = reservaRepository.cancelarReserva(reservaId)
                resultado.onSuccess { sucesso ->
                    if (sucesso) {
                        // Remove da lista local
                        val novaLista = _uiState.value.reservas.filter { it.id != reservaId }
                        _uiState.update {
                            it.copy(
                                isLoadingReservas = false,
                                reservas = novaLista,
                                mensagemSucesso = "Reserva cancelada com sucesso!"
                            )
                        }
                    }
                }.onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoadingReservas = false,
                            mensagemErro = "Erro ao cancelar reserva: ${erro.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingReservas = false,
                        mensagemErro = "Erro ao cancelar reserva: ${e.message}"
                    )
                }
            }
        }
    }

    // Métodos de navegação
    fun navegarPara(tela: String) {
        _uiState.update { it.copy(telaAtual = tela) }
    }

    fun mostrarDetalhesEstacionamento(estacionamentoId: String) {
        _uiState.update {
            it.copy(
                telaAtual = "DETALHES_ESTACIONAMENTO",
                estacionamentoDetalhesId = estacionamentoId
            )
        }
    }

    fun mostrarDetalhesReserva(reservaId: String) {
        _uiState.update {
            it.copy(
                telaAtual = "DETALHES_RESERVA",
                reservaDetalhesId = reservaId
            )
        }
    }

    // Limpar mensagens
    fun limparMensagens() {
        _uiState.update {
            it.copy(
                mensagemErro = null,
                mensagemSucesso = null,
                reservaCriada = false
            )
        }
    }

    // Refresh geral
    fun refresh() {
        carregarDadosIniciais()
    }
}