package m.derakhshan.done.feature_task.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_task.data.data_source.TaskDao
import m.derakhshan.done.feature_task.data.data_source.TaskSyncDao
import m.derakhshan.done.feature_task.domain.model.*
import m.derakhshan.done.feature_task.domain.repository.TaskRepository
import java.util.concurrent.CancellationException

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val taskSyncDao: TaskSyncDao,
    private val userDao: UserDao,
    private val storage: FirebaseFirestore,
) : TaskRepository {
    override fun getTasks(): Flow<List<TaskModel>?> {
        return taskDao.getTasks()
    }

    override suspend fun insertTask(task: TaskModel) {
        taskDao.insert(task = task)
    }

    override suspend fun updateTaskStatus(task: TaskModel, checked: Boolean) {
        val updateTask = TaskModel(
            id = task.id,
            description = task.description,
            color = task.color,
            date = task.date,
            time = task.time,
            status = if (checked) TaskStatus.Done else TaskStatus.InProgress
        )
        taskDao.insert(task = updateTask)
    }

    override suspend fun deleteTask(task: TaskModel) {
        taskDao.delete(task = task)
        val uid = userDao.getUserId()
        taskSyncDao.insert(taskSyncModel = task.toTaskSyncModel(uid, TaskSyncAction.Delete))

    }

    override fun getTaskToSync(): Flow<List<TaskSyncModel>?> {
        return taskSyncDao.getTaskToSync()
    }

    override suspend fun syncTasks(task: TaskSyncModel) {
        try {
            when (task.action) {
                is TaskSyncAction.Insert, is TaskSyncAction.Update -> {
                    storage.collection("users")
                        .document(task.userId).collection("tasks")
                        .document(task.id.toString()).set(task.toTaskModel()).await()
                    taskSyncDao.delete(task)
                }
                is TaskSyncAction.Delete -> {
                    storage.collection("users")
                        .document(task.userId).collection("tasks")
                        .document(task.id.toString()).delete().await()
                    taskSyncDao.delete(task)
                }
            }
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.i("Log", "syncTask: error in Syncing:${e.message}")
        }
    }
}