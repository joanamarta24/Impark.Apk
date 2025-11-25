package com.example.imparkapk.data.dao.remote.api.repository.dono

import com.example.imparkapk.data.dao.local.dao.entity.DonoEntity
import java.util.Optional

interface DonoRepository {
    fun findAll(): List<DonoEntity>

    fun findById(id: Long): Optional<DonoEntity>

    fun findByEmail(email: String): Optional<DonoEntity>

    fun findByCpf(cpf: String): Optional<DonoEntity>

    fun findByMatricula(matricula: String): Optional<DonoEntity>

    fun findByAtivo(ativo: Boolean): List<DonoEntity>

    fun existsByEmail(email: String): Boolean

    fun existsByCpf(cpf: String): Boolean

    fun existsByMatricula(matricula: String): Boolean

    fun findByNomeContaining(nome: String): List<DonoEntity>

    fun findByPercentualParticipacaoGreaterThan(percentualMinimo: Double): List<DonoEntity>

    fun countAtivos(): Long

    fun save(dono: DonoEntity): DonoEntity

    fun update(dono: DonoEntity): DonoEntity

    fun delete(id: Long): Boolean

    fun deleteByEmail(email: String): Boolean
}