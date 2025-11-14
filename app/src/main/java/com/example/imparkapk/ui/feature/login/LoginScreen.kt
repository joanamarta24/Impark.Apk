package com.example.imparkapk.ui.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    vm: LoginViewModel = hiltViewModel()
) {

}