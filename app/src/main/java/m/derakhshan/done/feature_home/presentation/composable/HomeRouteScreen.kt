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
    ) {
        NavHost(navController = navController, startDestination = HomeNavGraph.HomeScreen.route) {
            composable(HomeNavGraph.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
            composable(HomeNavGraph.ProfileScreen.route) {
                // TODO: replace with profile screen
                HomeScreen(navController = navController)
            }
            composable(HomeNavGraph.TasksScreen.route) {
                // TODO: replace with tasks screen
                HomeScreen(navController = navController)
            }
            composable(HomeNavGraph.NoteScreen.route) {
                // TODO: replace with notes screen 
                HomeScreen(navController = navController)
            }
        }
    }
}