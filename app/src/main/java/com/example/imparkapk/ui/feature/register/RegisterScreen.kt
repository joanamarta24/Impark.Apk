package com.example.imparkapk.ui.feature.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

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
    val state by vm.state.collectAsState();

}