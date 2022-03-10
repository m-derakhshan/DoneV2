package m.derakhshan.done.feature_task.domain.use_case


data class TaskUseCases(
    val getTasks: GetTasksUseCase,
    val insertNewTask: InsertNewTask,
    val updateTaskStatus: UpdateTaskStatus,
    val deleteTask: DeleteTaskUseCase,
    val syncTasks: SyncTasksUseCase,
    val getTasksToSync: GetTasksToSyncUseCase,
)
