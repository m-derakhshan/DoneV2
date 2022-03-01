package m.derakhshan.done.feature_authentication.data.repository


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_authentication.domain.repository.AuthenticationRepository
import m.derakhshan.done.feature_authentication.utils.credentialValidityChecker
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authentication: FirebaseAuth,
    private val storage: FirebaseFirestore,
    private val databaseDao: UserDao
) : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Response<UserModel> {

        return try {
            credentialValidityChecker(email = email, password = password)

            authentication.signInWithEmailAndPassword(email, password)
                .await()
                .user.let { info ->


                    val userInformation =
                        storage.collection("users").document(info!!.uid).get().await()

                    val newUser = UserModel(
                        uid = info.uid,
                        email = email,
                        name = userInformation.data?.get("name").toString()
                    )
                    databaseDao.insert(newUser)
                    Response.Success(newUser)

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

            authentication.createUserWithEmailAndPassword(email, password)
                .await()
                .user!!.let { info ->
                    storage.collection("users").document(info.uid)
                        .set(hashMapOf("uid" to info.uid, "name" to name))
                        .await()

                    val newUser = UserModel(
                        uid = info.uid,
                        email = email,
                        name = name
                    )
                    databaseDao.insert(newUser)
                    Response.Success(newUser)
                }
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> throw e
                else -> Response.Error(message = e.message ?: "Unknown Error.", responseCode = 0)
            }
        }
    }

    override suspend fun resetPassword(email: String): String {
        return try {
            credentialValidityChecker(email = email, password = "NoPassword")
            authentication.sendPasswordResetEmail(email).await()
            "Reset linked has been sent to your email."
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            e.message ?: "Unknown Error"
        }
    }

}