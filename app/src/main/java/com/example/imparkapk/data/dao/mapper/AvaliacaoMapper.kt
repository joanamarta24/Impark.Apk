package com.example.imparkapk.data.dao.mapper

import com.example.imparkapk.data.dao.model.Avaliacao
import com.example.imparkapk.data.dao.remote.api.dto.avaliacao.AvaliacaoDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface AvaliacaoMapper {
    companion object{
        val INSTANCE: AvaliacaoMapper = Mappers.getMapper(AvaliacaoMapper::class.java)

        fun toDto(avaliacao: Avaliacao): AvaliacaoDto {
            return INSTANCE.toDto(avaliacao)
        }
        fun toEntity(avaliacaoDto: AvaliacaoDto): Avaliacao {
            return INSTANCE.toEntity(avaliacaoDto)
        }
    }
}
