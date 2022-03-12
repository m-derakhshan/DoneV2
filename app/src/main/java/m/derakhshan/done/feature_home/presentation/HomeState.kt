package m.derakhshan.done.feature_home.presentation

import m.derakhshan.done.feature_task.domain.model.TaskModel

data class HomeState(
    val todayTaskList: List<TaskModel> = emptyList(),
    val inspirationQuoteAuthor: String = "Unknown",
    val inspirationQuote: String = "Try your best!",
    val greetings: Map<String, String> = mapOf(Pair("", "")),
    val taskListOffset: Float = 400f
)