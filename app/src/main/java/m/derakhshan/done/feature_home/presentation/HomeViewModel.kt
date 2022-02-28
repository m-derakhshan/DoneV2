package m.derakhshan.done.feature_home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import m.derakhshan.done.feature_home.domain.use_case.HomeUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: HomeUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private var job: Job? = null

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                greetings = useCases.greetingsUseCase()
            )
            useCases.updateInspirationQuotesUseCase()
        }
        getTodayQuote()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.TaskListSwiped -> {
                _state.value = _state.value.copy(
                    taskListOffset = event.offset
                )
            }
        }
    }


    private fun getTodayQuote() {
        job?.cancel()
        job = useCases.getInsertInspirationQuoteUseCase(
        ).onEach {
            it?.let { quote ->
                _state.value = _state.value.copy(
                    inspirationQuote = quote.quote,
                    inspirationQuoteAuthor = quote.author
                )
            }
        }.launchIn(viewModelScope)
    }

}