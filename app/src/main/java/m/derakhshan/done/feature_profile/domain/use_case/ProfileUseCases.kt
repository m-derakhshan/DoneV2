package m.derakhshan.done.feature_profile.domain.use_case

data class ProfileUseCases(
    val getUserInfo: GetUserInfoUseCase,
    val logOutUser: LogoutUseCase
)