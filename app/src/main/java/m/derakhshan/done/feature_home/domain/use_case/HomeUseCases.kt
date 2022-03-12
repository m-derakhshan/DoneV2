package m.derakhshan.done.feature_home.domain.use_case

import m.derakhshan.done.feature_task.domain.use_case.DeleteTaskUseCase
import m.derakhshan.done.feature_task.domain.use_case.InsertNewTask
import m.derakhshan.done.feature_task.domain.use_case.UpdateTaskStatus

data class HomeUseCases(
    val getInsertInspirationQuoteUseCase: GetInspirationQuoteUseCase,
    val updateInspirationQuotesUseCase: UpdateInspirationQuotesUseCase,
    val greetingsUseCase: GreetingsUseCase,
    val getTodayTask:GetTodayTaskUseCase,
    val updateTaskStatus: UpdateTaskStatus,
    val deleteTask: DeleteTaskUseCase,
    val insertNewTask: InsertNewTask,
)
