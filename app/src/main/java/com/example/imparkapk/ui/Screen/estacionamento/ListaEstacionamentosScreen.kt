package com.example.imparkapk.ui.Screen.estacionamento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparktcc.ui.viewmodel.EstacionamentoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEstacionamentosScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetalhesEstacionamento: (String) -> Unit,
    onNavigateToReserva: (String) -> Unit,
    onNavigateToAvaliacao: (String) -> Unit,
    viewModel: EstacionamentoViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estacionamentos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
              actions = {
                  IconButton(onClick =  { /* Abrir filtros */ }) {
                      Icon(Icons.Default.FilterList, contentDescription = "Filtros")
                  }
              }
            )
        }
    ) {  padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            //BARRA DE PESQUISA
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = {Text("Buscar estacionamento...")},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                singleLine = true
            )
         //FILTROS RÁPIDOS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilterChip(
                    text ="Com vagas",
                    selected = uiState.filterComVagas,
                    onSelectedChange ={viewModel::onFilterComVagasChange(it)}
                )
                FilterChip(
                    text = "Menor preço",
                    selected = uiState.ordenacaoSelecionada == EstacionamentoViewModel.OrdenacaoEstacionamento.MENOR_PRECO,

                )
            }
        }
    }
}