package m.derakhshan.done.feature_task.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.ui.theme.LightGreen

data class TaskState(
    val tasks: List<TaskGroup> = emptyList(),
    val fabOffset: Dp = 0.dp,
    val showAddTaskSection: Boolean = false,
    val newTaskColor: Color = LightGreen,
    val newTaskDescription: String = "",
    val isAddTaskEnable: Boolean = false
)

data class TaskGroup(
    val title: String = "",
    val tasks: List<TaskModel> = emptyList()
)