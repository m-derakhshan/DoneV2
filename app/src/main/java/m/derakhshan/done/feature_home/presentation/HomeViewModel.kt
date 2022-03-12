package m.derakhshan.done.feature_home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import m.derakhshan.done.feature_home.domain.use_case.HomeUseCases
import m.derakhshan.done.feature_task.domain.model.TaskModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: HomeUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _snackBar = MutableSharedFlow<String>()
    val snackBar = _snackBar.asSharedFlow()

    private var deletedTask: TaskModel? = null

    init {

        viewModelScope.launch {
            val greeting = useCases.greetingsUseCase()
            greeting.values.first().collectLatest { userName ->
                _state.value = _state.value.copy(
                    greetings = mapOf(Pair(greeting.keys.first(), userName ?: ""))
                )
            }
        }

        viewModelScope.launch {
            useCases.updateInspirationQuotesUseCase()
            useCases.getInsertInspirationQuoteUseCase().collectLatest {
                it?.let { quote ->
                    _state.value = _state.value.copy(
                        inspirationQuote = quote.quote,
                        inspirationQuoteAuthor = quote.author
                    )
                }
            }
        }

        viewModelScope.launch {
            useCases.getTodayTask().collectLatest {
                it?.let { tasks ->
                    _state.value = _state.value.copy(
                        todayTaskList = tasks
                    )
                }
            }
        }

    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.TaskListSwiped -> {
                _state.value = _state.value.copy(
                    taskListOffset = event.offset
                )
            }
            is HomeEvent.OnTaskCheckClicked -> {
                viewModelScope.launch {
                    useCases.updateTaskStatus(taskModel = event.task, checked = event.checked)
                }
            }
            is HomeEvent.TaskDeleteClicked -> {
                viewModelScope.launch {

                    useCases.deleteTask(task = event.task)

                    deletedTask = event.task
                    _snackBar.emit("Task deleted.")
                }
            }
            HomeEvent.TaskUndo -> {
                viewModelScope.launch {
                    deletedTask?.let {
                        useCases.insertNewTask(task = it)
                    }
                    deletedTask = null
                }
            }
        }
    }

}