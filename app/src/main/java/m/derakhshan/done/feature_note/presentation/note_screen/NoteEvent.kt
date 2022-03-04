package m.derakhshan.done.feature_note.presentation.note_screen

import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType

sealed class NoteEvent {

    data class OnNoteOrderTypeChange(val orderType: NoteOrderType) : NoteEvent()
    data class OnNoteOrderSortTypeChange(val sortType: NoteOrderSortType) : NoteEvent()
    data class OnDeleteNoteClicked(val note: NoteModel) : NoteEvent()
    object ToggleOrderSectionVisibility : NoteEvent()
    object RestoreNote : NoteEvent()
    object ListScrollUp : NoteEvent()
    object ListScrollDown : NoteEvent()
}
