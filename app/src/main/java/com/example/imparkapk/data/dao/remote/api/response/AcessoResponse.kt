package com.example.imparkapk.data.dao.remote.api.response.AcessoResponse

data class AcessoResponse(
    val id: String,
    val usuarioId: String,
    val veiculoId: String,
    val estacionamentoId: String,
    val tipo: String,
    val dataHoraEntrada: String,
    val dataHoraSaida: String?,
    val valor: Double,
    val status: String,
    val formaPagamento: String,
    val observacoes: String,
    val dataCriacao: String,
    val dataAtualizacao: String,
    val usuario: UsuarioSimplificadoResponse? = null,
    val veiculo: VeiculoSimplificadoResponse? = null,
    val estacionamento: EstacionamentoSimplificadoResponse? = null
)

// ========== REQUEST MODELS ==========

data class AcessoRequest(
    val usuarioId: String,
    val veiculoId: String,
    val estacionamentoId: String,
    val tipo: String = "ENTRADA",
    val dataHoraEntrada: String? = null,
    val dataHoraSaida: String? = null,
    val valor: Double = 0.0,
    val status: String = "ATIVO",
    val formaPagamento: String = "NENHUMA",
    val observacoes: String = ""
)

data class StatusAcessoRequest(
    val status: String,
    val motivo: String? = null
)

data class RegistrarEntradaRequest(
    val usuarioId: String,
    val veiculoId: String,
    val estacionamentoId: String,
    val formaPagamento: String = "NENHUMA",
    val observacoes: String = ""
)

data class RegistrarSaidaRequest(
    val acessoId: String,
    val dataHoraSaida: String? = null,
    val formaPagamento: String,
    val valorPago: Double? = null,
    val observacoes: String = ""
)

// ========== RESPONSE MODELS ESPECÍFICOS ==========

data class ValorEstadiaResponse(
    val acessoId: String,
    val valor: Double,
    val horasEstadia: Double,
    val tarifaAplicada: String
)

data class VagasDisponiveisResponse(
    val estacionamentoId: String,
    val vagasDisponiveis: Int,
    val vagasOcupadas: Int,
    val capacidadeTotal: Int
)

data class AcessoAtivoResponse(
    val possuiAcessoAtivo: Boolean,
    val acesso: AcessoResponse? = null
)

data class ValidacaoResponse(
    val valido: Boolean,
    val mensagem: String? = null,
    val codigoErro: String? = null
)

data class ContagemResponse(
    val total: Int,
    val ativos: Int,
    val finalizados: Int,
    val cancelados: Int
)

data class TempoMedioResponse(
    val tempoMedioHoras: Double,
    val tempoMedioMinutos: Double,
    val totalAcessos: Int
)

data class FaturamentoResponse(
    val faturamentoTotal: Double,
    val faturamentoMedioDiario: Double,
    val quantidadeAcessos: Int,
    val periodo: String
)

data class RelatorioAcessoResponse(
    val estacionamentoId: String,
    val periodo: String,
    val totalAcessos: Int,
    val acessosAtivos: Int,
    val faturamentoTotal: Double,
    val tempoMedioEstadia: Double,
    val acessosPorDia: Map<String, Int>,
    val topVeiculos: List<VeiculoEstatistica>,
    val formasPagamento: Map<String, Int>
)

data class SincronizacaoResponse(
    val sincronizados: Int,
    val falhas: Int,
    val detalhes: List<SincronizacaoDetalhe>
)

data class PageAcessoResponse(
    val content: List<AcessoResponse>,
    val pageable: PageableInfo,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)

data class LimpezaResponse(
    val registrosRemovidos: Int,
    val espacoLiberado: Long
)

data class BackupResponse(
    val nomeArquivo: String,
    val tamanho: Long,
    val dataCriacao: String,
    val quantidadeRegistros: Int
)

data class BackupRequest(
    val nomeArquivo: String,
    val dados: String // JSON ou base64
)

// ========== MODELS AUXILIARES ==========

data class UsuarioSimplificadoResponse(
    val id: String,
    val nome: String,
    val email: String,
    val cpf: String
)

data class VeiculoSimplificadoResponse(
    val id: String,
    val placa: String,
    val modelo: String,
    val cor: String
)

data class EstacionamentoSimplificadoResponse(
    val id: String,
    val nome: String,
    val endereco: String,
    val capacidade: Int
)

data class VeiculoEstatistica(
    val veiculoId: String,
    val placa: String,
    val quantidadeAcessos: Int,
    val tempoTotalHoras: Double
)

data class SincronizacaoDetalhe(
    val acessoId: String,
    val sucesso: Boolean,
    val mensagem: String? = null
)

data class PageableInfo(
    val sort: SortInfo,
    val offset: Long,
    val pageSize: Int,
    val pageNumber: Int,
    val paged: Boolean,
    val unpaged: Boolean
)

data class SortInfo(
    val sorted: Boolean,
    val unsorted: Boolean,
    val empty: Boolean
)