package com.example.imparkapk.ui.navigation

import androidx.navigation.NavController
import com.example.imparktcc.ui.navigation.AppRoutes

fun NavController.navigateToEstacionamentoDetalhes(estacionamentoId: String) {
  this.navigate("detalhes_estacionamento/$estacionamentoId")
}
fun NavController.navigateToReserva(estacionamentoId: String){
    this.navigate("reservas/$estacionamentoId")
}
fun NavController.navigateToAvaliacao(estacionamentoId: String){
    this.navigate("avaliacao/$estacionamentoId")
}
fun NavController.navigateToHomeAndClearStack(){
    this.navigate(AppRoutes.HOME){
        popUpTo(0) {inclusive = true}
    }
}
fun NavController.navigateToLoginAndClearStack() {
    this.navigate(AppRoutes.LOGIN) {
        popUpTo(0) { inclusive = true }
    }
}
// Verificações de rota
fun NavController.isUserOnHomeScreen(): Boolean {
    return currentDestination?.route == AppRoutes.HOME
}

fun NavController.isUserOnLoginScreen(): Boolean {
    return currentDestination?.route == AppRoutes.LOGIN
}
