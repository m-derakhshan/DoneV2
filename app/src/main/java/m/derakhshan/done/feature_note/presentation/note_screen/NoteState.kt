package m.derakhshan.done.feature_note.presentation.note_screen

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType

data class NoteState(
    val notes: List<NoteModel> = emptyList(),
    val isOrderSectionVisible: Boolean = false,
    val isSearchSectionVisible: Boolean = false,
    val fabOffset: Dp = 0.dp,
    val selectedOrderSortType: NoteOrderSortType = NoteOrderSortType.Ascending,
    val selectedOrderType: NoteOrderType = NoteOrderType.Date,
    val search: String = ""
)
