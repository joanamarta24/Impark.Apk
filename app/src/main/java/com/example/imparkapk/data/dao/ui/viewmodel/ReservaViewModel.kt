package com.example.imparktcc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparkapk.data.dao.remote.api.repository.carro.CarroRepository
import com.example.imparkapk.data.dao.remote.api.repository.reserva.ReservaRepository
import com.example.imparktcc.model.Carro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReservaViewModel @Inject constructor(
    private val reservaRepository: ReservaRepository,
    private val carroRepository: CarroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReservaUiState())
    val uiState: StateFlow<ReservaUiState> = _uiState.asStateFlow()

    fun carregarCarrosDoUsuario(usuarioId: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val carros = carroRepository.listarCarrosPorUsuario(usuarioId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        carros = carros,
                        carroSelecionado = carros.firstOrNull()
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro ao carregar carros"
                    )
                }
            }
        }
    }


    fun onCarroSelecionadoChange(carro: Carro) {
        _uiState.update { it.copy(carroSelecionado = carro) }
    }

    fun onDataReservaChange(data: Date) {
        _uiState.update { it.copy(dataReserva = data) }
        calcularValorReserva()
    }

    fun onHoraEntradaChange(hora: String) {
        _uiState.update { it.copy(horaEntrada = hora) }
        calcularValorReserva()
    }

    fun onHoraSaidaChange(hora: String) {
        _uiState.update { it.copy(horaSaida = hora) }
        calcularValorReserva()
    }

    fun onEstacionamentoSelecionado(estacionamentoId: String, valorHora: Double) {
        _uiState.update {
            it.copy(
                estacionamentoId = estacionamentoId,
                valorHora = valorHora
            )
        }
        calcularValorReserva()
    }

    private fun calcularValorReserva() {
        val estado = _uiState.value
        if (estado.horaEntrada.isNotBlank() && estado.horaSaida.isNotBlank()) {
            val horas = calcularHoras(estado.horaEntrada, estado.horaSaida)
            val valorTotal = horas * estado.valorHora

            _uiState.update {
                it.copy(
                    horasContratadas = horas,
                    valorTotal = valorTotal
                )
            }
        }
    }

    private fun calcularHoras(horaEntrada: String, horaSaida: String): Int {
        // Implementação simplificada - em produção, usar biblioteca de datas
        val entrada = horaEntrada.split(":").first().toInt()
        val saida = horaSaida.split(":").first().toInt()
        return (saida - entrada).coerceAtLeast(1)
    }

    fun criarReserva(usuarioId: String, onSuccess: (String) -> Unit = {}) {
        if (validarReserva()) {
            _uiState.update { it.copy(isLoading = true, mensagemErro = "") }

            viewModelScope.launch {
                try {
                    val reserva = Reserva(
                        usuarioId = usuarioId,
                        carroId = _uiState.value.carroSelecionado?.id ?: "",
                        estacionamentoId = _uiState.value.estacionamentoId,
                        dataReserva = _uiState.value.dataReserva,
                        horaEntrada = _uiState.value.horaEntrada,
                        horaSaida = _uiState.value.horaSaida,
                        valorTotal = _uiState.value.valorTotal,
                        status = "pendente",
                        codigoReserva = gerarCodigoReserva()
                    )

                    val sucesso = reservaRepository.criarReserva(reserva)

                    if (sucesso) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                reservaCriada = true,
                                reservaAtual = reserva,
                                mensagemSucesso = "Reserva criada com sucesso!"
                            )
                        }
                        onSuccess(reserva.id)
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                mensagemErro = "Erro ao criar reserva"
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemErro = "Erro de conexão"
                        )
                    }
                }
            }
        }
    }

    fun carregarReservasDoUsuario(usuarioId: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val reservas = reservaRepository.listarReservasPorUsuario(usuarioId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        reservas = reservas
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro ao carregar reservas"
                    )
                }
            }
        }
    }

    fun cancelarReserva(reservaId: String, onSuccess: () -> Unit = {}) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val sucesso = reservaRepository.cancelarReserva(reservaId)

                if (sucesso) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemSucesso = "Reserva cancelada com sucesso!"
                        )
                    }
                    // Recarregar lista de reservas
                    carregarReservasDoUsuario(
                        _uiState.value.reservas.firstOrNull()?.usuarioId ?: ""
                    )
                    onSuccess()
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mensagemErro = "Erro ao cancelar reserva"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro de conexão"
                    )
                }
            }
        }
    }

    private fun validarReserva(): Boolean {
        val estado = _uiState.value

        val camposValidos = estado.carroSelecionado != null &&
                estado.estacionamentoId.isNotBlank() &&
                estado.horaEntrada.isNotBlank() &&
                estado.horaSaida.isNotBlank()

        val horariosValidos = estado.horaEntrada < estado.horaSaida

        _uiState.update {
            it.copy(
                camposValidos = camposValidos,
                horariosValidos = horariosValidos
            )
        }

        return camposValidos && horariosValidos
    }

    private fun gerarCodigoReserva(): String {
        return "RES${(100000..999999).random()}"
    }

    fun limparMensagens() {
        _uiState.update { it.copy(mensagemErro = "", mensagemSucesso = "") }
    }

    fun resetState() {
        _uiState.value = ReservaUiState()
    }
}

data class ReservaUiState(
    val carros: List<Carro> = emptyList(),
    val carroSelecionado: Carro? = null,
    val reservas: List<Reserva> = emptyList(),
    val estacionamentoId: String = "",
    val dataReserva: Date = Date(),
    val horaEntrada: String = "",
    val horaSaida: String = "",
    val valorHora: Double = 0.0,
    val horasContratadas: Int = 0,
    val valorTotal: Double = 0.0,
    val isLoading: Boolean = false,
    val reservaCriada: Boolean = false,
    val reservaAtual: Reserva? = null,
    val camposValidos: Boolean = false,
    val horariosValidos: Boolean = true,
    val mensagemErro: String = "",
    val mensagemSucesso: String = ""
) {
    val botaoReservarHabilitado: Boolean
        get() = camposValidos && horariosValidos && !isLoading
}