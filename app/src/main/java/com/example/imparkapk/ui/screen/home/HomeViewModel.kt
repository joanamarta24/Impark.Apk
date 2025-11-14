package com.example.imparkapk.ui.screen.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imparkapk.domain.model.Estacionamento
import com.rafaelcosta.modelo_app_crud_usuario_api.data.repository.EstacionamentoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val estacionamentoRepository: EstacionamentoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // 1. Começa a observar a base de dados local imediatamente.
        //    A UI será atualizada automaticamente sempre que o banco de dados mudar.
        observarEstacionamentosLocais()

        // 2. Inicia uma busca por atualizações na API para sincronizar os dados.
        //    Isso atualizará o banco de dados local, que por sua vez ativará o fluxo acima.
        sincronizarDadosComApi()
    }

    private fun observarEstacionamentosLocais() {
        // A função correta no seu repositório é 'observeUsuarios', que retorna Flow<List<Estacionamento>>
        estacionamentoRepository.observeUsuarios()
            .onEach { estacionamentosDoDominio ->
                // Mapeia a lista do modelo de domínio para o modelo de UI
                val estacionamentosUi = estacionamentosDoDominio
                    // Filtra apenas os estacionamentos ativos para exibição
                    .filter { it.ativo }
                    .map { estacionamento ->
                        mapToUiModel(estacionamento)
                    }

                // Atualiza o estado com a nova lista, removendo o estado de loading/erro
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        estacionamentos = estacionamentosUi,
                        errorMessage = null // Limpa erros antigos ao receber novos dados
                    )
                }
            }
            .catch { e ->
                // Se houver um erro no fluxo do banco de dados (raro), informa a UI.
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Erro ao ler dados locais: ${e.message}"
                    )
                }
            }
            .launchIn(viewModelScope) // Inicia a coleta do fluxo no escopo do ViewModel
    }

    /**
     * Dispara a sincronização com a API usando a função 'refresh' do repositório.
     * Esta função pode ser chamada novamente para "puxar e atualizar".
     */
    fun sincronizarDadosComApi() {
        viewModelScope.launch {
            // Mostra o indicador de progresso apenas se a lista estiver vazia
            _uiState.update {
                if (it.estacionamentos.isEmpty()) it.copy(isLoading = true) else it
            }

            estacionamentoRepository.refresh()
                .onFailure { exception ->
                    // Se o refresh falhar (ex: sem conexão), atualiza a UI com um erro.
                    // Isso só é mostrado se não houver dados em tela.
                    _uiState.update {
                        if (it.estacionamentos.isEmpty()) {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Falha ao sincronizar: ${exception.message}"
                            )
                        } else {
                            // Se já temos dados, podemos ignorar o erro silenciosamente
                            // ou mostrar um Toast/Snackbar (lógica a ser adicionada na UI)
                            it
                        }
                    }
                }

            // O estado de isLoading é definido como 'false' automaticamente pelo fluxo
            // 'observarEstacionamentosLocais' quando os novos dados chegam.
        }
    }

    /**
     * Mapeia o modelo de dados do domínio para o modelo de UI.
     */
    private fun mapToUiModel(estacionamento: Estacionamento): EstacionamentoUiModel {
        val vagasDisponiveis = estacionamento.vagasDisponiveis
        val textoVagas: String
        val corVagas: Color

        if (vagasDisponiveis > 0) {
            textoVagas = "$vagasDisponiveis vagas disponíveis"
            corVagas = Color.Green // Cor do tema para 'disponível'
        } else {
            textoVagas = "Lotado"
            corVagas = Color.Red
        }

        return EstacionamentoUiModel(
            // Seu modelo de domínio usa Long para ID, mas o UiModel pode continuar com Int
            // se não houver problema de estouro. Vamos manter Long para segurança.
            id = estacionamento.id,
            nome = estacionamento.nome,
            endereco = estacionamento.endereco,
            vagasDisponiveis = vagasDisponiveis,
            textoVagas = textoVagas,
            corVagas = corVagas.value.toLong() // Converte a Cor do Compose para um Long
        )
    }
}
