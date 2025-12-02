package com.example.imparkapk.data.dao.model

import TipoVeiculo
import java.util.Date

data class Veiculo(
    val id: String = "",
    val usuarioId: String = "",
    val estacionamentoId: String? = null,
    val placa: String = "",
    val marca: String = "",
    val modelo: String = "",
    val anoFabricacao: Int? = null,
    val anoModelo: Int? = null,
    val cor: String = "",
    val tipo: TipoVeiculo,
    val renavam: String? = null,
    val chassi: String? = null,
    val fotoUrl: String? = null,
    val observacoes: String? = null,
    val dataCadastro: Date = Date(),
    val dataAtualizacao: Date = Date(),
    val ativo: Boolean = true
) {
    fun formatarPlaca(): String {
        return placa.uppercase().replace("([A-Z]{3})([0-9]{1})([A-Z0-9]{1})([0-9]{2})".toRegex(), "$1-$2$3$4")
    }

    fun obterDescricaoCompleta(): String {
        return "$marca $modelo ${anoModelo ?: ""} - $cor"
    }

    fun validar(): Boolean {
        return placa.isNotBlank() &&
                marca.isNotBlank() &&
                modelo.isNotBlank() &&
                cor.isNotBlank() &&
                usuarioId.isNotBlank()
    }

}