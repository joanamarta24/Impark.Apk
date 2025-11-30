package com.example.imparkapk.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.imparkApp.CadastroUsuarioScreen
import com.example.imparkapk.data.dao.ui.Screen.avaliacao.AvaliacaoScreen
import com.example.imparkapk.data.dao.ui.Screen.avaliacao.ListaAvaliacoesScreen
import com.example.imparkapk.data.dao.ui.Screen.cadastro.CadastroEstacionamentoScreen
import com.example.imparkapk.data.dao.ui.Screen.cadastro.CadastroGerenteScreen
import com.example.imparkapk.data.dao.ui.Screen.estacionamento.ListaEstacionamentosScreen
import com.example.imparkapk.data.dao.ui.Screen.perfil.EditarPerfilScreen
import com.example.imparkapk.data.dao.ui.Screen.perfil.MeusCarrosScreen
import com.example.imparkapk.data.dao.ui.Screen.perfil.MinhasReservasScreen
import com.example.imparkapk.data.dao.ui.Screen.recuperacao.RecuperacaoScreen
import com.example.imparkapk.data.dao.ui.Screen.recuperacao.RedefinirSenhaScreen
import com.example.imparkapk.data.dao.ui.Screen.reserva.ListaReservasScreen
import com.example.imparkapk.data.dao.ui.Screen.reserva.ReservaScreen
import com.example.imparkapk.data.dao.ui.Screen.splash.SplashScreen
import com.example.imparkapk.data.dao.ui.feature.login.home.HomeScreen
import com.example.imparkapk.data.dao.ui.feature.login.login.LoginScreen
import com.example.imparkapk.ui.screen.splash.SplashScreen // Adicione esta importação
import com.example.imparktcc.ui.screen.estacionamento.DetalhesEstacionamentoScreen
import com.example.imparktcc.ui.screen.recuperacao.CodigoVerificacaoScreen

object AppRoutes {
    // Navegação principal
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"

    // Cadastros
    const val CADASTRO_USUARIO = "cadastro_usuario"
    const val CADASTRO_CARRO = "cadastro_carro"
    const val CADASTRO_ESTACIONAMENTO = "cadastro_estacionamento"
    const val CADASTRO_GERENTE = "cadastro_gerente"

    // Estacionamentos
    const val LISTA_ESTACIONAMENTOS = "lista_estacionamentos"

    const val DETALHES_ESTACIONAMENTO = "detalhes_estacionamento"

    // Reservas
    const val RESERVA = "reserva"
    const val LISTA_RESERVAS = "lista_reservas"

    // Avaliações
    const val AVALIACAO = "avaliacao"
    const val LISTA_AVALIACOES = "lista_avaliacoes"

    // Perfil
    const val EDITAR_PERFIL = "editar_perfil"
    const val MEUS_CARROS = "meus_carros"
    const val MINHAS_RESERVAS = "minhas_reservas"

    // Recuperação de Senha
    const val RECUPERACAO_SENHA = "recuperacao_senha"
    const val CODIGO_VERIFICACAO = "codigo_verificacao"
    const val REDEFINIR_SENHA = "redefinir_senha"
}

// Parâmetros das rotas - NOME CORRIGIDO
object RouteParams {
    const val ESTACIONAMENTO_ID = "estacionamentoId"
    const val RESERVA_ID = "reservaId"
    const val AVALIACAO_ID = "avaliacaoId"
    const val CLIENTE_ID = "clienteId"
}

// Rotas com parâmetros - NOME CORRIGIDO
object RoutePatterns {
    const val DETALHES_ESTACIONAMENTO = "detalhes_estacionamento/{${RouteParams.ESTACIONAMENTO_ID}}"
    const val AVALIACAO = "avaliacao/{${RouteParams.ESTACIONAMENTO_ID}}"
    const val RESERVA = "reserva/{${RouteParams.ESTACIONAMENTO_ID}}"
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        appNavigation(navController)
        recuperacaoGraph(navController)
    }
}

fun NavGraphBuilder.appNavigation(navController: NavController) {
    composable(route = AppRoutes.SPLASH) {
        SplashScreen(
            onNavigateToLogin = { navController.navigate(AppRoutes.LOGIN) },
            onNavigateToHome = { navController.navigate(AppRoutes.HOME) }
        )
    }

    // Tela de login
    composable(route = AppRoutes.LOGIN) {
        LoginScreen(
            onNavigateToHome = { navController.navigate(AppRoutes.HOME) },
            onNavigateToCadastro = { navController.navigate(AppRoutes.CADASTRO_USUARIO) },
            onNavigateToRecuperarSenha = { navController.navigate(AppRoutes.RECUPERACAO_SENHA) }
        )
    }

    // Tela principal
    composable(route = AppRoutes.HOME) {
        HomeScreen(
            onNavigateToEstacionamentos = { navController.navigate(AppRoutes.LISTA_ESTACIONAMENTOS) },
            onNavigateToMinhasReservas = { navController.navigate(AppRoutes.MINHAS_RESERVAS) },
            onNavigateToPerfil = { navController.navigate(AppRoutes.EDITAR_PERFIL) },
            onNavigateToLogin = {
                navController.navigate(AppRoutes.LOGIN) {
                    popUpTo(AppRoutes.HOME) { inclusive = true }
                }
            }
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
fun NavGraphBuilder.cadastroGraph(navController: NavController) {
    navigation(
        startDestination = AppRoutes.CADASTRO_USUARIO,
        route = "cadastro_graph"
    ) {
        composable(route = AppRoutes.CADASTRO_USUARIO) {
            CadastroUsuarioScreen(
                navControllerBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.CADASTRO_USUARIO) { inclusive = true }
                    }
                },
                onNavigateToMeusCarros = { navController.navigate(AppRoutes.MEUS_CARROS) }
            )
        }
        composable(route = AppRoutes.CADASTRO_ESTACIONAMENTO) {
            CadastroEstacionamentoScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                navControllerToCadastroGerente = { navController.navigate(AppRoutes.CADASTRO_GERENTE) }
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
        composable(route = AppRoutes.LISTA_ESTACIONAMENTOS) { // NOME CORRIGIDO
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

        composable(
            route = RoutePatterns.DETALHES_ESTACIONAMENTO,
            arguments = listOf(
                navArgument(RouteParams.ESTACIONAMENTO_ID) {
                    type = NavType.StringType
                }
            )
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
}

// Graph de reservas
fun NavGraphBuilder.reservaGraph(navController: NavController) {
    navigation(
        startDestination = AppRoutes.LISTA_RESERVAS,
        route = "reserva_graph"
    ) {
        composable(route = AppRoutes.LISTA_RESERVAS) {
            ListaReservasScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetalhesEstacionamento = { estacionamentoId ->
                    navController.navigate("detalhes_estacionamento/$estacionamentoId")
                },
                onNavigateToNovaReserva = {
                    navController.navigate(AppRoutes.LISTA_ESTACIONAMENTOS)
                }
            )
        }

        composable(
            route = RoutePatterns.RESERVA,
            arguments = listOf(
                navArgument(RouteParams.ESTACIONAMENTO_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val estacionamentoId = backStackEntry.arguments?.getString(RouteParams.ESTACIONAMENTO_ID) ?: ""
            ReservaScreen(
                estacionamentoId = estacionamentoId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToConfirmacao = { reservaId ->
                    // Navegar para tela de confirmação
                    navController.navigate(AppRoutes.LISTA_RESERVAS) {
                        popUpTo(AppRoutes.LISTA_ESTACIONAMENTOS) { inclusive = false }
                    }
                },
                onNavigateToAdicionarCarro = {
                    navController.navigate(AppRoutes.CADASTRO_CARRO)
                }
            )
        }
    }
}

// Graph de avaliações
fun NavGraphBuilder.avaliacaoGraph(navController: NavController) {
    navigation(
        startDestination = AppRoutes.LISTA_AVALIACOES,
        route = "avaliacao_graph"
    ) {
        composable(route = AppRoutes.LISTA_AVALIACOES) {
            ListaAvaliacoesScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAvaliar = { estacionamentoId ->
                    navController.navigate("avaliacao/$estacionamentoId")
                },
                onNavigateToDetalhesEstacionamento = { estacionamentoId ->
                    navController.navigate("detalhes_estacionamento/$estacionamentoId")
                }
            )
        }

        composable(
            route = RoutePatterns.AVALIACAO,
            arguments = listOf(
                navArgument(RouteParams.ESTACIONAMENTO_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val estacionamentoId = backStackEntry.arguments?.getString(RouteParams.ESTACIONAMENTO_ID) ?: ""
            AvaliacaoScreen(
                estacionamentoId = estacionamentoId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToConfirmacao = {
                    navController.navigate(AppRoutes.LISTA_AVALIACOES) {
                        popUpTo(AppRoutes.LISTA_AVALIACOES) { inclusive = false }
                    }
                }
            )
        }
    }
}

// Graph de perfil
fun NavGraphBuilder.perfilGraph(navController: NavController) {
    navigation(
        startDestination = AppRoutes.EDITAR_PERFIL,
        route = "perfil_graph"
    ) {
        composable(route = AppRoutes.EDITAR_PERFIL) {
            EditarPerfilScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToMeusCarros = { navController.navigate(AppRoutes.MEUS_CARROS) },
                onNavigateToMinhasReservas = { navController.navigate(AppRoutes.MINHAS_RESERVAS) },
                onNavigateToLogin = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(route = AppRoutes.MEUS_CARROS) {
            MeusCarrosScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAdicionarCarro = { navController.navigate(AppRoutes.CADASTRO_CARRO) },
                onNavigateToEditarCarro = { carroId ->
                    navController.navigate("${AppRoutes.CADASTRO_CARRO}?carroId=$carroId")
                }
            )
        }

        composable(route = AppRoutes.MINHAS_RESERVAS) {
            MinhasReservasScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetalhesEstacionamento = { estacionamentoId ->
                    navController.navigate("detalhes_estacionamento/$estacionamentoId")
                },
                onNavigateToAvaliar = { estacionamentoId ->
                    navController.navigate("avaliacao/$estacionamentoId")
                },
                onNavigateToNovaReserva = {
                    navController.navigate(AppRoutes.LISTA_ESTACIONAMENTOS)
                }
            )
        }
    }
}

// Graph de recuperação de senha
fun NavGraphBuilder.recuperacaoGraph(navController: NavController) {
    navigation(
        startDestination = AppRoutes.RECUPERACAO_SENHA,
        route = "recuperacao_graph"
    ) {
        composable(route = AppRoutes.RECUPERACAO_SENHA) {
            RecuperacaoScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCodigo = { email ->
                    navController.navigate("${AppRoutes.CODIGO_VERIFICACAO}?email=$email")
                }
            )
        }

        composable(
            route = "${AppRoutes.CODIGO_VERIFICACAO}?email={email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            CodigoVerificacaoScreen(
                email = email,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRedefinirSenha = { email, codigo ->
                    navController.navigate("${AppRoutes.REDEFINIR_SENHA}?email=$email&codigo=$codigo")
                }
            )
        }

        composable(
            route = "${AppRoutes.REDEFINIR_SENHA}?email={email}&codigo={codigo}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("codigo") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
            RedefinirSenhaScreen(
                email = email,
                codigo = codigo,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.RECUPERACAO_SENHA) { inclusive = true }
                    }
                }
            )
        }
    }
}