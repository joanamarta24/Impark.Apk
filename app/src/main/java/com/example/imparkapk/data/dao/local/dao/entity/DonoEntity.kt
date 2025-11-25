package com.example.imparkapk.data.dao.local.dao.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name = "dono")
data class DonoEntity(
    @Id
    @GereneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    val nome: String,

    @Column(nullable = false, unique = true, length = 100)
    val email: String,

    @Column(nullable = false, unique = true, length = 14)
    val cpf: String,

    @Column(nullable = false, length = 20)
    val telefone: String,

    @Column("data_nascimento")
    val dataNascimento: LocalDateTime? = null,

    @Column(name = "data_criacao", nullable = false)
    val dataCriacao: LocalDateTime = LocalDateTime.now(),

    @Column(name = "data_atualizacao")
    val dataAtualizacao: LocalDateTime? = LocalDateTime.now(),

    @Column(nullable = false)
    val ativo: Boolean = true,

    //CAMPOS ESPECIFICOS DO DONO
    @Column(nullable = false, unique = true, length = 50)
    val matricula: String,

    @Column("percentual_participacao")
    val percentualParticipacao: Double? = null,



    //RELACIONADO
    @OneToMany(mappedBy = "dono", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val estacionamentos: List<EstacionamentoEntity> = mutableListOf()
) {
    // Métodos de negócio
    fun ativar() = copy(ativo = true, dataAtualizacao = LocalDateTime.now())

    fun inativar() = copy(ativo = false, dataAtualizacao = LocalDateTime.now())

    fun atualizarDataModificacao() = copy(dataAtualizacao = LocalDateTime.now())

    fun adicionarEstacionamento(estacionamentoEntity: EstacionamentoEntity): DonoEntity {
        val novaLista = estacionamentos.toMutableList()
        novaLista.add(estacionamentos)
        return copy(estacionamentos = novaLista)
    }

    fun calcularPArticipacaoTotal(): Double {
        return estacionamentos
            .filter { it.ativo }
            .sumOf { it.capacidade * (percentualParticipacao ?: 0.0) / 100 }
    }

    fun getQuantidadeEstacionamentosAtivos(): Int {
        return estacionamentos.count { it.ativo }
    }

    //VALIDAÇÔES
    fun isValid(): Boolean {
        return nome.isNotBlank() &&
                email.isNotBlank() &&
                cpf.isNotBlank() &&
                telefone.isNotBlank() &&
                matricula.isNotBlank() &&
                (percentualParticipacao == null || percentualParticipacao in 0.0..100.0)

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DonoEntity

        if (id != other.id) return false
        if (cpf != other.cpf) return false
        if (email != other.email) return false
        if (matricula != other.matricula) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + cpf.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + matricula.hashCode()
        return result
    }

    override fun toString(): String {
        return "DonoEntity(id=$id, " +
                "nome='$nome', " +
                "email='$email', " +
                "cpf='$cpf', " +
                "telefone='$telefone', " +
                "ativo=$ativo, " +
                "matricula='$matricula', "

    }
    companion object{
        fun  create(
            nome: String,
            email: String,
            cpf: String,
            telefone: String,
            matricula: String,
            percentualParticipacao: Double? = null,
            dataNascimento: LocalDateTime? = null
        ): DonoEntity{
            return DonoEntity(
                nome = nome,
                email = email,
                cpf = cpf,
                telefone = telefone,
                matricula = matricula,
                percentualParticipacao = percentualParticipacao,
                dataNascimento = dataNascimento
            )
        }
    }

}