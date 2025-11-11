package com.example.imparkapk.ui.feature.estacionamento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparkapk.domain.model.Estacionamento
import com.example.imparkapk.domain.model.StatusVagas
import com.example.imparkapk.data.local.ui.viewmodel.EstacionamentoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEstacionamentosScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetalhesEstacionamento: (String) -> Unit,
    onNavigateToReserva: (String) -> Unit,
    onNavigateToAvaliacao: (String) -> Unit,
    viewModel: EstacionamentoViewModel = hiltViewModel()
) {
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
            // BARRA DE PESQUISA
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text("Buscar estacionamento...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                singleLine = true
            )

            // FILTROS RÁPIDOS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    text = "Com vagas",
                    selected = uiState.filtrarComVagas,
                    onSelected = { viewModel.onFiltroVagasChange(it) }
                )
                FilterChip(
                    text = "Menor preço",
                    selected = uiState.ordenacaoSelecionada == com.example.imparkapk.data.local.ui.viewmodel.OrdenacaoEstacionamento.MENOR_PRECO,
                    onSelected = { if (it) viewModel.onOrdenacaoChange(com.example.imparkapk.data.local.ui.viewmodel.OrdenacaoEstacionamento.MENOR_PRECO) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.estacionamentosFiltrados) { estacionamento ->
                        EstacionamentoCard(
                            estacionamento = estacionamento,
                            onDetalhesClick = { onNavigateToDetalhesEstacionamento(estacionamento.id) },
                            onReservaClick = { onNavigateToReserva(estacionamento.id) },
                            onAvaliacaoClick = { onNavigateToAvaliacao(estacionamento.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    text: String,
    selected: Boolean,
    onSelected: (Boolean) -> Unit
) {
    Card(
        onClick = { onSelected(!selected) },
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

@Composable
fun EstacionamentoCard(
    estacionamento: Estacionamento,
    onDetalhesClick: () -> Unit,
    onReservaClick: () -> Unit,
    onAvaliacaoClick: () -> Unit,
    modifier: Modifier = Modifier,
    mostrarDistancia: Boolean = false,
    distancia: String? = null
) {
    Card(
        onClick = onDetalhesClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Cabeçalho: Nome e Avaliação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = estacionamento.nome,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                // Avaliação
                if (estacionamento.totalAvaliacoes > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Avaliação",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = estacionamento.notaMediaFormatada,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "(${estacionamento.totalAvaliacoes})",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.StarBorder,
                            contentDescription = "Sem avaliações",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Novo",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Localização e distância
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Localização",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = estacionamento.endereco,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                // Distância (se disponível)
                if (mostrarDistancia && distancia != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = distancia,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Informações principais
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Preço
                InfoColumn(
                    title = "Preço/hora",
                    value = estacionamento.valorHoraFormatado,
                    icon = null,
                    valueColor = MaterialTheme.colorScheme.primary
                )

                // Vagas
                InfoColumn(
                    title = "Vagas",
                    value = estacionamento.vagasDisponiveis,
                    icon = null,
                    valueColor = when (estacionamento.statusVagas) {
                        StatusVagas.DISPONIVEL -> MaterialTheme.colorScheme.primary
                        StatusVagas.MODERADO -> Color(0xFFFFA000) // Laranja
                        StatusVagas.QUASE_LOTADO -> Color(0xFFFF5722) // Laranja escuro
                        StatusVagas.LOTADO -> MaterialTheme.colorScheme.error
                    }
                )

                // Horário
                InfoColumn(
                    title = "Horário",
                    value = estacionamento.horarioFuncionamento,
                    icon = null
                )
            }

            // Barra de progresso das vagas
            if (estacionamento.totalVagas > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                VagasProgressBar(estacionamento = estacionamento)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status e ações
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status
                StatusIndicator(estacionamento = estacionamento)

                // Ações
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = onAvaliacaoClick
                    ) {
                        Text("Avaliar")
                    }

                    Button(
                        onClick = onReservaClick,
                        enabled = estacionamento.estaAberto && estacionamento.vagasDisponiveis > 0
                    ) {
                        Text("Reservar")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoColumn(
    title: String,
    value: String,
    icon: ImageVector?,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (icon != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(12.dp),
                    tint = valueColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = valueColor
                )
            }
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = valueColor
            )
        }
    }
}

@Composable
fun VagasProgressBar(estacionamento: Estacionamento) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Ocupação: ${estacionamento.vagasDisponiveis}/${estacionamento.totalVagas}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${estacionamento.porcentagemOcupacao.toInt()}%",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = (estacionamento.totalVagas - estacionamento.vagasDisponiveis).toFloat() / estacionamento.totalVagas.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = when (estacionamento.statusVagas) {
                StatusVagas.DISPONIVEL -> MaterialTheme.colorScheme.primary
                StatusVagas.MODERADO -> Color(0xFFFFA000)
                StatusVagas.QUASE_LOTADO -> Color(0xFFFF5722)
                StatusVagas.LOTADO -> MaterialTheme.colorScheme.error
            },
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun StatusIndicator(estacionamento: Estacionamento) {
    val (statusText, statusColor) = when {
        !estacionamento.ativo -> Pair("Inativo", MaterialTheme.colorScheme.error)
        !estacionamento.estaAberto -> Pair("Fechado", MaterialTheme.colorScheme.onSurfaceVariant)
        estacionamento.vagasDisponiveis == 0 -> Pair("Lotado", MaterialTheme.colorScheme.error)
        else -> Pair("Aberto", MaterialTheme.colorScheme.primary)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = statusColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = statusText,
            style = MaterialTheme.typography.labelSmall,
            color = statusColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EstacionamentoCardPreview() {
    MaterialTheme {
        EstacionamentoCard(
            estacionamento = Estacionamento(
                id = "1",
                nome = "Estacionamento Central Shopping",
                endereco = "Av. Principal, 123 - Centro, São Paulo - SP",
                totalVagas = 100,
                vagasDisponiveis = 25,
                valorHora = 8.50,
                telefone = "(11) 9999-8888",
                horarioAbertura = "08:00",
                horarioFechamento = "22:00",
                notaMedia = 4.2,
                totalAvaliacoes = 150
            ),
            onDetalhesClick = {},
            onReservaClick = {},
            onAvaliacaoClick = {},
            mostrarDistancia = true,
            distancia = "1.2 km"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EstacionamentoCardLotadoPreview() {
    MaterialTheme {
        EstacionamentoCard(
            estacionamento = Estacionamento(
                id = "2",
                nome = "Parking Express",
                endereco = "Rua Rápida, 456 - Centro",
                totalVagas = 50,
                vagasDisponiveis = 0,
                valorHora = 6.00,
                telefone = "(11) 9777-6666",
                horarioAbertura = "07:00",
                horarioFechamento = "21:00",
                notaMedia = 3.8,
                totalAvaliacoes = 89
            ),
            onDetalhesClick = {},
            onReservaClick = {},
            onAvaliacaoClick = {}
        )
    }
}