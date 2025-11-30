package com.example.imparkapk.data.dao.ui.Screen.perfil

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.imparktcc.ui.viewmodel.ReservaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MinhasReservasScreen(
   navController: NavController,
    viewModel: ReservaViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    var tabSelecionada by remember { mutableSetOf(0) }

    LaunchedEffect(Unit) {
        viewModel.carregarReservas
    }
    LaunchedEffect(tabSelecionada) {
        viewModel
    }

}