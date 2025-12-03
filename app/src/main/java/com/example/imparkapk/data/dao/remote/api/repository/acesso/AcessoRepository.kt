import com.example.imparkapk.data.dao.model.Acesso
import com.example.imparkapk.data.dao.model.enus.TipoAcesso
import kotlinx.coroutines.flow.Flow

interface AcessoRepository {

    // Operações CRUD básicas
    suspend fun criarAcesso(acesso: Acesso): Result<Acesso>
    suspend fun obterAcessoPorId(id: String): Result<Acesso?>
    suspend fun atualizarAcesso(acesso: Acesso): Result<Boolean>
    suspend fun deletarAcesso(id: String): Result<Boolean>

    // Consultas específicas
    suspend fun obterAcessosPorUsuario(usuarioId: String): Result<List<Acesso>>
    suspend fun obterAcessosPorEstacionamento(estacionamentoId: String): Result<List<Acesso>>
    suspend fun obterAcessosPorVeiculo(veiculoId: String): Result<List<Acesso>>
    suspend fun obterAcessosPorTipo(tipo: TipoAcesso): Result<List<Acesso>>

    // Consultas por data
    suspend fun obterAcessosPorData(data: String): Result<List<Acesso>>
    suspend fun obterAcessosPorPeriodo(dataInicio: String, dataFim: String): Result<List<Acesso>>
    suspend fun obterAcessosHoje(): Result<List<Acesso>>
    suspend fun obterAcessosEsteMes(): Result<List<Acesso>>

    // Consultas combinadas
    suspend fun obterAcessosPorUsuarioEData(usuarioId: String, data: String): Result<List<Acesso>>
    suspend fun obterAcessosPorEstacionamentoEData(estacionamentoId: String, data: String): Result<List<Acesso>>

    // Consultas com status
    suspend fun obterAcessosAtivos(): Result<List<Acesso>>
    suspend fun obterAcessosFinalizados(): Result<List<Acesso>>
    suspend fun obterAcessosCancelados(): Result<List<Acesso>>

    // Estatísticas
    suspend fun contarAcessosPorEstacionamento(estacionamentoId: String): Result<Int>
    suspend fun contarAcessosPorUsuario(usuarioId: String): Result<Int>
    suspend fun calcularTempoMedioEstadia(estacionamentoId: String): Result<Long>
    suspend fun calcularFaturamentoPeriodo(estacionamentoId: String, dataInicio: String, dataFim: String): Result<Double>

    // Operações de negócio
    suspend fun registrarEntrada(acesso: Acesso): Result<Acesso>
    suspend fun registrarSaida(acessoId: String, dataHoraSaida: String): Result<Acesso>
    suspend fun calcularValorEstadia(acessoId: String): Result<Double>
    suspend fun verificarVagasDisponiveis(estacionamentoId: String): Result<Int>
    suspend fun verificarAcessoAtivoPorVeiculo(veiculoId: String): Result<Acesso?>

    // Validações
    suspend fun verificarPermissaoAcesso(usuarioId: String, estacionamentoId: String): Result<Boolean>
    suspend fun verificarVeiculoAutorizado(veiculoId: String, estacionamentoId: String): Result<Boolean>
    suspend fun verificarHorarioFuncionamento(estacionamentoId: String): Result<Boolean>

    // Notificações e alertas
    suspend fun obterAcessosExcedendoTempo(tempoLimiteHoras: Int): Result<List<Acesso>>
    suspend fun obterAcessosPendentesPagamento(): Result<List<Acesso>>

    // Relatórios
    suspend fun gerarRelatorioDiario(estacionamentoId: String, data: String): Result<RelatorioAcesso>
    suspend fun gerarRelatorioMensal(estacionamentoId: String, mesAno: String): Result<RelatorioAcesso>

    // Observables/Flow
    fun observarAcessosPorEstacionamento(estacionamentoId: String): Flow<List<Acesso>>
    fun observarAcessosPorUsuario(usuarioId: String): Flow<List<Acesso>>
    fun observarAcessosAtivos(): Flow<List<Acesso>>
}