package com.example.imparkapk.UiState

import com.example.imparkapk.data.dto.reserva.AvalicaoDTO
import com.example.imparkapk.data.dto.reserva.ReservaDTO
import com.example.imparkapk.data.dto.reserva.ReservaValorResponse

data class ReservaAvaliacaoUiState(
    //Reservas
    val reservas: List<ReservaDTO> = emptyList(),
    val reservaSelecionadaId: String? = null,
    val reservaCriada: ReservaDTO? = null,
    val isLoadingReservas: Boolean = false,
    val isLoadingCalculo: Boolean = false,

    //Avalições
    val avaliacoes:List<AvalicaoDTO> = emptyList(),
    val minhasAvaliacoes: List<AvalicaoDTO> = emptyList(),
    val avaliacaoSelecionadaId: String? = null,
    val isLoadingAvaliacoes: Boolean = false,

    val valorCalculado: ReservaValorResponse? = null,

    val qrCodeReserva: String? = null,

    //MENSAGENS
    val mensagemErro: String ="",
    val mensagemSucesso: String ="",

    //FILTRONS
    val filtroStatusReserva: String = "TODAS",
    val filtroNotaAvaliacao: Int? = null,
    val serchQuery: String = ""
)