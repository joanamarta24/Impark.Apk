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
        TODO("Not yet implemented")
    }

    override fun findByCpf(cpf: String): Optional<DonoEntity> {
        TODO("Not yet implemented")
    }

    override fun findByMatricula(matricula: String): Optional<DonoEntity> {
        TODO("Not yet implemented")
    }

    override fun findByAtivo(ativo: Boolean): List<DonoEntity> {
        TODO("Not yet implemented")
    }

    override fun existsByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun existsByCpf(cpf: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun existsByMatricula(matricula: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun findByNomeContaining(nome: String): List<DonoEntity> {
        TODO("Not yet implemented")
    }

    override fun findByPercentualParticipacaoGreaterThan(percentualMinimo: Double): List<DonoEntity> {
        TODO("Not yet implemented")
    }

    override fun countAtivos(): Long {
        TODO("Not yet implemented")
    }

    override fun save(dono: DonoEntity): DonoEntity {
        TODO("Not yet implemented")
    }

    override fun update(dono: DonoEntity): DonoEntity {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }
}