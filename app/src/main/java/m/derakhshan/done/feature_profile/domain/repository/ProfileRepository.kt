package m.derakhshan.done.feature_profile.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.domain.model.UserModel

interface ProfileRepository {
    suspend fun getUserInfo(): Flow<UserModel?>
    suspend fun logoutUser()
    suspend fun updateUserInfo(userModel: UserModel): Response<String>
    suspend fun resetPasswordRequest(email: String): String
}