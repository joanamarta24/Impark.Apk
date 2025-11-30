package com.example.imparkapk.data.dao.remote.api.request

import TipoVeiculo
import com.google.gson.annotations.SerializedName


data class EstacionamentoRequest(
    @SerializedName("nome")
    val nome: String,

    @SerializedName("endereco")
    val endereco: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("total_vagas")
    val totalVagas: Int,

    @SerializedName("vagas_disponiveis")
    val vagasDisponiveis: Int,

    @SerializedName("valor_hora")
    val valorHora: Double,

    @SerializedName("telefone")
    val telefone: String,

    @SerializedName("horario_abertura")
    val horarioAbertura: String,

    @SerializedName("horario_fechamento")
    val horarioFechamento: String,

    @SerializedName("descricao")
    val descricao: String? = null,

    @SerializedName("fotos")
    val fotos: List<String>? = null,

    @SerializedName("servicos")
    val servicos: List<String>? = null
)

data class AtualizarEstacionamentoRequest(
    @SerializedName("nome")
    val nome: String? = null,

    @SerializedName("endereco")
    val endereco: String? = null,

    @SerializedName("valor_hora")
    val valorHora: Double? = null,

    @SerializedName("telefone")
    val telefone: String? = null,

    @SerializedName("horario_abertura")
    val horarioAbertura: String? = null,

    @SerializedName("horario_fechamento")
    val horarioFechamento: String? = null,

    @SerializedName("vagas_disponiveis")
    val vagasDisponiveis: Int? = null
)

data class BuscarEstacionamentosRequest(
    @SerializedName("latitude")
    val latitude: Double? = null,

    @SerializedName("longitude")
    val longitude: Double? = null,

    @SerializedName("raio_km")
    val raioKm: Double? = null,

    @SerializedName("query")
    val query: String? = null,

    @SerializedName("valor_maximo")
    val valorMaximo: Double? = null,

    @SerializedName("apenas_com_vagas")
    val apenasComVagas: Boolean = false,

    @SerializedName("servicos")
    val servicos: List<String>? = null
)

data class EstacionamentoResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("cnpj")
    val cnpj: String,

    @SerializedName("telefone")
    val telefone: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("endereco")
    val endereco: EnderecoResponse?,

    @SerializedName("total_vagas")
    val totalVagas: Int,

    @SerializedName("vagas_ocupadas")
    val vagasOcupadas: Int,

    @SerializedName("ativo")
    val ativo: Boolean,

    @SerializedName("horario_funcionamento")
    val horarioFuncionamento: HorarioFuncionamentoResponse?
)

data class EnderecoResponse(
    @SerializedName("cep")
    val cep: String,

    @SerializedName("logradouro")
    val logradouro: String,

    @SerializedName("numero")
    val numero: String,

    @SerializedName("complemento")
    val complemento: String?,

    @SerializedName("bairro")
    val bairro: String,

    @SerializedName("cidade")
    val cidade: String,

    @SerializedName("estado")
    val estado: String
)

data class HorarioFuncionamentoResponse(
    @SerializedName("segunda_a_sexta")
    val segundaASexta: HorarioDiaResponse?,

    @SerializedName("sabado")
    val sabado: HorarioDiaResponse?,

    @SerializedName("domingo")
    val domingo: HorarioDiaResponse?,

    @SerializedName("feriados")
    val feriados: HorarioDiaResponse?
)

data class HorarioDiaResponse(
    @SerializedName("abertura")
    val abertura: String,

    @SerializedName("fechamento")
    val fechamento: String,

    @SerializedName("funcionando")
    val funcionando: Boolean
)

// Request para atualizar estacionamento (versão completa)
data class AtualizarEstacionamentoCompletoRequest(
    @SerializedName("nome")
    val nome: String? = null,

    @SerializedName("cnpj")
    val cnpj: String? = null,

    @SerializedName("telefone")
    val telefone: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("total_vagas")
    val totalVagas: Int? = null,

    @SerializedName("endereco")
    val endereco: EnderecoRequest? = null,

    @SerializedName("horario_funcionamento")
    val horarioFuncionamento: HorarioFuncionamentoRequest? = null
)

// Response para estatísticas
data class EstatisticasResponse(
    @SerializedName("total_veiculos")
    val totalVeiculos: Int,

    @SerializedName("veiculos_ativos")
    val veiculosAtivos: Int,

    @SerializedName("faturamento_diario")
    val faturamentoDiario: Double,

    @SerializedName("faturamento_mensal")
    val faturamentoMensal: Double,

    @SerializedName("taxa_ocupacao")
    val taxaOcupacao: Double,

    @SerializedName("tempo_medio_permanencia")
    val tempoMedioPermanencia: Double,

    @SerializedName("veiculos_por_tipo")
    val veiculosPorTipo: Map<String, Int>
)

// Response para veículos
data class VeiculoResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("placa")
    val placa: String,

    @SerializedName("modelo")
    val modelo: String,

    @SerializedName("cor")
    val cor: String,

    @SerializedName("tipo")
    val tipo: String,

    @SerializedName("data_entrada")
    val dataEntrada: String,

    @SerializedName("data_saida")
    val dataSaida: String?,

    @SerializedName("valor_pago")
    val valorPago: Double?,

    @SerializedName("status")
    val status: String
)

// Request para horário de funcionamento
data class HorarioFuncionamentoRequest(
    @SerializedName("segunda_a_sexta")
    val segundaASexta: HorarioDiaRequest?,

    @SerializedName("sabado")
    val sabado: HorarioDiaRequest?,

    @SerializedName("domingo")
    val domingo: HorarioDiaRequest?,

    @SerializedName("feriados")
    val feriados: HorarioDiaRequest?
)

data class HorarioDiaRequest(
    @SerializedName("abertura")
    val abertura: String,

    @SerializedName("fechamento")
    val fechamento: String,

    @SerializedName("funcionando")
    val funcionando: Boolean = true
)

// Request para endereço
data class EnderecoRequest(
    @SerializedName("cep")
    val cep: String,

    @SerializedName("logradouro")
    val logradouro: String,

    @SerializedName("numero")
    val numero: String,

    @SerializedName("complemento")
    val complemento: String?,

    @SerializedName("bairro")
    val bairro: String,

    @SerializedName("cidade")
    val cidade: String,

    @SerializedName("estado")
    val estado: String
)

// Extensions para TipoVeiculo (deve estar no arquivo do enum ou em um arquivo separado)
fun TipoVeiculo.Companion.fromString(value: String): TipoVeiculo {
    return TipoVeiculo.values().find {
        it.descricao.equals(value, ignoreCase = true)
    } ?: TipoVeiculo.OUTROS
}

fun TipoVeiculo.Companion.toMapString(veiculosPorTipo: Map<TipoVeiculo, Int>): Map<String, Int> {
    return veiculosPorTipo.mapKeys { it.key.descricao }
}

fun TipoVeiculo.Companion.fromMapString(veiculosPorTipo: Map<String, Int>): Map<TipoVeiculo, Int> {
    return veiculosPorTipo.mapKeys { (key, _) -> fromString(key) }
}

