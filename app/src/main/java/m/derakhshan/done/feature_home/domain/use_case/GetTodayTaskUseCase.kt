package m.derakhshan.done.feature_home.domain.use_case

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_home.domain.repository.HomeRepository
import m.derakhshan.done.feature_task.domain.model.TaskModel
import javax.inject.Inject

class GetTodayTaskUseCase @Inject constructor(private val repository: HomeRepository) {

    operator fun invoke(): Flow<List<TaskModel>?> {
       return repository.getTodayTasks()
    }
}