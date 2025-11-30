package com.example.imparkapk.presentation.viewmodels

import com.example.imparkapk.data.dao.model.enus.Operacao
import com.example.imparkapk.data.dao.model.enus.OrdenarCarros
import com.example.imparkapk.domain.model.Carro
import kotlin.collections.sortedBy
import kotlin.collections.sortedByDescending

data class CarrosUiState(
    // Lista de carros
    val carros: List<Carro> = emptyList(),

    // Estados de loading
    val isLoading: Boolean = false,
    val isLoadingOperacao: Boolean = false,

    // Mensagens de feedback
    val mensagemErro: String? = null,
    val mensagemSucesso: String? = null,

    // Estados de operações específicas
    val operacaoEmAndamento: Operacao? = null,
    val carroSelecionado: Carro? = null,
    val carroParaExcluir: Carro? = null,

    // Estados de formulário
    val formulario: CarroFormularioState = CarroFormularioState(),

    // Filtros e ordenação
    val filtroAtivo: Boolean = true,
    val ordenacao: OrdenarCarros = OrdenarCarros.PADRAO,

    // Estados de UI
    val showDialogExclusao: Boolean = false,
    val showDialogConfirmacao: Boolean = false,
    val mensagemConfirmacao: String? = null,

    // Estados de sincronização
    val ultimaSincronizacao: Long? = null,
    val sincronizando: Boolean = false,

    // Estados de busca
    val termoBusca: String = "",
    val carrosFiltrados: List<Carro> = emptyList()
) {
    // Propriedades computadas
    val carroPrincipal: Carro?
        get() = carros.find { it.principal }

    val totalCarros: Int
        get() = carros.size

    val carrosAtivos: List<Carro>
        get() = carros.filter { it.ativo }

    val temCarros: Boolean
        get() = carros.isNotEmpty()

    val temCarroPrincipal: Boolean
        get() = carroPrincipal != null

    val podeAdicionarCarro: Boolean
        get() = !isLoading && !isLoadingOperacao

    val mostrarLoading: Boolean
        get() = isLoading || isLoadingOperacao

    val mostrarEmptyState: Boolean
        get() = !isLoading && carros.isEmpty()

    val mostrarLista: Boolean
        get() = !isLoading && carros.isNotEmpty()

    // Métodos de cópia para atualizações específicas
    fun comLoading(loading: Boolean): CarrosUiState {
        return copy(isLoading = loading)
    }

    fun comLoadingOperacao(loading: Boolean, operacao: Operacao? = null): CarrosUiState {
        return copy(
            isLoadingOperacao = loading,
            operacaoEmAndamento = if (loading) operacao else null
        )
    }

    fun comMensagemErro(mensagem: String?): CarrosUiState {
        return copy(mensagemErro = mensagem)
    }

    fun comMensagemSucesso(mensagem: String?): CarrosUiState {
        return copy(mensagemSucesso = mensagem)
    }

    fun comCarros(carros: List<Carro>): CarrosUiState {
        return copy(
            carros = carros,
            carrosFiltrados = aplicarFiltrosEBusca(carros)
        )
    }

    fun comCarroSelecionado(carro: Carro?): CarrosUiState {
        return copy(carroSelecionado = carro)
    }

    fun comCarroParaExcluir(carro: Carro?): CarrosUiState {
        return copy(
            carroParaExcluir = carro,
            showDialogExclusao = carro != null
        )
    }

    fun comTermoBusca(termo: String): CarrosUiState {
        return copy(
            termoBusca = termo,
            carrosFiltrados = aplicarFiltrosEBusca(carros, termo)
        )
    }

    fun comFiltroAtivo(filtro: Boolean): CarrosUiState {
        return copy(
            filtroAtivo = filtro,
            carrosFiltrados = aplicarFiltrosEBusca(carros)
        )
    }

    fun comOrdenacao(ordenacao: OrdenarCarros): CarrosUiState {
        return copy(
            ordenacao = ordenacao,
            carrosFiltrados = aplicarOrdenacao(carrosFiltrados, ordenacao)
        )
    }

    fun comFormulario(formulario: CarroFormularioState): CarrosUiState {
        return copy(formulario = formulario)
    }

    fun limparMensagens(): CarrosUiState {
        return copy(
            mensagemErro = null,
            mensagemSucesso = null,
            mensagemConfirmacao = null
        )
    }

    fun limparDialogos(): CarrosUiState {
        return copy(
            showDialogExclusao = false,
            showDialogConfirmacao = false,
            carroParaExcluir = null
        )
    }

    fun comSincronizacao(sincronizando: Boolean, ultimaSincronizacao: Long? = null): CarrosUiState {
        return copy(
            sincronizando = sincronizando,
            ultimaSincronizacao = ultimaSincronizacao ?: this.ultimaSincronizacao
        )
    }

    // Métodos auxiliares privados
    private fun aplicarFiltrosEBusca(
        lista: List<Carro>,
        termo: String = this.termoBusca
    ): List<Carro> {
        var resultado = lista

        // Aplicar filtro de ativos
        if (filtroAtivo) {
            resultado = resultado.filter { it.ativo }
        }

        // Aplicar busca
        if (termo.isNotBlank()) {
            resultado = resultado.filter { carro ->
                carro.placa.contains(termo, ignoreCase = true) ||
                        carro.marca.contains(termo, ignoreCase = true) ||
                        carro.modelo.contains(termo, ignoreCase = true) ||
                        carro.cor.contains(termo, ignoreCase = true) ||
                        carro.ano.toString().contains(termo)
            }
        }

        // Aplicar ordenação
        return aplicarOrdenacao(resultado, ordenacao)
    }

    private fun aplicarOrdenacao(
        lista: List<Carro>,
        ordenacao:  OrdenarCarros
    ): List<Carro> {
        return when (ordenacao) {
            OrdenarCarros.PADRAO -> lista.sortedByDescending { it.principal }
                .sortedBy { it.marca }
            OrdenarCarros.MARCA -> lista.sortedBy { it.marca }
            OrdenarCarros.MODELO -> lista.sortedBy { it.modelo }
            OrdenarCarros.ANO -> lista.sortedByDescending { it.ano }
            OrdenarCarros.PLACA -> lista.sortedBy { it.placa }
            OrdenarCarros.PRINCIPAL -> lista.sortedByDescending { it.principal }
            OrdenarCarros.DATA_CRIACAO -> lista.sortedByDescending { it.dataCriacao }
        }
    }

    companion object {
        // Estado inicial
        val INITIAL = CarrosUiState()
    }
}

// Estados do formulário de carro
data class CarroFormularioState(
    val placa: String = "",
    val marca: String = "",
    val modelo: String = "",
    val cor: String = "",
    val ano: Int = 2024,
    val observacoes: String = "",
    val fotoUrl: String? = null,
    val principal: Boolean = false,

    // Estados de validação
    val placaError: String? = null,
    val marcaError: String? = null,
    val modeloError: String? = null,
    val corError: String? = null,
    val anoError: String? = null,

    // Estados de UI do formulário
    val isEditando: Boolean = false,
    val carroId: String? = null,
    val showDialogFoto: Boolean = false
) {
    val isValid: Boolean
        get() = placa.isNotBlank() &&
                marca.isNotBlank() &&
                modelo.isNotBlank() &&
                cor.isNotBlank() &&
                ano in 1900..2100 &&
                placaError == null &&
                marcaError == null &&
                modeloError == null &&
                corError == null &&
                anoError == null

    val mostrarBotoes: Boolean
        get() = placa.isNotBlank() || marca.isNotBlank() || modelo.isNotBlank()

    fun comPlaca(placa: String): CarroFormularioState {
        return copy(
            placa = placa,
            placaError = if (placa.isBlank()) "Placa é obrigatória"
            else if (!validarPlaca(placa)) "Placa inválida"
            else null
        )
    }

    fun comMarca(marca: String): CarroFormularioState {
        return copy(
            marca = marca,
            marcaError = if (marca.isBlank()) "Marca é obrigatória" else null
        )
    }

    fun comModelo(modelo: String): CarroFormularioState {
        return copy(
            modelo = modelo,
            modeloError = if (modelo.isBlank()) "Modelo é obrigatório" else null
        )
    }

    fun comCor(cor: String): CarroFormularioState {
        return copy(
            cor = cor,
            corError = if (cor.isBlank()) "Cor é obrigatória" else null
        )
    }

    fun comAno(ano: Int): CarroFormularioState {
        return copy(
            ano = ano,
            anoError = if (ano < 1900 || ano > 2100) "Ano deve estar entre 1900 e 2100" else null
        )
    }

    fun comObservacoes(observacoes: String): CarroFormularioState {
        return copy(observacoes = observacoes)
    }

    fun comFotoUrl(fotoUrl: String?): CarroFormularioState {
        return copy(fotoUrl = fotoUrl)
    }

    fun comPrincipal(principal: Boolean): CarroFormularioState {
        return copy(principal = principal)
    }

    fun comModoEdicao(carroId: String? = null): CarroFormularioState {
        return copy(isEditando = true, carroId = carroId)
    }

    fun comModoCriacao(): CarroFormularioState {
        return copy(isEditando = false, carroId = null)
    }

    fun comShowDialogFoto(show: Boolean): CarroFormularioState {
        return copy(showDialogFoto = show)
    }

    fun limparErros(): CarroFormularioState {
        return copy(
            placaError = null,
            marcaError = null,
            modeloError = null,
            corError = null,
            anoError = null
        )
    }

    fun limpar(): CarroFormularioState {
        return CarroFormularioState()
    }

    fun preencherComCarro(carro: Carro): CarroFormularioState {
        return copy(
            placa = carro.placa,
            marca = carro.marca,
            modelo = carro.modelo,
            cor = carro.cor,
            ano = carro.ano,
            observacoes = carro.observacoes ?: "",
            fotoUrl = carro.fotoUrl,
            principal = carro.principal,
            isEditando = true,
            carroId = carro.id
        )
    }

    fun toCarro(): Carro {
        return Carro(
            id = carroId ?: "",
            placa = placa.trim(),
            marca = marca.trim(),
            modelo = modelo.trim(),
            cor = cor.trim(),
            ano = ano,
            observacoes = observacoes.trim().takeIf { it.isNotBlank() },
            fotoUrl = fotoUrl,
            principal = principal
        )
    }

    private fun validarPlaca(placa: String): Boolean {
        val placaRegex = Regex("^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$")
        return placaRegex.matches(placa.uppercase())
    }

    companion object {
        val EMPTY = CarroFormularioState()
    }
}


// Extensions para facilitar o uso
val CarrosUiState.operacaoAtual: String?
    get() = operacaoEmAndamento?.name

val CarrosUiState.tempoDesdeUltimaSincronizacao: String?
    get() = ultimaSincronizacao?.let { tempo ->
        val diferenca = System.currentTimeMillis() - tempo
        when {
            diferenca < 60000 -> "Agora mesmo"
            diferenca < 3600000 -> "${diferenca / 60000} min atrás"
            diferenca < 86400000 -> "${diferenca / 3600000} h atrás"
            else -> "${diferenca / 86400000} dias atrás"
        }
    }

val CarrosUiState.carrosParaExibicao: List<Carro>
    get() = if (termoBusca.isNotBlank() || filtroAtivo) carrosFiltrados else carros

val CarrosUiState.totalCarrosExibidos: Int
    get() = carrosParaExibicao.size