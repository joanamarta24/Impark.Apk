package com.example.imparkapk.data.dao.mapper.usuarios

import com.example.imparkapk.data.dao.remote.api.dto.usuario.DonoDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
@Mapper
interface DonoMapper {
    companion object{
        val INSTANCE: DonoMapper = Mappers.getMapper(DonoMapper::class.java)
    }
    fun toDto(dono:Dono): DonoDto
}