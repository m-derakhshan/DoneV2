package m.derakhshan.done.feature_home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import m.derakhshan.done.feature_home.domain.use_case.HomeUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: HomeUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

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

}