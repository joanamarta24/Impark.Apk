package com.example.imparkapk.data.dao.ui.Screen.estacionamento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparkapk.data.dao.ui.Screen.home.EstacionamentoCard
import com.example.imparkapk.data.dao.ui.viewmodel.EstacionamentoViewModel



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
                        selected = uiState.ordenacaoSelecionada == com.example.imparktcc.ui.viewmodel.OrdenacaoEstacionamento.MENOR_PRECO,
                        onSelected = { if (it) viewModel.onOrdenacaoChange(com.example.imparktcc.ui.viewmodel.OrdenacaoEstacionamento.MENOR_PRECO) }
                    )
                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }else{
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.estacionamentosFiltrados){estacionamento ->
                            EstacionamentoCard(
                                estacionamento = estacionamento,
                                onDetalhesClick ={onNavigateToDetalhesEstacionamento(estacionamento.id)},
                                onReservaClick ={onNavigateToReserva(estacionamento.id)},
                                onAvaliacaoClick ={onNavigateToAvaliacao(estacionamento.id)}
                            )
                        }
                    }
                }
            }
        }
        @Composable
        fun FilterChip(
            text:String,
            selected:Boolean,
            onSelected:(Boolean) ->Unit
        ){
            Card (
                onClick = {onSelected(!selected)},
                modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = if (selected){
                        MaterialTheme.colorScheme.primary
                    }else{
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            ){
                Text(
                    text = text,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (selected){
                        MaterialTheme.colorScheme.primary
                    }else{
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
        @Composable
        fun EstacionamentoCard(
            estacionamento:com.example.imparkapk.model.Estacionamento
        ){}
    }
}