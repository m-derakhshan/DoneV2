package m.derakhshan.done.feature_authentication.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import m.derakhshan.done.feature_authentication.presentation.composable.AuthenticationScreen


fun NavGraphBuilder.authentication(
    navController: NavController
) {
    navigation(
        startDestination = AuthenticationNavGraph.AuthenticationScreen.route,
        route = AuthenticationNavGraph.AuthenticationRoute.route
    ) {
        composable(route = AuthenticationNavGraph.AuthenticationScreen.route)
        {
            AuthenticationScreen(navController = navController)
        }
    }
}


sealed class AuthenticationNavGraph(val route: String) {

    object AuthenticationRoute : AuthenticationNavGraph("AuthenticationRoute")
    object AuthenticationScreen : AuthenticationNavGraph("AuthenticationScreen")
}