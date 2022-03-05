package m.derakhshan.done.feature_note.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType

interface NoteRepository {
    fun getNotes(
        orderType: NoteOrderType,
        sortType: NoteOrderSortType,
        keyword: String
    ): Flow<List<NoteModel>?>

    suspend fun deleteNote(note: NoteModel)
    suspend fun restoreNote(note: NoteModel)
    suspend fun insertNote(note: NoteModel)
    suspend fun insertNote(notes: List<NoteModel>)
    suspend fun getNoteById(noteId: Int): NoteModel?
}