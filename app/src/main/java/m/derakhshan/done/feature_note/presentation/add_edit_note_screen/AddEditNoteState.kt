package m.derakhshan.done.feature_note.presentation.add_edit_note_screen

import androidx.compose.ui.graphics.toArgb
import m.derakhshan.done.feature_note.domain.model.NoteModel

data class AddEditNoteState(
    val title: NoteTextFieldState = NoteTextFieldState(hint = "Enter title..."),
    val content: NoteTextFieldState = NoteTextFieldState(hint = "Enter content..."),
    val color: Int = NoteModel.colors.random().toArgb()
)

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
