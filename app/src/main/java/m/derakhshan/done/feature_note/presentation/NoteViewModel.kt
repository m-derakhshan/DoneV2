package m.derakhshan.done.feature_note.presentation


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.use_case.NoteUseCases
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCases: NoteUseCases
) : ViewModel() {

    private var job: Job? = null
    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var lastNote: NoteModel? = null

    init {
        getNotes()
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.OnDeleteNoteClicked -> {
                lastNote = event.note
                viewModelScope.launch {
                    useCases.deleteNote(event.note)
                }
            }
            is NoteEvent.OnNoteOrderTypeChange -> {
                _state.value = _state.value.copy(selectedOrderType = event.orderType)
                getNotes()
            }
            is NoteEvent.OnNoteOrderSortTypeChange -> {
                _state.value = _state.value.copy(selectedOrderSortType = event.sortType)
                getNotes()
            }

            is NoteEvent.ToggleOrderSectionVisibility -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
            NoteEvent.ListScrollDown -> {
                _state.value = _state.value.copy(fabOffset = (-10).dp)
            }
            NoteEvent.ListScrollUp -> {
                _state.value = _state.value.copy(fabOffset = 0.dp)
            }
            NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    lastNote?.let { note ->
                        useCases.restoreNote(note)
                    }
                    lastNote = null
                }
            }
        }
    }

    private fun getNotes() {
        job?.cancel()
        job = useCases.getNotes(
            orderType = _state.value.selectedOrderType,
            sortType = _state.value.selectedOrderSortType
        ).onEach {
            _state.value = _state.value.copy(
                notes = it
            )
        }.launchIn(viewModelScope)
    }


    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}