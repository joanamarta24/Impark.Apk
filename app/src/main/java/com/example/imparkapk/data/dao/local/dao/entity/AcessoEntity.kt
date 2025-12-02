package com.example.imparkapk.data.dao.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.imparkapk.data.dao.model.*
import com.example.imparkapk.data.dao.model.enus.StatusAcesso
import com.example.imparkapk.data.dao.model.enus.TipoAcesso
import java.util.Date

@Entity(
    tableName = "acessos",
    indices = [
        Index(value = ["usuario_id"]),
        Index(value = ["veiculo_id"]),
        Index(value = ["estacionamento_id"]),
        Index(value = ["tipo"]),
        Index(value = ["status"]),
        Index(value = ["data_hora_entrada"]),
        Index(value = ["data_hora_saida"]),
        Index(value = ["ativo"]),
        Index(value = ["data_criacao"]),
        Index(value = ["data_atualizacao"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = VeiculoEntity::class,
            parentColumns = ["id"],
            childColumns = ["veiculo_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EstacionamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estacionamento_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AcessoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "usuario_id", index = true)
    val usuarioId: String,

    @ColumnInfo(name = "veiculo_id", index = true)
    val veiculoId: String,

    @ColumnInfo(name = "estacionamento_id", index = true)
    val estacionamentoId: String,

    @ColumnInfo(name = "tipo")
    val tipo: String, // ENTRADA, SAIDA, RESERVA, CANCELAMENTO

    @ColumnInfo(name = "data_hora_entrada")
    val dataHoraEntrada: Long,

    @ColumnInfo(name = "data_hora_saida")
    val dataHoraSaida: Long? = null,

    @ColumnInfo(name = "valor")
    val valor: Double = 0.0,

    @ColumnInfo(name = "status")
    val status: String, // ATIVO, FINALIZADO, CANCELADO, PENDENTE

    @ColumnInfo(name = "observacoes")
    val observacoes: String? = null,

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Long,

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Long,

    @ColumnInfo(name = "ativo", defaultValue = "1")
    val ativo: Boolean = true,

    @ColumnInfo(name = "sincronizado", defaultValue = "0")
    val sincronizado: Boolean = false,

    @ColumnInfo(name = "tempo_estadia_minutos")
    val tempoEstadiaMinutos: Int? = null,

    @ColumnInfo(name = "codigo_barras")
    val codigoBarras: String? = null,

    @ColumnInfo(name = "nota_fiscal_url")
    val notaFiscalUrl: String? = null,

    @ColumnInfo(name = "funcionario_id")
    val funcionarioId: String? = null,

    @ColumnInfo(name = "vaga_numero")
    val vagaNumero: String? = null
) {
    fun toModel(): Acesso {
        return Acesso(
            id = this.id,
            usuarioId = this.usuarioId,
            veiculoId = this.veiculoId,
            estacionamentoId = this.estacionamentoId,
            tipo = when (this.tipo) {
                "ENTRADA" -> TipoAcesso.ENTRADA
                "SAIDA" -> TipoAcesso.SAIDA
                "RESERVA" -> TipoAcesso.RESERVA
                "CANCELAMENTO" -> TipoAcesso.CANCELAMENTO
                else -> TipoAcesso.ENTRADA
            },
            dataHoraEntrada = Date(this.dataHoraEntrada),
            dataHoraSaida = this.dataHoraSaida?.let { Date(it) },
            valor = this.valor,
            status = when (this.status) {
                "ATIVO" -> StatusAcesso.ATIVO
                "FINALIZADO" -> StatusAcesso.FINALIZADO
                "CANCELADO" -> StatusAcesso.CANCELADO
                "PENDENTE" -> StatusAcesso.PENDENTE
                else -> StatusAcesso.ATIVO
            },
            observacoes = this.observacoes,
            dataCriacao = Date(this.dataCriacao),
            dataAtualizacao = Date(this.dataAtualizacao),
            ativo = this.ativo,
            tempoEstadiaMinutos = this.tempoEstadiaMinutos,
            codigoBarras = this.codigoBarras,
            notaFiscalUrl = this.notaFiscalUrl,
            funcionarioId = this.funcionarioId,
            vagaNumero = this.vagaNumero
        )
    }

    companion object {
        fun fromModel(acesso: Acesso): AcessoEntity {
            return AcessoEntity(
                id = acesso.id,
                usuarioId = acesso.usuarioId,
                veiculoId = acesso.veiculoId,
                estacionamentoId = acesso.estacionamentoId,
                tipo = acesso.tipo.name,
                dataHoraEntrada = acesso.dataHoraEntrada?.time ?: Date().time,
                dataHoraSaida = acesso.dataHoraSaida?.time,
                valor = acesso.valor,
                status = acesso.status.name,
                observacoes = acesso.observacoes,
                dataCriacao = acesso.dataCriacao.time,
                dataAtualizacao = acesso.dataAtualizacao.time,
                ativo = acesso.ativo,
                sincronizado = false,
                tempoEstadiaMinutos = acesso.tempoEstadiaMinutos,
                codigoBarras = acesso.codigoBarras,
                notaFiscalUrl = acesso.notaFiscalUrl,
                funcionarioId = acesso.funcionarioId,
                vagaNumero = acesso.vagaNumero
            )
        }
    }
}

// Classes auxiliares para consultas
data class EstatisticasAcesso(
    val total: Int,
    val faturamento: Double?,
    val tempo_medio: Double?
)

data class DistribuicaoHoraria(
    val hora: String,
    val quantidade: Int
)

data class AcessoComDetalhes(
    val id: String,
    val usuarioId: String,
    val veiculoId: String,
    val estacionamentoId: String,
    val tipo: String,
    val dataHoraEntrada: Long,
    val dataHoraSaida: Long?,
    val valor: Double,
    val status: String,
    val observacoes: String?,
    val dataCriacao: Long,
    val dataAtualizacao: Long,
    val ativo: Boolean,
    val sincronizado: Boolean,
    val tempoEstadiaMinutos: Int?,
    val codigoBarras: String?,
    val notaFiscalUrl: String?,
    val funcionarioId: String?,
    val vagaNumero: String?,
    // Detalhes relacionados
    val usuarioNome: String?,
    val usuarioEmail: String?,
    val veiculoPlaca: String?,
    val veiculoModelo: String?,
    val estacionamentoNome: String?,
    val estacionamentoEndereco: String?
)