package com.example.imparkApp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroCarroScreen(
    onCadastroConcluido: () -> Unit = {}
) {
    var modelo by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }
    var cor by remember { mutableStateOf("") }
    var sucesso by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Cadastro de Carro",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text("Modelo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = placa,
                onValueChange = { placa = it },
                label = { Text("Placa") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = cor,
                onValueChange = { cor = it },
                label = { Text("Cor") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Validação básica antes de cadastrar
                    if (modelo.isNotBlank() && placa.isNotBlank() && cor.isNotBlank()) {
                        sucesso = true
                        visible = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = modelo.isNotBlank() && placa.isNotBlank() && cor.isNotBlank()
            ) {
                Text(text = "Cadastrar")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Mensagem animada simples
            AnimatedVisibility(
                visible = sucesso,
                enter = slideInVertically(
                    initialOffsetY = { with(density) { -40.dp.roundToPx() } }
                ) + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Text(
                    text = "Carro cadastrado com sucesso!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

            // Overlay de sucesso
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000))
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                            .background(MaterialTheme.colorScheme.primary), // Cor corrigida
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Carro cadastrado!",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }

        // LaunchedEffect para navegação automática
        LaunchedEffect(sucesso) {
            if (sucesso) {
                delay(2000) // Reduzido para 2 segundos
                sucesso = false
                visible = false
                onCadastroConcluido()
            }
        }
    }

    @Preview (showBackground = true)
    @Composable
    fun PreviewCadastroCarro() {
        MaterialTheme {
            CadastroCarroScreen()
        }
    }
