package m.derakhshan.done.feature_profile.domain.use_case

import m.derakhshan.done.feature_profile.domain.repository.ProfileRepository
import javax.inject.Inject


class UpdateUserInfoUseCase @Inject constructor(
    val repository: ProfileRepository
) {
    suspend operator fun invoke(uri:String){

    }
}