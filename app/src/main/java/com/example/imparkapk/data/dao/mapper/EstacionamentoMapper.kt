package com.example.imparkapk.data.dao.mapper

import com.example.imparkapk.data.dao.local.dao.entity.Estacionamento
import com.example.imparkapk.data.dao.remote.api.dto.EstacionamentoDto
import com.example.imparkapk.data.dao.remote.api.dto.estacionamento.EstacionamentoDTO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
interface EstacionamentoMapper {
    companion object{
        val INSTANCE = Mappers.getMapper(EstacionamentoMapper::class.java)

    }
    fun toDto(estacionamento: Estacionamento): EstacionamentoDto
    fun toEntity(estacionamentoDTO: EstacionamentoDTO):Estacionamento
    fun toDtoList(estacionamentos: List<Estacionamento>): List<EstacionamentoDto>


}