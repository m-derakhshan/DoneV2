package m.derakhshan.done.feature_task.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_task.domain.model.TaskModel

interface TaskRepository {
    fun getTasks(): Flow<List<TaskModel>?>
    suspend fun insertTask(task:TaskModel)
}