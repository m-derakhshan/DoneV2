package m.derakhshan.done.feature_task.presentation

import m.derakhshan.done.feature_task.domain.model.TaskModel

sealed class TaskEvent {
    object OnNewTaskClicked : TaskEvent()
    object ListScrollUp : TaskEvent()
    object ListScrollDown : TaskEvent()
    data class OnTaskCheckClicked(val task: TaskModel,val checked:Boolean) : TaskEvent()
}
