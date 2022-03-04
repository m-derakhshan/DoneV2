package m.derakhshan.done.feature_note.presentation.add_edit_note_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import m.derakhshan.done.core.data.data_source.Setting
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.use_case.AddEditNoteUseCases
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val useCases: AddEditNoteUseCases,
    private val setting: Setting
) : ViewModel() {

    private val _state = mutableStateOf(AddEditNoteState())
    val state: State<AddEditNoteState> = _state

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
                    useCases.insertNoteUseCase(
                        NoteModel(
                            id = setting.lastNoteId,
                            title = _state.value.title.text,
                            content = _state.value.content.text,
                            timestamp = System.currentTimeMillis(),
                            color = _state.value.color
                        )
                    )
                }
            }
        }
    }
}