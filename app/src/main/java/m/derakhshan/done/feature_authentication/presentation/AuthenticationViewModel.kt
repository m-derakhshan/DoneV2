package m.derakhshan.done.feature_authentication.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.domain.use_case.AuthenticationUseCase
import m.derakhshan.done.feature_authentication.presentation.AuthenticationEvent.*
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val useCase: AuthenticationUseCase
) : ViewModel() {


    private val _state = mutableStateOf(AuthenticationState())
    val state: State<AuthenticationState> = _state

    private val _snackBar = MutableSharedFlow<String>()
    val snackBar = _snackBar.asSharedFlow()


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


    private fun loginOrSignUp() {
        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoadingButtonExpanded = false
            )

            useCase.loginUseCase(email = _state.value.email, password = _state.value.password)
                .let { response ->
                    if (response is Response.Success)
                        _snackBar.emit("User logged in successfully")
                    else
                        when (response.responseCode) {
                            401 -> _snackBar.emit(response.message ?: "Unknown error.")
                            404 -> _state.value = _state.value.copy(
                                isNameAndFamilyFieldVisible = true
                            )
                        }
                }

            _state.value = _state.value.copy(
                isLoadingButtonExpanded = true
            )
        }
    }


}