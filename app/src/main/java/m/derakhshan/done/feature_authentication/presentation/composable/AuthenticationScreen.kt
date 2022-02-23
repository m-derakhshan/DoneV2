package m.derakhshan.done.feature_authentication.presentation.composable

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import m.derakhshan.done.R
import m.derakhshan.done.core.presentation.composable.LoadingButton
import m.derakhshan.done.feature_authentication.presentation.AuthenticationEvent
import m.derakhshan.done.feature_authentication.presentation.AuthenticationViewModel
import m.derakhshan.done.feature_authentication.utils.AuthenticationTestingConstants
import m.derakhshan.done.ui.theme.spacing


@Composable
fun AuthenticationScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
    ) {
        // todo: comment or uncomment this part for android testing
        LottieBanner(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )

        Text(
            text = stringResource(id = R.string.welcome),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )


        OutlinedTextField(
            value = state.email,
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            onValueChange = { viewModel.onEvent(AuthenticationEvent.EmailChanged(it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small)
                .testTag(AuthenticationTestingConstants.EMAIL_TEXT_FIELD)
        )

        OutlinedTextField(
            value = state.password,
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            onValueChange = { viewModel.onEvent(AuthenticationEvent.PasswordChanged(it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation =
            if (state.isPasswordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (state.isPasswordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Default.VisibilityOff
                IconButton(
                    onClick = {
                        viewModel.onEvent(AuthenticationEvent.TogglePasswordVisibility)
                    },
                    modifier = Modifier.testTag(AuthenticationTestingConstants.PASSWORD_VISIBILITY_ICON_BUTTON)
                ) {
                    Icon(imageVector = image, "show or hide password")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small)
                .testTag(AuthenticationTestingConstants.PASSWORD_TEXT_FIELD)
        )

        AnimatedVisibility(
            visible = state.isNameAndFamilyFieldVisible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            OutlinedTextField(
                value = state.email,
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                onValueChange = { viewModel.onEvent(AuthenticationEvent.EmailChanged(it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.small)
                    .testTag(AuthenticationTestingConstants.EMAIL_TEXT_FIELD)
            )
        }



        LoadingButton(
            buttonText = stringResource(id = R.string.login_signup),
            isExpanded = state.isLoadingButtonExpanded,
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .align(Alignment.CenterHorizontally)
        ) {
            viewModel.onEvent(AuthenticationEvent.LoginSignUpClicked)
        }


    }
}