package m.derakhshan.done.feature_profile.domain.use_case

import m.derakhshan.done.feature_profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(email: String) :String{
        return repository.resetPasswordRequest(email)
    }

}