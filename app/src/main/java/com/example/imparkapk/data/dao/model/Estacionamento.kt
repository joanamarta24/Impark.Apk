package com.example.imparkapk.data.dao.model

import com.example.imparkapk.data.dao.local.dao.entity.EstacionamentoEntity
import com.example.imparkapk.data.dao.model.enus.StatusVagas
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


data class Estacionamento(
    val id: String = "",
    val nome: String = "",
    val endereco: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val totalVagas: Int = 0,
    val vagasDisponiveis: Int = 0,
    val valorHora: Double = 0.0,
    val telefone: String = "",
    val horarioAbertura: String = "",
    val horarioFechamento: String = "",
    val ativo: Boolean = true,
    val descricao: String = "",
    val fotos: List<String> = emptyList(),
    val servicos: List<String> = emptyList(),
    val notaMedia: Double = 0.0,
    val totalAvaliacoes: Int = 0,
    val dataCriacao: Date = Date(),
    val dataAtualizacao: Date = Date(),

) {
    companion object {
        private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    }

    val estaAberto: Boolean
        get() {
            return try {
                val agora = Calendar.getInstance()
                val horaAtual = timeFormat.format(agora.time)

                val abertura = timeFormat.parse(horarioAbertura)
                val fechamento = timeFormat.parse(horarioFechamento)
                val atual = timeFormat.parse(horaAtual)

                atual in abertura..fechamento && vagasDisponiveis > 0 && ativo
            } catch (e: Exception) {
                false
            }
        }
    val porcentagemVagas: Double
        get() = if (totalVagas > 0) {
            (vagasDisponiveis.toDouble() / totalVagas.toDouble()) * 100
        } else {
            0.0
        }
    val statusVagas: StatusVagas
        get() = when {
            vagasDisponiveis == 0 -> StatusVagas.LOTADO
            porcentagemVagas < 20 -> StatusVagas.QUASE_LOTADO
            porcentagemVagas < 50 -> StatusVagas.MODERADO
            false -> StatusVagas.INATIVO
            else -> StatusVagas.DISPONIVEL


        }

    val temVagas: Boolean
        get() = vagasDisponiveis > 0

    val horarioFuncionamento: String
        get() = "$horarioAbertura - $horarioFechamento"
    val valorHoraFormatado: String
        get() = when {
            vagasDisponiveis == 0 -> "Lotado"
            vagasDisponiveis == 1 -> "1 vaga"
            else -> "$vagasDisponiveis vagas"
        }
    val notaMediaFormatada: String
        get() = "%.1f".format(notaMedia)

    val avaliacoesTexto: String
        get() = when (totalAvaliacoes) {
            0 -> "Sem avaliações"
            1 -> "1 avaliação"
            else -> "$totalAvaliacoes avaliações"
        }

    /*Calcula a distância entre este estacionamento e uma coordenada*/

    fun calcularDistancia(lat: Double, lon: Double): Double {
        val earthRadius = 6371.0

        val dLat = Math.toRadians(lat - latitude)
        val dLon = Math.toRadians(lon - longitude)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(lat)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadius * c
    }
    /*RETORNA A DISTÂNCIA FORMATADA*/

    fun getDistanciaFormatada(lat: Double, lon: Double): String {
        val distancia = calcularDistancia(lat, lon)
        return if (distancia < 1) {
            "${(distancia * 1000).toInt()} m"
        } else {
            "%.1f km".format(distancia)
        }
    }
    /*VERIFICAR SE O ESTACIONAMENTO TEM UM SERÇO ESPECIFICO*/

    fun temServico(servico: String): Boolean {
        return servicos.any { it.equals(servico, ignoreCase = true) }
    }

    /*RETORNA OS SERVIÇOS COMO TEXTO SEPARADO POR VIRGULA*/

    val servicosTexto: String
        get() = servicos.take(3).joinToString { "," }

    /*VALIDA SE OS DADOS DO ESTACIONAMENTO SÃO VÁLIDOS*/

    fun validar(): Boolean {
        return nome.isNotBlank() &&
                endereco.isNotBlank() &&
                totalVagas >= 0 &&
                vagasDisponiveis >= 0 &&
                vagasDisponiveis <= totalVagas &&
                valorHora > 0
                horarioAbertura.isNotBlank() &&
                horarioFechamento.isNotBlank() &&
                validarHorario(horarioAbertura) &&
                validarHorario(horarioFechamento)

    }
    /*VALIDAR O FORMATO DO HORÁRIO*/

    private fun validarHorario(horario: String): Boolean {
        return try {
            timeFormat.parse(horario)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun copyComVagasAtualizadas(novasVagas: Int): Estacionamento {
        return this.copy(
            vagasDisponiveis = novasVagas.coerceIn(0, totalVagas),
            dataAtualizacao = Date()
        )
    }

    /*CRIAR UMA CÓPIA COM VAGAS ATUALIZADAS*/

    fun copyComNovaAvaliacao(novaNota: Double, totalAval: Int): Estacionamento {
        return this.copy(
            notaMedia = novaNota,
            totalAvaliacoes = totalAval,
            dataAtualizacao = Date()
        )
    }
}
/*FILTRAR OS ESTACIONAMENTOS ABERTOS*/
fun List<Estacionamento>.filtarAbertos(): List<Estacionamento>{
    return this.filter { it.estaAberto }
}

/*FILTRAR OS ESTACIONAMENTOS COM VAGAS DISPONÍVEIS*/
fun List<Estacionamento>.filtrarComVagas(): List<Estacionamento>{
    return this.filter { it.vagasDisponiveis >0 }
}

/*ORDENA POR MENOR PREÇO*/
fun List<Estacionamento>.ordenarPorMenorPreco(): List<Estacionamento> {
    return this.sortedBy { it.valorHora }
}

/*ORDENA POR MAIOR NOTA*/
fun List<Estacionamento>.ordenarPorMaiorNota(): List<Estacionamento> {
    return this.sortedByDescending { it.notaMedia }
}

/*ORDENA POR MAIS VAGAS DISPONÍVEIS*/
fun List<Estacionamento>.ordenarPorMaisVagas(): List<Estacionamento> {
    return this.sortedByDescending { it.vagasDisponiveis }
}

/*BUSCA ESTACIONAMENTO POR NOME OU ENDEREÇO*/
fun List<Estacionamento>.buscar(query: String): List<Estacionamento> {
    return this.filter {
        it.nome.contains(query, ignoreCase = true) ||
                it.endereco.contains(query, ignoreCase = true)

    }
}
fun EstacionamentoEntity.toEstacionamento(): Estacionamento {
    val amenitiesList = try {
        this.amenities?.let {
            Gson().fromJson(it, Array<String>::class.java).toList()
        } ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }

    return Estacionamento(
        id = this.id.toString(),
        nome = this.nome,
        endereco = this.endereco,
        latitude = this.latitude,
        longitude = this.longitude,
        valorHora = this.valorHora,
        vagasDisponiveis = this.vagasDisponiveis,
        vagasTotal = this.vagasTotal,
        ativo = this.ativo,
        dataAtualizacao = this.dataAtualizacao,
        horarioFuncionamento = this.horarioFuncionamento,
        telefone = this.telefone,
        email = this.email,
        notaMedia = this.notaMedia,
        totalAvaliacoes = this.totalAvaliacoes,
        amenities = amenitiesList
    )
}




