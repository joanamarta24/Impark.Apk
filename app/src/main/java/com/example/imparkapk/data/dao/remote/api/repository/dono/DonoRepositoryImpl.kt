package com.example.imparkapk.data.dao.remote.api.repository.dono

import com.example.imparkapk.data.dao.local.dao.entity.DonoEntity
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicLong

class DonoRepositoryImpl: DonoRepository{
    private val donosStorage = ConcurrentHashMap<Long, DonoEntity>()
    private val idCounter = AtomicLong(1)


    override fun findAll(): List<DonoEntity> {
       return donosStorage.values.toList()
    }
    override fun findById(id: Long): Optional<DonoEntity> {
        return Optional.ofNullable(donosStorage[id])
    }

    override fun findByEmail(email: String): Optional<DonoEntity> {
       return donosStorage.values.find { it.email == email }?.let { Optional.of(it) } ?: Optional.empty()
    }

    override fun findByCpf(cpf: String): Optional<DonoEntity> {
        return donosStorage.values.find { it.cpf == cpf }?.let { Optional.of(it) }?: Optional.empty()
    }

    override fun findByMatricula(matricula: String): Optional<DonoEntity> {
        return donosStorage.values.find { it.matricula == matricula }?.let { Optional.of(it) }?: Optional.empty()
    }

    override fun findByAtivo(ativo: Boolean): List<DonoEntity> {
        return donosStorage.values.filter { it.ativo == ativo }
    }


    override fun existsByEmail(email: String): Boolean {
        return donosStorage.values.any { it.email == email }
    }

    override fun existsByCpf(cpf: String): Boolean {
        return donosStorage.values.any { it.cpf == cpf }
    }

    override fun existsByMatricula(matricula: String): Boolean {
        return donosStorage.values.any { it.matricula == matricula }
    }

    override fun findByNomeContaining(nome: String): List<DonoEntity> {
        return donosStorage.values.filter { it.nome.contains(nome, ignoreCase = true) }
    }

    override fun findByPercentualParticipacaoGreaterThan(percentualMinimo: Double): List<DonoEntity> {
        return donosStorage.values.filter {
            it.percentualParticipacao != null && it.percentualParticipacao > percentualMinimo
        }
    }

    override fun countAtivos(): Long {
        return donosStorage.values.count { it.ativo }.toLong()
    }

    override fun save(dono: DonoEntity): DonoEntity {
        val id = idCounter.getAndIncrement()
        val donoComId = dono.copy(id = id)
        donosStorage[id] = donoComId
        return donoComId
    }

    override fun update(dono: DonoEntity): DonoEntity {
        if (dono.id == null || !donosStorage.containsKey(dono.id)) {
            throw IllegalArgumentException("Dono não encontrado para atualização")
        }
        donosStorage[dono.id!!] = dono
        return dono
    }

    override fun delete(id: Long): Boolean {
        return donosStorage.remove(id) != null
    }

    override fun deleteByEmail(email: String): Boolean {
        val dono = findByEmail(email).orElse(null) ?: return false
        return delete(dono.id!!)
    }
}