package com.example.imparkapk.ui.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Scaffold { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
        ) {

        }
    }
}