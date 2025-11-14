package com.example.imparkapk.data.dao.ui.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparktcc.ui.viewmodel.LoginViewModel



@Composable
fun LoginScreen (
    onNavigateToHome: () -> Unit,
    onNavigateToCadastroUsuario: () -> Unit,
    onNavigateToRecuperarSenha: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.loginSucesso) {
        if (uiState.loginSucesso) {
            onNavigateToHome
        }
    }
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo/Header
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Login",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "BEM-VINDO AO IMPARK",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Faca login para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            //CAMPO DE EMAIL
            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "E-mail")
                },
                isError = !uiState.emailValido,
                supportingText = {
                    if (!uiState.emailValido) {
                        Text("Digite um e-mail válido")
                    }
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            //CAMPO DE SENHA
            OutlinedTextField(
                value = uiState.senha,
                onValueChange = viewModel::onSenhaChange,
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Senha")
                },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            //LEMBRA DE MIM
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Checkbox(
                    checked = uiState.lembrarMe,
                    onCheckedChange = viewModel::onLembrarMeChange
                )
                Text(
                    text = "Lembrar de mim",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            //MENSAGEM DE ERRO
            if (uiState.mensagemErro.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = uiState.mensagemErro,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            //BOTAO DE LOGIN
            Button(
                onClick = {viewModel.login()},
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.botaoLoginHabilitado
            ) {
                if (uiState.isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Entrado")
                }else{
                    Text("Entrar")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            //LINKS
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = onNavigateToRecuperarSenha) {
                    Text("Esqueceu a senha?")
                }
            }
            Text(
                text = "Não tem uma conta?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant

            )
            TextButton(onClick = onNavigateToCadastroUsuario) {
                Text("Cadastre-se")
            }
        }

    }
}