package m.derakhshan.done.feature_authentication.data.repository


import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_authentication.domain.repository.AuthenticationRepository
import m.derakhshan.done.feature_authentication.utils.credentialValidityChecker

class AuthenticationRepositoryImplTest : AuthenticationRepository {

    companion object{
        var TestSituationOfSignUp = false
    }

    override suspend fun login(email: String, password: String): Response<UserModel> {
        return try {
            credentialValidityChecker(email, password)
            if (TestSituationOfSignUp)
                Response.Error("user not found", responseCode = 404)
            else
                Response.Success(
                    data = UserModel(
                        uid = "testing_uid",
                        email = email,
                        password = password,
                        name = "test user"
                    )
                )

        } catch (e: Exception) {
            Response.Error(e.message ?: "unknown error", responseCode = 0)
        }

    }

    override suspend fun signUp(
        email: String,
        password: String,
        name: String
    ): Response<UserModel> {

        return try {
            credentialValidityChecker(email, password, name)
            Response.Success(
                data = UserModel(
                    uid = "testing_uid",
                    email = email,
                    password = password,
                    name = name
                )
            )
        } catch (e: Exception) {
            Response.Error(e.message ?: "unknown error", responseCode = 0)
        }

    }

    override suspend fun resetPassword(email: String): String {
        return try {
            credentialValidityChecker(email, "no_pass_required.")
            "ok"
        } catch (e: Exception) {
            e.message ?: "Unknown error"
        }
    }
}