package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.imparkapk.data.dao.model.Veiculo
import com.example.imparkapk.data.dao.model.VeiculoEstatisticas
import java.util.Date

@Entity(
    tableName = "veiculos",
    indices = [
        Index(value = ["placa"], unique = true),
        Index(value = ["usuario_id"]),
        Index(value = ["estacionamento_id"]),
        Index(value = ["marca"]),
        Index(value = ["modelo"]),
        Index(value = ["tipo"]),
        Index(value = ["ativo"]),
        Index(value = ["data_cadastro"]),
        Index(value = ["data_atualizacao"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class VeiculoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "usuario_id", index = true)
    val usuarioId: String,

    @ColumnInfo(name = "estacionamento_id")
    val estacionamentoId: String? = null,

    @ColumnInfo(name = "placa", index = true)
    val placa: String,

    @ColumnInfo(name = "marca")
    val marca: String,

    @ColumnInfo(name = "modelo")
    val modelo: String,

    @ColumnInfo(name = "ano_fabricacao")
    val anoFabricacao: Int? = null,

    @ColumnInfo(name = "ano_modelo")
    val anoModelo: Int? = null,

    @ColumnInfo(name = "cor")
    val cor: String,

    @ColumnInfo(name = "tipo")
    val tipo: String, // CARRO, MOTO, CAMINHAO, ONIBUS, OUTRO

    @ColumnInfo(name = "renavam")
    val renavam: String? = null,

    @ColumnInfo(name = "chassi")
    val chassi: String? = null,

    @ColumnInfo(name = "foto_url")
    val fotoUrl: String? = null,

    @ColumnInfo(name = "observacoes")
    val observacoes: String? = null,

    @ColumnInfo(name = "data_cadastro")
    val dataCadastro: Long,

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Long,

    @ColumnInfo(name = "ativo", defaultValue = "1")
    val ativo: Boolean = true,

    @ColumnInfo(name = "sincronizado", defaultValue = "0")
    val sincronizado: Boolean = false
) {
    fun toModel(): Veiculo {
        return Veiculo(
            id = this.id,
            usuarioId = this.usuarioId,
            estacionamentoId = this.estacionamentoId,
            placa = this.placa,
            marca = this.marca,
            modelo = this.modelo,
            anoFabricacao = this.anoFabricacao,
            anoModelo = this.anoModelo,
            cor = this.cor,
            tipo = when (this.tipo) {
                "CARRO" -> TipoVeiculo.CARRO
                "MOTO" -> TipoVeiculo.MOTO
                "CAMINHAO" -> TipoVeiculo.CAMINHAO
                "ONIBUS" -> TipoVeiculo.ONIBUS
                else -> TipoVeiculo.OUTROS
            },
            renavam = this.renavam,
            chassi = this.chassi,
            fotoUrl = this.fotoUrl,
            observacoes = this.observacoes,
            dataCadastro = Date(this.dataCadastro),
            dataAtualizacao = Date(this.dataAtualizacao),
            ativo = this.ativo
        )
    }

    companion object {
        fun fromModel(veiculo: Veiculo): VeiculoEntity {
            return VeiculoEntity(
                id = veiculo.id,
                usuarioId = veiculo.usuarioId,
                estacionamentoId = veiculo.estacionamentoId,
                placa = veiculo.placa,
                marca = veiculo.marca,
                modelo = veiculo.modelo,
                anoFabricacao = veiculo.anoFabricacao,
                anoModelo = veiculo.anoModelo,
                cor = veiculo.cor,
                tipo = veiculo.tipo.name,
                renavam = veiculo.renavam,
                chassi = veiculo.chassi,
                fotoUrl = veiculo.fotoUrl,
                observacoes = veiculo.observacoes,
                dataCadastro = veiculo.dataCadastro.time,
                dataAtualizacao = veiculo.dataAtualizacao.time,
                ativo = veiculo.ativo,
                sincronizado = false
            )
        }
    }
}

data class EstatisticaTipo(
    val tipo: String,
    val quantidade: Int
)

data class EstatisticaMarca(
    val marca: String,
    val quantidade: Int
)

data class NovosVeiculosPorMes(
    val mes: String,
    val novos_veiculos: Int
)
// Classe para o resultado da consulta
data class VeiculoEstatisticasResult(
    @ColumnInfo(name = "id")
    val veiculoId: String,

    @ColumnInfo(name = "total_acessos")
    val totalAcessos: Int,

    @ColumnInfo(name = "total_gasto")
    val totalGasto: Double
)

// E uma função separada para obter os detalhes completos
suspend fun obterVeiculosComEstatisticasCompletasPorUsuario(usuarioId: String): Map<String, VeiculoEstatisticas> {
    val veiculos = obterVeiculosPorUsuario(usuarioId)
    val estatisticas = obterEstatisticasVeiculosPorUsuario(usuarioId)

    val resultado = mutableMapOf<String, VeiculoEstatisticas>()

    veiculos.forEach { veiculo->
        val estatistica = estatisticas.find { it.veiculoId == veiculo.id }
        resultado[veiculo.id] = VeiculoEstatisticas(
            veiculo = veiculo.toModel(),
            totalAcessos = estatistica?.totalAcessos ?: 0,
            totalGasto = estatistica?.totalGasto ?: 0.0
        )
    }

    return resultado
}


// Classe para o resultado completo
