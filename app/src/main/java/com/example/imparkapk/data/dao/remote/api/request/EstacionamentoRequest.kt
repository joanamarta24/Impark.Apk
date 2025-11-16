package com.example.imparkapk.data.dao.remote.api.request

import com.example.imparkapk.data.dao.model.enus.TipoVeiculo
import com.example.imparkapk.data.dao.remote.api.response.GerenteResponse
import com.google.gson.annotations.SerializedName
import kotlin.let

data class EstacionamentoRequest (
    @SerializedName("nome")
    val nome: String,

    @SerializedName("endereco")
    val endereco:String,

    @SerializedName("latitude")
    val latitude:Double,

    @SerializedName("longitude")
    val longitude:Double,

    @SerializedName("total_vagas")
    val totalVagas:Int,

    @SerializedName("vagas_disponiveis")
    val vagasDisponiveis:Int,

    @SerializedName("valor_hora")
    val valorHora:Double,


    @SerializedName("telefone")
    val telefone:String,

    @SerializedName("horario_abertura")
    val horarioAbertura: String,

    @SerializedName("horario_fechamento")
    val horarioFechamento: String,

    @SerializedName("descricao")
    val descricao:String? = null,

    @SerializedName("fotos")
    val fotos:List<String>? =null,

    @SerializedName("servicos")
    val servicos: List<String>? = null
)
data class AtualizarEstacionamentoReques(
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
    val segundaASexta: Boolean,

    @SerializedName("sabado")
    val sabado: Boolean,

    @SerializedName("domingos")
    val domingos: Boolean,

    @SerializedName("feriados")
    val feriados: Boolean
)

// Request para atualizar estacionamento
data class AtualizarEstacionamentoRequest(
    val nome: String? = null,
    val cnpj: String? = null,
    val telefone: String? = null,
    val email: String? = null,
    val totalVagas: Int? = null,
    val endereco: EnderecoRequest? = null,
    val horarioFuncionamento: HorarioFuncionamentoRequest? = null
)

// Response para estatísticas
data class EstatisticasResponse(
    val totalVeiculos: Int,
    val veiculosAtivos: Int,
    val faturamentoDiario: Double,
    val faturamentoMensal: Double,
    val taxaOcupacao: Double,
    val tempoMedioPermanencia: Double,
    veiculosPorTipo: Map<String, Int>
)

// Response para veículos
data class VeiculoResponse(
    val id: String,
    val placa: String,
    val modelo: String,
    val cor: String,
    val tipo: String,
    val dataEntrada: String,
    val dataSaida: String?,
    val valorPago: Double?,
    val status: String
)

// Request para horário de funcionamento
data class HorarioFuncionamentoRequest(
    val segundaASexta: String,
    val sabado: String?,
    val domingo: String?,
    val feriados: String?
)
// Request para endereço
data class EnderecoRequest(
    val cep: String,
    val logradouro: String,
    val numero: String,
    val complemento: String?,
    val bairro: String,
    val cidade: String,
    val estado: String
)

companion object {
    fun fromString(value: String): TipoVeiculo {
        return value.find { it.descricao.equals(value, ignoreCase = true) } ?: TipoVeiculo.OUTROS
    }

    fun toMapString(veiculosPorTipo: Map<TipoVeiculo, Int>): Map<String, Int> {
        return veiculosPorTipo.mapKeys { it.key.descricao }
    }

    fun fromMapString(veiculosPorTipo: Map<String, Int>): Map<TipoVeiculo, Int> {
        return veiculosPorTipo.mapKeys { (key, _) -> fromString(key) }
    }

}


