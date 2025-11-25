package com.example.imparkapk.data.dao.mapper.usuarios

import com.example.imparkapk.data.dao.model.Gerente
import com.example.imparkapk.data.dao.remote.api.dto.GerenteDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.factory.Mappers

@Mapper
interface GerenteMapper {
    companion object{
        val INSTANCE: GerenteMapper = Mappers.getMapper(GerenteMapper::class.java)
    }

    fun toDto(gerente: Gerente): GerenteDto
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)

    fun toEntity(gerenteDto: GerenteDto): Gerente
    fun toDtoList(gerenteList: List<Gerente>): List<GerenteDto>
    fun updateFromDto(gerenteDto: GerenteDto, @MappingTarget gerente: Gerente)
}