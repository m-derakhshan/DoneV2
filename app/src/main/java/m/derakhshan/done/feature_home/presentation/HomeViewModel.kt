package m.derakhshan.done.feature_home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnAddClicked -> {
                _state.value = _state.value.copy(
                    isNoteFieldVisible = !_state.value.isNoteFieldVisible
                )
            }
            is HomeEvent.OnNoteFieldChange -> {
                _state.value = _state.value.copy(
                    isAddNoteAnimationPlaying = true,
                    addNoteAnimationSpeed = when {
                        event.text.length > 3 && (event.text.length - _state.value.noteFieldText.length) > 0 -> 1f
                        event.text.length < 4 -> -1.5f
                        else -> 0f
                    }
                )
            }
            is HomeEvent.OnSaveNoteClicked -> {}
        }
    }

}