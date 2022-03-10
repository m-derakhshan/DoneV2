package m.derakhshan.done.feature_task.domain.use_case

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_task.domain.model.TaskSyncModel
import m.derakhshan.done.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasksToSyncUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<TaskSyncModel>?> {
        return repository.getTaskToSync()
    }
}