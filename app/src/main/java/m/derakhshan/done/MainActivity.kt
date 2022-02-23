package m.derakhshan.done

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import m.derakhshan.done.feature_authentication.presentation.AuthenticationNavGraph
import m.derakhshan.done.feature_authentication.presentation.authentication
import m.derakhshan.done.ui.theme.DoneTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        // TODO: fix the start destination based on that user has logged in or not.
        //      if so, navigate to home screen.
        val startDestination = if (true)
            AuthenticationNavGraph.AuthenticationRoute.route
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
                }
            }
        }

    }
}