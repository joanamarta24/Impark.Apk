package com.example.imparkapk.UiState

import com.example.imparkapk.data.dao.model.Estacionamento
import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparktcc.model.Carro
import com.example.imparktcc.model.Usuario

class ClienteUiState {
    //USUARIO
    val usuarioLogado: Usuario? = null

    //CARROS
    val carros: List<Carro> = emptyList()
    val carroSelecionado: Carro? = null
    val isLoadinfCarros: Boolean = false

    //ESTACIONAMENTO
    val estacionamentos: List<Estacionamento> = emptyList()
    val estacionamentoSelecionado: Estacionamento? = null
    val isLoadinfEstacionamento: Boolean = false
    val searchQueryEstacionamentos: String = ""
    val filtroPrecoMaximo: Double? = null

    //RESERVAS
    val reservas: List<Reserva> = emptyList()
    val isLoadingReservas: Boolean = false
    val reservaCriada: Boolean = false

    //NAVEGAÇÃO
    val telaAtual: String = ""
    val estacionamentoDetalhesId: String? = null
    val reservaDetalhesId:String? = null

    // LOCALIZAÇÃO
    val localizacaoUsuario:CoordinatesDto?

}