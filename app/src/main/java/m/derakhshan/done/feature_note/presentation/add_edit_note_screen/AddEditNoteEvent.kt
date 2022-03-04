package m.derakhshan.done.feature_note.presentation.add_edit_note_screen

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    object Save : AddEditNoteEvent()
    data class ChangeTitle(val title: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeContent(val content: String) : AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()
}
