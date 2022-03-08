package m.derakhshan.done.feature_task.data.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_task.data.data_source.TaskDao
import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.model.TaskStatus
import m.derakhshan.done.feature_task.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskDao: TaskDao
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
        return taskDao.delete(task = task)
    }
}