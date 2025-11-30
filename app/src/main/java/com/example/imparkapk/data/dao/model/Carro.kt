package com.example.imparkapk.domain.model

data class Carro(
    val id: String,
    val placa: String,
    val marca: String,
    val modelo: String,
    val cor: String,
    val ano: Int =0,
    val principal: Boolean = false,
    val ativo: Boolean = true,
    val dataCriacao: Long = System.currentTimeMillis(),
    val dataAtualizacao: Long = System.currentTimeMillis(),
    val fotoUrl: String? = null,
    val observacoes: String? = null
) {
    val nomeCompleto: String
        get() = "$marca $modelo"

    val placaFormatada: String
        get() = placa.uppercase()

    fun validar(): Boolean {
        return placa.isNotBlank() &&
                marca.isNotBlank() &&
                modelo.isNotBlank() &&
                cor.isNotBlank() &&
                ano in 1900..2100
    }

    companion object {
        fun criar(
            placa: String,
            marca: String,
            modelo: String,
            cor: String,
            ano: Int,
            observacoes: String? = null
        ): Carro {
            return Carro(
                id = generateId(),
                placa = placa.trim(),
                marca = marca.trim(),
                modelo = modelo.trim(),
                cor = cor.trim(),
                ano = ano,
                observacoes = observacoes?.trim(),
                principal = false
            )
        }

        private fun generateId(): String {
            return "carro_${System.currentTimeMillis()}_${(1000..9999).random()}"
        }

    }

}