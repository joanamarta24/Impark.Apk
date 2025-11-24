package com.example.imparkapk.ui.feature.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparktcc.ui.components.ValidatedTextField

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onRegisterButtonPressed: () -> Unit = {},
    vm: LoginViewModel = hiltViewModel()
) {
    val state by vm._uiState.collectAsState()

    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(400.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        ValidatedTextField(
                            value = state.email,
                            onValueChange = { vm.onEmailChange(it) },
                            label = "E-mail"
                        )
                        ValidatedTextField(
                            value = state.senha,
                            onValueChange = { vm.onSenhaChange(it) },
                            label = "Senha"
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                modifier = Modifier,
                                content = { Text("Login") },
                                colors = ButtonColors(
                                    containerColor = Color.Green,
                                    contentColor = Color.White,
                                    disabledContentColor = Color.Gray,
                                    disabledContainerColor = Color.LightGray
                                ),
                                onClick = { vm.login(onLoginSuccess) },
                            )
                            Button(
                                modifier = Modifier,
                                content = { Text("Cadastro") },
                                colors = ButtonColors(
                                    containerColor = Color.Blue,
                                    contentColor = Color.White,
                                    disabledContentColor = Color.Gray,
                                    disabledContainerColor = Color.LightGray
                                ),
                                onClick = { onRegisterButtonPressed() },
                            )
                        }
                    }
                }
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = Color.Red
                    )
                }
            }
        }
    }
}