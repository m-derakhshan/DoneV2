package m.derakhshan.done.feature_home.presentation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Task
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import m.derakhshan.done.R
import m.derakhshan.done.feature_home.presentation.composable.HomeRouteScreen


fun NavGraphBuilder.home() {
    navigation(
        startDestination = HomeNavGraph.HomeScreen.route,
        route = HomeNavGraph.HomeRoute.route
    ) {
        composable(route = HomeNavGraph.HomeScreen.route)
        {
            HomeRouteScreen()
        }
    }
}


sealed class HomeNavGraph(
    val route: String,
    val icon: ImageVector? = null,
    val label: Int? = null,
) {

    object HomeRoute : HomeNavGraph("HomeRoute")
    object HomeScreen :
        HomeNavGraph(route = "HomeScreen", icon = Icons.Default.Home, label = R.string.home)

    object TasksScreen :
        HomeNavGraph(route = "TasksScreen", icon = Icons.Default.Task, label = R.string.tasks)

    object ProfileScreen :
        HomeNavGraph(route = "ProfileScreen", icon = Icons.Default.Person, label = R.string.profile)

    object NoteScreen :
        HomeNavGraph(route = "NoteScreen", icon = Icons.Default.Notes, label = R.string.notes)

    object ImageScreen : HomeNavGraph(route = "ImageScreen")
    object AddEditNoteScreen : HomeNavGraph(route = "AddEditNoteScreen")

}