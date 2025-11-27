package com.example.imparkapk.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.imparkapk.ui.feature.dashboard.DashboardScreen
import com.example.imparkapk.ui.feature.login.LoginScreen
import com.example.imparkapk.ui.feature.register.RegisterScreen
import com.example.imparkapk.ui.feature.splash.SplashScreen

@Composable
fun AppNavGraph(navGraph: NavHostController, modifier: Modifier) {
    val vm: AuthStateViewModel = hiltViewModel()
    val authState = vm.state.collectAsState().value

    val startDestination = when (authState) {
        is AuthState.Authenticated -> Routes.Register
        AuthState.Unauthenticated -> Routes.Login
        AuthState.Loading -> "splash"
    }

    NavHost(
        navController = navGraph,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("splash") {
            SplashScreen()
        }

        composable(Routes.Login) {
            LoginScreen(
                onLoginSuccess = {
                    navGraph.navigate(Routes.Dashboard) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                },
                onRegisterButtonPressed = {
                    navGraph.navigate(Routes.Register) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Dashboard) {
            DashboardScreen(
                onLogout = {
                    navGraph.navigate(Routes.Login) {
                        vm.logout()
                        popUpTo(Routes.Dashboard) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Register) {
            RegisterScreen(
                onRegisterSuccess = {
                    navGraph.navigate(Routes.Login) {
                        popUpTo(Routes.Register) { inclusive = true }
                    }
                },
                onLoginButtonPressed = {
                    navGraph.navigate(Routes.Login) {
                        popUpTo(Routes.Register) { inclusive = true }
                    }
                }
            )
        }
    }
}