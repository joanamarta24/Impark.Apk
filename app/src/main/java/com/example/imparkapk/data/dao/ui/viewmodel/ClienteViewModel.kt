package com.example.imparkapk.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.imparkapk.UiState.ClienteUiState
import com.example.imparkapk.data.dao.model.Estacionamento
import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparkapk.data.dao.remote.api.dto.estacionamento.EstacionamentoFilterDTO
import com.example.imparkapk.data.dao.remote.api.repository.carro.CarroRepository
import com.example.imparkapk.data.dao.remote.api.repository.estacionamento.EstacionamentoRepository
import com.example.imparkapk.data.dao.remote.api.repository.reserva.ReservaRepository
import com.example.imparkapk.data.dao.remote.api.repository.usuario.ClienteRepository
import com.example.imparkapk.data.dto.estacionamento.EstacionamentoFilterDTO
import com.example.imparkapk.data.dto.shared.CoordinatesDTO
import com.example.imparktcc.model.Carro
import com.example.imparktcc.model.Estacionamento
import com.example.imparktcc.model.Reserva
import com.example.imparktcc.model.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    //  USUÁRIO
    private suspend fun carregarUsuarioLogado() {
        // Simula recuperação de usuário logado
        // Em produção, isso viria de um gerenciador de sessão
        val usuario =clienteRepository.getUsuarioPorId("1") // ID mock
        usuario.onSuccess { user ->
            _uiState.update { it.copy(usuarioLogado = user) }
        }
    }

    fun atualizarPerfilUsuario(usuario: Usuario) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val resultado = clienteRepository.atualizarUsuario(usuario)
                resultado.onSuccess { sucesso ->
                    if (sucesso) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                mensagemSucesso = "Perfil atualizado com sucesso!",
                                usuarioLogado = usuario
                            )
                        }
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

    //  CARROS
    fun carregarCarrosDoUsuario() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCarros = true) }

            try {
                val usuarioId = _uiState.value.usuarioLogado?.id ?: return@launch
                // Simulação - em produção, usaria Flow do repository
                val carros = listOf(
                    Carro(
                        id = "1",
                        usuarioId = usuarioId,
                        modelo = "Fiat Uno",
                        placa = "ABC1234",
                        cor = "Vermelho"
                    ),
                    Carro(
                        id = "2",
                        usuarioId = usuarioId,
                        modelo = "Volkswagen Gol",
                        placa = "DEF5678",
                        cor = "Preto"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoadingCarros = false,
                        carros = carros,
                        carroSelecionado = carros.firstOrNull()
                    )
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

    fun adicionarCarro(carro: Carro) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCarros = true) }

            try {
                val resultado = carroRepository.cadastrarCarro(carro)
                resultado.onSuccess { sucesso ->
                    if (sucesso) {
                        carregarCarrosDoUsuario() // Recarrega a lista
                        _uiState.update {
                            it.copy(
                                isLoadingCarros = false,
                                mensagemSucesso = "Carro adicionado com sucesso!"
                            )
                        }
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

    fun selecionarCarro(carro: Carro?) {
        _uiState.update { it.copy(carroSelecionado = carro) }
    }

    fun removerCarro(carroId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCarros = true) }

            try {
                val resultado = carroRepository.excluirCarro(carroId)
                resultado.onSuccess { sucesso ->
                    if (sucesso) {
                        carregarCarrosDoUsuario() // Recarrega a lista
                        _uiState.update {
                            it.copy(
                                isLoadingCarros = false,
                                mensagemSucesso = "Carro removido com sucesso!"
                            )
                        }
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

    //  ESTACIONAMENTOS
    fun carregarEstacionamentosProximos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingEstacionamentos = true) }

            try {
                val filter = EstacionamentoFilterDTO(
                    coordenadas = CoordinatesDTO(-23.5505, -46.6333), // São Paulo mock
                    raio = 5.0, // 5km
                    vagasDisponiveis = true
                )

                // Simulação - em produção, usaria o repository
                val estacionamentos = listOf(
                    Estacionamento(
                        id = "1",
                        nome = "Estacionamento Central",
                        endereco = "Rua Principal, 123 - Centro",
                        latitude = -23.5505,
                        longitude = -46.6333,
                        totalVagas = 50,
                        vagasDisponiveis = 15,
                        valorHora = 8.50,
                        telefone = "(11) 9999-8888",
                        horarioAbertura = "06:00",
                        horarioFechamento = "22:00"
                    ),
                    Estacionamento(
                        id = "2",
                        nome = "Parking Shopping",
                        endereco = "Av. Comercial, 456 - Jardins",
                        latitude = -23.5632,
                        longitude = -46.6544,
                        totalVagas = 200,
                        vagasDisponiveis = 45,
                        valorHora = 12.00,
                        telefone = "(11) 9777-6666",
                        horarioAbertura = "08:00",
                        horarioFechamento = "23:00"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoadingEstacionamentos = false,
                        estacionamentos = estacionamentos,
                        estacionamentoSelecionado = estacionamentos.firstOrNull()
                    )
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

    fun buscarEstacionamentosPorNome(nome: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingEstacionamentos = true) }

            try {
                val resultados = estacionamentoRepository.buscarEstacionamentoPorNome(nome)
                _uiState.update {
                    it.copy(
                        isLoadingEstacionamentos = false,
                        estacionamentos = resultados,
                        searchQueryEstacionamentos = nome
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingEstacionamentos = false,
                        mensagemErro = "Erro na busca: ${e.message}"
                    )
                }
            }
        }
    }

    fun filtrarEstacionamentosPorPreco(maxPreco: Double) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingEstacionamentos = true) }

            try {
                val resultados = estacionamentoRepository.buscarEstacionamentoPorPreco(maxPreco)
                _uiState.update {
                    it.copy(
                        isLoadingEstacionamentos = false,
                        estacionamentos = resultados,
                        filtroPrecoMaximo = maxPreco
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingEstacionamentos = false,
                        mensagemErro = "Erro ao filtrar: ${e.message}"
                    )
                }
            }
        }
    }

    fun selecionarEstacionamento(estacionamento: Estacionamento?) {
        _uiState.update { it.copy(estacionamentoSelecionado = estacionamento) }
    }

    //  RESERVAS
    fun carregarReservasAtivas() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingReservas = true) }

            try {
                val usuarioId = _uiState.value.usuarioLogado?.id ?: return@launch
                // Simulação - em produção, usaria o repository
                val reservas = listOf(
                    Reserva(
                        id = "1",
                        clienteId = usuarioId,
                        estacionamentoId = "1",
                        dataHoraEntrada = "2024-01-15 14:00",
                        dataHoraSaida = "2024-01-15 16:00",
                        vagaNumero = 15,
                        status = "CONFIRMADA",
                        valorTotal = 17.00,
                        placaVeiculo = "ABC1234"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoadingReservas = false,
                        reservas = reservas
                    )
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

    fun criarReserva(reserva: Reserva) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingReservas = true) }

            try {
                val resultado = reservaRepository.criarReserva(reserva)
                resultado.onSuccess { sucesso ->
                    if (sucesso) {
                        carregarReservasAtivas() // Recarrega a lista
                        _uiState.update {
                            it.copy(
                                isLoadingReservas = false,
                                mensagemSucesso = "Reserva criada com sucesso!",
                                reservaCriada = true
                            )
                        }
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
                        carregarReservasAtivas() // Recarrega a lista
                        _uiState.update {
                            it.copy(
                                isLoadingReservas = false,
                                mensagemSucesso = "Reserva cancelada com sucesso!"
                            )
                        }
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

    //  NAVEGAÇÃO
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

    //  MENSAGENS E ESTADO
    fun limparMensagens() {
        _uiState.update {
            it.copy(
                mensagemErro = "",
                mensagemSucesso = "",
                reservaCriada = false
            )
        }
    }

    fun resetarEstado() {
        _uiState.value = ClienteUiState()
        carregarDadosIniciais()
    }

    //  ATUALIZAÇÕES DE FORMULÁRIO
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQueryEstacionamentos = query) }
    }

    fun onFiltroPrecoChange(preco: Double) {
        _uiState.update { it.copy(filtroPrecoMaximo = preco) }
    }

    fun onLocationChange(latitude: Double, longitude: Double) {
        _uiState.update {
            it.copy(
                localizacaoUsuario = CoordinatesDTO(latitude, longitude)
            )
        }
    }
}