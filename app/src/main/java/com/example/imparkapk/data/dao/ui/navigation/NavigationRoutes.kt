package com.example.imparkapk.data.dao.ui.navigation


object NavigationRoutes {
    //Rotas de perfil
    const val PERFIL = "perfil"
    const val EDITAR_PERFIL = "editar_perfil"

    //Rotas de Carros
    const val MEUS_CARROS = "meus_carros"
    const val ADICIONAR_CARRO = "adicionar_carro"
    const val EDITAR_CARRO = "editar_carro/{carroId}"


    // Rotas de Reservas
    const val MINHAS_RESERVAS = "minhas_reservas"
    const val DETALHES_RESERVA = "detalhes_reserva/{reservaId}"

    // Funções auxiliares para construir rotas com parâmetros
    fun editarCarro(carroId: String): String {
        return "editar_carro/$carroId"
        fun detalhesReserva(reservaId: String): String {
            return "detalhes_reserva/$reservaId"
        }
    }
}