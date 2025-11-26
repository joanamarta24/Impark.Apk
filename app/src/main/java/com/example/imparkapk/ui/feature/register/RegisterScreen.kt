package com.example.imparkapk.ui.feature.register

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onLoginButtonPressed: () -> Unit = {},
    vm: RegisterViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    val imParkGreen = Color(0xFF1DB954)
    val inParkWhite = Color.White
    val inParkDarkGray = Color(0xFF2A2A2A)
    val inParkLightGray = Color(0xFFF5F5F5)
    val inParkBorder = Color(0xFFE0E0E0)

    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(inParkLightGray)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(inParkLightGray)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    top = 24.dp,
                    bottom = 32.dp
                )
            ) {
                // Logo e Título
                item {
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
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        text = "Criar Conta",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = inParkDarkGray
                    )
                }

                item {
                    Text(
                        text = "Junte-se ao Inpark",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }

                // Card de Cadastro
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = inParkWhite,
                        shadowElevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Campo Nome
                            ValidatedTextField(
                                label = "Nome Completo",
                                onValueChange = { vm.onNomeChange(it) },
                                value = state.nome,
                                modifier = Modifier.fillMaxWidth(),
                                isError = false
                            )

                            // Campo E-mail
                            ValidatedTextField(
                                label = "E-mail",
                                onValueChange = { vm.onEmailChange(it) },
                                value = state.email,
                                modifier = Modifier.fillMaxWidth(),
                                isError = false
                            )

                            // Campo Senha
                            ValidatedTextField(
                                label = "Senha",
                                onValueChange = { vm.onSenhaChange(it) },
                                value = state.senha,
                                modifier = Modifier.fillMaxWidth(),
                                isError = false
                            )

                            // Campo Confirmar Senha
                            ValidatedTextField(
                                label = "Confirmar Senha",
                                onValueChange = { vm.onConfirmarSenhaChange(it) },
                                value = state.confirmarSenha,
                                modifier = Modifier.fillMaxWidth(),
                                isError = false
                            )

                            // Campo Data de Nascimento
                            OutlinedTextField(
                                value = state.nascimento.toString(),
                                onValueChange = {},
                                label = { Text("Data de Nascimento") },
                                readOnly = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = imParkGreen,
                                    unfocusedBorderColor = inParkBorder,
                                    focusedLabelColor = imParkGreen
                                ),
                                trailingIcon = {
                                    IconButton(onClick = { showDatePicker = true }) {
                                        Icon(
                                            Icons.Default.DateRange,
                                            contentDescription = "Selecione a data",
                                            tint = imParkGreen
                                        )
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Botão de Registrar
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonColors(
                                    containerColor = imParkGreen,
                                    contentColor = inParkWhite,
                                    disabledContentColor = Color.Gray,
                                    disabledContainerColor = Color.LightGray
                                ),
                                onClick = {
                                    vm.registerUser(onRegisterSuccess)
                                },
                                enabled = !state.isLoading
                            ) {
                                if (state.isLoading) {
                                    Text(
                                        text = "Carregando...",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                } else {
                                    Text(
                                        text = "Criar Conta",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
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

                            // Botão de Login
                            TextButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                onClick = {
                                    onLoginButtonPressed()
                                }
                            ) {
                                Text(
                                    text = "Já possui uma conta? Fazer Login",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = imParkGreen
                                )
                            }
                        }
                    }
                }

                // Mensagem de Erro
                if (state.errorMessage != null) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFFFEBEE)
                        ) {
                            Text(
                                text = state.errorMessage!!,
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

    // Date Picker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDateMillis = datePickerState.selectedDateMillis?.let {
                            selectedDate = it
                            vm.onNascimentoChange(Date(it))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK", color = imParkGreen)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Cancelar", color = imParkGreen)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}