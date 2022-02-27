package m.derakhshan.done.feature_home.presentation.composable


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import m.derakhshan.done.feature_home.presentation.HomeNavGraph
import m.derakhshan.done.feature_note.presentation.composable.NoteScreen
import m.derakhshan.done.feature_profile.presentation.composable.ProfileScreen
import m.derakhshan.done.feature_task.presentation.composable.TaskScreen

@Composable
fun HomeRouteScreen() {
    val screens = listOf(
        HomeNavGraph.HomeScreen,
        HomeNavGraph.TasksScreen,
        HomeNavGraph.NoteScreen,
        HomeNavGraph.ProfileScreen,
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screens.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon!!, contentDescription = null) },
                        label = { Text(stringResource(screen.label!!)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = HomeNavGraph.HomeScreen.route) {
            composable(HomeNavGraph.HomeScreen.route) {
                HomeScreen(navController = navController, paddingValues = padding)
            }
            composable(HomeNavGraph.ProfileScreen.route) {
                ProfileScreen(navController = navController, paddingValues = padding)
            }
            composable(HomeNavGraph.TasksScreen.route) {
                TaskScreen(navController = navController, paddingValues = padding)
            }
            composable(HomeNavGraph.NoteScreen.route) {
                NoteScreen(navController = navController, paddingValues = padding)
            }
        }
    }
}