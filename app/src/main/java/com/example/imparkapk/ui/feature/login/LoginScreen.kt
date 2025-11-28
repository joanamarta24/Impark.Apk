package com.example.imparkapk.ui.feature.login

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imparkapk.R
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

    val inParkGreen = Color(0xFF1DB954)
    val inParkWhite = Color.White
    val inParkDarkGray = Color(0xFF2A2A2A)
    val inParkLightGray = Color(0xFFF5F5F5)

    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(inParkLightGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(inParkLightGray),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo e Título
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        contentDescription = "Logo InPark",
                        painter = painterResource(R.drawable.logo),
                        modifier = Modifier
                            .background(Color.White),
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Inpark",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = inParkDarkGray
                )

                Text(
                    text = "Estacionamento Inteligente",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Card de Login
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = inParkWhite,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Campo E-mail
                        ValidatedTextField(
                            value = state.email,
                            onValueChange = { vm.onEmailChange(it) },
                            label = "E-mail",
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        // Campo Senha
                        ValidatedTextField(
                            value = state.senha,
                            onValueChange = { vm.onSenhaChange(it) },
                            label = "Senha",
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Botão de Login
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonColors(
                                containerColor = inParkGreen,
                                contentColor = inParkWhite,
                                disabledContentColor = Color.Gray,
                                disabledContainerColor = Color.LightGray
                            ),
                            onClick = { vm.login(onLoginSuccess) }
                        ) {
                            Text(
                                text = "Entrar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        // Divisor
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(1.dp)
                                    .background(Color.LightGray)
                            )
                            Text(
                                text = "ou",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(1.dp)
                                    .background(Color.LightGray)
                            )
                        }

                        // Botão de Cadastro
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            onClick = { onRegisterButtonPressed() }
                        ) {
                            Text(
                                text = "Criar uma conta",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = inParkGreen
                            )
                        }
                    }
                }

                // Mensagem de Erro
                if (state.error != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFEBEE)
                    ) {
                        Text(
                            text = state.error!!,
                            color = Color(0xFFC62828),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(12.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}