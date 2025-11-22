package com.example.imparkapk.data.dao.mapper.usuarios

import com.example.imparkapk.data.dao.model.enus.Cliente
import com.example.imparkapk.data.dao.remote.api.dto.ClienteDto
import com.example.imparkapk.data.dao.remote.api.dto.usuario.ClienteDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.factory.Mappers

@Mapper
interface ClienteMapper {
    val INSTANCE: ClienteMapper = Mappers.getMapper(ClienteMapper::class.java)

    fun toDto(cliente: Cliente): ClienteDto
    fun toEntity(clienteDto: ClienteDto): Cliente
    fun toDtoList(clientes: List<Cliente>): List<ClienteDto>

    @Mapping(target = "id", ignore = true)
    fun updateFromDto(clienteDTO: ClienteDTO, @MappingTarget cliente: Cliente)

}