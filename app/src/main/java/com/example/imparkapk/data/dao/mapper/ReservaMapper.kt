package com.example.imparkapk.data.dao.mapper

import com.example.imparkapk.data.dao.model.Reserva
import com.example.imparkapk.data.dao.remote.api.dto.ReservaDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface ReservaMapper {

    companion object {
        val INSTANCE: ReservaMapper = Mappers.getMapper(ReservaMapper::class.java)
    }
    fun toDto(reserva: Reserva): ReservaDto
    fun toEntity(reservaDto: ReservaDto): Reserva
    fun toDtoList(reservas: List<Reserva>): List<ReservaDto>
}