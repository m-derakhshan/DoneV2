package m.derakhshan.done.feature_task.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SyncDisabled
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import m.derakhshan.done.feature_note.domain.model.NoteSyncModel
import m.derakhshan.done.feature_task.domain.model.TaskModel
import m.derakhshan.done.feature_task.domain.model.TaskSyncModel
import m.derakhshan.done.feature_task.domain.use_case.TaskUseCases
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val useCase: TaskUseCases
) : ViewModel() {

    private var job: Job? = null
    private var deletedTask: TaskModel? = null

    private val _state = mutableStateOf(TaskState())
    val state: State<TaskState> = _state

    private val _snackBar = MutableSharedFlow<String>()
    val snackBar = _snackBar.asSharedFlow()

    private var syncJob: Job? = null
    private var taskToSyncList = ArrayList<TaskSyncModel>()

    init {
        getTasks()
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.OnNewTaskClicked -> {
                _state.value = _state.value.copy(showAddTaskSection = true)
            }
            is TaskEvent.NewTaskPanelClosed -> {
                _state.value = _state.value.copy(showAddTaskSection = false)
            }
            is TaskEvent.OnTaskCheckClicked -> {
                viewModelScope.launch {
                    useCase.updateTaskStatus(taskModel = event.task, checked = event.checked)
                }
            }
            is TaskEvent.ListScrollDown -> {
                _state.value = _state.value.copy(fabOffset = (100).dp)
            }
            is TaskEvent.ListScrollUp -> {
                _state.value = _state.value.copy(fabOffset = (0).dp)
            }
            is TaskEvent.NewTaskColorSelected -> {
                _state.value = _state.value.copy(
                    newTaskColor = event.color
                )
            }
            is TaskEvent.NewTaskDescriptionChanged -> {
                _state.value = _state.value.copy(
                    newTaskDescription = event.description,
                    isAddTaskEnable = event.description.length > 2
                )
            }
            TaskEvent.NewTaskSaveClick -> {
                viewModelScope.launch {
                    for (date in _state.value.newTaskDate)
                        useCase.insertNewTask(
                            task = TaskModel(
                                description = _state.value.newTaskDescription,
                                color = _state.value.newTaskColor.toArgb(),
                                time = "18:30",
                                date = "${date.year}/${date.month}/${date.day}"
                            )
                        )
                    _state.value = _state.value.copy(
                        newTaskDescription = "",
                        showAddTaskSection = false,
                        isAddTaskEnable = false
                    )
                }
            }
            is TaskEvent.TaskDeleteClicked -> {
                viewModelScope.launch {

                    useCase.deleteTask(task = event.task)

                    deletedTask = event.task
                    _snackBar.emit("Task deleted.")
                }
            }
            TaskEvent.TaskUndo -> {
                viewModelScope.launch {
                    deletedTask?.let {
                        useCase.insertNewTask(task = it)
                    }
                    deletedTask = null
                }
            }
            is TaskEvent.NewTaskDateSelectedSelected -> {
                _state.value = _state.value.copy(
                    newTaskDate = event.dates
                )
            }
            TaskEvent.OnTaskSyncClicked -> {
                _state.value = _state.value.copy(
                    isSyncIconRotating = true
                )
                viewModelScope.launch {
                    useCase.syncTasks(tasks = taskToSyncList)
                    _state.value = _state.value.copy(
                        isSyncIconRotating = false
                    )
                }
            }
        }


    }


    private fun getTasks() {
        job?.cancel()
        job = useCase.getTasks().onEach {
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
            getSyncTasks()
        }.launchIn(viewModelScope)
    }

    private fun getSyncTasks() {
        syncJob?.cancel()
        syncJob = useCase.getTasksToSync().onEach {
            _state.value = _state.value.copy(
                syncNumber = it?.size ?: 0,
                syncIcon = if (it != null && it.isNotEmpty()) Icons.Default.Sync else Icons.Default.SyncDisabled
            )
            it?.let { notes ->
                taskToSyncList.addAll(notes)
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        job?.cancel()
        syncJob?.cancel()
        super.onCleared()
    }
}