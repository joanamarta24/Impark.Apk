package com.example.imparkapk.data.dao.model

import com.example.imparkapk.data.dao.model.enus.StatusAcesso
import com.example.imparkapk.data.dao.model.enus.TipoAcesso
import java.util.Date


data class Acesso(
    val id: String = "",
    val usuarioId: String = "",
    val veiculoId: String = "",
    val estacionamentoId: String = "",
    val tipo: TipoAcesso = TipoAcesso.ENTRADA,
    val dataHoraEntrada: Date? = null,
    val dataHoraSaida: Date? = null,
    val valor: Double = 0.0,
    val status: StatusAcesso = StatusAcesso.ATIVO,
    val observacoes: String? = null,
    val dataCriacao: Date = Date(),
    val dataAtualizacao: Date = Date(),
    val ativo: Boolean = true,
    val tempoEstadiaMinutos: Int? = null,
    val codigoBarras: String? = null,
    val notaFiscalUrl: String? = null,
    val funcionarioId: String? = null,
    val vagaNumero: String? = null
) {
    fun calcularTempoEstadia(): Long? {
        if (dataHoraEntrada == null) return null
        val saida = dataHoraSaida ?: Date()
        return (saida.time - dataHoraEntrada.time) / (1000 * 60) // minutos
    }

    fun calcularValorPorTempo(tarifaHora: Double = 10.0): Double {
        val minutos = calcularTempoEstadia() ?: 0
        val horas = minutos / 60.0
        return horas * tarifaHora
    }

    fun estaAtivo(): Boolean = status == StatusAcesso.ATIVO
}