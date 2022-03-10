package m.derakhshan.done.feature_task.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.model.TaskSyncModel

interface TaskRepository {
    fun getTasks(): Flow<List<TaskModel>?>
    suspend fun insertTask(task: TaskModel)
    suspend fun updateTaskStatus(task: TaskModel, checked: Boolean)
    suspend fun deleteTask(task: TaskModel)
    fun getTaskToSync(): Flow<List<TaskSyncModel>?>
    suspend fun syncTasks(task: TaskSyncModel)
}