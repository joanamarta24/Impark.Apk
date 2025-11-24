package com.example.imparkapk.ui.feature.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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

    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                ValidatedTextField(
                    label = "Nome",
                    onValueChange = { vm.onNomeChange(it) },
                    value = state.nome,
                    modifier = Modifier,
                    isError = false
                )
                ValidatedTextField(
                    label = "E-mail",
                    onValueChange = { vm.onEmailChange(it) },
                    value = state.email,
                    modifier = Modifier,
                    isError = false
                )
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ValidatedTextField(
                        label = "Senha",
                        onValueChange = { vm.onSenhaChange(it) },
                        value = state.senha,
                        modifier = Modifier,
                        isError = false
                    )
                    ValidatedTextField(
                        label = "Confirme a senha",
                        onValueChange = { vm.onConfirmarSenhaChange(it) },
                        value = state.confirmarSenha,
                        modifier = Modifier,
                        isError = false
                    )
                }

                ValidatedTextField(
                    label = "Telefone",
                    onValueChange = { vm.onTelefoneChange(it) },
                    value = state.telefone,
                    modifier = Modifier,
                    isError = false
                )
                OutlinedTextField(
                    value = state.nascimento.toString(),
                    onValueChange = {  },
                    label = { Text("Data de Nascimento") },
                    readOnly = true,
                    modifier = Modifier,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Selecione a data")
                        }
                    }
                )

                Button (
                    onClick = {
                        vm.registerUser(onRegisterSuccess)
                    },
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading)
                        Text("Carregando...")
                    else
                        Text("Registrar")
                }
                Button(
                    onClick = {
                        onLoginButtonPressed()
                    }
                ) {
                    Text("Já possui uma conta? Login")
                }

                if (showDatePicker) {
                    val datePickerState = rememberDatePickerState()

                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            IconButton(
                                onClick = {
                                    val selectedDateMillis = datePickerState.selectedDateMillis?.let {
                                        selectedDate = it
                                        vm.onNascimentoChange(Date(it))
                                    }
                                    showDatePicker = false
                                }
                            ) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            IconButton(
                                onClick = {
                                    showDatePicker = false
                                }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    ) {
                        DatePicker( state = datePickerState )
                    }
                }
                if (state.errorMessage != null) {
                    Text(
                        text = state.errorMessage!!,
                    )
                }
            }
        }
    }
}