package m.derakhshan.done.feature_task.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.model.TaskStatus
import m.derakhshan.done.feature_task.domain.use_case.TaskUseCases
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val useCase: TaskUseCases
) : ViewModel() {

    private var job: Job? = null
    private val _state = mutableStateOf(TaskState())
    val state: State<TaskState> = _state


    init {
        getTasks()
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.OnNewTaskClicked -> {
                viewModelScope.launch {
                    useCase.insertNewTask(
                        TaskModel(
                            description = "just for testing new task\njust for testing new task\njust for testing new task",
                            color = TaskModel.colors[1].toArgb(),
                            date = "08-02-2022",
                            time = "18:28",
                            status = TaskStatus.Done
                        )
                    )
                }
            }
            TaskEvent.ListScrollDown -> {
                _state.value = _state.value.copy(fabOffset = (100).dp)
            }
            TaskEvent.ListScrollUp -> {
                _state.value = _state.value.copy(fabOffset = (0).dp)
            }
            is TaskEvent.OnTaskCheckClicked -> {
                // TODO: update the task
            }
        }

    }


    private fun getTasks() {
        job?.cancel()
        job = useCase.getTasksUseCase().onEach {
            it?.let { tasks ->
                val group = tasks.groupBy { item -> item.date }
                val result = ArrayList<TaskGroup>()
                for (item in group) {
                    result.add(TaskGroup(title = item.key, tasks = item.value))
                }
                _state.value = _state.value.copy(
                    tasks = result
                )
            }
        }.launchIn(viewModelScope)
    }


    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}