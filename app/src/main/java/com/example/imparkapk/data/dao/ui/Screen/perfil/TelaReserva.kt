import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparktcc.ui.viewmodel.ReservaViewModel

@Composable
fun TelaReserva(
    viewModel: ReservaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.shouldNavigateToConfirmacao) {
        LaunchedEffect(Unit) {
            // Navegar para tela de confirmação
            viewModel.resetNavigation()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campos do formulário
        OutlinedTextField(
            value = uiState.dataReserva,
            onValueChange = viewModel::updateDataReserva,
            label = { Text("Data") },
            isError = uiState.dataError != null
        )

        // ... outros campos

        Button(
            onClick = viewModel::fazerReserva,
            enabled = uiState.isDataValid && !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Fazer Reserva")
            }
        }
    }
}