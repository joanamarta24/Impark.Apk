package com.example.imparkapk.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparkapk.theme.ImparkApkTheme

// A classe de dados de exemplo foi removida daqui.

@Composable
fun HomeScreen(
    onGoToReservas: () -> Unit,
    onGoToProfile: () -> Unit,
    onGoToLogin: () -> Unit,
    onGoToSearch: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // A UI agora pode chamar a sincronização diretamente.
    val onRefresh = { viewModel.sincronizarDadosComApi() }

    HomeScreenContent(
        uiState = uiState,
        onRefresh = onRefresh, // Passa a função de atualização para o conteúdo da UI
        onGoToReservas = onGoToReservas,
        onGoToProfile = onGoToProfile,
        onGoToLogin = onGoToLogin,
        onGoToSearch = onGoToSearch
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onRefresh: () -> Unit,
    onGoToReservas: () -> Unit,
    onGoToProfile: () -> Unit,
    onGoToLogin: () -> Unit,
    onGoToSearch: () -> Unit,
) {
    var menuAberto by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = onRefresh
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ImPark") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    Box {
                        IconButton(onClick = { menuAberto = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Abrir menu")
                        }
                        DropdownMenu(
                            expanded = menuAberto,
                            onDismissRequest = { menuAberto = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Minhas Reservas") },
                                onClick = {
                                    menuAberto = false
                                    onGoToReservas()
                                },
                                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Minhas Reservas") }
                            )
                            DropdownMenuItem(
                                text = { Text("Perfil") },
                                onClick = {
                                    menuAberto = false
                                    onGoToProfile()
                                },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Perfil") }
                            )
                            DropdownMenuItem(
                                text = { Text("Sair (Login)") },
                                onClick = {
                                    menuAberto = false
                                    onGoToLogin()
                                },
                                leadingIcon = { Icon(Icons.Default.ExitToApp, contentDescription = "Sair") }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .pullRefresh(pullRefreshState) // Aplica o modificador pullRefresh
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // A lógica de if/else para isLoading/errorMessage/Conteúdo foi movida para dentro da Column
                if (uiState.isLoading && uiState.estacionamentos.isEmpty()) {
                    // Mostra um spinner centralizado apenas se a lista estiver vazia e carregando
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (uiState.errorMessage != null && uiState.estacionamentos.isEmpty()) {
                    // Mostra o erro apenas se a lista estiver vazia
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = uiState.errorMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = onRefresh) {
                                Text("Tentar Novamente")
                            }
                        }
                    }
                } else {
                    // Mostra a lista de estacionamentos (mesmo que um refresh esteja acontecendo em segundo plano)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Estacionamentos Próximos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(uiState.estacionamentos) { estacionamento ->
                            EstacionamentoCard(estacionamento = estacionamento)
                        }
                    }

                    Button(
                        onClick = onGoToSearch,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text("Ver todos os estacionamentos")
                    }
                }
            }

            // Indicador do PullToRefresh, fica centralizado no topo
            PullRefreshIndicator(
                refreshing = uiState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun EstacionamentoCard(estacionamento: EstacionamentoUiModel) { // <- Agora recebe EstacionamentoUiModel
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = estacionamento.nome,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = estacionamento.endereco,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = estacionamento.textoVagas, // Usa o texto pré-formatado
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(estacionamento.corVagas.toULong()) // Usa a cor pré-definida
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ImparkApkTheme {
        // O preview agora exibe um estado de sucesso com dados fictícios
        val previewState = HomeUiState(
            isLoading = false,
            estacionamentos = listOf(
                EstacionamentoUiModel(1, "Estacionamento Preview", "Rua do Preview, 123", 10, "10 vagas", Color.Green.value.toLong()),
                EstacionamentoUiModel(2, "Garagem Lotada Preview", "Avenida Teste, 456", 0, "Lotado", Color.Red.value.toLong())
            )
        )
        HomeScreenContent(
            uiState = previewState,
            onGoToReservas = {},
            onGoToProfile = {},
            onGoToLogin = {},
            onGoToSearch = {},
            onRefresh = {}
        )
    }
}
