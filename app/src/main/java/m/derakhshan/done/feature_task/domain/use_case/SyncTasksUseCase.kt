package m.derakhshan.done.feature_task.domain.use_case

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import m.derakhshan.done.feature_task.domain.model.TaskSyncModel
import m.derakhshan.done.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class SyncTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(tasks: List<TaskSyncModel>) {
        val execAll = ArrayList<Deferred<Unit>>()
        coroutineScope {
            for (task in tasks) {
                execAll.add(async { repository.syncTasks(task = task) })
            }
        }
        execAll.awaitAll()
    }
}