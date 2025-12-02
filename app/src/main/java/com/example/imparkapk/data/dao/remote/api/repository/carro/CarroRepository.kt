package com.example.imparkapk.data.dao.remote.api.repository.carro

import com.example.imparkapk.domain.model.Carro

interface CarroRepository{
    // Operações CRUD básicas
    suspend fun cadastrarCarro(carro: Carro): Result<Boolean>
    suspend fun getCarroPorId(id: String): Result<Carro?>
    suspend fun getCarroPorPlaca(placa: String): Result<Carro?>
    suspend fun atualizarCarro(carro: Carro): Result<Boolean>
    suspend fun sincronizarUsuarios()
    suspend fun deletarCarro(id: String): Result<Boolean>

    // Operações por usuário
    suspend fun getobterCarrosPorUsuario(usuarioId: String): Result<List<Carro>>
    suspend fun countCarrosPorUsuario(usuarioId: String): Result<Int>
    suspend fun obterTodosCarros(): Result<List<Carro>>
    suspend fun salvarCarro(carro: Carro): Result<Carro>



    // Operações de gestão de carro principal

    suspend fun definirComoPrincipal(carroId: String): Result<Boolean>
    suspend fun getCarroPrincipal(usuarioId: String): Result<Carro?>



    // Operações de busca e filtro

    suspend fun buscarCarrosPorModelo(modelo: String): Result<List<Carro>>
    suspend fun buscarCarrosPorMarca(marca: String): Result<List<Carro>>
    suspend fun buscarCarrosPorCor(cor: String): Result<List<Carro>>
    suspend fun buscarCarrosPorAno(ano: Int): Result<List<Carro>>

    // Operações de validação

    suspend fun verificarPlacaExistente(placa: String): Result<Boolean>
    suspend fun validarCarro(carro: Carro): Result<Boolean>

    // Operações de sincronização

    suspend fun sincronizarCarros(usuarioId: String): Result<Boolean>
    suspend fun limparCacheCarros(): Result<Boolean>

    suspend fun carregarCarrosDoUsuario()


    // Operações em lote

    suspend fun inserirCarrosEmLote(carros: List<Carro>): Result<Boolean>
    suspend fun atualizarCarrosEmLote(carros: List<Carro>): Result<Boolean>

    // Operações de backup/restauração

    suspend fun exportarCarros(usuarioId: String): Result<String>
    suspend fun importarCarros(usuarioId: String, dados: String): Result<Boolean>

    suspend fun listarCarros(carro: List <Carro>)
}