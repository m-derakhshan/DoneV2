package m.derakhshan.done.feature_note.presentation.note_screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.ui.graphics.vector.ImageVector
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
    val search: String = "",
    val syncIcon: ImageVector = Icons.Default.Sync,
    val isSyncIconRotating: Boolean = false,
    val syncNumber: Int = 0
)
