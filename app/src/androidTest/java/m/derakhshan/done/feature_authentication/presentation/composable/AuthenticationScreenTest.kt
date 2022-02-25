package m.derakhshan.done.feature_authentication.presentation.composable


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import m.derakhshan.done.MainActivity
import m.derakhshan.done.core.di.AppModule
import m.derakhshan.done.feature_authentication.data.repository.AuthenticationRepositoryImplTest.Companion.TestSituationOfSignUp
import m.derakhshan.done.feature_authentication.di.AuthenticationModule
import m.derakhshan.done.feature_authentication.presentation.AuthenticationNavGraph
import m.derakhshan.done.feature_authentication.utils.AuthenticationTestingConstants
import m.derakhshan.done.feature_home.presentation.HomeNavGraph
import m.derakhshan.done.feature_home.presentation.composable.HomeScreen
import m.derakhshan.done.ui.theme.DoneTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AuthenticationModule::class, AppModule::class)
class AuthenticationScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            DoneTheme {
                NavHost(
                    navController = navController,
                    startDestination = AuthenticationNavGraph.AuthenticationScreen.route
                ) {
                    composable(route = AuthenticationNavGraph.AuthenticationScreen.route) {
                        AuthenticationScreen(navController = navController)
                    }
                    composable(route = HomeNavGraph.HomeRoute.route) {
                        HomeScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun togglePasswordVisibility_changeAuthenticationStateIsPasswordVisible() {
        val toggle =
            composeRule.onNodeWithTag(AuthenticationTestingConstants.PASSWORD_VISIBILITY_ICON_BUTTON)
        val password = composeRule.onNodeWithTag(AuthenticationTestingConstants.PASSWORD_TEXT_FIELD)
        password.performTextInput("Mohammad")
        toggle.performClick()
        for ((key, value) in password.fetchSemanticsNode().config) {
            if (key.name == "EditableText")
                assertThat(value.toString()).isEqualTo("Mohammad")
        }
        toggle.performClick()
        for ((key, value) in password.fetchSemanticsNode().config) {
            if (key.name == "EditableText")
                assertThat(value.toString()).isEqualTo("••••••••")
        }
    }


    @Test
    fun newUser_showsNameAndFamilyInputAndNavigateToHomeScreen() {
        TestSituationOfSignUp = true
        composeRule.onNodeWithTag(AuthenticationTestingConstants.EMAIL_TEXT_FIELD)
            .performTextInput("newTestingUser@mail.com")
        composeRule.onNodeWithTag(AuthenticationTestingConstants.PASSWORD_TEXT_FIELD)
            .performTextInput("newTestingUserPassword")
        val singUp = composeRule.onNodeWithTag(AuthenticationTestingConstants.LOGIN_BUTTON)
        singUp.performClick()
        val name = composeRule.onNodeWithTag(AuthenticationTestingConstants.NAME_FAMILY_TEXT_FIELD)
        name.assertIsDisplayed()
        name.performTextInput("new Testing User")
        singUp.performClick()
        name.assertDoesNotExist()
    }


    @Test
    fun oldUser_navigateToHomeScreen() {
        TestSituationOfSignUp = false
        val email = composeRule.onNodeWithTag(AuthenticationTestingConstants.EMAIL_TEXT_FIELD)
        email.performTextInput("mohammad@derakhshan.com")
        composeRule.onNodeWithTag(AuthenticationTestingConstants.PASSWORD_TEXT_FIELD)
            .performTextInput("mohammad")
        composeRule.onNodeWithTag(AuthenticationTestingConstants.LOGIN_BUTTON)
            .performClick()
        email.assertDoesNotExist()
    }


}