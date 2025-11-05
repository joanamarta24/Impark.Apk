package com.example.imparktcc.ui.navigation

import android.window.SplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.imparkApp.CadastroUsuarioScreen

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
                onNavigateBack { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                navControllerToCadastroGrante = { navController.navigate(AppRoutes.CADASTRO_GERENTE) }
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
        val estacionamentoId =
            backStackEntry.arguments?.getString(RouteParams.ESTACIONAMENTO_ID) ?: ""
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
    }
    composable(
        route = RoutePatterns.RESERVA
    ) { backStackEntry ->
        val estacionamentoId =
            backStackEntry.arguments?.getString(RouteParams.ESTACIONAMENTO_ID) ?: ""
        ReservaScreen(
            estacionamentoId = estacionamentoId,
            onNavigateBack = { navController.popBackStack() },
            onNavigateToConfirmacao = { reservaId ->
                // Navegar para tela de confirmação (pode ser implementada depois)
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

    }
    composable(
        route = RoutePatterns.AVALIACAO
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
    }
    composable(route = AppRoutes.MEUS_CARROS) {
        MeusCarrosScreen(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToAdicionarCarro = { navController.navigate(AppRoutes.CADASTRO_CARRO) },
            onNavigateToEditarCarro = { carroId ->
                // Navegar para edição de carro (pode ser implementada depois)
                navController.navigate(AppRoutes.CADASTRO_CARRO)
            }
        )
    }
    composable(route = AppRoutes.MINHAS_RESERVAS) {
        MinhasReservasScreen(
            onNavigateBack ={navController.popBackStack()},
            onNavigateToDetalhesEstacionamento = {estacionamentoId ->
                navController.navigate(AppRoutes.LISTA_RESERVAS)
            },
            onNavigateToAvaliar = {
                navController.navigate("avaliacao/$estacionamentoId")
            },
            onNavigateToNovaReserva = {
                navController.navigate(AppRoutes.LISTA_ESTACIONAMENTOS)
            }
        )
    }
}
// Navegação para desenvolvedores (telas de debug/administração)
fun NavGraphBuilder.devNavigation(navController: NavController) {
    navigation(
        startDestination = "dev_dashboard",
        route = "dev_graph"
    ) {
        composable(route = "dev_dashboard") {
            // Tela de dashboard para desenvolvedores
            // Pode conter links para todas as telas do app
        }

        composable(route = "dev_estacionamentos") {
            // Tela de administração de estacionamentos
        }

        composable(route = "dev_usuarios") {
            // Tela de administração de usuários
        }

        composable(route = "dev_relatorios") {
            // Tela de relatórios e métricas
        }
    }
}
// Adicionar no NavGraphBuilder
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