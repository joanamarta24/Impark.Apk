package com.example.imparktcc.ui.navigation

import android.window.SplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.imparkApp.CadastroUsuarioScreen

object AppRoutes {
    //Navegação principal
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"

    // Cadastros
    const val CADASTRO_USUARIO = "cadastro_usuario"
    const val CADASTRO_CARRO = "cadastro_carro"
    const val CADASTRO_ESTACIONAMENTO = "cadastro_estacionamento"
    const val CADASTRO_GERENTE = "cadastro_gerente"

    //Estacionamento
    const val LISTA_ESTACIONAMENTO = "lista_estacionamento"
    const val DETALHES_ESTACIONAMENTO = "detalhes_estacionamento"

    //Reservas
    const val RESERVAS = "reservas"
    const val DETALHES_RESERVA = "detalhes_reserva"

    //Avaliações
    const val AVALIACAO = "avaliacao"
    const val LISTA_AVALIACOES = "lista_avaliacoes"

    //Perfil
    const val EDITAR_PERFIL = "editar_perfil"
    const val MEUS_CARROS = "meus_carros"
    const val MINHAS_RESERVAS = "minhas_reservas"

}

// Parâmetros das rotas
object RoutrParams {
    const val ESTACIONAMENTO_ID = "estacionamentoId"
    const val RESERVA_ID = "reservaId"
    const val AVALIACAO_ID = "avaliacaoId"
    const val USUARIO_ID = "usuarioId"
}

//Rotas com parãmentos
object RoutePatterns {
    const val DETALHES_ESTACIONAMENTO =
        "detatalhes_estacionamento/{${RoutrParams.ESTACIONAMENTO_ID}}"
    const val AVALIACAO = "avaliacao/{${RoutrParams.ESTACIONAMENTO_ID}}"
    const val RESERVA = "reservas/{${RoutrParams.RESERVA_ID}"
}

fun NavGraphBuilder.appNavigation(navController: NavController) {
    composable(route = AppRoutes.SPLASH) {
        SplashScreen(
            onNavigateToLogin = { navController.navigate.(AppRoutes.LOGIN) },
            onNavigateToHome = { navController.navigate(AppRoutes.HOME) }

        )
    }
    //Tela de login
    composable(route = AppRoutes.LOGIN) {
        LoginScreen(
            onNavigateToHome = { navController.navigate(AppRoutes.HOME) },
            onNavigateToCadastro = { navController.navigate(AppRoutes.CADASTRO_USUARIO) },
            onNavigateToRecuperarSenha = {}


        )
    }
    //Tela principal
    composable(route = AppRoutes.HOME) {
        HomeScreen(
            onNavigateToEstacionamentos = { navController.navigate(AppRoutes.LISTA_ESTACIONAMENTO) },
            onNavigateToMinhasReservas = { navController.navigate(AppRoutes.MINHAS_RESERVAS) },
            onNavigateToPerfil = { navController.navigate(AppRoutes.EDITAR_PERFIL) },
            onNavigateToLogin = { navController.navigate(AppRoutes.LOGIN) }
        )
    }
    // Graph de cadastros
    cadastroGraph(navController)

    // Graph de estacionamentos
    estacionamentoGraph(navController)

    // Graph de reservas
    reservaGraph(navController)

    // Graph de avaliações
    avaliacaoGraph(navController)

    // Graph de perfil
    perfilGraph(navController)

}
// Graph de cadastros
fun NavGraphBuilder.cadastroGraph(navController: NavController){
    navigation(
        startDestination = AppRoutes.CADASTRO_USUARIO,
        route ="cadastro_graph"
    ){
        composable(route = AppRoutes.CADASTRO_USUARIO) {
            CadastroUsuarioScreen(
                navControllerBack = { navController.popBackStack() },
                onNavigateToHome ={
                    navController.navigate(AppRoutes.HOME){
                        popUpTo(AppRoutes.CADASTRO_USUARIO){inclusive = true}
                    }
                },
                onNavigateToMeusCarros ={navController.navigate(AppRoutes.MEUS_CARROS)}
            )
        }
        composable(route = AppRoutes.CADASTRO_ESTACIONAMENTO) {
            CadastroEstacionamentoScreen(
                onNavigateBack{navController.popBackStack() },
                onNavigateToHome ={
                    navController.navigate(AppRoutes.HOME){
                        popUpTo(0){inclusive = true}
                    }
                },
                navControllerToCadastroGrante ={navController.navigate(AppRoutes.CADASTRO_GERENTE)}
            )
        }
        composable(route = AppRoutes.CADASTRO_GERENTE) {
            CadastroGerenteScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

// Graph de estacionamentos
fun NavGraphBuilder.estacionamentoGraph(navController: NavController) {
    navigation(
        startDestination = AppRoutes.LISTA_ESTACIONAMENTOS,
        route = "estacionamento_graph"
    ) {
        composable(route = AppRoutes.LISTA_ESTACIONAMENTO) {
            ListaEstacionamentosScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetalhesEstacionamento = { estacionamentoId ->
                    navController.navigate("detalhes_estacionamento/$estacionamentoId")
                },
                onNavigateToReserva = { estacionamentoId ->
                    navController.navigate("reserva/$estacionamentoId")
                },
                onNavigateToAvaliacao = { estacionamentoId ->
                    navController.navigate("avaliacao/$estacionamentoId")
                }
            )
        }
    }
    composable(
        route = RoutePatterns.DETALHES_ESTACIONAMENTO
    ) { backStackEntry ->
        val estacionamentoId = backStackEntry.arguments?.getString(RouteParams.ESTACIONAMENTO_ID) ?: ""
        DetalhesEstacionamentoScreen(
            estacionamentoId = estacionamentoId,
            onNavigateBack = { navController.popBackStack() },
            onNavigateToReserva = {
                navController.navigate("reserva/$estacionamentoId")
            },
            onNavigateToAvaliacoes = {
                navController.navigate(AppRoutes.LISTA_AVALIACOES)
            },
            onNavigateToAvaliar = {
                navController.navigate("avaliacao/$estacionamentoId")
            }
        )
    }
}
// Graph de reservas
fun NavGraphBuilder.reservaGraph(navController: NavController) {
    navigation(
        startDestination = AppRoutes.RESERVAS,
        route = "reserva_graph"
    ) {
   composable(route = AppRoutes.LISTA_RESERVAS) {)
}
