package m.derakhshan.done.feature_note.presentation

import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType

sealed class NoteEvent {

    data class OnNoteOrderTypeChange(val orderType: NoteOrderType) : NoteEvent()
    data class OnNoteOrderSortTypeChange(val sortType: NoteOrderSortType) : NoteEvent()
    object OnDeleteNoteClicked : NoteEvent()
    object ToggleOrderSectionVisibility : NoteEvent()
}
