package m.derakhshan.done.feature_task.presentation

import androidx.compose.ui.graphics.Color
import m.derakhshan.done.feature_task.domain.model.TaskModel

sealed class TaskEvent {
    object OnNewTaskClicked : TaskEvent()
    object ListScrollUp : TaskEvent()
    object ListScrollDown : TaskEvent()
    object NewTaskPanelClosed : TaskEvent()
    data class NewTaskDescriptionChanged(val description: String) : TaskEvent()
    data class NewTaskColorSelected(val color: Color) : TaskEvent()
    data class OnTaskCheckClicked(val task: TaskModel, val checked: Boolean) : TaskEvent()
}
