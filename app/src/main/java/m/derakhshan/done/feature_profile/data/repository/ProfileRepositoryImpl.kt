package m.derakhshan.done.feature_profile.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.tasks.await
import m.derakhshan.done.core.data.data_source.Setting
import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_authentication.utils.credentialValidityChecker
import m.derakhshan.done.feature_profile.domain.repository.ProfileRepository
import java.util.concurrent.CancellationException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val setting: Setting,
    private val authentication: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : ProfileRepository {
    override suspend fun getUserInfo(): Flow<UserModel> {
        return userDao.getInfo()
    }

    override suspend fun logoutUser() {
        // TODO: delete all user tasks and notes
        userDao.delete()
        setting.isUserLoggedIn = false


        //--------------------(use this line after everything is deleted. it cause app close.)--------------------//
        authentication.signOut()
    }

    override suspend fun updateUserInfo(userModel: UserModel): Response<String> {
        return try {
            fireStore.collection("users")
                .document(userModel.uid)
                .update(mapOf(Pair("name", userModel.name))).await()
            userDao.insert(
                user = userModel
            )
            Response.Success(data = "Changes saved.")
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Response.Error(message = e.message ?: "Unknown Error.", responseCode = 0)
        }


    }

    override suspend fun resetPasswordRequest(email: String): String {
        return try {
            authentication.sendPasswordResetEmail(email).await()
            "Reset linked has been sent to your email."
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            e.message ?: "Unknown Error."
        }
    }
}