package com.moviles.paninisupport.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moviles.paninisupport.data.AppContainer
import com.moviles.paninisupport.ui.screens.create.CreateTicketScreen
import com.moviles.paninisupport.ui.screens.detail.TicketDetailScreen
import com.moviles.paninisupport.ui.screens.login.LoginScreen
import com.moviles.paninisupport.ui.screens.tickets.TicketListScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.LOGIN,
        modifier = modifier
    ) {
        composable(route = AppDestinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppDestinations.TICKET_LIST) {
                        popUpTo(AppDestinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(route = AppDestinations.TICKET_LIST) {
            TicketListScreen(
                onTicketClick = { ticketId ->
                    navController.navigate(AppDestinations.ticketDetailRoute(ticketId))
                },
                onCreateTicketClick = {
                    navController.navigate(AppDestinations.CREATE_TICKET)
                },
                onLogout = {
                    AppContainer.authRepository.clearSession()
                    navController.navigate(AppDestinations.LOGIN) {
                        popUpTo(AppDestinations.TICKET_LIST) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "${AppDestinations.TICKET_DETAIL}/{ticketId}",
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId").orEmpty()
            TicketDetailScreen(
                ticketId = ticketId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = AppDestinations.CREATE_TICKET) {
            CreateTicketScreen(
                onBack = { navController.popBackStack() },
                onTicketCreated = { navController.popBackStack() }
            )
        }
    }
}
