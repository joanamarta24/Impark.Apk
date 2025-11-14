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
    // A navegação continua sendo responsabilidade de quem chama a tela
    onGoToReservas: () -> Unit,
    onGoToProfile: () -> Unit,
    onGoToLogin: () -> Unit,
    onGoToSearch: () -> Unit,
    // O ViewModel agora é o provedor do estado
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Coleta o estado da UI a partir do ViewModel
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        onGoToReservas = onGoToReservas,
        onGoToProfile = onGoToProfile,
        onGoToLogin = onGoToLogin,
        onGoToSearch = onGoToSearch
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    // Recebe o estado pronto para ser desenhado
    uiState: HomeUiState,
    onGoToReservas: () -> Unit,
    onGoToProfile: () -> Unit,
    onGoToLogin: () -> Unit,
    onGoToSearch: () -> Unit,
) {
    var menuAberto by remember { mutableStateOf(false) }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                // Mostra um indicador de progresso enquanto os dados carregam
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.errorMessage != null) {
                // Mostra uma mensagem de erro se algo falhar
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                // Mostra o conteúdo principal se tudo estiver certo
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
                    // Usa a lista de estacionamentos do uiState
                    items(uiState.estacionamentos) { estacionamento ->
                        EstacionamentoCard(estacionamento = estacionamento)
                    }
                }

                Button(
                    onClick = onGoToSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Ver todos os estacionamentos")
                }
            }
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
            onGoToSearch = {}
        )
    }
}
