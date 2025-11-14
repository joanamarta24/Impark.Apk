package com.example.imparkapk.data.dao.ui.feature.login.home

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp



@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(
    onNavigateToEstacionamento: () -> Unit,
    onNavigateToMinhasReservas: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    onNavigateToLogin: () -> Unit

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Impark") },
                actions = {
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //HEADER/BEM-VINDO
            item {
                Text(
                    text = "Bem-vindo,Luan!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Encotre o estacionamento perfeito para você!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            //CARDS DE FUNCIONALIDADES PRINCIPAIS
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Buscar Estacionamentos
                    FeatureCard(
                        title = "Estacionamentos",
                        subtitle = "Encontre vagas",
                        icon = Icons.Default.LocationOn,
                        onClick = onNavigateToEstacionamento,
                        modifier = Modifier.weight(1f)
                    )

                    // Minhas Reservas
                    FeatureCard(
                        title = "Reservas",
                        subtitle = "Minhas reservas",
                        icon = Icons.Default.History,
                        onClick = onNavigateToMinhasReservas,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            // Cards secundários
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    //Meus Carros
                    FeatureCard(
                        title = "Meus Carros",
                        subtitle = "Gerenciar veiculos",
                        icon = Icons.Default.CarRental,
                        onClick = { /* Navegar para meus carros */ },
                        modifier = Modifier.weight(1f)

                    )
                    //Avaliações
                    FeatureCard(
                        title = "Avaliações",
                        subtitle = "Minhas avaliações",
                        icon = Icons.Default.Star,
                        onClick = { /* Navegar para minhas avaliações */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            // Seção de reservas recentes
            item {
                Text(
                    text = "Reservas Recentes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                val reservasRecentes = listOf(
                    ReservaItem("Estacionamento Central", "Hoje - 14:00 às 16:00", "R$ 16,00"),
                    ReservaItem("Parking Shopping", "Ontem - 10:00 às 12:00", "R$ 24,00")
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(reservasRecentes) { reserva ->
                        ReservaCard(reserva = reserva)
                    }
                }
            }
            item {
                Text(
                    text = "Estacionamentos Próximos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                val estacionamentosProximos = listOf(
                    EstacionamentoItem("Estacionamento Express", "0.8 km", "R$ 6,00/h", "8 vagas"),
                    EstacionamentoItem("Parking Center", "1.2 km", "R$ 8,50/h", "15 vagas")
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(estacionamentosProximos) { estacionamento ->
                        EstacionamentoCard(estacionamento = estacionamento)
                    }
                }
            }
        }
    }
}

@Composable
fun FeatureCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha =  0.8f)
        )
    }
}
@Composable
fun ReservaCard(reserva: ReservaItem){
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = "Reserva",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reserva.estacionamento,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = reserva.horario,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = reserva.valor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun EstacionamentoCard(estacionamento: EstacionamentoItem){
    Card(
        modifier = Modifier.fillMaxWidth()

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
               modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = estacionamento.nome,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = estacionamento.distancia,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant

                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = estacionamento.preco,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = estacionamento.vagas,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
data class ReservaItem(
    val estacionamento: String,
    val horario: String,
    val valor: String
)
data class EstacionamentoItem(
    val nome: String,
    val distancia: String,
    val preco: String,
    val vagas: String

)