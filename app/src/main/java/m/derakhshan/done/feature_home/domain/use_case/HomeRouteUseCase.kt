package m.derakhshan.done.feature_home.domain.use_case

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRouteUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    operator fun invoke(): Flow<UserModel> {
        return repository.userInfo()
    }
}