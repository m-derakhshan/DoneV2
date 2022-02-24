package m.derakhshan.done.feature_home.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import m.derakhshan.done.feature_home.presentation.composable.HomeScreen

fun NavGraphBuilder.home(
    navController: NavController
) {
    navigation(
        startDestination = HomeNavGraph.HomeScreen.route,
        route = HomeNavGraph.HomeRoute.route
    ) {
        composable(route = HomeNavGraph.HomeScreen.route)
        {
            HomeScreen(navController = navController)
        }
    }
}


sealed class HomeNavGraph(val route: String) {

    object HomeRoute : HomeNavGraph("HomeRoute")
    object HomeScreen : HomeNavGraph("HomeScreen")
    object TasksScreen : HomeNavGraph("TasksScreen")
    object ProfileScreen : HomeNavGraph("ProfileScreen")
    object NoteScreen : HomeNavGraph("NoteScreen")

}