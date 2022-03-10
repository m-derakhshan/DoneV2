package m.derakhshan.done.feature_authentication.data.repository


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import m.derakhshan.done.core.data.data_source.Setting
import m.derakhshan.done.core.domain.model.Response
import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_authentication.domain.model.UserModel
import m.derakhshan.done.feature_authentication.domain.repository.AuthenticationRepository
import m.derakhshan.done.feature_authentication.utils.credentialValidityChecker
import m.derakhshan.done.feature_note.data.data_source.NoteDao
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_task.data.data_source.TaskDao
import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.model.TaskStatus
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authentication: FirebaseAuth,
    private val storage: FirebaseFirestore,
    private val userDao: UserDao,
    private val noteDao: NoteDao,
    private val taskDao: TaskDao,
    private val setting: Setting
) : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Response<UserModel> {

        return try {
            credentialValidityChecker(email = email, password = password)

            authentication.signInWithEmailAndPassword(email, password)
                .await()
                .user.let { info ->

                    // TODO: make these tasks run in parallel


                    val userInformation =
                        storage.collection("users").document(info!!.uid).get().await()

                    val newUser = UserModel(
                        uid = info.uid,
                        email = email,
                        name = userInformation.data?.get("name").toString()
                    )
                    userDao.insert(newUser)

                    //--------------------(fetch notes from fire store and insert in local db)--------------------//
                    val noteInfo = storage.collection("users").document(info.uid)
                        .collection("notes").get().await()
                    val notes = ArrayList<NoteModel>()
                    var maxNoteId = 0
                    for (item in noteInfo.documents.map { it.data }) {
                        item?.let {
                            val noteId = item["id"].toString().toInt()
                            maxNoteId = if (maxNoteId > noteId)
                                maxNoteId
                            else
                                noteId

                            notes.add(
                                NoteModel(
                                    id = noteId,
                                    title = item["title"] as String,
                                    content = item["content"] as String,
                                    timestamp = item["timestamp"] as Long,
                                    color = item["color"].toString().toInt()
                                )
                            )
                        }
                    }

                    noteDao.insertAll(notes = notes)
                    setting.lastNoteId = maxNoteId + 1

                    //--------------------(fetch tasks from fire store and insert in local db)--------------------//
                    val taskInfo = storage.collection("users").document(info.uid)
                        .collection("tasks").get().await()
                    val tasks = ArrayList<TaskModel>()
                    for (item in taskInfo.documents.map { it.data }) {
                        item?.let {
                            tasks.add(
                                TaskModel(
                                    id = item["id"] as Long,
                                    description = item["description"] as String,
                                    color = item["color"].toString().toInt(),
                                    date = item["date"].toString(),
                                    time = item["time"].toString(),
                                    status = when ((item["status"] as Map<*, *>).values.first()) {
                                        "InProgress" -> TaskStatus.InProgress
                                        else -> TaskStatus.Done
                                    }
                                )
                            )
                        }
                    }
                    taskDao.insert(tasks)



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
                    userDao.insert(newUser)
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