package com.example.imparktcc.cadastro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroUsuarioScreen(navController: NavController, function: @Composable () -> Unit) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    var emailValido by remember { mutableStateOf(true) }
    var senhaValida by remember { mutableStateOf(true) }
    var senhasCoincidem by remember { mutableStateOf(true) }
    var camposPreenchidos by remember { mutableStateOf(true) }

    var cadastroSucesso by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }

    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")

    fun validarSenha(senha: String): Boolean {
        return senha.length >= 6 && senha.any { it.isDigit() } && senha.any { it.isLetter() }
    }

    fun validarEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }

    fun validarCampos(): Boolean {
        val camposPreenchidosValidos =
            nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty() && confirmarSenha.isNotEmpty()
        val emailValidoLocal = validarEmail(email)
        val senhaValidaLocal = validarSenha(senha)
        val senhasCoincidemLocal = senha == confirmarSenha

        camposPreenchidos = camposPreenchidosValidos
        emailValido = emailValidoLocal
        senhaValida = senhaValidaLocal
        senhasCoincidem = senhasCoincidemLocal

        return camposPreenchidosValidos && emailValidoLocal && senhaValidaLocal && senhasCoincidemLocal
    }

    fun realizarCadastro() {
        if (validarCampos()) {
            isLoading = true
            mensagemErro = ""

            simulateApiCall { sucesso ->
                isLoading = false
                if (sucesso) {
                    cadastroSucesso = true

                    nome = ""
                    email = ""
                    senha = ""
                    confirmarSenha = ""

                    LaunchedEffect(Unit) {
                        delay(2000)
                        navController.navigate("carro")
                    }
                } else {
                    mensagemErro = "Falha no cadastro. Tente novamente mais tarde."
                }
            }
        } else {
            mensagemErro = "Por favor, corrija os erros nos campos acima."
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro de Usuário") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    camposPreenchidos = true
                },
                label = { Text("Nome Completo*") },
                modifier = Modifier.fillMaxWidth(),
                isError = !camposPreenchidos && nome.isEmpty(),
                supportingText = {
                    if (!camposPreenchidos && nome.isEmpty()) {
                        Text("Campo obrigatório")
                    }
                }
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailValido = true
                },
                label = { Text("Email*") },
                modifier = Modifier.fillMaxWidth(),
                isError = !emailValido,
                supportingText = {
                    if (!emailValido) {
                        Text("Digite um e-mail válido")
                    }
                }
            )
            OutlinedTextField(
                value = senha,
                onValueChange = {
                    senha = it
                    senhaValida = true
                    senhasCoincidem = true
                },
                label = { Text("Senha*") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                isError = !senhaValida,
                supportingText = {
                    if (!senhaValida) {
                        Text("Senha deve ter pelo menos 6 caracteres, uma letra e um número")
                    }
                }
            )
            OutlinedTextField(
                value = confirmarSenha,
                onValueChange = {
                    confirmarSenha = it
                    senhasCoincidem = true
                },
                label = { Text("Confirmar Senha*") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                isError = !senhasCoincidem,
                supportingText = {
                    if (!senhasCoincidem) {
                        Text("As senhas devem corresponder")
                    }
                }
            )
            if (mensagemErro.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        text = mensagemErro,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            AnimatedVisibility(
                visible = cadastroSucesso,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cadastro realizado com sucesso",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.weight(1f)
                        )
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { realizarCadastro() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cadastrando...")
                } else {
                    Text("Cadastrar Usuário")
                }
            }
            TextButton(
                onClick = { navController.navigate("carro") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Já tem conta? Ir para Cadastro de Carro")
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Requisitos da senha:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "• Mínimo 6 caracteres",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "• Pelo menos 1 letra e 1 número",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Função simulateApiCall corrigida
fun simulateApiCall(
    callback: (Boolean) -> Unit,
    delayMillis: Long = 2000,
    successRate: Double = 0.9,
    simulateNetworkError: @Composable (ERROR) -> ERROR = false
) {
    Thread {
        try {
            Thread.sleep(delayMillis)
            if (simulateNetworkError && Random.nextInt(0, 10) == 0) {
                throw RuntimeException("Simulated network error")
            }
            val sucesso = Random.nextDouble() < successRate
            callback(sucesso)
        } catch (e: InterruptedException) {
            callback(false)
        } catch (e: Exception) {
            callback(false)
        }
    }.start()
}

@Composable
private fun ScrollBoxesSmooth() {
    val state = rememberScrollState()
    LaunchedEffect(Unit) {
        state.animateScrollTo(100)
    }

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .padding(horizontal = 8.dp)
            .verticalScroll(state)
    ) {
        repeat(10) {
            Text("Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}
@Preview(showBackground = true)
@Composable
