package com.example.imparkapk.data.dao.ui.Screen.reserva

import android.R.attr.contentDescription
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparkapk.data.dao.ui.viewmodel.EstacionamentoViewModel
import com.example.imparkapk.ui.viewmodel.EstacionamentoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaReservasScreen(
    onNavigateBack:() -> Unit,
    onNavigateToDetalhesEstacionamento:(String) -> Unit,
    onNagateToReserva:(String) -> Unit,
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
                    IconButton(onClick = { /* Abrir filtros */ }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtros")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            //BARRA DE PESQUISA
            // Barra de pesquisa
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text("Buscar estacionamentos...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                singleLine = true
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                FilterChip(
                    text ="Com vagas",
                    selected = uiState.filtrarComVagas,
                    onSelectrd = {viewModel.onFiltroVagasChange(it)}
                )
                FilterChip(
                    text ="Menor preço",
                    selectd =uiState.ordenacaoSelecionada == com.example.imparkapk.ui.viewmodel.OrdenacaoEstacionamento.MENOR_PRECO,
                    onSelected ={if (it) viewModel.onOrdenacaoChange(com.example.imparkapk.ui.viewmodel.OrdenacaoEstacionamento.MENOR_PRECO)}
                )

            }
            Spacer(modifier = Modifier.height(16.dp))
            if (uiState.isLoading)
    }
}