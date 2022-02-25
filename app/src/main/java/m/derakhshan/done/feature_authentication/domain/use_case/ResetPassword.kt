package m.derakhshan.done.feature_authentication.domain.use_case

import m.derakhshan.done.feature_authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject

class ResetPassword @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String) :String{
        return repository.resetPassword(email)
    }
}