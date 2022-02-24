package m.derakhshan.done.feature_authentication.presentation.composable


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.fail
import m.derakhshan.done.MainActivity
import m.derakhshan.done.feature_authentication.presentation.AuthenticationNavGraph
import m.derakhshan.done.feature_authentication.utils.AuthenticationTestingConstants
import m.derakhshan.done.ui.theme.DoneTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
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
                }
            }
        }
    }


    @Test
    fun emptyEmail_raiseException() {
        composeRule.onNodeWithTag(AuthenticationTestingConstants.EMAIL_TEXT_FIELD)
        composeRule.onNodeWithTag(AuthenticationTestingConstants.LOGIN_BUTTON).performClick()
        composeRule.onNodeWithContentDescription("Email address can't left blank.").assertIsDisplayed()
    }


    @Test
    fun wrongFormatEmail_raiseException() {
        val wrongFormat = listOf(
            "Mohammad",
            "Mohammad.derakhshan",
            "Mohammad@derakhshan",
            "Mohammad.derakhshan@com",
            "Mohammad.",
            "Mohammad.derakhshan@"
        )
        val emailInput = composeRule.onNodeWithTag(AuthenticationTestingConstants.EMAIL_TEXT_FIELD)
        val loginButton = composeRule.onNodeWithTag(AuthenticationTestingConstants.LOGIN_BUTTON)
        for (email in wrongFormat) {
            emailInput.performTextInput(email)
            loginButton.performClick()
            composeRule.onNodeWithText("Email address format is not correct.").assertIsDisplayed()
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

}