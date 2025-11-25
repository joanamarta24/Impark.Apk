package com.example.imparkapk.ui.feature.dashboard

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
fun DashboardScreen(
    onLogout: () -> Unit = {},
    vm: DashboardViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()
}