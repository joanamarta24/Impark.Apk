package com.example.imparktcc.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.imparktcc.CadastroUsuarioScreen
import com.example.imparktcc.cadastro.CadastroUsuarioScreen
import com.example.imparktcc.ui.CadastroCarroScreen

fun NavGraphBuilder.cadastroGraph(navController: NavController) {
    composable(route = "usuario") {
        CadastroUsuarioScreen(navController = navController)
    }

    composable(route = "carro") {
        CadastroCarroScreen()
    }
}
