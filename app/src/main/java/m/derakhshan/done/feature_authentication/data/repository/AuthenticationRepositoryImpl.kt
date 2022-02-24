package m.derakhshan.done.feature_authentication.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_authentication.domain.repository.AuthenticationRepository
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authentication: FirebaseAuth
) : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Response<UserModel> {
        // TODO: get user name from database
        return try {
            val user = authentication.signInWithEmailAndPassword(email, password).await()
            user.user!!.let { info ->
                Response.Success(
                    UserModel(
                        token = info.uid,
                        email = info.email!!,
                        password = password,
                        name = "Mohammad"
                    )
                )
            }
        } catch (e: Exception) {

            when (e) {
                is FirebaseAuthInvalidUserException -> {
                    Response.Error(message = e.message ?: "Unknown error", responseCode = 404)
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    Response.Error(message = e.message ?: "Unknown error", responseCode = 401)
                }
                else -> throw e
            }


        }

    }

    override suspend fun signUp(
        email: String,
        password: String,
        name: String
    ): Response<UserModel> {
        // TODO: implement user sign up 
        var result: Response<UserModel> = Response.Loading()
        authentication.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                result = if (task.isSuccessful)
                    Response.Success(
                        data = UserModel(
                            token = "",
                            email = "",
                            password = "",
                            name = ""
                        )
                    )
                else
                    Response.Error(message = "can't create user", responseCode = 0)
            }

        return result
    }
}