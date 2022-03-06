package m.derakhshan.done.feature_task.domain.use_case

import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskStatus @Inject constructor(private val repository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel, checked: Boolean) {
        repository.updateTaskStatus(task = taskModel, checked = checked)
    }
}