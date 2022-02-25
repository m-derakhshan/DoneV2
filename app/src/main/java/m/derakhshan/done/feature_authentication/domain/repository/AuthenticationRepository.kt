package m.derakhshan.done.feature_authentication.domain.repository

import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.domain.model.UserModel

interface AuthenticationRepository {

    suspend fun login(email: String, password: String): Response<UserModel>
    suspend fun signUp(email: String, password: String, name: String): Response<UserModel>
    suspend fun resetPassword(email: String): String
}