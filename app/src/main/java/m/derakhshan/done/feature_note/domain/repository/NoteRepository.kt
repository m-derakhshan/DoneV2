package m.derakhshan.done.feature_note.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType

interface NoteRepository {
    fun getNotes(orderType: NoteOrderType, sortType: NoteOrderSortType): Flow<List<NoteModel>>
    suspend fun deleteNote(note: NoteModel)
    suspend fun restoreNote(note: NoteModel)
}