package m.derakhshan.done.feature_home.presentation

import m.derakhshan.done.feature_task.domain.model.TaskModel


sealed class HomeEvent {
    data class TaskListSwiped(val offset: Float) : HomeEvent()
    data class OnTaskCheckClicked(val task: TaskModel, val checked: Boolean) : HomeEvent()
    data class TaskDeleteClicked(val task: TaskModel) : HomeEvent()
    object TaskUndo : HomeEvent()
}