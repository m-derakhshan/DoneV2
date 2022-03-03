package m.derakhshan.done

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import m.derakhshan.done.core.data.data_source.Setting
import m.derakhshan.done.feature_authentication.presentation.AuthenticationNavGraph
import m.derakhshan.done.feature_authentication.presentation.authentication
import m.derakhshan.done.feature_home.presentation.HomeNavGraph
import m.derakhshan.done.feature_home.presentation.home
import m.derakhshan.done.ui.theme.DoneTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var setting: Setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDestination = if (setting.isUserLoggedIn)
            HomeNavGraph.HomeRoute.route
        else
            AuthenticationNavGraph.AuthenticationRoute.route

        setContent {
            val navController = rememberNavController()
            DoneTheme {
                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    authentication(navController = navController)
                    home()
                }
            }
        }
    }
}