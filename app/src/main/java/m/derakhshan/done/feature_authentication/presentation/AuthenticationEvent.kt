package m.derakhshan.done.feature_authentication.presentation

sealed class AuthenticationEvent {
    object LoginSignUpClicked : AuthenticationEvent()
    object TogglePasswordVisibility : AuthenticationEvent()
    data class EmailChanged(val email: String) : AuthenticationEvent()
    data class PasswordChanged(val password: String) : AuthenticationEvent()
    data class NameAndFamilyChanged(val NameAndFamily: String) : AuthenticationEvent()
}
