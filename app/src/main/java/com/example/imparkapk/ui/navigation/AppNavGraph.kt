package com.example.imparkapk.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.imparkapk.ui.feature.login.LoginScreen
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
            LoginScreen()
        }

        composable(Routes.Register) {
            // Register Screen Content
        }
    }
}