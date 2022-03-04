package m.derakhshan.done.feature_note.presentation.add_edit_note_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import m.derakhshan.done.core.data.data_source.Setting
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.use_case.AddEditNoteUseCases
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val useCases: AddEditNoteUseCases,
    private val setting: Setting,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _snackBar = MutableSharedFlow<String>()
    val snackBar = _snackBar.asSharedFlow()

    private val _state = mutableStateOf(AddEditNoteState())
    val state: State<AddEditNoteState> = _state

    private var noteId: Int? = null

    init {
        noteId = savedStateHandle.get<Int>("noteId")
        noteId?.let { id ->
            viewModelScope.launch {
                useCases.getNoteById(noteId = id)?.let { note ->
                    _state.value = _state.value.copy(
                        title = NoteTextFieldState(
                            text = note.title,
                            isHintVisible = false,
                            hint = "Title..."
                        ),
                        content = NoteTextFieldState(
                            text = note.content,
                            isHintVisible = false,
                            hint = "Content..."
                        ),
                        color = note.color
                    )
                }
            }
        }
    }


    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _state.value = _state.value.copy(
                    color = event.color
                )
            }
            is AddEditNoteEvent.ChangeContent -> {
                _state.value = _state.value.copy(
                    content = _state.value.content.copy(
                        text = event.content
                    )
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _state.value = _state.value.copy(
                    content = _state.value.content.copy(
                        isHintVisible = !event.focusState.isFocused && _state.value.content.text.isBlank()
                    )
                )
            }
            is AddEditNoteEvent.ChangeTitle -> {
                _state.value = _state.value.copy(
                    title = _state.value.title.copy(
                        text = event.title
                    )
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _state.value = _state.value.copy(
                    title = _state.value.title.copy(
                        isHintVisible = !event.focusState.isFocused && _state.value.title.text.isBlank()
                    )
                )
            }
            AddEditNoteEvent.Save -> {
                viewModelScope.launch {
                    try {
                        val newNoteId =
                            if (noteId != null && noteId!! >= 0) noteId
                            else setting.lastNoteId
                        useCases.insertNoteUseCase(
                            NoteModel(
                                id = newNoteId!!,
                                title = _state.value.title.text,
                                content = _state.value.content.text,
                                timestamp = System.currentTimeMillis(),
                                color = _state.value.color
                            )
                        )
                        _snackBar.emit(if (noteId != null && noteId!! >= 0) "Note updated successfully." else "Note inserted successfully.")
                        noteId = newNoteId
                    } catch (e: Exception) {
                        if (e is CancellationException)
                            throw e
                        _snackBar.emit(e.message ?: "Unknown error!")
                    }
                }
            }
        }
    }
}