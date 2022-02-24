package m.derakhshan.done.feature_authentication.domain.use_case

data class AuthenticationUseCase(
    val loginUseCase: LoginUseCase,
    val signUpUseCase: SignUpUseCase
)
