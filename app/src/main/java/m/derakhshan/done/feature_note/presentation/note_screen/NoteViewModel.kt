package m.derakhshan.done.feature_note.presentation.note_screen


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SyncDisabled
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
import m.derakhshan.done.core.data.data_source.Setting
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.model.NoteSyncModel
import m.derakhshan.done.feature_note.domain.use_case.NoteUseCases
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCases: NoteUseCases,
    private val setting: Setting
) : ViewModel() {

    private var job: Job? = null
    private var syncJob: Job? = null

    private var noteToSyncList = ArrayList<NoteSyncModel>()

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var lastNote: NoteModel? = null

    init {
        _state.value = _state.value.copy(
            selectedOrderType = setting.noteOrderType,
            selectedOrderSortType = setting.noteOrderSortType
        )
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
                setting.noteOrderType = event.orderType
                getNotes()
            }
            is NoteEvent.OnNoteOrderSortTypeChange -> {
                _state.value = _state.value.copy(selectedOrderSortType = event.sortType)
                setting.noteOrderSortType = event.sortType
                getNotes()
            }

            is NoteEvent.ListScrollDown -> {
                _state.value = _state.value.copy(fabOffset = (100).dp)
            }
            is NoteEvent.ListScrollUp -> {
                _state.value = _state.value.copy(fabOffset = (0).dp)
            }
            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    lastNote?.let { note ->
                        useCases.restoreNote(note)
                    }
                    lastNote = null
                }
            }
            is NoteEvent.ToggleOrderSectionVisibility -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible,
                    isSearchSectionVisible = false
                )
            }
            is NoteEvent.ToggleSearchSectionVisibility -> {
                _state.value = _state.value.copy(
                    isSearchSectionVisible = !_state.value.isSearchSectionVisible,
                    isOrderSectionVisible = false
                )
            }
            is NoteEvent.Search -> {
                _state.value = _state.value.copy(
                    search = event.search
                )
                getNotes()
            }
            NoteEvent.OnNoteSyncClicked -> {
                _state.value = _state.value.copy(
                    isSyncIconRotating = true
                )
                viewModelScope.launch {
                    useCases.syncNotes(notes = noteToSyncList)

                    _state.value = _state.value.copy(
                        isSyncIconRotating = false
                    )
                }

            }
        }
    }


    private fun getNotes() {
        job?.cancel()
        job = useCases.getNotes(
            orderType = _state.value.selectedOrderType,
            sortType = _state.value.selectedOrderSortType,
            keyword = _state.value.search
        ).onEach {
            it?.let { notes ->
                _state.value = _state.value.copy(
                    notes = notes
                )
            }
            getSyncNotes()
        }.launchIn(viewModelScope)
    }


    private fun getSyncNotes() {
        syncJob?.cancel()
        syncJob = useCases.getNotesToSync().onEach {
            _state.value = _state.value.copy(
                syncNumber = it?.size ?: 0,
                syncIcon = if (it != null && it.isNotEmpty()) Icons.Default.Sync else Icons.Default.SyncDisabled
            )
            it?.let { notes ->
                noteToSyncList.addAll(notes)
            }
        }.launchIn(viewModelScope)

    }


    override fun onCleared() {
        job?.cancel()
        syncJob?.cancel()
        super.onCleared()
    }
}