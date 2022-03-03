package m.derakhshan.done.feature_note.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.use_case.NoteUseCases
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private val _snackBar = MutableSharedFlow<String>()
    val snackBar = _snackBar.asSharedFlow()

    private var lastNote: NoteModel? = null

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.OnDeleteNoteClicked -> {}
            is NoteEvent.OnNoteOrderTypeChange -> {
                _state.value = _state.value.copy(
                    selectedOrderType = event.orderType
                )
            }
            is NoteEvent.OnNoteOrderSortTypeChange -> {
                _state.value = _state.value.copy(
                    selectedOrderSortType = event.sortType
                )
            }

            is NoteEvent.ToggleOrderSectionVisibility -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
        }
    }

}