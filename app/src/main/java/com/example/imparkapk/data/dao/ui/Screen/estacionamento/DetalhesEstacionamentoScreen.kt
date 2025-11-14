package com.example.imparktcc.ui.screen.estacionamento


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparkapk.data.dao.ui.viewmodel.EstacionamentoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesEstacionamentoScreen(
    estacionamentoId: String,
    onNavigateBack: () -> Unit,
    onNavigateToReserva: () -> Unit,
    onNavigateToAvaliacoes: () -> Unit,
    onNavigateToAvaliar: () -> Unit,
    viewModel: EstacionamentoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    LaunchedEffect(estacionamentoId) {
        viewModel.buscarEstacionamentoPorId(estacionamentoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        uiState.estacionamentoSelecionado?.nome ?: "Carregando...",
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        bottomBar = {
            if (uiState.estacionamentoSelecionado != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = onNavigateToReserva,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = uiState.estacionamentoSelecionado?.vagasDisponiveis ?: 0 > 0
                    ) {
                        Text("Fazer Reserva")
                    }
                }
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.estacionamentoSelecionado != null) {
            val estacionamento = uiState.estacionamentoSelecionado!!

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }
    }
}