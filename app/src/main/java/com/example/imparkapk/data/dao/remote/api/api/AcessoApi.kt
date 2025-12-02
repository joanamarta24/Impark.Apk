import retrofit2.Response
import retrofit2.http.*

interface AcessoApi {

    // ========== OPERAÇÕES CRUD BÁSICAS ==========

    @GET("acessos/{id}")
    suspend fun obterAcessoPorId(@Path("id") id: String): Response<AcessoResponse>

    @POST("acessos")
    suspend fun criarAcesso(@Body acessoRequest: AcessoRequest): Response<AcessoResponse>

    @PUT("acessos/{id}")
    suspend fun atualizarAcesso(
        @Path("id") id: String,
        @Body acessoRequest: AcessoRequest
    ): Response<AcessoResponse>

    @DELETE("acessos/{id}")
    suspend fun deletarAcesso(@Path("id") id: String): Response<Void>

    @PATCH("acessos/{id}/status")
    suspend fun atualizarStatusAcesso(
        @Path("id") id: String,
        @Body statusRequest: StatusAcessoRequest
    ): Response<AcessoResponse>

    // ========== CONSULTAS POR FILTROS ==========

    @GET("acessos/usuario/{usuarioId}")
    suspend fun obterAcessosPorUsuario(@Path("usuarioId") usuarioId: String): Response<List<AcessoResponse>>

    @GET("acessos/estacionamento/{estacionamentoId}")
    suspend fun obterAcessosPorEstacionamento(@Path("estacionamentoId") estacionamentoId: String): Response<List<AcessoResponse>>

    @GET("acessos/veiculo/{veiculoId}")
    suspend fun obterAcessosPorVeiculo(@Path("veiculoId") veiculoId: String): Response<List<AcessoResponse>>

    @GET("acessos/tipo/{tipo}")
    suspend fun obterAcessosPorTipo(@Path("tipo") tipo: String): Response<List<AcessoResponse>>

    @GET("acessos/status/{status}")
    suspend fun obterAcessosPorStatus(@Path("status") status: String): Response<List<AcessoResponse>>

    // ========== CONSULTAS POR DATA ==========

    @GET("acessos/data/{data}")
    suspend fun obterAcessosPorData(@Path("data") data: String): Response<List<AcessoResponse>>

    @GET("acessos/periodo")
    suspend fun obterAcessosPorPeriodo(
        @Query("dataInicio") dataInicio: String,
        @Query("dataFim") dataFim: String
    ): Response<List<AcessoResponse>>

    @GET("acessos/hoje")
    suspend fun obterAcessosHoje(): Response<List<AcessoResponse>>

    @GET("acessos/este-mes")
    suspend fun obterAcessosEsteMes(): Response<List<AcessoResponse>>

    // ========== CONSULTAS COMBINADAS ==========

    @GET("acessos/usuario/{usuarioId}/data/{data}")
    suspend fun obterAcessosPorUsuarioEData(
        @Path("usuarioId") usuarioId: String,
        @Path("data") data: String
    ): Response<List<AcessoResponse>>

    @GET("acessos/estacionamento/{estacionamentoId}/data/{data}")
    suspend fun obterAcessosPorEstacionamentoEData(
        @Path("estacionamentoId") estacionamentoId: String,
        @Path("data") data: String
    ): Response<List<AcessoResponse>>

    @GET("acessos/usuario/{usuarioId}/estacionamento/{estacionamentoId}")
    suspend fun obterAcessosPorUsuarioEEstacionamento(
        @Path("usuarioId") usuarioId: String,
        @Path("estacionamentoId") estacionamentoId: String
    ): Response<List<AcessoResponse>>

    // ========== CONSULTAS DE NEGÓCIO ==========

    @GET("acessos/ativos")
    suspend fun obterAcessosAtivos(): Response<List<AcessoResponse>>

    @GET("acessos/finalizados")
    suspend fun obterAcessosFinalizados(): Response<List<AcessoResponse>>

    @GET("acessos/cancelados")
    suspend fun obterAcessosCancelados(): Response<List<AcessoResponse>>

    @GET("acessos/veiculo/{veiculoId}/ativo")
    suspend fun obterAcessoAtivoPorVeiculo(@Path("veiculoId") veiculoId: String): Response<AcessoResponse>

    @GET("acessos/estacionamento/{estacionamentoId}/ativos")
    suspend fun obterAcessosAtivosPorEstacionamento(@Path("estacionamentoId") estacionamentoId: String): Response<List<AcessoResponse>>

    // ========== OPERAÇÕES DE NEGÓCIO ==========

    @POST("acessos/registrar-entrada")
    suspend fun registrarEntrada(@Body entradaRequest: RegistrarEntradaRequest): Response<AcessoResponse>

    @POST("acessos/registrar-saida")
    suspend fun registrarSaida(@Body saidaRequest: RegistrarSaidaRequest): Response<AcessoResponse>

    @POST("acessos/{id}/calcular-valor")
    suspend fun calcularValorEstadia(@Path("id") id: String): Response<ValorEstadiaResponse>

    @GET("acessos/estacionamento/{estacionamentoId}/vagas-disponiveis")
    suspend fun verificarVagasDisponiveis(@Path("estacionamentoId") estacionamentoId: String): Response<VagasDisponiveisResponse>

    @GET("acessos/veiculo/{veiculoId}/tem-acesso-ativo")
    suspend fun verificarAcessoAtivoPorVeiculo(@Path("veiculoId") veiculoId: String): Response<AcessoAtivoResponse>

    // ========== VALIDAÇÕES ==========

    @GET("acessos/validar-permissao")
    suspend fun verificarPermissaoAcesso(
        @Query("usuarioId") usuarioId: String,
        @Query("estacionamentoId") estacionamentoId: String
    ): Response<ValidacaoResponse>

    @GET("acessos/validar-veiculo")
    suspend fun verificarVeiculoAutorizado(
        @Query("veiculoId") veiculoId: String,
        @Query("estacionamentoId") estacionamentoId: String
    ): Response<ValidacaoResponse>

    @GET("acessos/validar-horario")
    suspend fun verificarHorarioFuncionamento(@Query("estacionamentoId") estacionamentoId: String): Response<ValidacaoResponse>

    // ========== RELATÓRIOS E ESTATÍSTICAS ==========

    @GET("acessos/estacionamento/{estacionamentoId}/contar")
    suspend fun contarAcessosPorEstacionamento(@Path("estacionamentoId") estacionamentoId: String): Response<ContagemResponse>

    @GET("acessos/usuario/{usuarioId}/contar")
    suspend fun contarAcessosPorUsuario(@Path("usuarioId") usuarioId: String): Response<ContagemResponse>

    @GET("acessos/estacionamento/{estacionamentoId}/tempo-medio")
    suspend fun calcularTempoMedioEstadia(@Path("estacionamentoId") estacionamentoId: String): Response<TempoMedioResponse>

    @GET("acessos/estacionamento/{estacionamentoId}/faturamento")
    suspend fun calcularFaturamentoPeriodo(
        @Path("estacionamentoId") estacionamentoId: String,
        @Query("dataInicio") dataInicio: String,
        @Query("dataFim") dataFim: String
    ): Response<FaturamentoResponse>

    @GET("acessos/relatorio/diario")
    suspend fun gerarRelatorioDiario(
        @Query("estacionamentoId") estacionamentoId: String,
        @Query("data") data: String
    ): Response<RelatorioAcessoResponse>

    @GET("acessos/relatorio/mensal")
    suspend fun gerarRelatorioMensal(
        @Query("estacionamentoId") estacionamentoId: String,
        @Query("mesAno") mesAno: String
    ): Response<RelatorioAcessoResponse>

    // ========== NOTIFICAÇÕES E ALERTAS ==========

    @GET("acessos/excedendo-tempo")
    suspend fun obterAcessosExcedendoTempo(@Query("tempoLimiteHoras") tempoLimiteHoras: Int): Response<List<AcessoResponse>>

    @GET("acessos/pendentes-pagamento")
    suspend fun obterAcessosPendentesPagamento(): Response<List<AcessoResponse>>

    // ========== OPERAÇÕES EM LOTE ==========

    @POST("acessos/lote")
    suspend fun criarAcessosEmLote(@Body acessos: List<AcessoRequest>): Response<List<AcessoResponse>>

    @PUT("acessos/lote")
    suspend fun atualizarAcessosEmLote(@Body acessos: List<AcessoRequest>): Response<List<AcessoResponse>>

    @POST("acessos/sincronizar")
    suspend fun sincronizarAcessos(@Body acessos: List<AcessoRequest>): Response<SincronizacaoResponse>

    // ========== PAGINAÇÃO ==========

    @GET("acessos")
    suspend fun listarAcessos(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("sort") sort: String = "dataHoraEntrada,desc"
    ): Response<PageAcessoResponse>

    @GET("acessos/estacionamento/{estacionamentoId}/pagina")
    suspend fun listarAcessosPorEstacionamentoPaginado(
        @Path("estacionamentoId") estacionamentoId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("sort") sort: String = "dataHoraEntrada,desc"
    ): Response<PageAcessoResponse>

    // ========== OPERAÇÕES DE MANUTENÇÃO ==========

    @DELETE("acessos/antigos")
    suspend fun limparAcessosAntigos(@Query("dias") dias: Int = 30): Response<LimpezaResponse>

    @POST("acessos/backup")
    suspend fun criarBackup(): Response<BackupResponse>

    @POST("acessos/restaurar")
    suspend fun restaurarBackup(@Body backupRequest: BackupRequest): Response<Void>
}