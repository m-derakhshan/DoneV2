package m.derakhshan.done.feature_profile.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_authentication.domain.model.UserModel

interface ProfileRepository {
    suspend fun getUserInfo():Flow<UserModel>
}