@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package com.example.imparkApp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imparkapk.theme.ImparkApkTheme

class CadastroUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImparkApkTheme {
                AppNavigation()
            }
        }
    }
}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "usuario") {
        composable("usuario") { CadastroUsuarioScreen(navController) }
        composable("carro") { CadastroCarroScreen() }
        composable("")
    }
}

// ----------- TELA DE CADASTRO DE USUÁRIO -------------
@Composable
fun CadastroUsuarioScreen(navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Cadastro de Usuário") }) }
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
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = { navController.navigate("carro") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir para Cadastro de Carro")
            }
        }
    }
}

// ----------- TELA DE CADASTRO DE CARRO -------------
@Composable
fun CadastroCarroScreen() {
    var modelo by remember { mutableStateOf(TextFieldValue("")) }
    var placa by remember { mutableStateOf(TextFieldValue("")) }
    var cor by remember { mutableStateOf(TextFieldValue("")) }
    var sucesso by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Cadastro de Carro", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = modelo, onValueChange = { modelo = it }, label = { Text("Modelo") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = placa, onValueChange = { placa = it }, label = { Text("Placa") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = cor, onValueChange = { cor = it }, label = { Text("Cor") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { sucesso = !sucesso }, modifier = Modifier.fillMaxWidth()) {
                Text("Cadastrar")
            }

            Spacer(modifier = Modifier.height(20.dp))
            AnimatedVisibility(
                visible = sucesso,
                enter = slideInVertically { with(density) { -40.dp.roundToPx() } } + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Text(
                    text = " Carro cadastrado com sucesso!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// ----------- PREVIEWS -------------
@Preview(showBackground = true)
@Composable
fun PreviewCadastroUsuario() {
    ImparkAppTheme { CadastroUsuarioScreen(rememberNavController()) }
}

@Composable
fun ImparkAppTheme(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun CadastroUsuarioScreen(x0: NavHostController) {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun PreviewCadastroCarro() {
    ImparktccTheme { CadastroCarroScreen() }
}

@Composable
fun ImparktccTheme(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}

