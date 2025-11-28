package com.example.imparkapk.data.dao.ui.Screen.cadastro

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import com.example.imparkapk.data.dao.ui.viewmodel.CadastroGerenteViewlModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroGerenteScreen(
    navController: NavController,
    estacionamentoId: String? = null,
    viewModel: CadastroGerenteViewlModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    //Efeito para carregar dados

    LargeTopAppBar(estacionamentoId){
        estacionamentoId?.let { viewModel.setEstacionamentoId(it) }
    }
    //Navegação após sucesso
    LaunchedEffect(uiState.cadastroSucesso) {
        if (uiState.castroSucesso){
            navController.popBackStack()
        }
    }


}