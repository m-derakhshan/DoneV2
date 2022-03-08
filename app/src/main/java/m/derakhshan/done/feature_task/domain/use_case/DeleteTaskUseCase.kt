package m.derakhshan.done.feature_task.domain.use_case

import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {

    suspend operator fun invoke(task: TaskModel) {
        repository.deleteTask(task = task)
    }
}