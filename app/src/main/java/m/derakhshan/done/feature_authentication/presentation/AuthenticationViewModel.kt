package m.derakhshan.done.feature_authentication.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import m.derakhshan.done.feature_authentication.presentation.AuthenticationEvent.*
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authentication: FirebaseAuth,
) : ViewModel() {


    private val _state = mutableStateOf(AuthenticationState())
    val state: State<AuthenticationState> = _state


    fun onEvent(event: AuthenticationEvent) {
        when (event) {
            is LoginSignUpClicked -> {
                loginOrSignUp()
            }
            is EmailChanged -> {
                _state.value = _state.value.copy(
                    email = event.email
                )
            }
            is PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = event.password
                )
            }
            is NameAndFamilyChanged -> {
                _state.value = _state.value.copy(
                    nameAndFamily = event.NameAndFamily
                )
            }
            is TogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                )
            }
        }
    }

    // TODO: solve the authentication listener problem
    private fun loginOrSignUp() {
        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoadingButtonExpanded = false
            )

            delay(3000)

            _state.value = _state.value.copy(
                isLoadingButtonExpanded = true,
                isNameAndFamilyFieldVisible = true
            )

        }


//        authentication.signInWithEmailAndPassword(
//            _state.value.email,
//            _state.value.password
//        )
    }


}