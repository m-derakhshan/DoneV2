package m.derakhshan.done.feature_note.presentation

import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType

data class NoteState(
    val isOrderSectionVisible: Boolean = true,
    val selectedOrderType: NoteOrderType = NoteOrderType.Date,
    val selectedOrderSortType: NoteOrderSortType = NoteOrderSortType.Ascending
)
