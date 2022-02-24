package m.derakhshan.done.feature_authentication.data.repository


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_authentication.domain.repository.AuthenticationRepository
import m.derakhshan.done.feature_authentication.utils.credentialValidityChecker
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authentication: FirebaseAuth,
    private val storage: FirebaseFirestore
) : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Response<UserModel> {

        return try {
            credentialValidityChecker(email = email, password = password)

            authentication.signInWithEmailAndPassword(email, password)
                .await()
                .user.let { info ->

                    val userInformation =
                        storage.collection("users").document(info!!.uid).get().await()

                    Response.Success(
                        UserModel(
                            uid = info.uid,
                            email = email,
                            password = password,
                            name = userInformation.data?.get("name").toString()
                        )
                    )
                }
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> throw  e
                is FirebaseAuthInvalidUserException -> {
                    Response.Error(message = e.message ?: "Unknown error", responseCode = 404)
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    Response.Error(message = e.message ?: "Unknown error", responseCode = 401)
                }
                else -> Response.Error(message = e.message ?: "Unknown error", responseCode = 0)
            }
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        name: String
    ): Response<UserModel> {

        return try {
            credentialValidityChecker(email = email, password = password, nameAndFamily = name)
            authentication.createUserWithEmailAndPassword(
                email, password
            )
                .await()
                .user!!.let { info ->
                    storage.collection("users").document(info.uid).set(
                        hashMapOf(
                            "uid" to info.uid,
                            "name" to name,
                        )
                    ).await()

                    Response.Success(
                        UserModel(
                            uid = info.uid,
                            email = email,
                            password = password,
                            name = name
                        )
                    )
                }
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> throw e
                else -> Response.Error(message = e.message ?: "Unknown Error.", responseCode = 0)
            }
        }
    }

}