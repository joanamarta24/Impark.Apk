package com.example.imparkapk.data.dao.remote.api.repository.acesso

import AcessoApi
import AcessoRepository
import com.example.imparkapk.data.dao.model.Acesso
import com.example.imparkapk.data.dao.model.enus.TipoAcesso
import com.example.imparkapk.data.dao.remote.api.repository.estacionamento.EstacionamentoRepository
import com.example.imparkapk.data.dao.remote.api.repository.veiculo.VeiculoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AcessoRepositoryImpl @Inject constructor(
    private val acessoDao:AcessoDao,
    private val acessoApi: AcessoApi,
    private val estacionamentoRepository: EstacionamentoRepository,
    private val veiculoRepository: VeiculoRepository
):AcessoRepository {
    private val acessosCache = mutableListOf<Acesso>()
    private val acessosAtivosFlow = MutableStateFlow<List<Acesso>>(emptyList())

    override suspend fun criarAcesso(acesso: Acesso): Result<Acesso> {
        TODO("Not yet implemented")
    }

    override suspend fun criarAcesso(acesso: Acesso): Result<Acesso> {
        TODO("Not yet implemented")
    }

    override suspend fun criarAcesso(acesso: Acesso): Result<Acesso> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessoPorId(id: String): Result<Acesso> {
        TODO("Not yet implemented")
    }

    override suspend fun atualizarAcesso(acesso: Acesso): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun atualizarAcesso(acesso: Acesso): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun atualizarAcesso(acesso: Acesso): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deletarAcesso(id: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorUsuario(usuarioId: String): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorEstacionamento(estacionamentoId: String): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorVeiculo(veiculoId: String): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorTipo(tipo: TipoAcesso): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorTipo(tipo: TipoAcesso): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorTipo(tipo: TipoAcesso): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorData(data: String): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorPeriodo(
        dataInicio: String,
        dataFim: String
    ): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosHoje(): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosEsteMes(): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorUsuarioEData(
        usuarioId: String,
        data: String
    ): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPorEstacionamentoEData(
        estacionamentoId: String,
        data: String
    ): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosAtivos(): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosFinalizados(): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosCancelados(): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun contarAcessosPorEstacionamento(estacionamentoId: String): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun contarAcessosPorUsuario(usuarioId: String): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun calcularTempoMedioEstadia(estacionamentoId: String): Result<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun calcularFaturamentoPeriodo(
        estacionamentoId: String,
        dataInicio: String,
        dataFim: String
    ): Result<Double> {
        TODO("Not yet implemented")
    }

    override suspend fun registrarEntrada(acesso: Acesso): Result<Acesso> {
        TODO("Not yet implemented")
    }

    override suspend fun registrarEntrada(acesso: Acesso): Result<Acesso> {
        TODO("Not yet implemented")
    }

    override suspend fun registrarEntrada(acesso: Acesso): Result<Acesso> {
        TODO("Not yet implemented")
    }

    override suspend fun registrarSaida(
        acessoId: String,
        dataHoraSaida: String
    ): Result<Acesso> {
        TODO("Not yet implemented")
    }

    override suspend fun calcularValorEstadia(acessoId: String): Result<Double> {
        TODO("Not yet implemented")
    }

    override suspend fun verificarVagasDisponiveis(estacionamentoId: String): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun verificarAcessoAtivoPorVeiculo(veiculoId: String): Result<Acesso> {
        TODO("Not yet implemented")
    }

    override suspend fun verificarPermissaoAcesso(
        usuarioId: String,
        estacionamentoId: String
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun verificarVeiculoAutorizado(
        veiculoId: String,
        estacionamentoId: String
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun verificarHorarioFuncionamento(estacionamentoId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosExcedendoTempo(tempoLimiteHoras: Int): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun obterAcessosPendentesPagamento(): Result<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override suspend fun gerarRelatorioDiario(
        estacionamentoId: String,
        data: String
    ): Result<RelatorioAcesso> {
        TODO("Not yet implemented")
    }

    override suspend fun gerarRelatorioMensal(
        estacionamentoId: String,
        mesAno: String
    ): Result<RelatorioAcesso> {
        TODO("Not yet implemented")
    }

    override fun observarAcessosPorEstacionamento(estacionamentoId: String): Flow<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override fun observarAcessosPorUsuario(usuarioId: String): Flow<List<Acesso>> {
        TODO("Not yet implemented")
    }

    override fun observarAcessosAtivos(): Flow<List<Acesso>> {
        TODO("Not yet implemented")
    }
}