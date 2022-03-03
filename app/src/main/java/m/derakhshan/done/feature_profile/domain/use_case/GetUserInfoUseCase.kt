package m.derakhshan.done.feature_profile.domain.use_case

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_profile.domain.repository.ProfileRepository

class GetUserInfoUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Flow<UserModel?> {
        return repository.getUserInfo()
    }
}