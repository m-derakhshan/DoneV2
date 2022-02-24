package m.derakhshan.done.feature_authentication.presentation

import m.derakhshan.done.R

data class AuthenticationState(
    val email: String = "",
    val password: String = "",
    val nameAndFamily: String = "",
    val showNameAndFamily: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoadingButtonExpanded: Boolean = true,
    val isNameAndFamilyFieldVisible: Boolean = false,
    val loadingButtonText: Int = R.string.login_signup
)
