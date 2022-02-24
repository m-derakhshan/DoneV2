package m.derakhshan.done.feature_authentication.domain.use_case

import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String
    ): Response<UserModel> {
        return repository.signUp(email = email, password = password, name = name)
    }
}