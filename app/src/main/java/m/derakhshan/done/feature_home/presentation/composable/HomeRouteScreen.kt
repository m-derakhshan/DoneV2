package m.derakhshan.done.feature_home.presentation.composable


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import m.derakhshan.done.core.presentation.composable.ImageScreen
import m.derakhshan.done.feature_home.domain.use_case.HomeRouteUseCase
import m.derakhshan.done.feature_home.presentation.HomeNavGraph
import m.derakhshan.done.feature_note.presentation.add_edit_note_screen.composable.AddEditNoteScreen
import m.derakhshan.done.feature_note.presentation.note_screen.composable.NoteScreen
import m.derakhshan.done.feature_profile.presentation.composable.ProfileScreen
import m.derakhshan.done.feature_task.presentation.composable.TaskScreen
import m.derakhshan.done.ui.theme.DarkBlue
import javax.inject.Inject

@Composable
fun HomeRouteScreen(
    viewModel: HomeRouteViewModel = hiltViewModel(),
) {
    val userProfile = viewModel.state.value

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
                        icon = {
                            if (screen.route == HomeNavGraph.ProfileScreen.route)
                                Image(
                                    painter = rememberImagePainter(
                                        data = userProfile,
                                        builder = {
                                            transformations(CircleCropTransformation())
                                        }),
                                    contentDescription = "profile",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .border(
                                            2.dp,
                                            color = DarkBlue,
                                            shape = CircleShape
                                        )
                                        .padding(2.dp)
                                )
                            else
                                Icon(screen.icon!!, contentDescription = null)

                        },
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
                        }, alwaysShowLabel = false
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = HomeNavGraph.HomeScreen.route) {
            composable(HomeNavGraph.HomeScreen.route) {
                HomeScreen(paddingValues = padding)
            }
            composable(HomeNavGraph.ProfileScreen.route) {
                ProfileScreen(paddingValues = padding, navController = navController)
            }
            composable(HomeNavGraph.TasksScreen.route) {
                TaskScreen(navController = navController, paddingValues = padding)
            }
            composable(HomeNavGraph.NoteScreen.route) {
                NoteScreen(navController = navController, paddingValues = padding)
            }
            composable(
                HomeNavGraph.ImageScreen.route +
                        "?uri={uri}&title={title}",
                arguments = listOf(
                    navArgument(name = "uri") { this.type = NavType.StringType },
                    navArgument(name = "title") { this.type = NavType.StringType }
                )
            ) { backStack ->
                val uri = backStack.arguments?.getString("uri") ?: ""
                val title = backStack.arguments?.getString("title") ?: ""
                ImageScreen(
                    uri = uri,
                    paddingValues = padding,
                    navController = navController,
                    title = title
                )
            }

            composable(route = HomeNavGraph.AddEditNoteScreen.route +
                    "?noteId={noteId}&noteColor={noteColor}",
                arguments = listOf(
                    navArgument(name = "noteId") {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument(name = "noteColor") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )) { backStack ->
                val color = backStack.arguments?.getInt("noteColor")
                AddEditNoteScreen(
                    navController = navController,
                    noteColor = color,
                    paddingValues = padding
                )
            }
        }
    }
}


@HiltViewModel
class HomeRouteViewModel @Inject constructor(
    val useCase: HomeRouteUseCase
) : ViewModel() {
    private var job: Job? = null
    private val _state = mutableStateOf("")
    val state: State<String> = _state

    init {
        getUserImage()
    }

    private fun getUserImage() {
        job?.cancel()
        job = useCase().onEach {
            it?.let {
                _state.value = it.profileImage
            }
        }.launchIn(viewModelScope)
    }
}