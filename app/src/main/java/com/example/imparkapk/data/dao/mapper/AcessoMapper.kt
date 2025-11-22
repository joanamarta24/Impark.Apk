package com.example.imparkapk.data.dao.mapper

import com.example.imparkapk.data.dao.local.dao.usuarios.Acesso
import com.example.imparkapk.data.dao.remote.api.dto.AcessoDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
@Mapper
interface AcessoMapper {
    companion object{
        val INSTANCE: AcessoMapper = Mappers.getMapper(AcessoMapper::class.java)

        fun toDto (access: Acesso): AcessoDto {
            return AcessoDto(
                access.id,
                access.nome,
                access.descricao
            )
        }
        fun toEntity (accessDto: AcessoDto): Acesso{
            return Acesso(
                accessDto.id,
                accessDto.nome,
                accessDto.descricao
            )
        }

    }
}