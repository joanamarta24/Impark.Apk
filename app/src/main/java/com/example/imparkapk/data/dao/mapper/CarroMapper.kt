package com.example.imparkapk.data.dao.mapper

import com.example.imparkapk.data.dao.remote.api.dto.carro.CarroDto
import com.example.imparkapk.domain.model.Carro
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface CarroMapper {
    companion object
    val INSTANCE: CarroMapper = Mappers.getMapper(CarroMapper::class.java)

    fun toDto(carro: Carro): CarroDto{
        return CarroDto()
        fun  toEntity(carroDto: CarroDto): Carro{
            return Carro()
        }
        fun  toDtoList(carros: List<Carro>):List<CarroDto>{
            return carros.map { toDto(it) }
        }
    }
}