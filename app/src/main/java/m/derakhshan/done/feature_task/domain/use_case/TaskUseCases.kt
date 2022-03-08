package m.derakhshan.done.feature_task.domain.use_case

data class TaskUseCases(
    val getTasksUseCase: GetTasksUseCase,
    val insertNewTask: InsertNewTask,
    val updateTaskStatus: UpdateTaskStatus,
    val deleteTaskUseCase: DeleteTaskUseCase
)
